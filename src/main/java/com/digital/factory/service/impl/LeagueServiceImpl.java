package com.digital.factory.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.factory.exception.LeagueException;
import com.digital.factory.model.League;
import com.digital.factory.repository.LeagueRepository;
import com.digital.factory.service.LeagueService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Service
public class LeagueServiceImpl implements LeagueService{
	private static final Logger logger = LoggerFactory.getLogger(LeagueServiceImpl.class);
	
	@Autowired
	LeagueRepository LeagueRepository;
	
	/**
	 * find league entity by league id.
	 * @param leagueId
	 * @return
	 */
	@Override
	public League findById(Long leagueId) {
		try {
			logger.info("com.digital.factory.service.impl.LeagueServiceImpl findById "
					+ "Start with leagueId = " + leagueId);
			
			// check if league id is null
			if(leagueId == null) {
				throw new LeagueException("league id is required");
			}
			
			// find league entity by league id.
			Optional<League> leagueOtp = LeagueRepository.findById(leagueId);
			
			// check if league entity present or no.
			if(leagueOtp.isPresent()) {
				return leagueOtp.get();
			}else {
				throw new LeagueException("League not found.");
			}
			
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.LeagueServiceImpl createParticipant "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.LeagueServiceImpl createParticipant "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to find league by id");
		}
	}

}
