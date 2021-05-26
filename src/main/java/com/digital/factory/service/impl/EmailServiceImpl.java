package com.digital.factory.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.digital.factory.service.EmailService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Service
public class EmailServiceImpl implements EmailService{
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	 @Autowired
	 private JavaMailSender javaMailSender;
	 
	 /**
	 * send simple message by mail
	 * @param to
	 * @param subject
	 * @param text
	 */
	 @Override
	 public void sendSimpleMessage(String to, String subject, String text) {
		 try {
			 logger.info("com.digital.factory.service.impl.EmailServiceImpl sendSimpleMessage "
						+ "Start with to = " + to + ", subject = " + subject + ", text = " + text);
			 SimpleMailMessage msg = new SimpleMailMessage();
			 msg.setFrom("noreply@bankmisr.com");
 			 msg.setTo(to);
		 	 msg.setSubject(subject);
		 	 msg.setText(text);
		 	 javaMailSender.send(msg);
		 	logger.info("com.digital.factory.service.impl.EmailServiceImpl sendSimpleMessage End");
		 }catch(Exception e) {
			 logger.error("com.digital.factory.service.impl.EmailServiceImpl sendSimpleMessage "
						+ "END With Exception with " + e.getMessage());
		 }
	 }
}
