package com.uas.gsiam.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	private final static String REGEX_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	

	public static boolean validaMail (String email){
		
		boolean result = true;
		
		Matcher match = Pattern.compile(REGEX_EMAIL).matcher(email);
		if (!match.matches()) {
			result = false;
		} 
		
		return result;
		
	}
	

}
