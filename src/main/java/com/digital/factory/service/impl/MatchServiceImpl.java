package com.digital.factory.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.factory.common.Statuses;
import com.digital.factory.dto.MatchDto;
import com.digital.factory.dto.request.MatchRequestDto;
import com.digital.factory.dto.request.ParticipantDto;
import com.digital.factory.exception.LeagueException;
import com.digital.factory.model.League;
import com.digital.factory.model.Match;
import com.digital.factory.model.Participant;
import com.digital.factory.model.Round;
import com.digital.factory.repository.MatchRepository;
import com.digital.factory.service.LeagueService;
import com.digital.factory.service.MatchService;
import com.digital.factory.service.ParticipantService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Service
public class MatchServiceImpl implements MatchService{
	private static final Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	LeagueService leagueService;
	
	@Autowired
	MatchRepository matchRepository;
	
	@Autowired
	ParticipantService participantService;
	
	/**
	 * Create match entity.
	 * @param league
	 * @param round
	 * @param player1
	 * @param player2
	 * @param matchDate
	 * @return
	 */
	@Override
	public Match createMatch(League league, Round round, Participant player1, Participant player2, LocalDate matchDate) {
		 try {
			 logger.info("com.digital.factory.service.impl.MatchServiceImpl createMatch Start");
			 Match match = new Match();
			 match.setLeague(league);
			 match.setRound(round);
			 match.setPlayer1(player1);
			 match.setPlayer2(player2);
			 match.setStatus(Statuses.NEW);
			 match.setMatchDate(matchDate);
			 return match;
		 } catch (Exception e) {
	 		logger.error("com.digital.factory.service.impl.MatchServiceImpl createMatch "
					+ "END With Exception with " + e.getMessage());
	 		throw new LeagueException("Failed to create round");
	 	}
	 }
	
	
	/**
	  * list current round matches.
	  * @param leagueId
	  * @return
	  */
	@Override
	public List<MatchDto> getCurrentRoundMatches(Long leagueId) {
		try {
			logger.info("com.digital.factory.service.impl.MatchServiceImpl getCurrentRoundMatches "
					+ "Start with leagueId = " + leagueId);
			
			// find league entity by league id.
			League league = leagueService.findById(leagueId);
			
			/* check if league entity status is still new or current round is null.
			which mean league didn't start yet*/
			if(league.getStatus().equals(Statuses.NEW) || league.getCurrentRound() == null) {
				throw new LeagueException("League didn't start yet.");
			}
			
			// getting current round matches and map them from Match entity to MatchDto.
			List<MatchDto> matches = league.getCurrentRound().getMatches().stream().map(match -> {
				MatchDto matchDto = mapper.map(match, MatchDto.class);
				matchDto.setPlayer1(mapper.map(match.getPlayer1(), ParticipantDto.class));
				matchDto.setPlayer2(mapper.map(match.getPlayer2(), ParticipantDto.class));
				if(match.getWinner() != null) {
					matchDto.setWinner(mapper.map(match.getWinner(), ParticipantDto.class));
				}
				return matchDto;
			}).collect(Collectors.toList());
			
			logger.info("com.digital.factory.service.impl.MatchServiceImpl getCurrentRoundMatches End matches = " + matches.size());
			return matches;
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.MatchServiceImpl getCurrentRoundMatches "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.MatchServiceImpl getCurrentRoundMatches "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to generate matches");
		}	
	}


	 /**
	  * update match winner and results.
	  * @param matchRequestDto
	  * @param leagueId
	  * @param matchId
	  */
	@Override
	public void updateMatchResult(MatchRequestDto matchRequestDto, Long leagueId, Long matchId) {
		try {
			logger.info("com.digital.factory.service.impl.MatchServiceImpl updateMatchResult "
					+ "Start with matchRequestDto = " + matchRequestDto.toString() + ", leagueId = " + leagueId 
					+ ", matchId = " + matchId);
			
			// find league entity by league id.
			League league = leagueService.findById(leagueId);
			
			// if league entity status not in progress throw error.
			if(!league.getStatus().equals(Statuses.INPROGRESS)) {
				throw new LeagueException("You can add match results only for leagues with status INPROGRESS");
			}
			
			// find match by matchId.
			Optional<Match> matchOtp = matchRepository.findById(matchId);
			
			// check if match is present.
			if(matchOtp.isPresent()) {
				
				Match match = matchOtp.get();
				// check if match is part of current league.
				if(match.getLeague().getId().equals(leagueId)) {
					// check if match round is the league current round.
					if(match.getRound().getId().equals(league.getCurrentRound().getId())){
						// check if match didn't update result for it before.
						if(match.getStatus().equals(Statuses.NEW)) {
							// check if winner is one of the match players.
							if(matchRequestDto.getWinnerId() == match.getPlayer1().getId()){
								match.setWinner(match.getPlayer1());
							}else if(matchRequestDto.getWinnerId() == match.getPlayer2().getId()){
								match.setWinner(match.getPlayer2());
							}else {
								throw new LeagueException("Winner player doesn't belong to that match.");
							}
							
							// updating match results and status 
							match.setPlayer1Score(matchRequestDto.getPlayer1Score());
							match.setPlayer2Score(matchRequestDto.getPlayer2Score());
							match.setStatus(Statuses.COMPLETED);
							matchRepository.save(match);
						}else {
							throw new LeagueException("You can update results of matches with status NEW only.");
						}
					}else {
						throw new LeagueException("Match doesn't belong to current round.");
					}
				}else {
					throw new LeagueException("Match doesn't belong to that league.");
				}
			}else {
				throw new LeagueException("Match doesn't exist");
			}

			logger.info("com.digital.factory.service.impl.MatchServiceImpl updateMatchResult End");
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.MatchServiceImpl updateMatchResult "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.MatchServiceImpl updateMatchResult "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to create participant");
		}
		
	}
}
