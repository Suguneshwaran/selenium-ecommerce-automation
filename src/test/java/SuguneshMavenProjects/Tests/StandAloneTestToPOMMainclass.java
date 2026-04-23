package SuguneshMavenProjects.Tests;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import SuguneshMavenProjects.TestComponents.BaseTest;
import SuguneshMavenProjects.pageObjectModule.CartPage;
import SuguneshMavenProjects.pageObjectModule.CheckOutPage;
import SuguneshMavenProjects.pageObjectModule.ConfirmationPage;
import SuguneshMavenProjects.pageObjectModule.ProductCataloguePage;

// ******* NOTE ***********//
// This class is exact copy of "StandAloneTest" class - In StandAloneTest class we implemented all operation from login to checkout in one single class which is hard to maintain
// So we are trying to convert it to easily manage able and modifiable using POM 

public class StandAloneTestToPOMMainclass extends BaseTest{

	@Test
	public void submitOrder() throws IOException{

		String productName= "ZARA COAT 3";
		String coutryName= "India";
		
		// launchAppliaction(); // first step will always be to launch application - this method is present in base test class
							   //  we know we need this line to execute as first all time because  we declared "launchAppliaction()" of BaseTest class as @BeforeMethod
							   // as this class "StandAloneTestToPOMMainclass" extends from "BaseTest" class, TestNg will first look whether there is any annotation in parent class and then will come to child class
			
 		
 		// landpage.loginApplication("suguneshthanospmmm@gmail.com","Eagle@5714");	
        // ProductCataloguePage productPage = new ProductCataloguePage(driver);
		
		// Instead of writing above two lines - we definitely know when we click submit button on login page it will take us to product page
		// So we are encapsulating object creation statement in the login to application method "loginApplication()" of login page and returning the object from that method 
		// And catching that object here in object creation statement 
					
		ProductCataloguePage productPage= landPage.loginApplication("suguneshthanospmmm@gmail.com","Eagle@5714");	
		List<WebElement> products = productPage.getProductList();
		productPage.addToCart(productName);
		productPage.mainCartClick();
		
		CartPage cartPageObj = new CartPage(driver);
		Boolean match = cartPageObj.verifyProductDisplayed(productName);
		Assert.assertTrue(match);
		cartPageObj.checkOutBtnClick();

		CheckOutPage checkOutobj =  new CheckOutPage(driver);
		checkOutobj.chooseCountry(coutryName);
		// checkOutobj.clickSubmitBtn();
        // ConfirmationPage confirmpgobj =  new ConfirmationPage(driver);
		
		// Instead of writing above two lines - we definitely know when we click submit button on checkout page it will take us to confirmational page
		// So we are encapsulating object creation statement in the click submit button method "clickSubmitBtn()" of check out page and returning the object from that method 
		// And catching that object here in object creation statement 
		
        ConfirmationPage confirmpgobj = checkOutobj.clickSubmitBtn(); // calling the method and catching the newly created obj
        String successMsg = confirmpgobj.getConfirmationMsg();
        
        String expectedMsg = "THANKYOU FOR THE ORDER.";
	    Assert.assertEquals(successMsg,expectedMsg);
	    System.out.println(successMsg);
        
        // tearDown(); // driver close method - but we no need to call here as we declared that as after method 
	}

}



// My Version using Loop to choose desired product

/*		for (WebElement product : products) 
{
    String productName = product.findElement(By.cssSelector("b")).getText();
    System.out.println(productName);

    if (productName.equals("ZARA COAT 3")) 
    {
        product.findElement(By.cssSelector("button:last-of-type")).click();
        break;
    }
}
*/	
