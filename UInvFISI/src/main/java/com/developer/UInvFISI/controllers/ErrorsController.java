package com.developer.UInvFISI.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.developer.UInvFISI.util.Constantes;

@ControllerAdvice
public class ErrorsController {

	@ExceptionHandler(Exception.class)
	String showInternalServerError() {
		
		return Constantes.ISE_VIEW;
	}
}
