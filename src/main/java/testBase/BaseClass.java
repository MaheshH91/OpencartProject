package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
        FileReader file = new FileReader("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);
        file.close();

        logger = LogManager.getLogger(this.getClass());
        logger.info("===== Starting Test Setup =====");
        logger.info("Execution Environment: " + p.getProperty("execution_env"));
        logger.info("OS: " + os + ", Browser: " + br);

        if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
            // Remote execution (Selenium Grid)
            String gridUrl = p.getProperty("grid.url", "http://localhost:4444");
            logger.info("Grid URL: " + gridUrl);

            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    
                    // Set platform using proper method
                    if (os.equalsIgnoreCase("windows")) {
                        chromeOptions.setCapability("platformName", Platform.WINDOWS);
                    } else if (os.equalsIgnoreCase("linux")) {
                        chromeOptions.setCapability("platformName", Platform.LINUX);
                    } else if (os.equalsIgnoreCase("mac")) {
                        chromeOptions.setCapability("platformName", Platform.MAC);
                    } else {
                        logger.error("Unsupported OS: " + os);
                        throw new IllegalArgumentException("Invalid OS: " + os);
                    }
                    
                    // Chrome-specific options
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.setCapability("se:name", "Remote Chrome Test - " + os);
                    
                    logger.info("Attempting to connect to Chrome on Grid...");
                    driver = new RemoteWebDriver(new URL(gridUrl), chromeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    
                    if (os.equalsIgnoreCase("windows")) {
                        firefoxOptions.setCapability("platformName", Platform.WINDOWS);
                    } else if (os.equalsIgnoreCase("linux")) {
                        firefoxOptions.setCapability("platformName", Platform.LINUX);
                    } else if (os.equalsIgnoreCase("mac")) {
                        firefoxOptions.setCapability("platformName", Platform.MAC);
                    } else {
                        logger.error("Unsupported OS: " + os);
                        throw new IllegalArgumentException("Invalid OS: " + os);
                    }
                    
                    firefoxOptions.setCapability("se:name", "Remote Firefox Test - " + os);
                    
                    logger.info("Attempting to connect to Firefox on Grid...");
                    driver = new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    
                    if (os.equalsIgnoreCase("windows")) {
                        edgeOptions.setCapability("platformName", Platform.WINDOWS);
                    } else if (os.equalsIgnoreCase("linux")) {
                        edgeOptions.setCapability("platformName", Platform.LINUX);
                    } else if (os.equalsIgnoreCase("mac")) {
                        edgeOptions.setCapability("platformName", Platform.MAC);
                    } else {
                        logger.error("Unsupported OS: " + os);
                        throw new IllegalArgumentException("Invalid OS: " + os);
                    }
                    
                    edgeOptions.setCapability("se:name", "Remote Edge Test - " + os);
                    
                    logger.info("Attempting to connect to Edge on Grid...");
                    driver = new RemoteWebDriver(new URL(gridUrl), edgeOptions);
                    break;

                default:
                    logger.error("No matching browser for remote execution: " + br);
                    throw new IllegalArgumentException("Unsupported browser: " + br);
            }
            
            logger.info("Successfully connected to Selenium Grid");
        }
        else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
            // Local execution
            logger.info("Starting local browser execution...");
            
            switch (br.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                    
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                    
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    break;
                    
                default:
                    logger.error("Invalid browser name for local execution: " + br);
                    throw new IllegalArgumentException("Unsupported browser: " + br);
            }
            
            logger.info("Local browser started successfully");
        } else {
            logger.error("Invalid execution_env in config.properties: " + p.getProperty("execution_env"));
            throw new IllegalArgumentException("execution_env must be 'local' or 'remote'");
        }

        // Common setup for both local and remote
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        String appUrl = p.getProperty("appURL2");
        logger.info("Navigating to: " + appUrl);
        driver.get(appUrl);
        driver.manage().window().maximize();
        
        logger.info("===== Test Setup Completed =====");
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing browser...");
            driver.quit();
            logger.info("Browser closed successfully");
        }
    }

    public String randomeString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomeNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomeAlphaNumberic() {
        String generatedString = RandomStringUtils.randomAlphabetic(3);
        String generatedNumber = RandomStringUtils.randomNumeric(3);
        return generatedString + "@" + generatedNumber;
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        
        // Create screenshots directory if it doesn't exist
        targetFile.getParentFile().mkdirs();
        
        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}