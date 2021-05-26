package com.digital.factory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digital.factory.dto.Response;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public abstract class AbstractController {
	private static final Logger wsLogger = LoggerFactory.getLogger(AbstractController.class);
	
	/**
	 * set success response object.
	 * @param response
	 */
	public void setResponseSuccess(Response<?> response) {
		response.setStatus(true);
		response.setMessage(Response.ResponseStatus.SUCCESS.toString());
		response.setCode(Response.ResponseStatus.SUCCESS.toString());
		wsLogger.info(response.toString());
	}
	
	/**
	 * set success response object.
	 * @param response
	 * @param responseCode
	 */
	public void setResponseSuccess(Response<?> response, String responseCode) {
		response.setStatus(true);
		response.setMessage(Response.ResponseStatus.SUCCESS.toString());
		response.setCode(responseCode);
		wsLogger.info(response.toString());
	}
	
	/**
	 * set failure response object.
	 * @param response
	 * @param e
	 * @param msg
	 */
	public void setResponseFail(Response<?> response, Exception e, String msg) {
		response.setStatus(false);
		if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getMessage() != null) {

			response.setCode(e.getCause().getCause().getMessage());
		} else if (e.getCause() != null && e.getCause().getMessage() != null) {

			response.setCode(e.getCause().getMessage());
		} else {

			response.setCode(e.getMessage());
		}

		if (msg != null) {
			response.setMessage(msg);
		}
		wsLogger.info(response.toString());
	}
	
	/**
	 * set failure response object.
	 * @param response
	 * @param e
	 */
	public void setResponseFail(Response<?> response, Exception e) {
		response.setStatus(false);
		if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getMessage() != null) {

			response.setCode(e.getCause().getCause().getMessage());
		} else if (e.getCause() != null && e.getCause().getMessage() != null) {

			response.setCode(e.getCause().getMessage());
		} else {

			response.setCode(e.getMessage());
		}

		wsLogger.info(response.toString());
	}
	
	/**
	 * set failure response object.
	 * @param response
	 * @param responseCode
	 * @param msg
	 */
	public void setResponseFail(Response<?> response, String responseCode, String msg) {
		response.setStatus(false);
		response.setMessage(msg);
		response.setCode(responseCode);
		wsLogger.info(response.toString());
	}
}
