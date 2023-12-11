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
import utilities.DataUtil;

public class Scenario2_TestNoOfItemsAndSort extends BaseTest {
	
	Properties config = new Properties();
	FileInputStream fis;

	@Test(dataProviderClass = DataUtil.class, dataProvider = "dp1")
	public void itemsCountAndSorting(String browser, String username, String password, String expProdPageTitle,
			String sortType) {

		setUp(browser);

		LoginPage loginPage = new LoginPage(driver);
		ExtentListeners.test.log(Status.INFO, "Login by user: " + username);
		ProductsPage prodPage = loginPage.goToProductsPageByLogin(username, password);
		String actProdPageTitle = prodPage.getProductPageTitle();

		int actItemCount = prodPage.countItemsOnProductsPage();

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
		
		int expItemCount = Integer.parseInt(config.getProperty("expItemCount"));

		try {

			// Verify login to the application
			Assert.assertEquals(actProdPageTitle, expProdPageTitle);
			log.info("Login successful by username: " + username);
			ExtentListeners.test.log(Status.INFO, "Products Page title matches. Expected title: " + expProdPageTitle
					+ " Actual title: " + actProdPageTitle);
			ExtentListeners.test.log(Status.INFO, "Login successful");

			// Verify number of items on the page
			Assert.assertEquals(actItemCount, expItemCount);
			log.info("Product items count matched.");
			ExtentListeners.test.log(Status.INFO,
					"Product items count matched. Expected: " + expItemCount + " Actual: " + actItemCount);

		} catch (Throwable t) {

			ExtentListeners.test.log(Status.FAIL, t.getMessage());
			Assert.fail();
		}

		// Click on the dropdown and select Price (low to high)
		prodPage.sortItems(sortType);

		String firstItemPrice = prodPage.firstItemPrice();
		String lastItemPrice = prodPage.lastItemPrice();
		String lowestPrice = prodPage.lowestPriceInItemsList();
		String highestPrice = prodPage.highestPriceInItemsList();

		try {
			// Verify first item has the lowest price
			Assert.assertEquals(firstItemPrice, lowestPrice);
			log.info("First item has the lowest price " + lowestPrice);
			ExtentListeners.test.log(Status.INFO, "First item has the lowest price " + lowestPrice);

			// Verify last item has the highest price
			Assert.assertEquals(lastItemPrice, highestPrice);
			log.info("Last item has the highest price " + highestPrice);
			ExtentListeners.test.log(Status.INFO, "Last item has the highest price " + highestPrice);

		} catch (Throwable t) {

			ExtentListeners.test.log(Status.FAIL, t.getMessage());
			Assert.fail();
		}

	}

}
