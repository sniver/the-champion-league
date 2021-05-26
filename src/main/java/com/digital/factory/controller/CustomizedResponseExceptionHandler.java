package com.digital.factory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.digital.factory.dto.Response;
import com.digital.factory.exception.LeagueException;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@RestController
@ControllerAdvice
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler{
	/**
	 * handle custom league exceptions.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({LeagueException.class})
	public final ResponseEntity<Object> handleCustomLeagueException(Exception ex, WebRequest request){
		LeagueException leagueException = (LeagueException) ex;
		Response<String> response = new Response<String>();
		response.setCode(leagueException.getMessage());
		response.setMessage(leagueException.getMessage());
		response.setStatus(false);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * handle all other exceptions.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
		Response<String> response = new Response<String>();
		response.setCode(ex.getMessage());
		response.setMessage(ex.getMessage());
		response.setStatus(false);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
