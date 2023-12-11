package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import base.BasePage;

public class ProductsPage extends BasePage {

	public ProductsPage(WebDriver driver) {

		super(driver);
	}

	@FindBy(xpath = "//*[@class='title']")
	private WebElement productPageTitle;

	@FindBy(xpath = "//*[@class='inventory_item']")
	private List<WebElement> noOfItems;

	@FindBy(xpath = "//*[@class='product_sort_container']")
	private WebElement sortDropdown;

	@FindBy(xpath = "//*[@class='inventory_item_price']")
	private List<WebElement> inventoryItemPrices;
	
	@FindBy(xpath = "//*[@class='shopping_cart_link']")
	private WebElement shoppingCart;
	
	@FindBy(xpath = "//button[@class='btn btn_primary btn_small btn_inventory ']")
	List<WebElement> addToCartBtn;
	
	// Method to click on cart icon and navigate to YourCart page
	public YourCartPage goToYourCartPage() {
		
		shoppingCart.click();
		return new YourCartPage(driver);
		
	}

	// Method to get the Product page title
	public String getProductPageTitle() {

		return productPageTitle.getText();

	}

	// Method to count items on Products page
	public int countItemsOnProductsPage() {

		return noOfItems.size();

	}

	// Method to click on Sort dropdown and select the type of sorting
	public void sortItems(String sortType) {

		Select select = new Select(sortDropdown);
		select.selectByVisibleText(sortType);

	}

	// Method to find the price of the first item in the Products page
	public String firstItemPrice() {

		return inventoryItemPrices.get(0).getText().replace("$", "");

	}

	// Method to find the price of the last item in the Products page
	public String lastItemPrice() {

		return inventoryItemPrices.get(inventoryItemPrices.size() - 1).getText().replace("$", "");

	}

	// Method to find the lowest price of all the items in the Products page
	public String lowestPriceInItemsList() {

		List<Float> minMaxList = new ArrayList<Float>();

		for (int i = 0; i < inventoryItemPrices.size(); i++) {
			minMaxList.add(Float.parseFloat(inventoryItemPrices.get(i).getText().replace("$", "")));
		}

		Float minValue = Collections.min(minMaxList);

		return minValue.toString();

	}

	// Method to find the highest price of all the items in the Products page
	public String highestPriceInItemsList() {

		List<Float> minMaxList = new ArrayList<Float>();

		for (int i = 0; i < inventoryItemPrices.size(); i++) {
			minMaxList.add(Float.parseFloat(inventoryItemPrices.get(i).getText().replace("$", "")));
		}

		Float maxValue = Collections.max(minMaxList);

		return maxValue.toString();

	}
	
	// Method to add items to basket
	public void addItemstoBasket(int itemsToBeAdded) {
		
		for (int i = 0; i < itemsToBeAdded; i++) {

			addToCartBtn.get(i).click();

		}
		
	}
	

}
