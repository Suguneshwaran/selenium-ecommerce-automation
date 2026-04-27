package SuguneshMavenProjects.pageObjectModule;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import SuguneshMavenProjects.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;
	
	public LandingPage(WebDriver driverMain) {
		
		super(driverMain); // whenever child class extends from parent it should feed its parent's constructor (here "AbstractComponent" class has constructor which expects driver)
		this.driver = driverMain;
		
		// providing knowledge about "driver" to Web elements stored using "Page Factory" concept
		PageFactory.initElements(driver, this);
	}
	
	//normal way of storing web element
	// -> WebElement emails= driver.findElement(By.id("userEmail"));
			
// PAGE FACTORY - Storing web elements using concept "Page Factor" of POM
	
	@FindBy(id="userEmail")
	WebElement emailElement;  // In run time this step will be converted into normal way
	
	// Web element stored using normal way will have knowledge about driver, but for element stored with page factory does not know about driver
	// so we are giving knowledge of driver inside constructor
	
	@FindBy(id="userPassword")
	WebElement passwordElement;
	
	@FindBy(id="login")
	WebElement loginBtn;
	
	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMsg;
	
// Performing action using actions methods - creating a method where actions are performed
	
	public ProductCataloguePage loginApplication(String email, String pass) // Action Method 
	{
		emailElement.sendKeys(email);
		passwordElement.sendKeys(pass);
		loginBtn.click();
		return new ProductCataloguePage(driver);
				
	}
	
    public void goTo()
    {
    	driver.get("https://rahulshettyacademy.com/client");
    }
	
    public String getErrorMsg()
    {
    	webElementToAppear(errorMsg);
    	String errorMessage = errorMsg.getText();
    	return errorMessage;
    }
	
}
