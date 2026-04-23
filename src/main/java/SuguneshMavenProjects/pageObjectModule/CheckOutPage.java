package SuguneshMavenProjects.pageObjectModule;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SuguneshMavenProjects.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent{

	WebDriver driver;
	
	public CheckOutPage(WebDriver drivermain)
	{
		super(drivermain);
		this.driver =drivermain;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement countryChooseDDElement;
	
	@FindBy(xpath = "//button[contains(@class,'ta-item')]/span")
	List<WebElement> countryDD;
	
	@FindBy(css = "a[class*='action__submit ']")
	WebElement submitbtn;
	
	By countryDDBy = By.xpath("//button[contains(@class,'ta-item')]/span");
	
	public void chooseCountry(String countryName)
	{
		countryChooseDDElement.sendKeys(countryName);
		allElementToAppear(countryDDBy);
		 for(WebElement country : countryDD)
	        {
	        	String currCountry = country.getText();
	            System.out.println(currCountry);
	            if (currCountry.equalsIgnoreCase(countryName)) {
					country.click();
					break;
				}
	        	
	        }		
	}
	
	public ConfirmationPage clickSubmitBtn() // this methods return type is an obj which is newly created for ConfirmationPage
	{
		elementClick(submitbtn);
		return new ConfirmationPage(driver); // returning obj as return type
	}
	
}
