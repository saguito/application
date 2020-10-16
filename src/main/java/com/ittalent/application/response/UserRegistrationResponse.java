package com.ittalent.application.response;

import java.util.List;

import com.ittalent.application.utils.Error;

public class UserRegistrationResponse {

	private UserResponse userData;
	private List<Error> errors;
	
	public UserResponse getUserData() {
		return userData;
	}
	public void setUserData(UserResponse userData) {
		this.userData = userData;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	
}
