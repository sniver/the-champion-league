package com.digital.factory.service;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public interface RoundService {
	
	/**
	 * start league and generate round matches.
	 * @param leagueId
	 */
	void generateFirstRound(Long leagueId);
	
	/**
	 * close league current round.
	 * @param leagueId
	 */
	void closeRound(Long leagueId);
}
