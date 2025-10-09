package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegisterationPage extends BasePage {
	private WebDriverWait wait;

    public AccountRegisterationPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    private WebElement txtFirstname;

    @FindBy(xpath = "//input[@id='input-lastname']")	
    private WebElement txtLastname;  
    
    @FindBy(xpath = "//input[@id='input-email']")
    private WebElement txtEmail;
    
    @FindBy(xpath = "//input[@id='input-telephone']")
    private WebElement txtTelephone;

    @FindBy(xpath = "//input[@id='input-password']")
    private WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    private WebElement txtConfirmPassword;
    
    @FindBy(xpath = "//input[@name= 'agree']")
    private WebElement chkPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    private WebElement msgConfirmation;

    // REMOVED DUPLICATE METHODS - Keep only one set
    public void setFirstName(String fName) {
        wait.until(ExpectedConditions.visibilityOf(txtFirstname)).sendKeys(fName);
    }

    public void setLastName(String lName) {
        wait.until(ExpectedConditions.visibilityOf(txtLastname)).sendKeys(lName);
    }

    public void setEmail(String emailId) {
        wait.until(ExpectedConditions.visibilityOf(txtEmail)).sendKeys(emailId);
    }

    public void setTelephone(String telNumber) {
        wait.until(ExpectedConditions.visibilityOf(txtTelephone)).sendKeys(telNumber);
    }

    public void setPassord(String pwd) {
        wait.until(ExpectedConditions.visibilityOf(txtPassword)).sendKeys(pwd);
    }

    public void setConfirmPassord(String pwd) {
        wait.until(ExpectedConditions.visibilityOf(txtConfirmPassword)).sendKeys(pwd);
    }

    public void setPrivacyPolicy() {
        wait.until(ExpectedConditions.elementToBeClickable(chkPolicy)).click();
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
    }

    public String getConfirmationMsg() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(msgConfirmation)).getText();
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}