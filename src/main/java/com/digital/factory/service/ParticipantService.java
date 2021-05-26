package com.digital.factory.service;

import java.util.List;

import com.digital.factory.dto.request.ParticipantDto;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public interface ParticipantService {
	
	/**
	 * register new participant to specific league.
	 * @param participantDto holding participant basic information
	 * @param leagueId determine which league.
	 */
	void createParticipant(ParticipantDto participantDto, Long leagueId);
	
	/**
	 * List all participants for specific league.
	 * @param leagueId
	 * @return
	 */
	List<ParticipantDto> findAllParticipants(Long leagueId);
}
