package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import extentlisteners.ExtentListeners;
import pages.LoginPage;
import utilities.DataUtil;

public class Scenario1_LoginTestWithInvalidCredentials extends BaseTest {

	@Test(dataProviderClass = DataUtil.class, dataProvider = "dp1")
	public void loginNonWorkingCredentials(String browser, String username, String password, String expLoginLogo,
			String expErrorMessage) {

		setUp(browser);

		LoginPage loginPage = new LoginPage(driver);
		ExtentListeners.test.log(Status.INFO, "Login attempt by user: " + username);
		loginPage.goToProductsPageByLogin(username, password);

		String actErrorMessage = loginPage.getLoginFailureErrorMessage();
		String actLoginLogo = loginPage.getLoginLogo();

		try {
			// Verify login failure for a locked user as expected
			Assert.assertEquals(actErrorMessage, expErrorMessage);
			Assert.assertEquals(actLoginLogo, expLoginLogo);
			log.info("Login attempt by user: " + username + " failed as expected with error - " + actErrorMessage);
			ExtentListeners.test.log(Status.INFO,
					"Login attempt by user: " + username + " failed as expected with error - " + actErrorMessage);

		} catch (Throwable t) {

			ExtentListeners.test.log(Status.FAIL, t.getMessage());
			Assert.fail();
		}
	}

}
