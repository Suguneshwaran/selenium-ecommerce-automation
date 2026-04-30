package SuguneshMavenProjects.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import SuguneshMavenProjects.TestComponents.BaseTest;
import SuguneshMavenProjects.pageObjectModule.CartPage;
import SuguneshMavenProjects.pageObjectModule.ProductCataloguePage;

public class ErrorValidationTest extends BaseTest{

	@Test (groups = {"ErrorHandling"})
	public void loginOrderErrorValidation() throws IOException{
			
		// Checking what happens when we give wrong email and pass 
		landPage.loginApplication("suguneshthanospmm@gmail.com","Eage@5714");	
		Assert.assertEquals("Incorrect email or password.",landPage.getErrorMsg().trim());
	}
	
	@Test
	public void productErrorValidation()
	{
		// Checking what happens when we add one product and try to check for another product - just a negative test case
		String productName= "ZARA COAT 3";
		ProductCataloguePage productPage= landPage.loginApplication("suguneshthanos@gmail.com","Sample@5714");	
		List<WebElement> products = productPage.getProductList();
		productPage.addToCart(productName);
		productPage.goToCartPage();
		
		CartPage cartPageObj = new CartPage(driver);
		Boolean match = cartPageObj.verifyProductDisplayed("ZARA COAT 33");
		Assert.assertFalse(match);
	}

}



