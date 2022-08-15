package com.classroom.controller.exception;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.classroom.services.exception.Validation;
import com.classroom.services.exception.OperationNotAllowed;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(
			EntityNotFoundException e, HttpServletRequest req){
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(OperationNotAllowed.class)
	public ResponseEntity<StandardError> myException(
			OperationNotAllowed e, HttpServletRequest req){
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<StandardError> myException(
			EntityNotFoundException e, HttpServletRequest req){
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
	}
	
	@ExceptionHandler(Validation.class)
	public ResponseEntity<StandardError> myException(
			Validation e, HttpServletRequest req){
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setError("Incorrect data");
		error.setMessage(e.getMessage());
		error.setPath(req.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
