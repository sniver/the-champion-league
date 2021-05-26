package com.digital.factory.exception;

import java.io.Serializable;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public class LeagueException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;

	public LeagueException() {
		
	}
	
	public LeagueException(String message) {
		super(message);
	}

	public LeagueException(String message, Throwable cause) {
		super(message, cause);
	}

	public LeagueException(Throwable cause) {
		super(cause);
	}
}
