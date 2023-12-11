package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import extentlisteners.ExtentListeners;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.DataUtil;

public class Scenario1_SuccessfulLoginTest extends BaseTest {
	
	@Test(dataProviderClass = DataUtil.class, dataProvider = "dp1")
	public void loginWorkingCredentials(String browser, String username, String password, String expProdPageTitle) {
		
		setUp(browser);
		
		LoginPage loginPage = new LoginPage(driver);
		ExtentListeners.test.log(Status.INFO, "Login attempt by user: " +username);
		ProductsPage prodPage = loginPage.goToProductsPageByLogin(username, password);
		String actProdPageTitle = prodPage.getProductPageTitle();
		
		try {
			//Verify login to the application with different valid users
			Assert.assertEquals(actProdPageTitle, expProdPageTitle);
			log.info("Login successful by username: " +username);
			ExtentListeners.test.log(Status.INFO, "Products Page title matches. Expected title: " +expProdPageTitle+ " Actual title: " +actProdPageTitle);
			ExtentListeners.test.log(Status.INFO, "Login successful");
			
		} catch (Throwable t) {
			
			ExtentListeners.test.log(Status.FAIL, t.getMessage());
			Assert.fail();
		}
	}

}
