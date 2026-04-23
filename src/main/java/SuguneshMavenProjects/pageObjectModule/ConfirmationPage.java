package SuguneshMavenProjects.pageObjectModule;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SuguneshMavenProjects.AbstractComponents.AbstractComponent;

public class ConfirmationPage  extends AbstractComponent{
	
	WebDriver driver;
	
	public ConfirmationPage(WebDriver drivermain)
	{
		super(drivermain);
		this.driver = drivermain;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".hero-primary")
	WebElement successMessage;
	
	By confirmpgTitleBy= By.cssSelector(".hero-primary");

	public String getConfirmationMsg()
	{
		elementToAppear(confirmpgTitleBy);
		String successMsg = successMessage.getText().trim();
	    return successMsg;
	}
      
}
