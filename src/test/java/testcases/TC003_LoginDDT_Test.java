package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


public class TC003_LoginDDT_Test extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")
	public void verify_LoginDDT(String email, String pwd, String exp) {
		try {
			logger.info("*********** Starting TC003_LoginDDT_Test **********");

			HomePage hp = new HomePage(getDriver());
			hp.clickMyAccount();
			hp.clickLogin();

			LoginPage lp = new LoginPage(getDriver());
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			MyAccountPage macc = new MyAccountPage(getDriver());

			boolean target = macc.isMyAccountPageIsExist();
			//System.out.println(target);
			/*
			 * Data is valid - login success - test pass login failed - test fail
			 * 
			 * Data is invalid - login success - test fail - logout login failed - test pass
			 */
			if(exp.equalsIgnoreCase("Valid"))
			{
				if (target == true) {
							
					macc.clickLogout();
					Assert.assertTrue(true);
					
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(target==true)
				{
					macc.clickLogout();
					Assert.assertTrue(false);
					
				}
				else
				{
					Assert.assertTrue(true);
				}
			}

		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("*********** Finished TC003_LoginDDT_Test **********");
	}
}
