package com.manulife.ap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.manulife.listeners.CustomListeners;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class GenLib {

	public static FileInputStream fis;
	public static Properties prop = new Properties();
	public String systemDir = System.getProperty("user.dir");
	private static ExtentReports extent;
	
	public Properties getPropertyFile(String property_filename) throws IOException {
		String systemDir = System.getProperty("user.dir");
        InputStream input_stream = null;
        try {
        	fis = new FileInputStream(systemDir + "\\src\\main\\resources\\properties\\"+property_filename);
        	System.out.println("Oke");
        	prop.load(fis);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("property file '" + property_filename + "' not found in the classpath");
        }
        return prop;
    }
	
	public static ExtentReports getInstance() {
		String folderName = CustomListeners.folderName;
		if(extent==null) {			
			extent = new ExtentReports(System.getProperty("user.dir")+"/target/surefire-reports/"+folderName+"/extentReport.html",true,DisplayOrder.OLDEST_FIRST);		
			extent.loadConfig(new File(System.getProperty("user.dir")+"/src/main/resources/ReportsConfig.xml"));			
		}
		return extent;
	}
}
