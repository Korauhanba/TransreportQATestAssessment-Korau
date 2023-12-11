package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BasePage;

public class YourCartPage extends BasePage {

	public YourCartPage(WebDriver driver) {

		super(driver);

	}

	@FindBy(xpath = "//*[@class='shopping_cart_link']")
	private WebElement shopCartNumWebElem;

	@FindBy(xpath = "(//*[@class='btn btn_secondary btn_small cart_button'])[1]")
	private WebElement removeBtn;

	// Method to count the item number displayed on shop cart
	public int shopCartCount() {

		return Integer.parseInt(shopCartNumWebElem.getText());

	}

	// Method to remove one item from the cart
	public void removeOneItemFromCart() {

		removeBtn.click();

	}

}
