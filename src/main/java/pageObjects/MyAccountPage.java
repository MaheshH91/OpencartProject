	package pageObjects;
	
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	
	
	public class MyAccountPage extends BasePage {
	
		public MyAccountPage(WebDriver driver) {
			super(driver);
		}
		@FindBy(linkText = "Edit your account information")
		private WebElement editYourAccountInfoLink;
		
		@FindBy(xpath = "//h2[text()='My Account']")
		private WebElement msgHeading;
		
		@FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Logout']")
		private WebElement lnkLogout;
		
		public boolean isMyAccountPageIsExist() {
			try {
				return (msgHeading.isDisplayed());	
			} catch (Exception e) {
				return false;
			}	
		}
		public boolean isElementDisplayed(WebElement element) {
			return element.isDisplayed();
		}
	
		
	
		public boolean isEditYourAccountInfoLinkDisplayed() {
			return isElementDisplayed(editYourAccountInfoLink);
		}
		
		public void clickLogout() {
			lnkLogout.click();
		}
		
	}
