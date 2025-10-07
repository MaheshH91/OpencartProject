package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegisterationPage extends BasePage {

    public AccountRegisterationPage(WebDriver driver) {
        super(driver);
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
        txtFirstname.sendKeys(fName);
    }
    
    public void setLastName(String lName) {
        txtLastname.sendKeys(lName);
    }
    
    public void setEmail(String emailId) {
        txtEmail.sendKeys(emailId);
    }
    
    public void setTelephone(String telNumber) {  // Fixed parameter name
        txtTelephone.sendKeys(telNumber);
    }
    
    public void setPassord(String pwd) {
        txtPassword.sendKeys(pwd);
    }
    
    public void setConfirmPassord(String pwd) {
        txtConfirmPassword.sendKeys(pwd);
    }
    
    public void setPrivacyPolicy() {
        chkPolicy.click();
    }
    
    public void clickContinue() {
        btnContinue.click();
    }
    
    public String getConfirmationMsg() {
        try {
            return msgConfirmation.getText();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}