package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class AccountRegisterationPage {
    WebDriver driver;

    @FindBy(id="input-firstname") WebElement txtFirstName;
    @FindBy(id="input-lastname") WebElement txtLastName;
    @FindBy(id="input-email") WebElement txtEmail;
    @FindBy(id="input-telephone") WebElement txtTelephone;
    @FindBy(id="input-password") WebElement txtPassword;
    @FindBy(id="input-confirm") WebElement txtConfirmPassword;
    @FindBy(name="agree") WebElement chkPrivacyPolicy;
    @FindBy(xpath="//input[@value='Continue']") WebElement btnContinue;
    @FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public AccountRegisterationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setFirstName(String fname) { txtFirstName.sendKeys(fname); }
    public void setLastName(String lname) { txtLastName.sendKeys(lname); }
    public void setEmail(String email) { txtEmail.sendKeys(email); }
    public void setTelephone(String tel) { txtTelephone.sendKeys(tel); }
    public void setPassord(String pwd) { txtPassword.sendKeys(pwd); }
    public void setConfirmPassord(String pwd) { txtConfirmPassword.sendKeys(pwd); }
    public void setPrivacyPolicy() { chkPrivacyPolicy.click(); }
    public void clickContinue() { btnContinue.click(); }
    public String getConfirmationMsg() { return msgConfirmation.getText(); }
}
