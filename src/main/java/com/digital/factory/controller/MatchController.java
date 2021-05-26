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

import com.digital.factory.dto.MatchDto;
import com.digital.factory.dto.Response;
import com.digital.factory.dto.request.MatchRequestDto;
import com.digital.factory.exception.LeagueException;
import com.digital.factory.service.MatchService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@RestController
@RequestMapping(path = { "leagues/{leagueId}/matches" })
public class MatchController  extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(MatchController.class);
	
	@Autowired
	MatchService matchService;
	
	/**
	 * list current round matches.
	 * @param leagueId
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<Response<List<MatchDto>>> getCurrentRoundMatches(
			@PathVariable(name = "leagueId") Long leagueId) {
		Response<List<MatchDto>> response = new Response<List<MatchDto>>();
		try {
			logger.info("com.digital.factory.controller.MatchController getCurrentRoundMatches "
					+ "Start with leagueId = " + leagueId);
			
			// retrieve list of current round matches 
			List<MatchDto> matches = matchService.getCurrentRoundMatches(leagueId);
			
			// set response object.
			setResponseSuccess(response);
			response.setContent(matches);

			logger.info("com.digital.factory.controller.MatchController getCurrentRoundMatches "
					+ "End with response =  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("com.digital.factory.controller.MatchController getCurrentRoundMatches"
					+ " End with Exception = " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * update match winner and results.
	 * @param matchRequestDto
	 * @param errors
	 * @param leagueId
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/{matchId}", method = RequestMethod.PUT)
	public ResponseEntity<Response<String>> updateMatchResult(
			@RequestBody @Validated MatchRequestDto matchRequestDto, Errors errors,
			@PathVariable(name = "leagueId") Long leagueId,
			@PathVariable(name = "matchId") Long matchId) {
		Response<String> response = new Response<String>();
		try {
			logger.info("com.digital.factory.controller.MatchController updateMatchResult "
					+ "Start with matchRequestDto = " + matchRequestDto + ", leagueId = " + leagueId 
					+ ", matchId = " + matchId);
			
			// check if there are any errors from matchRequestDto.
			if (errors.hasErrors()) {
				throw new LeagueException(errors.getAllErrors().get(0).getDefaultMessage());
			}
			
			// update match winner and result.
			matchService.updateMatchResult(matchRequestDto, leagueId, matchId);
			
			// set response object.
			setResponseSuccess(response);

			logger.info("com.digital.factory.controller.MatchController updateMatchResult "
					+ "End with response =  " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("com.digital.factory.controller.MatchController updateMatchResult"
					+ " End with Exception = " + e.getMessage());
			throw e;
		}
	}
	
}
