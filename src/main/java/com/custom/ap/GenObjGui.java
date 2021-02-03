package com.custom.ap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.custom.listeners.CustomListeners;
//import com.manulife.listeners.CustomListeners;
import com.qmetry.qaf.automation.core.QAFTestBase;
import com.qmetry.qaf.automation.ui.WebDriverBaseTestPage;
import com.qmetry.qaf.automation.ui.api.PageLocator;
import com.qmetry.qaf.automation.ui.api.WebDriverTestPage;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
//import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.LogStatus;

public class GenObjGui extends WebDriverBaseTestPage<WebDriverTestPage> {
	
	public static JavascriptExecutor jse;
	public static WebDriverWait wait;
	WebDriver browser;
	Actions act;
	public static String screenshotName;

	@Override
	protected void openPage(PageLocator locator, Object... args) {
	}
	
	public GenObjGui() {
		browser = driver.getUnderLayingDriver();
		jse = (JavascriptExecutor) browser;
		act = new Actions(browser);
	}

	public void javascriptClick(QAFWebElement locator) throws Throwable {
		String script = "arguments[0].click();";
		jse.executeScript(script, locator);
		logger.info("Javascript Clicked on "+locator);
	}
	
	public void javascriptSendKeys(QAFWebElement locator, String text) throws Throwable{
		String script = "arguments[0].value='"+text+"';";
		jse.executeScript(script, locator);
		logger.info("Javascript Send Keys :"+text+ " on "+locator.toString());		
	}
	
	public String javascriptGetText(QAFWebElement locator) throws Throwable {
		String script = "arguments[0].textContent;";
		String temp = jse.executeScript(script, locator).toString();
		return temp;
	}
	
	public void javascriptScrollIntoView(QAFWebElement locator) throws Throwable {
		jse.executeScript("arguments[0].scrollIntoView();", locator);
	}
	
	public void javascriptScrollIntoView(WebElement webElement) throws Throwable {
		jse.executeScript("arguments[0].scrollIntoView();", webElement);
	}
	
	public void waitForAlert(int timeOut) throws Throwable{
		logger.info("Wait for Alert "+timeOut+" Seconds");
		wait = new WebDriverWait(browser, timeOut);
		wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert() throws Throwable{
		logger.info("Accept Alert");
		browser.switchTo().alert().accept();
	}
	
	public void dismissAlert() throws Throwable{
		logger.info("Dismiss Alert");
		browser.switchTo().alert().dismiss();
	}
	
	public String getAlertText() throws Throwable{
		logger.info("Get Alert Text");
		return browser.switchTo().alert().getText();
	}
	
	public void setAlertText() throws Throwable{
		logger.info("Set Alert Text");
		browser.switchTo().alert().sendKeys("Text");
	}

	public void waitForElementClickable(QAFWebElement locator, int timeOut) throws Throwable{
		wait = new WebDriverWait(browser, timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		logger.info("Wait for Element Clickable "+locator.toString());
	}

	
	public void switchToDefaultContent() throws Throwable {
		browser.switchTo().defaultContent();
		logger.info("Switch to default content");
	}
	
	public void switchToFrame(QAFWebElement locator, int timeOut) throws Throwable {
		wait = new WebDriverWait(browser, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		logger.info("Switch to frame "+locator.toString());
	}
	
	public void switchToFrame(String id, int timeOut) throws Throwable {
		wait = new WebDriverWait(browser, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
		logger.info("Switch to frame "+id);
	}
	
	public void switchWindowIndex(int index) throws Throwable {
		Set<String> windowHandles = browser.getWindowHandles();
		List<String> windowStrings = new ArrayList<>(windowHandles);
		String reqWindow = windowStrings.get(index);
		browser.switchTo().window(reqWindow);
		logger.info("Switched to "+browser.getTitle());
	}
	
	public void switchToWindowTitle(String title) throws Throwable {
		Set<String> windowHandles = browser.getWindowHandles();
		List<String> windowStrings = new ArrayList<>(windowHandles);
		
		for(int i=0;i<windowStrings.size();i++) {
			String reqWindow = windowStrings.get(i);
			browser.switchTo().window(reqWindow);
			if(browser.getTitle().toLowerCase().equals(title.toLowerCase())) {
				break;
			}
		}		
		logger.info("Switched to "+browser.getTitle());
	}
	
	public String getUrl() throws Throwable {
		String url = null;
		url = browser.getCurrentUrl();
		logger.info("The URL is "+url);
		return url;
	}
	
	public void refresh() throws Throwable {
		browser.navigate().refresh();
		logger.info("Page Refreshed");
	}
	
	public void takeScreenshot(String comment) throws Throwable {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			Date d = new Date();
			screenshotName = d.toString().replace(":", "_").replace(" ", "_");
			FileUtils.copyFile(scrFile,new File(currentDir + "/test-output/"+screenshotName+".png"));
			
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void takeScreenshotForExtentReport(String comment) throws Throwable {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			String folderName = CustomListeners.folderName;
			FileUtils.copyFile(scrFile,new File(currentDir + "/target/surefire-reports/" +folderName+"/"+comment+".png"));
		
			CustomListeners.test.log(LogStatus.PASS, comment);
			CustomListeners.test.log(LogStatus.PASS, CustomListeners.test.addScreenCapture(comment+".png"));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void setCommentIntoExtentReport(LogStatus logStatus, String comment) throws Throwable {
		CustomListeners.test.log(logStatus, comment);
	}
	
	public void deleteAllCookies() throws Throwable {
		browser.manage().deleteAllCookies();
		logger.info("All Cookies has been deleted");
	}
	
	public void back() throws Throwable {
		browser.navigate().back();
		logger.info("Back to before page");
	}
	
	public void maximizeWindow() throws Throwable {
		browser.manage().window().maximize();
		logger.info("Maximize window");
	}
	
	public void selectDropDownByIndex(QAFWebElement locator, int index) throws Throwable {
		Select select = new Select(locator);
		select.selectByIndex(index);
	}
	
	public void selectDropdownByLabel(QAFWebElement locator, String labelText) throws Throwable {
		Select select = new Select(locator);
		select.selectByVisibleText(labelText);
	}
	
	public void selectDropdownByValue(QAFWebElement locator, String value) throws Throwable {
		Select select = new Select(locator);
		select.selectByValue(value);
	}
	
	public void delay(int second) throws Throwable {
		String temp = String.valueOf(second) + "000";
		QAFTestBase.pause(Integer.parseInt(temp));
	}
	
	public void doubleClick(QAFWebElement locator) throws Throwable {
		logger.info("Double Click on "+locator.toString());
		act.doubleClick(locator).build().perform();
		logger.info("Successfully Double Click on "+locator.toString());
	}
}
