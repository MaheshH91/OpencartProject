package testcases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;
import factory.DriverFactory;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

import java.time.Duration;

public class TC_002_LoginTest extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verify_Login() {
        
        logger.info("*********** Starting TC_002_LoginTest **********");

        try {

            // Home Page
            HomePage hp = new HomePage(getDriver());
            logger.info("Opening Login page...");
            hp.clickOnLogin();

            // Login Page
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail(p.getProperty("validEmail"));
            lp.setPassword(p.getProperty("validPassword"));
            lp.clickLogin();
            logger.info("Entered login credentials");

            // Wait for Account Page to load
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleContains("My Account"));

            // My Account Page
            MyAccountPage macc = new MyAccountPage(getDriver());
            boolean target = macc.isEditYourAccountInfoLinkDisplayed();

            Assert.assertTrue(target, "❌ Edit your account information link NOT displayed.");
            logger.info("✅ Login successful, My Account page visible");

        } catch (Exception e) {
            logger.error("❌ Login test failed", e);

            try {
                String path = captureScreen("TC_002_LoginTest");
                logger.info("📸 Screenshot saved at: " + path);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot", ex);
            }

            Assert.fail("Login test failed unexpectedly: " + e.getMessage());
        }

        logger.info("*********** Finished TC_002_LoginTest **********");
    }
}
