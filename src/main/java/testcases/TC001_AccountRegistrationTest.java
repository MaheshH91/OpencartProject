package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegisterationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = {"Regression","Master"})
	void verify_Account_Registration() {

		logger.info("***** Starting TC001_AccountRegistrationTest  ****");
		logger.debug("This is a debug log message");
		try {
			// Initialize page objects WITH driver
			HomePage homePage = new HomePage(driver);
			homePage.clickOnRegister();
			logger.info("Clicked on Register Link.. ");

			// Initialize registration page WITH driver
			AccountRegisterationPage regPage = new AccountRegisterationPage(driver);
			logger.info("Providing customer details...");
			regPage.setFirstName(randomeString().toUpperCase());
			regPage.setLastName(randomeString().toUpperCase());
			regPage.setEmail(randomeString() + "@gmail.com");
			regPage.setTelephone(randomeNumber());

			String password = randomeAlphaNumberic();
			regPage.setPassord(password);
			regPage.setConfirmPassord(password);
			regPage.setPrivacyPolicy();
			regPage.clickContinue();

			logger.info("Validating expected message..");
			String confMsg = regPage.getConfirmationMsg();
			Assert.assertEquals(confMsg, "Your Account Has Been Created!");

			logger.info("Test passed");
		} catch (Exception e) {
			logger.error("Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		} finally {
			logger.info("***** Finished TC001_AccountRegistrationTest *****");
		}
	}
}
