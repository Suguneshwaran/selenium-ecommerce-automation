package SuguneshMavenProjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import SuguneshMavenProjects.pageObjectModule.CartPage;
import SuguneshMavenProjects.pageObjectModule.CheckOutPage;
import SuguneshMavenProjects.pageObjectModule.ConfirmationPage;
import SuguneshMavenProjects.pageObjectModule.LandingPage;
import SuguneshMavenProjects.pageObjectModule.ProductCataloguePage;
import io.github.bonigarcia.wdm.WebDriverManager;

// ******* NOTE ***********//
// This class is exact copy of "StandAloneTest" class - In StandAloneTest class we implemented all operation from login to checkout in one single class which is hard to maintain
// So we are trying to convert it to easily manage able and modifiable using POM 

public class StandAloneTestToPOMMainclass {

	public static void main(String[] args) {

		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
 		LandingPage landpage = new LandingPage(driver);
 		landpage.goTo();
 		landpage.loginApplication("suguneshthanospmmm@gmail.com","Eagle@5714");	
		
		String productName= "ZARA COAT 3";
		String coutryName= "India";
		
		ProductCataloguePage productPage= new ProductCataloguePage(driver);
		List<WebElement> products = productPage.getProductList();
		productPage.addToCart(productName);
		productPage.mainCartClick();
		
		CartPage cartPageObj = new CartPage(driver);
		Boolean match = cartPageObj.verifyProductDisplayed(productName);
		Assert.assertTrue(match);
		cartPageObj.checkOutBtnClick();

		CheckOutPage checkOutobj =  new CheckOutPage(driver);
		checkOutobj.chooseCountry(coutryName);
		//checkOutobj.clickSubmitBtn();
        //ConfirmationPage confirmpgobj =  new ConfirmationPage(driver);
		
		//Instead of writing above two lines - we definitely know when we click submit button on checkout page it will take us to confirmational page
		//So we are encapsulating object creation statement in the click submit button method "clickSubmitBtn()" of check out page and returning the object from that method 
		// And catching that object here in object creation statement 
		
        ConfirmationPage confirmpgobj = checkOutobj.clickSubmitBtn(); // calling the method and catching the newly created obj
        String successMsg = confirmpgobj.getConfirmationMsg();
        
        String expectedMsg = "THANKYOU FOR THE ORDER.";
	    Assert.assertEquals(successMsg,expectedMsg);
	    System.out.println(successMsg);
        
        driver.close();
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
