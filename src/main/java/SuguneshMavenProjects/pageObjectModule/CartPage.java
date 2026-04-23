package SuguneshMavenProjects.pageObjectModule;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SuguneshMavenProjects.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

WebDriver driver;
	
	public CartPage(WebDriver driverMain) {
		
		super(driverMain);
		this.driver = driverMain;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartproducts;
	
	@FindBy(xpath = "//*[@class='totalRow']/button")
	WebElement checkoutBtnElement;
	
	By cartpageListBy = By.cssSelector(".cartSection h3");
	
	public Boolean verifyProductDisplayed(String productName)
	{   
		allElementToAppear(cartpageListBy);
		Boolean prodMatch = cartproducts.stream().anyMatch(cartproduct->cartproduct.getText().equalsIgnoreCase(productName));
		return prodMatch;
	}
	
	public void checkOutBtnClick()
	{
		elementClick(checkoutBtnElement); // method defined in abstract components class
	}
	
}
