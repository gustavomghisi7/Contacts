package com.classroom.services.exception;

public class Validation extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public Validation(String message) {
		super(message);
	}
}
