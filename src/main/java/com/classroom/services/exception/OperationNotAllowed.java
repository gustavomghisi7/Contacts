package com.classroom.services.exception;

public class OperationNotAllowed extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public OperationNotAllowed(String message) {
		super(message);
	}
}
