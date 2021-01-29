package com.quantum.steps.api;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import static com.qmetry.qaf.automation.util.Validator.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jayway.jsonpath.JsonPath;
import com.manulife.ap.GenLib;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.rest.RestRequestBean;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.step.WsStep;
import com.qmetry.qaf.automation.ws.rest.RestTestBase;
import com.sun.jersey.api.client.ClientResponse;


public class WebServicesStepDef {
	GenLib genLib;
	public static Properties prop = new Properties();
	public String systemDir = System.getProperty("user.dir");
	private Properties temp_properties;
	
	public WebServicesStepDef() {
		genLib = new GenLib();
	}
	
	
	@QAFTestStep(description="user requests with json data from path {0}")
	public static ClientResponse userResuestsWithJsonDataFromPath(String path) throws IOException, ParseException{
		/* 
		 * This method will trigger a request based from the json request template
		 * Parameter:
		 * 		- JSON Request filepath
		 */
		Logger logger = LoggerFactory.getLogger(WebServicesStepDef.class);
		
		JSONParser jsonParser = new JSONParser();
		File file = new File(path);
		Object data = jsonParser.parse(new FileReader(file));
		JsonObject jsonObject = new Gson().toJsonTree(data).getAsJsonObject();
		HashMap<String, Object> result;
		
		try {
			result = new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
			RestRequestBean bean = new RestRequestBean();
			bean.fillData(jsonObject.toString());
			bean.resolveParameters(result);
			return WsStep.request(bean);
		} catch (JsonParseException e) {
			logger.info("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			logger.info("JsonParseException: ", e);
		} catch (IOException e) {
			logger.info("JsonParseException: ", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@QAFTestStep(description = "user requests with json data {0}")
	public static ClientResponse userRequestsWithJsonData(Object data) {
		/* 
		 * This method will trigger a request based from the json request variable key (old implementation)
		 * Parameter:
		 * 		- JSON Request Variable Key
		 */
		Logger logger = LoggerFactory.getLogger(WebServicesStepDef.class);
		System.out.println(data.getClass());
		
		JsonObject jsonObject = new Gson().toJsonTree(data).getAsJsonObject();
		HashMap<String, Object> result;
		
		try {
			result = new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
			System.out.println(jsonObject.toString());
			RestRequestBean bean = new RestRequestBean();
			bean.fillData(jsonObject.toString());
			bean.resolveParameters(result);
			return WsStep.request(bean);
		}catch (JsonParseException e) {
			logger.info("JsonParseException: ", e);
		}catch (JsonMappingException f) {
			logger.info("JsonMappingException: ", f);
		}catch (IOException g) {
			logger.info("IOException: ", g);
		}
		return null;
	}
	
	@QAFTestStep(description = "store expected value {0} into key {1}")
	public static void storeResponseBodytoNew(Object value, String variable) {
		/* 
		 * This method will store the expected value {0} into a variable {1}. 
		 * You may use the datafile in the examples or you can delegate hard coded expected value
		 * Parameters:
		 * 		- Expected Value
		 * 		- Variable Name
		 */
		String convertedValue = String.valueOf(value);
		getBundle().setProperty(variable, convertedValue);
	}
	
//	@QAFTestStep(description = "store expected int value {0} into key {1}")
//	public static void storeResponseBodyIntValue(int value, String variable) {
//		/* 
//		 * This method will store the expected value {0} into a variable {1}. 
//		 * You may use the datafile in the examples or you can delegate hard coded expected value
//		 * Parameters:
//		 * 		- Expected Value
//		 * 		- Variable Name
//		 */
//		String convertedValueToInt = Integer.toString(value);
//		getBundle().setProperty(variable, convertedValueToInt);
//	}
//	
//	@QAFTestStep(description = "store expected long value {0} into key {1}")
//	public static void storeResponseBodyLongValue(long value, String variable) {
//		/* 
//		 * This method will store the expected value {0} into a variable {1}. 
//		 * You may use the datafile in the examples or you can delegate hard coded expected value
//		 * Parameters:
//		 * 		- Expected Value
//		 * 		- Variable Name
//		 */
//		String convertedValueToLong = Long.toString(value);
//		getBundle().setProperty(variable, convertedValueToLong);
//	}
	
	@QAFTestStep(description="I wait for {0} seconds")
	public void iWaitForSeconds(int seconds) throws InterruptedException{
		/* 
		 * This method will do hard wait for 'x' number of seconds
		 * Parameters:
		 * 		- Wait time (seconds)
		 */
		int secondsToSleep = seconds * 1000;
		Thread.sleep(secondsToSleep);
	}
	
	@QAFTestStep(description = "user run sessions endpoint {jsonRequestTemplate}")
	public static ClientResponse callSessionAPIEndpoint(String path) throws IOException, ParseException {
		/* 
		 * This method will store the expected value {0} into a variable {1}. 
		 * You may use the datafile in the examples or you can delegate hard coded expected value
		 * Parameters:
		 * 		- Expected Value
		 * 		- Variable Name
		 */
		Logger logger = LoggerFactory.getLogger(WebServicesStepDef.class);
		
		if(ConfigurationManager.getBundle().getProperty("environment").equals("dev")) {
			logger.info("Session Environment is set to false");
		}else if (ConfigurationManager.getBundle().getProperty("environment").equals("sit")) {
			userResuestsWithJsonDataFromPath(path);
		}
		return null;		
	}
	
	@QAFTestStep(description="read Json from String {0} and store the response body {1} into variable {2}")
	public void readJsonFromStringAndStoreTheResponseBodyIntoVariable(String key,String path, String variable) {
		String jsonString = getBundle().getString(key);
		if (!path.startsWith("$"))
			path = "$." + path;
		String Jsonpathvalue = JsonPath.read(jsonString, path).toString().replace("\"", "").replace("[", "").replace("]", "");
		//String Jsonpathvalue = JsonPath.read(jsonString, path).toString();
		getBundle().setProperty(variable, Jsonpathvalue);
	}
	
	
	@QAFTestStep(description="show response value {0} in Console")
	public void showToken(String key) {
		System.out.println("value is = "+getBundle().getString(key));
	}
	
	@QAFTestStep(description="store value from properties file {0} into variable {1} for parameter {2}")
	public void storeValueIntoBundleFromProperties(String property_filename, String key, String value) {	
		try {
			temp_properties = genLib.getPropertyFile(property_filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getBundle().setProperty(key, temp_properties.getProperty(value));
	}
	
	@QAFTestStep(description ="tests are run with test data flagged as {0} only")
	public void testCaseApiRun(String TCRun) throws Throwable {
		if(TCRun.equalsIgnoreCase("N")) {
			throw new SkipException("Skipping the testcase as the runmode for data set is N");
		}
	}
	
	@QAFTestStep(description = "I verify the result between {0} and {1}")
	public void verifyString(String actual, String expected) {
		assertThat(getBundle().getString(actual), Matchers.equalToIgnoringCase(getBundle().getString(expected)));
	}
	
	@QAFTestStep(description = "response should have expected value contains {expectedvalue} at {jsonpath}")
	public static void responseShouldHaveExpectedKeyAndValueContains(Object value, String path) {
		if (!path.startsWith("$"))
			path = "$." + path;
		Object actual = JsonPath.read(new RestTestBase().getResponse().getMessageBody(), path);
		if(value==null|| value.toString().equals(""))
		{
			System.out.println("expected value null!");
			System.out.println("actual = "+actual);
			System.out.println("Expected = "+value);
			Assert.assertEquals(actual, value);	
		}
		else 
		{
			System.out.println("expected value exist!");
			String convertedValue = String.valueOf(value);
			System.out.println("actual = "+String.valueOf(actual));
			System.out.println("Expected = "+convertedValue);
			assertThat(String.valueOf(actual), Matchers.containsString(convertedValue));
		}
	}
		
}
