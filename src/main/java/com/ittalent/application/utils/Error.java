package com.ittalent.application.utils;

import org.springframework.http.HttpStatus;

public class Error {

	private HttpStatus status;
	private String mensaje;
	
	public Error(HttpStatus status, String mensaje) {
		super();
		this.status = status;
		this.mensaje = mensaje;
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
