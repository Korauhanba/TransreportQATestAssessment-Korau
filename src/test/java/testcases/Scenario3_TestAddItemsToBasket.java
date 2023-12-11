package testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import extentlisteners.ExtentListeners;
import pages.LoginPage;
import pages.ProductsPage;
import pages.YourCartPage;
import utilities.DataUtil;

public class Scenario3_TestAddItemsToBasket extends BaseTest {

	Properties config = new Properties();
	FileInputStream fis;

	@Test(dataProviderClass = DataUtil.class, dataProvider = "dp1")
	public void addItemsToBasket(String browser, String username, String password) {

		setUp(browser);

		LoginPage loginPage = new LoginPage(driver);
		ExtentListeners.test.log(Status.INFO, "Login by user: " + username);
		ProductsPage prodPage = loginPage.goToProductsPageByLogin(username, password);

		try {
			fis = new FileInputStream("./src/test/resources/properties/Config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			config.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Get the number of items to be removed from Config.properties file
		int itemsToBeAdded = Integer.parseInt(config.getProperty("itemsToBeAdded"));

		// Add items to cart
		prodPage.addItemstoBasket(itemsToBeAdded);
		ExtentListeners.test.log(Status.INFO, "Items added to cart: " + itemsToBeAdded);
		YourCartPage yourCartPage = prodPage.goToYourCartPage();
		int cartCount = yourCartPage.shopCartCount();
		ExtentListeners.test.log(Status.INFO, "Cart count: " + cartCount);
		
		// Validate that the number of items added match with the number displayed on the Cart
		try {
			Assert.assertEquals(itemsToBeAdded, cartCount);
			ExtentListeners.test.log(Status.INFO, "Items added and Cart count matches.");
		} catch (Throwable t) {
			ExtentListeners.test.log(Status.FAIL, t.getMessage());
			Assert.fail();
		}

		// Remove 1 item from Cart
		yourCartPage.removeOneItemFromCart();
		ExtentListeners.test.log(Status.INFO, "1 item removed from Cart");
		cartCount = yourCartPage.shopCartCount();

		int expNewItemCount = itemsToBeAdded - 1;
		
		// Validate that the Cart count is reduced by 1 after removing one item from the cart
		try {
			Assert.assertEquals(expNewItemCount, cartCount);
			ExtentListeners.test.log(Status.INFO, "Cart count after removing 1 item: " + cartCount);
		} catch (Throwable t) {
			ExtentListeners.test.log(Status.FAIL, t.getMessage());
			Assert.fail();
		}

	}

}
