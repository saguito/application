package com.ittalent.application.utils;

import java.util.regex.Pattern;

import com.ittalent.application.model.User;

public class ValidationUtils {

	public static boolean anyNullField(User u) { 
 
        if (u.getName() == null || u.getName().length() == 0
        		|| u.getPassword() == null || u.getPassword().length() == 0
        		|| u.getEmail() == null || u.getEmail().length() == 0) {
        	return true;
        } else {
        	return false;
        }
 
    }
	
	public static boolean isValidEmail(String email) { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        
        return pat.matcher(email).matches(); 
    } 
	
	public static boolean isValidPassword(String password) { 
        String passwordRegex = "(?:.*[A-Z].*){1}" +
        		 			"(?:.*[a-z].*)" +
        		 			"(?:.*\\d.*){2}";
                              
        Pattern pat = Pattern.compile(passwordRegex); 
        
        return pat.matcher(password).matches(); 
    }

	public static boolean isValidName(String name) {
		String nameRegex = "^[a-zA-Z ]*$";
                  
		Pattern pat = Pattern.compile(nameRegex); 
		
		return pat.matcher(name).matches(); 
	} 
	
}
