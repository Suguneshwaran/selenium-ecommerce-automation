package SuguneshMavenProjects.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import SuguneshMavenProjects.TestComponents.BaseTest;
import SuguneshMavenProjects.pageObjectModule.CartPage;
import SuguneshMavenProjects.pageObjectModule.CheckOutPage;
import SuguneshMavenProjects.pageObjectModule.ConfirmationPage;
import SuguneshMavenProjects.pageObjectModule.OrderPage;
import SuguneshMavenProjects.pageObjectModule.ProductCataloguePage;

// ******* NOTE ***********//
// This class is exact copy of "StandAloneTest" class - In StandAloneTest class we implemented all operation from login to checkout in one single class which is hard to maintain
// So we are trying to convert it to easily manage able and modifiable using POM 

public class StandAloneTestToPOMMainclass extends BaseTest{
	
	String productNameMain= "ZARA COAT 3";
	String coutryName= "India";

	@Test(dataProvider = "getData", groups = "Purchase") // using data provider to run this test with multiple inputs
	public void submitOrder(HashMap<String,String> input) throws IOException{ // catching the data sent by dataProvider here
		
		// launchAppliaction(); // first step will always be to launch application - this method is present in base test class
							   //  we know we need this line to execute as first all time because  we declared "launchAppliaction()" of BaseTest class as @BeforeMethod
							   // as this class "StandAloneTestToPOMMainclass" extends from "BaseTest" class, TestNg will first look whether there is any annotation in parent class and then will come to child class
			
 		
 		// landpage.loginApplication("suguneshthanospmmm@gmail.com","Eagle@5714");	
        // ProductCataloguePage productPage = new ProductCataloguePage(driver);
		
		// Instead of writing above two lines - we definitely know when we click submit button on login page it will take us to product page
		// So we are encapsulating object creation statement in the login to application method "loginApplication()" of login page and returning the object from that method 
		// And catching that object here in object creation statement 
		//this is called "encapsulating object creation statement"
					
		ProductCataloguePage productPage= landPage.loginApplication(input.get("email"),input.get("password"));	
		List<WebElement> products = productPage.getProductList();
		productPage.addToCart(input.get("product"));
		productPage.goToCartPage();
		
		CartPage cartPageObj = new CartPage(driver);
		Boolean match = cartPageObj.verifyProductDisplayed(input.get("product"));
		Assert.assertTrue(match);
		cartPageObj.checkOutBtnClick();

		CheckOutPage checkOutobj =  new CheckOutPage(driver);
		checkOutobj.chooseCountry(coutryName);
		// checkOutobj.clickSubmitBtn();
        // ConfirmationPage confirmpgobj =  new ConfirmationPage(driver);
		
		// Instead of writing above two lines - we definitely know when we click submit button on checkout page it will take us to confirmational page
		// So we are encapsulating object creation statement in the click submit button method "clickSubmitBtn()" of check out page and returning the object from that method 
		// And catching that object here in object creation statement 
		
        ConfirmationPage confirmpgobj = checkOutobj.clickSubmitBtn(); // encapsulating object creation statement //calling the method and catching the newly created obj
        String successMsg = confirmpgobj.getConfirmationMsg();
        
        String expectedMsg = "THANKYOU FOR THE ORDER.";
	    Assert.assertEquals(successMsg,expectedMsg);
	    System.out.println(successMsg);
        
        // tearDown(); // driver close method - but we no need to call here as we declared that as after method 
	}
	
	@Test(dependsOnMethods = "submitOrder") // after submitting order we need to check whether that product is in order, 
											//so basically order should be submitted first , then verify, so order history depends on submit order
	public void orderHistoryTest()
	{
		ProductCataloguePage productPage= landPage.loginApplication("suguneshthanospmmm@gmail.com","Eagle@5714");	
		OrderPage orderPgObj = productPage.goToOrdersPage(); // encapsulating object creation statement
		Assert.assertTrue(orderPgObj.verifyProductDisplayed(productNameMain)); // verifyProductDisplayed method will return boolean, so catching it in assert statement
	}

	@DataProvider
	public Object[][] getData() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email","suguneshthanospmmm@gmail.com");
		map.put("password","Eagle@5714");
		map.put("product","ZARA COAT 3");
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email","suguneshthanos@gmail.com");
		map1.put("password","Sample@5714");
		map1.put("product","ADIDAS ORIGINAL");
		
		
		return new Object[][] {{map},{map1}};
		// here we are returning objects which has data set inside it
		//syntax -> return new Object[][] { {1st data set},{second data set} } // u need to add {} curly braces inside curly braces, to indicate how many data set u are going to pass and perform same test 
	}
	
	
	
}

/* Returning object directly and catching
 * 
@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"suguneshthanospmmm@gmail.com","Eagle@5714","ZARA COAT 3"},{"suguneshthanos@gmail.com","Sample@5714","ADIDAS ORIGINAL"}};
		// here we are returning objects which has data set inside it
		//syntax -> return new Object[][] { {1st data set},{second data set} } // u need to add {} curly braces inside curly braces, to indicate how many data set u are going to pass and perform same test 
	}
 */


	
