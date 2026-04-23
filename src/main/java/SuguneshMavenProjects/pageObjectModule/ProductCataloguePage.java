package SuguneshMavenProjects.pageObjectModule;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import SuguneshMavenProjects.AbstractComponents.AbstractComponent;

public class ProductCataloguePage extends AbstractComponent {

	WebDriver driver;
	
	public ProductCataloguePage(WebDriver driverMain) {
		
		super(driverMain);
		this.driver = driverMain;
		PageFactory.initElements(driver, this);
	}
	
	//List<WebElement> products = driver.findElements(By.cssSelector(".mb-3")); 
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css="#toast-container")
	WebElement prodAddedMsgBox;
	
	@FindBy(css=".ng-animating")
	WebElement loadAnimation;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCartBy = By.cssSelector(".card-body button:last-of-type");
	
	
	public  List<WebElement> getProductList()
	{
		elementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement expectedproduct = getProductList().stream() // here we can use products.stream also as products list is global inside the class, here we again called getProductList() method to get products
											.filter(product -> product.findElement(By.cssSelector("b")).getText()
											.equalsIgnoreCase(productName))
											.findFirst().orElse(null);
		return expectedproduct;
	}
	
	public void addToCart(String productName)
	{
		WebElement productToAddcart = getProductByName(productName);
		WebElement addToCartBtn = productToAddcart.findElement(addToCartBy);
		elementClick(addToCartBtn); // method defined in abstract components class
		
		// after adding product you will see a msg "product added" and a loading spinner, we have to wait until those to disappear then perform next actions
		elementToInvisibleWait(prodAddedMsgBox);
		elementToInvisibleWait(loadAnimation);		
	}
		
}
