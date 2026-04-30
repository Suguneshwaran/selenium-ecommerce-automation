package SuguneshMavenProjects.pageObjectModule;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SuguneshMavenProjects.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {

WebDriver driver;
	
	public OrderPage(WebDriver driverMain) {
		
		super(driverMain);
		this.driver = driverMain;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//tbody/tr/td[2]")
	List<WebElement> orderList;
	
	By orderListBy = By.xpath("//tbody/tr/td[2]");
	
	public Boolean verifyProductDisplayed(String productName)
	{   
		allElementToAppear(orderListBy);
		Boolean prodMatch = orderList.stream().anyMatch(orders->orders.getText().equalsIgnoreCase(productName));
		return prodMatch;
	}

}
