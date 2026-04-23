package SuguneshMavenProjects.Tests;

import java.io.IOException;
import org.testng.annotations.Test;
import SuguneshMavenProjects.TestComponents.BaseTest;
import SuguneshMavenProjects.pageObjectModule.ProductCataloguePage;


public class ErrorValidation extends BaseTest{

	@Test
	public void submitOrder() throws IOException{
			
		// Checking what happens when we give wrong email and pass 
		ProductCataloguePage productPage= landPage.loginApplication("suguneshthanospmm@gmail.com","Eage@5714");	
		
	}

}



