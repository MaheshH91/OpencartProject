package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(@Optional("windows") String os, @Optional("chrome") String br) throws IOException {

        // Load config.properties
        String configPath = System.getProperty("user.dir") + File.separator + "src" + File.separator
                + "test" + File.separator + "resources" + File.separator + "config.properties";
        FileReader file = new FileReader(configPath);
        p = new Properties();
        p.load(file);
        file.close();

        logger = LogManager.getLogger(this.getClass());
        logger.info("===== Starting Test Setup =====");
        logger.info("Execution Environment: {}", p.getProperty("execution_env"));
        logger.info("OS: {}, Browser: {}", os, br);

        String execEnv = p.getProperty("execution_env", "local").toLowerCase();

        if (execEnv.equals("remote")) {
            // -------- Remote Execution --------
            String gridUrl = p.getProperty("grid.url", "http://localhost:4444/wd/hub");
            logger.info("Running on Selenium Grid: {}", gridUrl);

            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    setPlatform(os, chromeOptions);
                    chromeOptions.addArguments("--start-maximized", "--disable-notifications", "--disable-popup-blocking");
                    chromeOptions.setCapability("se:name", "Remote Chrome Test - " + os);
                    driver = new RemoteWebDriver(new URL(gridUrl), chromeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    setPlatform(os, firefoxOptions);
                    firefoxOptions.setCapability("se:name", "Remote Firefox Test - " + os);
                    driver = new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    setPlatform(os, edgeOptions);
                    edgeOptions.setCapability("se:name", "Remote Edge Test - " + os);
                    driver = new RemoteWebDriver(new URL(gridUrl), edgeOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser for remote execution: " + br);
            }
            logger.info("Connected to Selenium Grid successfully.");

        } else if (execEnv.equals("local")) {
            // -------- Local Execution --------
            logger.info("Running locally on {}", br);
            boolean isHeadless = Boolean.parseBoolean(p.getProperty("headless", "false"));

            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized", "--disable-notifications");
                    if (isHeadless) chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) edgeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser for local execution: " + br);
            }
            logger.info("Local browser initialized successfully.");
        } else {
            throw new IllegalArgumentException("Invalid execution_env value in config.properties: " + execEnv);
        }

        // -------- Common Setup --------
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        String appUrl = p.getProperty("appURL2", "https://example.com");
        driver.get(appUrl);
        driver.manage().window().maximize();

        logger.info("Navigated to: {}", appUrl);
        logger.info("===== Test Setup Completed =====");
    }

    private void setPlatform(String os, Object options) {
        Platform platform;
        switch (os.toLowerCase()) {
            case "windows":
                platform = Platform.WINDOWS;
                break;
            case "linux":
                platform = Platform.LINUX;
                break;
            case "mac":
                platform = Platform.MAC;
                break;
            default:
                throw new IllegalArgumentException("Unsupported OS: " + os);
        }
        if (options instanceof ChromeOptions)
            ((ChromeOptions) options).setCapability("platformName", platform);
        else if (options instanceof FirefoxOptions)
            ((FirefoxOptions) options).setCapability("platformName", platform);
        else if (options instanceof EdgeOptions)
            ((EdgeOptions) options).setCapability("platformName", platform);
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing browser...");
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }

    // -------- Utility Methods --------
    public String randomeString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomeNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomeAlphaNumberic() {
        return RandomStringUtils.randomAlphabetic(3) + "@" + RandomStringUtils.randomNumeric(3);
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetDir = System.getProperty("user.dir") + File.separator + "screenshots";
        File targetFile = new File(targetDir, tname + "_" + timeStamp + ".png");
        targetFile.getParentFile().mkdirs();

        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        logger.info("Screenshot captured: {}", targetFile.getAbsolutePath());
        return targetFile.getAbsolutePath();
    }
}
