package com.digital.factory.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital.factory.dto.Response;
import com.digital.factory.dto.request.ParticipantDto;
import com.digital.factory.exception.LeagueException;
import com.digital.factory.service.ParticipantService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@RestController
@RequestMapping(path = { "leagues/{leagueId}/participants" })
@Validated
public class ParticipantController extends AbstractController  {
	private static final Logger logger = LoggerFactory.getLogger(ParticipantController.class);
	
	@Autowired
	ParticipantService participantService;
	
	
	/**
	 * register new participant to specific league.
	 * @param participantDto
	 * @param errors
	 * @param leagueId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Response<String>> createParticipant(
			@RequestBody @Validated ParticipantDto participantDto, Errors errors, @PathVariable(name = "leagueId") Long leagueId) {
		Response<String> response = new Response<String>();
		try {
			logger.info("com.digital.factory.controller.ParticipantController createParticipant "
					+ "Start with participantDto = " + participantDto);
			
			// check if any validation errors from participantDto.
			if (errors.hasErrors()) {
				throw new LeagueException(errors.getAllErrors().get(0).getDefaultMessage());
			}
			
			// register participant to league.
			participantService.createParticipant(participantDto, leagueId);
			
			// set response object.
			setResponseSuccess(response);

			logger.info("com.digital.factory.controller.ParticipantController createParticipant "
					+ "End with response =  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("com.digital.factory.controller.ParticipantController createParticipant"
					+ " End with Exception = " + e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * List all participants of specific league
	 * @param leagueId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Response<List<ParticipantDto>>> findAllParticipants(@PathVariable(name = "leagueId") Long leagueId) {
		Response<List<ParticipantDto>> response = new Response<List<ParticipantDto>>();
		try {
			logger.info("com.digital.factory.controller.ParticipantController findAllParticipants Start");
			
			// List all participants of specific league
			List<ParticipantDto> participants = participantService.findAllParticipants(leagueId);
			
			//set response object.
			setResponseSuccess(response);
			response.setContent(participants);
			
			logger.info("com.digital.factory.controller.ParticipantController findAllParticipants "
					+ "End with response =  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("com.digital.factory.controller.ParticipantController findAllParticipants"
					+ " End with Exception = " + e.getMessage());
			throw e;
		}
	}
}
