package com.digital.factory.service;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public interface EmailService {
	
	/**
	 * send simple message by mail
	 * @param to
	 * @param subject
	 * @param text
	 */
	public void sendSimpleMessage(String to, String subject, String text);
}
