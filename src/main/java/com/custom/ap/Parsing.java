package com.custom.ap;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Parsing {
	
	public String parseDateRemoveTime(String dateInput) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date = dateFormat.parse(dateInput);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
			LocalDateTime temp = timeStampDate.toLocalDateTime();
			return temp.format(myFormatObj).toString();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	public String parseDateAddTime(String dateInput) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date = dateFormat.parse(dateInput);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"); 
			LocalDateTime temp = timeStampDate.toLocalDateTime();
			return temp.format(myFormatObj).toString();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	public String parseDateAddTimeAmPm(String dateInput) {
		System.out.println(dateInput);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date = dateFormat.parse(dateInput);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM yyyy h:mm:ss tt"); 
			LocalDateTime temp = timeStampDate.toLocalDateTime();
			return temp.format(myFormatObj).toString();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}	
	}
	
	public String parseDate(String dateInput) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date = dateFormat.parse(dateInput);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd MMM yyyy"); 
			LocalDateTime temp = timeStampDate.toLocalDateTime();
			return temp.format(myFormatObj).toString();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	public String parseDateFormatD(String dateInput) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date date = dateFormat.parse(dateInput);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("d MMM yyyy"); 
			LocalDateTime temp = timeStampDate.toLocalDateTime();
			return temp.format(myFormatObj).toString();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}		
	}
	
	
	public String setCurrencyFormat(String amount) {
		try { 
			//String money = "50000000";
			
			/*NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
			String temp = nf.format(new BigDecimal(amount));
			String temp2 = temp.substring(2);
			return temp;*/
			
			String rupiah = "";         
	        Locale locale = new Locale("ca", "CA");
	        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(locale);
	         
	        rupiah = rupiahFormat.format(Double.parseDouble(amount));
	        rupiah = rupiah.substring(0, rupiah.length() - 4);
	         
	        return rupiah;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
