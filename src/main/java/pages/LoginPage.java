package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.Status;

import base.BasePage;
import extentlisteners.ExtentListeners;

public class LoginPage extends BasePage {
	
	public LoginPage(WebDriver driver) {
		
		super(driver);
	}
	
	@FindBy(id = "user-name")
	private WebElement usernameField;

	@FindBy(xpath = "//*[@id='password']")
	private WebElement passwordField;
	
	@FindBy(id = "login-button")
	private WebElement loginBtn;
	
	@FindBy(xpath = "//*[@class='login_logo']")
	private WebElement loginLogo;
	
	@FindBy(xpath = "//*[@data-test='error']")
	private WebElement loginFailureErrorMessage;
	
	// Method to get Login page title
	public String getLoginLogo() {
		
		return loginLogo.getText();
	}
	
	//Method to capture login failure message
	public String getLoginFailureErrorMessage() {
		
		return loginFailureErrorMessage.getText();
	}
	
	// Method to login and navigate to Products page
	public ProductsPage goToProductsPageByLogin(String username, String password) {
		
		try {
			
			usernameField.sendKeys(username);
			passwordField.sendKeys(password);
			loginBtn.click();
			
		} catch(Throwable t) {
			
			ExtentListeners.test.log(Status.FAIL, t.getMessage());
		}
		
		return new ProductsPage(driver);
	}
	
}
