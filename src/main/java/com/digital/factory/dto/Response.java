package com.digital.factory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Mohamed Mahdy
 *
 * @param <T> used to determine content data type.
 */
public class Response<T> {
	public static enum ResponseStatus
	{
		SUCCESS, FAIL
	};
	
	private Boolean status ;
	private String code ;
	private String message;
	@JsonProperty("data")
	private T content;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public T getContent() {
		return content;
	}
	
	public void setContent(T content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return status + ":" + message;
	}	
}
