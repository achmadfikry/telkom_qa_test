package com.manulife.ap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GenLib {

	public static FileInputStream fis;
	public static Properties prop = new Properties();
	public String systemDir = System.getProperty("user.dir");
	
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
}
