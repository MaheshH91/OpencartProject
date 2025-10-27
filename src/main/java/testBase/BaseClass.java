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
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import factory.DriverFactory;

public class BaseClass {

    public static Logger logger = LogManager.getLogger(BaseClass.class);
    public static Properties p;
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    @Parameters({"os", "browser"})
    public void setup(@Optional("windows") String os, @Optional("chrome") String browser) throws IOException {

        // Load config.properties
        String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        p = new Properties();
        p.load(new FileReader(configPath));

        String execEnv = p.getProperty("execution_env").toLowerCase();

        logger.info("========== Test Setup Started ==========");
        logger.info("Execution Environment: {}", execEnv);
        logger.info("OS: {}, Browser: {}", os, browser);

        if (execEnv.equals("remote")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setPlatform(Platform.WIN11);
            caps.setBrowserName(browser);
            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps));
        } else {
            driver.set(DriverFactory.initDriver(browser));
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        getDriver().get(p.getProperty("appURL2"));
        logger.info("Navigated to: {}", p.getProperty("appURL2"));
        logger.info("========== Setup Completed ==========");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            logger.info("Closing browser...");
            getDriver().quit();
            logger.info("Browser closed successfully.");
        }
    }

    // Thread-safe getter
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Utilities
    public String randomString() { return RandomStringUtils.randomAlphabetic(5); }
    public String randomNumber() { return RandomStringUtils.randomNumeric(6); }

    public String captureScreen(String testName) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";
        Files.copy(src.toPath(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);
        logger.info("Screenshot captured: {}", path);
        return path;
    }
}
