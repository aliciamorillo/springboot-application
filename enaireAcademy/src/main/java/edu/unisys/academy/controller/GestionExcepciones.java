package edu.unisys.academy.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Centraliza la gestión de excepciones en esta clase
@RestControllerAdvice(basePackages = {"edu.unisys.academy"})
public class GestionExcepciones {
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> errorDeBorrado (EmptyResultDataAccessException exception){
		ResponseEntity<?> responseEntity = null;
		String strException = null;
		
			strException = "Error detectado SPRING DATA: " + strException.toString();
			responseEntity = ResponseEntity.internalServerError().body(strException);
				
		return responseEntity;
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> errorDeBorradoConHbJPA (IllegalArgumentException exception){
		ResponseEntity<?> responseEntity = null;
		String strException = null;
		
			strException = "Error detectado HB JPA: " + strException.toString();
			responseEntity = ResponseEntity.internalServerError().body(strException);
				
		return responseEntity;
	}

}
