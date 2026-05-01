package SuguneshMavenProjects.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// Lecture no 177 
public class DataReader {
	
	// We use this same method in BaseTest so that all test can access this without creating object for this class
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException 
	{
		// Step 1: Read json and store it in a string
		String jsonContent =  FileUtils.readFileToString(new File(filePath), // we can give file path here which will look like below, but to make it generic and reusable we send path from test which it needs data,
							  StandardCharsets.UTF_8); 						//FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\SuguneshMavenProjects\\data\\PurchaseOrder.json")
	
	    // Step 2: String to HashMap - dependency used here is "jackson databind"
		ObjectMapper mapper = new ObjectMapper(); // this is a class present in the "jackson databind" dependency which helps to read data
		
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>()
				{});
		
		// by the above step we are indicating to read data from string stored "jsonContent" , and as it has 2 arrays of data
		// so convert them into hash map one by one and store as list
		
		return data;
		
	}
	
}
