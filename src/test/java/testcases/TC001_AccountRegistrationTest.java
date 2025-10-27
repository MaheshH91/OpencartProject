package testcases;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegisterationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verify_Account_Registration() {
        logger.info("***** Starting TC001_AccountRegistrationTest *****");
        logger.debug("Debug: Beginning account registration flow");

        try {
            // Initialize page objects
            HomePage homePage = new HomePage(getDriver());
            homePage.clickOnRegister();
            logger.info("Clicked on Register link");

            AccountRegisterationPage regPage = new AccountRegisterationPage(getDriver());
            logger.info("Filling registration details...");

            regPage.setFirstName(randomString().toUpperCase());
            regPage.setLastName(randomString().toUpperCase());
            regPage.setEmail(randomString() + "@gmail.com");
            regPage.setTelephone(randomNumber());

            String password = randomeAlphaNumberic();
            regPage.setPassord(password);
            regPage.setConfirmPassord(password);
            regPage.setPrivacyPolicy();
            regPage.clickContinue();

            logger.info("Validating registration success message...");
            String confirmation = regPage.getConfirmationMsg();

            Assert.assertEquals(confirmation, "Your Account Has Been Created!",
                    "❌ Registration failed: confirmation message mismatch.");

            logger.info("✅ Registration successful, test passed!");

        } catch (Exception e) {
            logger.error("❌ Exception during test execution: {}", e.getMessage());
            Assert.fail("Test failed due to unexpected error: " + e.getMessage());
        } finally {
            logger.info("***** Finished TC001_AccountRegistrationTest *****");
        }
    }

	private String randomeAlphaNumberic() {
		 return RandomStringUtils.randomAlphabetic(3)
		            + "@" +
		           RandomStringUtils.randomNumeric(3);
	}
}
