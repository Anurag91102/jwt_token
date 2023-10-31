package com.spring.jwtauthentication.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.jwtauthentication.payloads.Response;


@RestControllerAdvice
public class GlobalExceptionHandler 
{
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArguement(MethodArgumentNotValidException ex)
	{
		Map<String, Object>  response = new HashMap<>();
		Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(fieldName, message);
        });
		response.put("status", 0);
        response.put("message", errorMap);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Response> handleSQLConstraint(SQLIntegrityConstraintViolationException ex)
	{
		String errorMessage = ex.getMessage(); 

		if (errorMessage.contains("employee.UKfopic1oh5oln2khj8eat6in")) 
		{
	        errorMessage = "Email address already exists in the system";
        } 
		else if (errorMessage.contains("employee.UKbuf2qp04xpwfp5qq355706h4a"))
		{
            errorMessage = "Phone number already exists";
        }
		Response  response = new Response(0,errorMessage);
        return new ResponseEntity<Response>(response,HttpStatus.OK);	
	}
	
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Response> handeNoSuchElement(NoSuchElementException ex)
	{
		String message = ex.getMessage();
		Response response = new Response(0,message,new ArrayList<>());
		return new ResponseEntity<Response>(response,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(TokenGetExpired.class)
	public ResponseEntity<Response> handleTokenGetExpired(TokenGetExpired ex)
	{
		String message = ex.getMessage();
		Response response = new Response(0, message, new ArrayList<>());
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
