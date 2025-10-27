package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verify_Login() {
        logger.info("*********** Starting TC_002_LoginTest **********");

        try {
            // Create Home Page instance
            HomePage hp = new HomePage(getDriver());
            hp.clickOnLogin();
            logger.info("Clicked on Login link");

            // Login Page
            LoginPage lp = new LoginPage(getDriver());
            lp.setEmail(p.getProperty("validEmail"));
            lp.setPassword(p.getProperty("validPassword"));
            lp.clickLogin();
            logger.info("Entered login credentials");

            // My Account Page
            MyAccountPage macc = new MyAccountPage(getDriver());
            boolean target = macc.isEditYourAccountInfoLinkDisplayed();

            Assert.assertTrue(target, "Edit your account information link not displayed.");
            logger.info("Login verification successful - My Account Page displayed ✅");

        } catch (Exception e) {
            logger.error("Login test failed due to: {}", e.getMessage());
            Assert.fail("Login test failed unexpectedly");
        }

        logger.info("*********** Finished TC_002_LoginTest **********");
    }
}
