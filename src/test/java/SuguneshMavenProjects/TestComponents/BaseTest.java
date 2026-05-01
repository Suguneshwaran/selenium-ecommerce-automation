package SuguneshMavenProjects.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SuguneshMavenProjects.pageObjectModule.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver; // we declared driver globally
	
	public LandingPage landPage; // creating object for a class globally, as landing page is the first page for all test cases , we create object globally
	
	public WebDriver initializeDriver() throws IOException { // this method's return type is driver
		
		// Using properties class of Java to use global data 
		// Which means test cases can run in any browser eg: chrome, edge etc, We cant write browser initialization code fro all browser again and again
		// Instead we set global data and store it as properties and use them ( src/main/java -> SuguneshMavenProjects.Resources -> GlobalData.properties)
		
// PROPERTIES CLASS ( setting path for global properties using File Input Stream reader)
		
		Properties prop = new Properties();
		
		//FileInputStream fis= new FileInputStream("C:\\Users\\HARI\\git\\repository\\SeleniumFrameworkDesign\\src\\main\\java\\SuguneshMavenProjetcs\\Resources\\GlobalData.properties");
		// above line has full path as local path for properties package which may dont work in others system, to make it work globally we are declaring as below
		
		FileInputStream fis= new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\SuguneshMavenProjetcs\\Resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");

// Deciding which browser to use
		
		if(browserName.equalsIgnoreCase("chrome"))	
		{
			WebDriverManager.chromedriver().setup(); // WebDriverManager - set up driver automatically as per version of chrome - need to add extension in POM, easy to use and will not throw error due to version
			driver = new ChromeDriver(); // no need WebDriver driver = new ChromeDriver() as we declared driver globally;		
		}
		else if(browserName.equalsIgnoreCase("firefox"))	
		{
			System.setProperty("webdriver.getko.driver", "D:\\Webdrivers for automation\\geckodriver-v0.36.0-win-aarch64\\geckodriver.exe");
			// primitive way of setting driver path , by downloading driver and setting the path - version error may hit when driver and chrome version are different 
			driver = new FirefoxDriver();		
		}
		else if (browserName.equalsIgnoreCase("edge")) 
		{
			System.setProperty("webdriver.edge.driver","D:\\Webdrivers for automation\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		return driver;
		
	}
	
	@BeforeMethod(alwaysRun = true) // as this method is declared as "Before Method" - before every method of "StandAloneTestToPOMMainclass" class this method will execute
	public LandingPage launchAppliaction() throws IOException
	{
		driver = initializeDriver();
		landPage = new LandingPage(driver);//LandingPage landPage = new LandingPage(driver); // as landing page object already declared globally , we just given variable name for the object
		landPage.goTo();
		return landPage;
		
	}
	
	@AfterMethod (alwaysRun = true)// as this method is declared as "After Method" - after every method of "StandAloneTestToPOMMainclass" class this method will execute
	public void tearDown()
	{
		driver.close();
	}
	
	// This method is present in "SuguneshMavenProjects > DataReader" - we placed it here so that all class in "SuguneshMavenProjects.Test" can use this without creating object for DataReader class
	// Explanation for each and every line is present in the class "DataReader"
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException 
	{
		String jsonContent =  FileUtils.readFileToString(new File(filePath), 
							  StandardCharsets.UTF_8);
	
		ObjectMapper mapper = new ObjectMapper(); 
		
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>()
				{});
		
		return data;
		
	}
	

}
