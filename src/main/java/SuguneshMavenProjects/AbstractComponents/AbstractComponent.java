package SuguneshMavenProjects.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {
	
    WebDriver driver;
	
	public AbstractComponent(WebDriver drivermain)
	{
		this.driver = drivermain;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="button[routerlink*='cart']")
	WebElement mainCartBtn;
	
	
// We added main "Cart" button click code here because Cart button is common and present in header and can be seen in all pages	
	public void mainCartClick()
	{
		elementClick(mainCartBtn);
	}
	
	public void elementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));		
	}
	
	public void allElementToAppear (By allElementFindby)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allElementFindby));
	}
	
	public void elementToclickableWait (WebElement elementToClickWait)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(elementToClickWait));
	}
	
	public void elementToInvisibleWait(WebElement elementToDisappear)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try 
		{
			wait.until(ExpectedConditions.invisibilityOf(elementToDisappear));
		}
		catch(NoSuchElementException e)
		{
			System.out.println("loading animator does not come");
		}
	}
	
	public void scrollJS(WebElement elementToScrollBy)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView({block:'center'})",elementToScrollBy);
	}
	
	public void elementClick(WebElement elementToBeClick)
	{
		scrollJS(elementToBeClick);
		elementToclickableWait(elementToBeClick);
		elementToBeClick.click();
	}

}
