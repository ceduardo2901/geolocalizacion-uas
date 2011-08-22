package com.uas.gsiam.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	

	public static boolean validaMail (String email){
		
		boolean result = true;
		
		Matcher match = Pattern.compile(Constantes.REGEX_EMAIL).matcher(email);
		if (!match.matches()) {
			result = false;
		} 
		
		return result;
		
	}
	

}
