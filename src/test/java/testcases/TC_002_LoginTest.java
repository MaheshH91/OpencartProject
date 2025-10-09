package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass {

	@Test(groups = {"Sanity","Master"})
	public void verify_Login() {
		try {
			logger.info("*********** Starting TC_002_LoginTest **********");

			HomePage hp = new HomePage(driver);
			hp.clickOnLogin();

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("validEmail"));
			lp.setPassword(p.getProperty("validPassword"));
			lp.clickLogin();

			MyAccountPage macc = new MyAccountPage(driver);

//			boolean target = macc.isMyAccountPageIsExist();
			boolean target = macc.isEditYourAccountInfoLinkDisplayed();
//			System.out.println(target);
//			Assert.assertTrue(target);
			Assert.assertTrue(target,
					"Edit your account information is not displayed.");
		} catch (Exception e) {

			Assert.fail();
		}

		logger.info("********* Finished TC_002_LoginTest ***********");

	}

}
