package com.custom.ap;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class OracleConnection {
	private static String host;
	private static String serviceName;
	private static String username;
	private static String password;
	public static Connection con;
	public static CallableStatement callableStmt;
	public static Statement stmt;
	public static ResultSet rs;
	private GenLib genLib;
	public static Properties prop = new Properties();
	public String systemDir = System.getProperty("user.dir");
	private Properties temp_properties;
	
	public OracleConnection() {
		genLib = new GenLib();
	}
	
	public void config(String serviceName) {
		try {
			temp_properties = genLib.getPropertyFile("oracledbconnection.properties");
			host = temp_properties.getProperty(serviceName+".host");
			OracleConnection.serviceName = serviceName.toUpperCase();
			username = temp_properties.getProperty(serviceName+".username");
			password = temp_properties.getProperty(serviceName+".password");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void oracleConnection(String serviceName) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			config(serviceName);	
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":1521:" + OracleConnection.serviceName + "",username, password);
        }catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
