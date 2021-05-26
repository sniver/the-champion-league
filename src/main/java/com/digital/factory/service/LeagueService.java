package com.digital.factory.service;

import com.digital.factory.model.League;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
public interface LeagueService {
	
	/**
	 * find league entity by league id.
	 * @param leagueId
	 * @return
	 */
	League findById(Long leagueId);

}
