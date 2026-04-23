package SuguneshMavenProjects.Tests;

import java.sql.Driver;
import java.time.Duration;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
 		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("suguneshthanospmmm@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Eagle@5714");
		driver.findElement(By.id("login")).click();
		
		String productName= "ZARA COAT 3";
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3")); // each card

		// My Version using Loop to choose desired product is present below
	 
		// Using stream
		WebElement prod = products.stream()
				.filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName))
				.findFirst().orElse(null);

		WebElement prodAddTocartBtn = prod.findElement(By.cssSelector(".card-body button:last-of-type"));
		
        // why java script executor and wait present here : For Rahul it is working without these
		// But for me "ElementClickInterceptedException: element click intercepted: Element is not clickable at point" is coming when i try to click without any of these
		// asked GPT and Claude, tried multiple ways only this is working or else need to use thread.sleep
		//Reason : The element exists in the DOM and is technically "clickable", but something is physically covering it — so Selenium's click hits the overlay instead of the button.
		// why we use JS here even if we have wait : This bypasses the visual click entirely — it fires a DOM-level click event, skipping whatever overlay is blocking. It also scrolls the element into view first, ensuring it's not off-screen.
		
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", prodAddTocartBtn);
		wait.until(ExpectedConditions.elementToBeClickable(prodAddTocartBtn));
		
		prodAddTocartBtn.click();	
		
		// after adding product "Product is Added" message will come and go fast, we need to click cart button only after this msg and the screen loading 
		// click add to cart button and inspect on the msg ASAP, so that u can find HTML elements for the message
		// You can't inspect and find html element for loading of page which happens after clicking add to cart - so u can ask developers and get its element 
		// and here it is present in the class "ng-animating"
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));
		//using try and catch here bcz loading animation comes sometimes and not sometimes and very fast sometimes so we are giving like if it is found wait until it disappear , if it is not found just skip the exception
		try {
		    wait.until(ExpectedConditions.invisibilityOf(
		            driver.findElement(By.cssSelector(".ng-animating")))); // we can use "visibilityOfElementLocated" for this loading animation also but instead of locating an element inside wait using locator will be slow when compared to directly locating an element by its own
		} catch (NoSuchElementException e) {
		    // loading animator Already gone so need to wait and if u wait u will get exception which we catched here, so continue
		} 
		
		WebElement cartButton= driver.findElement(By.cssSelector("button[routerlink*='cart']"));
	
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartButton);
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        
        cartButton.click(); 
        
        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.cssSelector(".cartSection h3"))));
        List<WebElement> cartproducts= driver.findElements(By.cssSelector(".cartSection h3")); // parent to child traverse in CSS selector xpath - xpath //*[@class='cartSection'] /h3
		
        Boolean match=cartproducts.stream().anyMatch(cartPr-> cartPr.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
       
        WebElement checkoutBtn = driver.findElement(By.xpath("//*[@class='totalRow']/button"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", checkoutBtn);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='totalRow']/button"))).click(); 
        
        driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("Ind");
        
        //wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[contains(@class,'ta-item')]/span")));
        //List<WebElement> countryDD= driver.findElements(By.xpath("//button[contains(@class,'ta-item')]/span"));
        
        List<WebElement> countryDD = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//button[contains(@class,'ta-item')]/span")));
        
        // Using foreach loop
        for(WebElement country : countryDD)
        {
        	String currCountry = country.getText();
            System.out.println(currCountry);
            if (currCountry.equalsIgnoreCase("India")) {
				country.click();
				break;
			}
        	
        }
        
        //using stream
       /*
         WebElement targetCountry = countryDD.stream()
        	    .filter(country -> country.getText().equalsIgnoreCase("India"))
        	    .findFirst()
        	    .orElse(null);

        	targetCountry.click();
        	
        */
        
        driver.findElement(By.cssSelector("a[class*='action__submit ']")).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary")));
        String successMsg = driver.findElement(By.cssSelector(".hero-primary")).getText().trim();
        String expectedMsg = "THANKYOU FOR THE ORDER.";
        Assert.assertEquals(successMsg,expectedMsg);
        
        System.out.println(successMsg);
        
        
	}

public void safeClick(WebDriver driver, WebDriverWait wait , By locator )
{
	//
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

