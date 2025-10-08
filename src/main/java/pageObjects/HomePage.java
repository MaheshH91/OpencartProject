package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	private WebElement lnkMyAccount;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	private WebElement lnkRegister;

	@FindBy(linkText = "Login")
	private WebElement linkLogin;

	public void clickOnRegister() {
		lnkMyAccount.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(lnkRegister));
		lnkRegister.click();
	}

	public void clickMyAccount() {
		
		lnkMyAccount.click();
	}
public void clickLogin() {

	linkLogin.click();
}
	public void clickOnLogin() {
		lnkMyAccount.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(linkLogin));
		linkLogin.click();
	}
}
