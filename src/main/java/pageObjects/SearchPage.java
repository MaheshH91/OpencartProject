package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage {
	public SearchPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(linkText = "HP LP3065")
	private WebElement validHPProduct;

	@FindBy(xpath = "//div[@id='content']/h2/following-sibling::p")
	private WebElement noProductMessage;

	

	public String retrieveNoProductMessageText() {
		return noProductMessage.getText();
	}

	public boolean isHPValidProductDisplayed() {
		return validHPProduct.isDisplayed();
	}

}
