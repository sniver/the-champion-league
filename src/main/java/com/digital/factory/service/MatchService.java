package com.digital.factory.service;

import java.time.LocalDate;
import java.util.List;

import com.digital.factory.dto.MatchDto;
import com.digital.factory.dto.request.MatchRequestDto;
import com.digital.factory.model.League;
import com.digital.factory.model.Match;
import com.digital.factory.model.Participant;
import com.digital.factory.model.Round;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public interface MatchService {
	/**
	 * Create match entity.
	 * @param league
	 * @param round
	 * @param player1
	 * @param player2
	 * @param matchDate
	 * @return
	 */
	 Match createMatch(League league, Round round, Participant player1, Participant player2, LocalDate matchDate);
	 
	 /**
	  * list current round matches.
	  * @param leagueId
	  * @return
	  */
	 List<MatchDto> getCurrentRoundMatches(Long leagueId);
	 
	 /**
	  * update match winner and results.
	  * @param matchRequestDto
	  * @param leagueId
	  * @param matchId
	  */
	 void updateMatchResult(MatchRequestDto matchRequestDto, Long leagueId, Long matchId);
}
