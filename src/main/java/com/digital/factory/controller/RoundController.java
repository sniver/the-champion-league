package com.digital.factory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital.factory.dto.Response;
import com.digital.factory.service.RoundService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@RestController
@RequestMapping(path = { "leagues/{leagueId}/rounds" })
public class RoundController  extends AbstractController{
	private static final Logger logger = LoggerFactory.getLogger(RoundController.class);
	
	@Autowired
	RoundService roundService;
	
	/**
	 * start league and generate round matches.
	 * @param leagueId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Response<String>> generateFirstRound(@PathVariable(name = "leagueId") Long leagueId) {
		Response<String> response = new Response<String>();
		try {
			logger.info("com.digital.factory.controller.RoundController generateFirstRound Start");
			
			// start league and generate round matches.
			roundService.generateFirstRound(leagueId);
			
			// set response object.
			setResponseSuccess(response);
			
			logger.info("com.digital.factory.controller.RoundController generateFirstRound "
					+ "End with response =  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("com.digital.factory.controller.RoundController generateFirstRound"
					+ " End with Exception = " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * close league current round.
	 * @param leagueId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<Response<String>> closeRound(@PathVariable(name = "leagueId") Long leagueId) {
		Response<String> response = new Response<String>();
		try {
			logger.info("com.digital.factory.controller.RoundController closeRound Start");
			
			// close league current round.
			roundService.closeRound(leagueId);
			
			// set response object.
			setResponseSuccess(response);
			
			logger.info("com.digital.factory.controller.RoundController closeRound "
					+ "End with response =  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("com.digital.factory.controller.RoundController closeRound"
					+ " End with Exception = " + e.getMessage());
			throw e;
		}
	}
	
}
