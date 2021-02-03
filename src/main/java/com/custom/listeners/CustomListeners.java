package com.custom.listeners;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.custom.ap.GenLib;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class CustomListeners implements ITestListener {

	WebDriver driver;
	public static ExtentReports rep;
	// CommonDriver commonDriver;
	private Properties env_properties;
	public static ExtentTest test;
	private GenLib genLib;
	public static String systemDir = System.getProperty("user.dir");
	private Properties jiraConfig_properties;
	public static final String RESOURCESLOC = systemDir + "src/main/resources/json/jiraConfig/";
	public static String folderName = "";
	Log logger = LogFactory.getLog(getClass());
	String url = "";
	// WebDriver driver;

	public CustomListeners() {
		genLib = new GenLib();
	}

	@Override
	public void onTestStart(ITestResult result) {
		boolean extentReport = Boolean.parseBoolean(result.getMethod().getXmlTest().getParameter("extentReport"));
		
		if(extentReport) {
			rep = GenLib.getInstance();
			test = rep.startTest(result.getName().toUpperCase());
		}

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		boolean extentReport = Boolean.parseBoolean(result.getMethod().getXmlTest().getParameter("extentReport"));
		
		if(extentReport) {
			test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
			rep.endTest(test);
			rep.flush();
		}
		
		// Jira integrate - begin
		try {
			jiraConfig_properties = genLib.getPropertyFile("jiraConfig.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean createJiraReport = Boolean
				.parseBoolean(result.getMethod().getXmlTest().getParameter("jiraIntegration"));

		if (createJiraReport) {
			String globalJiraSwitchKey = jiraConfig_properties.getProperty("globalJiraSwitchKey").toString();
			String projectKey = jiraConfig_properties.getProperty("projectKey").toString();
			String testExecutionKey = result.getMethod().getXmlTest().getParameter("testExecutionKey");
			String jiraHostURL = jiraConfig_properties.getProperty("jiraHostURL").toString();
			String jiraUser = jiraConfig_properties.getProperty("jiraUser").toString();
			String jiraPassword = jiraConfig_properties.getProperty("jiraPassword").toString();

			System.out.println("createJiraReport = " + createJiraReport);
			System.out.println("testExecutionKey = " + testExecutionKey);

			String testKey = "";
			String[] tags = result.getMethod().getGroups();
			for (String tag : tags) {
				if (tag.contains("TC_" + projectKey + "-")) {
					testKey = tag.replace("@TC_", "");
				}
			}
			System.out.println("Test Key is - " + testKey);

			if (globalJiraSwitchKey.equalsIgnoreCase("true") && !testKey.isEmpty()) {
				postXRayExDetailsJiraAccount(jiraHostURL, jiraUser, jiraPassword, testExecutionKey, true, testKey, null,
						null);
			}
		}
		// Jira integrate - end

	}

	@Override
	public void onTestFailure(ITestResult result) {	
		boolean extentReport = Boolean.parseBoolean(result.getMethod().getXmlTest().getParameter("extentReport"));
		
		// extent Report
		if(extentReport) {
			test.log(LogStatus.FAIL, result.getName().toUpperCase() + " FAILED TESTING");
			rep.endTest(test);
			rep.flush();
	
			Date d = new Date();
			String screenshotName = d.toString().replace(":", "_").replace(" ", "_");
			/*try {
				genObjGui.takeScreenshotForExtentReport(screenshotName);
			} catch (Throwable e) {
				e.printStackTrace();
			}*/
			
			try {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String currentDir = System.getProperty("user.dir");
				FileUtils.copyFile(scrFile,new File(currentDir + "/target/surefire-reports/" +folderName+"/"+screenshotName+".png"));
			
				CustomListeners.test.log(LogStatus.PASS, screenshotName);
				CustomListeners.test.log(LogStatus.PASS, CustomListeners.test.addScreenCapture(screenshotName+".png"));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
	
			test.log(LogStatus.FAIL, result.getName().toUpperCase() + " Failed with exception : " + result.getThrowable());
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotName + ".png"));
		}
		
		// Jira Integrate - Start
		try {
			jiraConfig_properties = genLib.getPropertyFile("jiraConfig.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean createJiraReport = Boolean
				.parseBoolean(result.getMethod().getXmlTest().getParameter("jiraIntegration"));

		if (createJiraReport) {
			String scenarioName = result.getMethod().getMethodName();
			String globalJiraSwitchKey = jiraConfig_properties.getProperty("globalJiraSwitchKey").toString();
			String projectKey = jiraConfig_properties.getProperty("projectKey").toString();
			String testExecutionKey = result.getMethod().getXmlTest().getParameter("testExecutionKey");
			String createNewJiraTasks = jiraConfig_properties.getProperty("createNewJiraTasks").toString();
			String jiraHostURL = jiraConfig_properties.getProperty("jiraHostURL").toString();
			String jiraUser = jiraConfig_properties.getProperty("jiraUser").toString();
			String jiraPassword = jiraConfig_properties.getProperty("jiraPassword").toString();

			System.out.println("jiraUser = " + jiraUser);

			String testKey = "";
			String[] tags = result.getMethod().getGroups();
			for (String tag : tags) {
				if (tag.contains("TC_" + projectKey + "-")) {
					testKey = tag.replace("@TC_", "");
				}
			}
			System.out.println("Test Key is - " + testKey);

			if (globalJiraSwitchKey.equalsIgnoreCase("true") && !testKey.isEmpty()) {

				String encodedStringEvidence = "";

				postXRayExDetailsJiraAccount(jiraHostURL, jiraUser, jiraPassword, testExecutionKey, false, testKey,
						encodedStringEvidence, "failureScreen" + scenarioName);

				if (createNewJiraTasks.equalsIgnoreCase("true")) {

					String failureException = "";
					if (result.getThrowable() == null) {
						failureException = "No exception stacktrace";
					} else {
						failureException = ExceptionUtils.getStackTrace(result.getThrowable());
					}

					String taskDesc = "Please check the failed test case - " + scenarioName + " in the QAF Report."
							+ "\n This test case failed with exception - " + failureException;

					createJiraTask(jiraHostURL, jiraUser, jiraPassword, projectKey, taskDesc);
				}
			}
		}
		// JIRA Integrate - End

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		boolean extentReport = Boolean.parseBoolean(result.getMethod().getXmlTest().getParameter("extentReport"));
		
		if(extentReport) {
			test.log(LogStatus.SKIP, result.getName().toUpperCase() + " Skipped the test as the Run mode is NO");
			rep.endTest(test);
			rep.flush();
		}

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		boolean extentReport = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("extentReport"));
		boolean isAPIOnly = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("isAPIOnly"));
		
		if(extentReport) { 
			String suiteName = context.getCurrentXmlTest().getSuite().getName(); 
			folderName = suiteName; 
		}
		 

		try {
			env_properties = genLib.getPropertyFile("Environment.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String envProfile = context.getCurrentXmlTest().getParameter("env.profile");

		if (isAPIOnly) {
			setUrlAPI(envProfile);
		} else {
			driver = new WebDriverTestBase().getDriver();
			setUrlWeb(envProfile);
			setUrlAPI(envProfile);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.get(url);
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void setUrlWeb(String envProfile) {
		switch (envProfile.toUpperCase()) {
		case "DEV":
			logger.info("Navigated to DEV env");
			url = env_properties.getProperty("dev.url");
			break;
		case "SIT":
			logger.info("Navigated to SIT env");
			url = env_properties.getProperty("sit.url");
			break;
		case "UAT":
			logger.info("Navigated to UAT env");
			url = env_properties.getProperty("uat.url");
			System.out.println("url = " + url);
			break;
		case "REH":
			logger.info("Navigated to Rehearsal env");
			url = env_properties.getProperty("reh.url");
			break;
		case "PROD":
			logger.info("Navigated to PROD env");
			url = env_properties.getProperty("prod.url");
			break;
		}
	}

	public void setUrlAPI(String envProfile) {
		String urlAPI = "";
		switch (envProfile.toUpperCase()) {
		case "DEV":
			logger.info("Navigated to DEV env");
			urlAPI = env_properties.getProperty("dev.url.api");
			getBundle().setProperty("urlAPI", urlAPI);
			break;
		case "SIT":
			logger.info("Navigated to SIT env");
			urlAPI = env_properties.getProperty("sit.url.api");
			getBundle().setProperty("urlAPI", urlAPI);
			break;
		case "UAT":
			logger.info("Navigated to UAT env");
			urlAPI = env_properties.getProperty("uat.url.api");
			getBundle().setProperty("urlAPI", urlAPI);
			break;
		case "REH":
			logger.info("Navigated to Rehearsal env");
			urlAPI = env_properties.getProperty("reh.url.api");
			getBundle().setProperty("urlAPI", urlAPI);
			break;
		case "PROD":
			logger.info("Navigated to PROD env");
			urlAPI = env_properties.getProperty("prod.url.api");
			getBundle().setProperty("urlAPI", urlAPI);
			break;
		}
	}
	
	private static final int TIMEOUT_MILLIS = 60000;

	public static String makeAPICall(String hostURL, String endPoint, Map<String, String> headers, String body,
			String basicAuthUser, String basicAuthPass) {
		URIBuilder taskUriBuilder;
		try {
			taskUriBuilder = new URIBuilder(hostURL + endPoint);

			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			if (basicAuthUser != null) {
				UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(basicAuthUser, basicAuthPass);
				credentialsProvider.setCredentials(AuthScope.ANY, credentials);
			}
			System.out.println("BODY before setting the entity is - " + body);
			StringEntity requestEntity = new StringEntity(body, ContentType.APPLICATION_JSON);

			HttpPost httpPost = new HttpPost(taskUriBuilder.build());
			addRequestHeaders(httpPost, headers);
			httpPost.setEntity(requestEntity);

			HttpResponse response = null;
			HttpClient httpClient = HttpClientBuilder.create()
					.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
					.setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(TIMEOUT_MILLIS)
							.setConnectTimeout(TIMEOUT_MILLIS).setConnectionRequestTimeout(TIMEOUT_MILLIS).build())
					.setDefaultCredentialsProvider(credentialsProvider).build();
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "UTF-8");
			System.out.println("Response of URL - " + hostURL + endPoint + " is response - " + responseString);
			return responseString;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void addRequestHeaders(HttpRequestBase request, Map<String, String> headers) {
		for (String key : headers.keySet()) {
			request.addHeader(key, headers.get(key));
		}
	}

	@SuppressWarnings("unused")
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static String getXRAYAuthorization(String xrayHost, String xrayClientID, String xrayClientSecret) {
		Map<String, String> xrayHeader = new HashMap<String, String>();
		xrayHeader.put("Content-Type", "application/json");

		String xrayAuthBody = "{ \"client_id\": \"$clientID\",\"client_secret\": \"$clientSecret\" }"
				.replace("$clientID", xrayClientID).replace("$clientSecret", xrayClientSecret);
		// System.out.println(xrayAuthBody);
		String appListResponse = makeAPICall(xrayHost, "/api/v1/authenticate", xrayHeader, xrayAuthBody, null, null);

		System.out.println(appListResponse);

		String xRayAuthToken = appListResponse.replace("\"", "");
		System.out.println(xRayAuthToken);
		return xRayAuthToken;
	}

	@SuppressWarnings({ "deprecation" })
	private static String postXRayExDetails(String xrayHost, String xRayAuthToken, String testExecutionKey,
		boolean passFailStatus, String testKey, String evidence, String fileName) {
		String updateTestRunResp = "";
		Map<String, String> xrayHeaderBearer = new HashMap<String, String>();
		xrayHeaderBearer.put("Content-Type", "application/json");
		xrayHeaderBearer.put("Authorization", "Bearer " + xRayAuthToken);

		JSONParser parser = new JSONParser();

		Object obj, passObj, failObj;
		try {
			obj = parser.parse(new FileReader(RESOURCESLOC + "xrayTestExUpdate.json"));
			JSONObject jsonObject = (JSONObject) obj;

			if (passFailStatus) {
				passObj = parser.parse(new FileReader(RESOURCESLOC + "xrayTestPass.json"));
				JSONObject passJsonObject = (JSONObject) passObj;
				passJsonObject.put("testKey", testKey);
				((JSONArray) jsonObject.get("tests")).add(passJsonObject);
			} else {
				failObj = parser.parse(new FileReader(RESOURCESLOC + "xrayTestFail.json"));
				JSONObject failJsonObject = (JSONObject) failObj;
				failJsonObject.put("testKey", testKey);
				((JSONArray) jsonObject.get("tests")).add(failJsonObject);
			}
			jsonObject.put("testExecutionKey", testExecutionKey);
			// Code to add body as JSON object

			System.out.println("JSON BODY - " + jsonObject.toString());
			updateTestRunResp = makeAPICall(xrayHost, "/api/v1/import/execution", xrayHeaderBearer,
					jsonObject.toString(), null, null);
			System.out.println("Finished with updating the execution status");
			System.out.println(updateTestRunResp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateTestRunResp;
	}

	@SuppressWarnings("unchecked")
	private static String postXRayExDetailsJiraAccount(String jiraHostURL, String jiraUser, String jiraPassword,
			String testExecutionKey, boolean passFailStatus, String testKey, String evidence, String fileName) {
		String updateTestRunResp = "";
		Map<String, String> xrayHeaderBearer = new HashMap<String, String>();
		xrayHeaderBearer.put("Content-Type", "application/json");
		// xrayHeaderBearer.put("Authorization", "Bearer " + xRayAuthToken);
		String auth = jiraUser + ":" + jiraPassword;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		xrayHeaderBearer.put(HttpHeaders.AUTHORIZATION, authHeader);
		JSONParser parser = new JSONParser();

		Object obj, passObj, failObj;
		try {
			obj = parser.parse(new FileReader(RESOURCESLOC + "xrayTestExUpdate.json"));
			JSONObject jsonObject = (JSONObject) obj;

			if (passFailStatus) {
				passObj = parser.parse(new FileReader(RESOURCESLOC + "xrayTestPass.json"));
				JSONObject passJsonObject = (JSONObject) passObj;
				passJsonObject.put("testKey", testKey);
				((JSONArray) jsonObject.get("tests")).add(passJsonObject);
				System.out.println("JSON Object" + jsonObject.toString());
			} else {
				failObj = parser.parse(new FileReader(RESOURCESLOC + "xrayTestFail.json"));
				JSONObject failJsonObject = (JSONObject) failObj;
				failJsonObject.put("testKey", testKey);
//				JSONObject evidenceObj = ((JSONObject)((JSONArray)failJsonObject.get("evidences")).get(0));
//				evidenceObj.put("data", evidence);
//				evidenceObj.put("filename", fileName);
				((JSONArray) jsonObject.get("tests")).add(failJsonObject);
			}
			jsonObject.put("testExecutionKey", testExecutionKey);
			// Code to add body as JSON object

			System.out.println("JSON BODY - " + jsonObject.toString());
//			if(passFailStatus){
			updateTestRunResp = makeAPICall(jiraHostURL, "/rest/raven/1.0/import/execution", xrayHeaderBearer,
					jsonObject.toString(), jiraUser, jiraPassword); // user name and password
//			}
			System.out.println("Finished with updating the execution status");
			System.out.println(updateTestRunResp);
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return updateTestRunResp;
	}

	@SuppressWarnings({ "deprecation" })
	private static String createJiraTask(String jiraHostURL, String basicAuthUser, String basicAuthPass,
			String projectKey, String taskDesc) {
		String jiraTaskCreateResp = "";
		Map<String, String> jiraHeader = new HashMap<String, String>();
		jiraHeader.put("Content-Type", "application/json");

		String auth = basicAuthUser + ":" + basicAuthPass;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		String authHeader = "Basic " + new String(encodedAuth);
		jiraHeader.put(HttpHeaders.AUTHORIZATION, authHeader);

		// System.out.println(xrayAuthBody);

		JSONParser parser = new JSONParser();

		Object obj;
		try {
			obj = parser.parse(new FileReader(RESOURCESLOC + "jiraNewTask.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject fieldsObj = ((JSONObject) jsonObject.get("fields"));
			((JSONObject) fieldsObj.get("project")).put("key", projectKey);
			fieldsObj.put("description", taskDesc);

			// Code to add body as JSON object

			System.out.println("JSON BODY - " + jsonObject.toString());
			jiraTaskCreateResp = makeAPICall(jiraHostURL, "/rest/api/2/issue/", jiraHeader, jsonObject.toString(), null,
					null);
			System.out.println("Finished with updating the execution status");
			System.out.println(jiraTaskCreateResp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jiraTaskCreateResp;
	}

}
