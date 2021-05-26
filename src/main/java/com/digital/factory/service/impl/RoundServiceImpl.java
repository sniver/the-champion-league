package com.digital.factory.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.factory.common.Statuses;
import com.digital.factory.exception.LeagueException;
import com.digital.factory.model.League;
import com.digital.factory.model.Match;
import com.digital.factory.model.Participant;
import com.digital.factory.model.Round;
import com.digital.factory.repository.RoundRepository;
import com.digital.factory.service.EmailService;
import com.digital.factory.service.LeagueService;
import com.digital.factory.service.MatchService;
import com.digital.factory.service.ParticipantService;
import com.digital.factory.service.RoundService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Service
public class RoundServiceImpl implements RoundService{
	private static final Logger logger = LoggerFactory.getLogger(RoundServiceImpl.class);
	
	@Autowired
	LeagueService leagueService;
	
	@Autowired
	ParticipantService participantService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	RoundRepository roundRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	EmailService emailService;
	
	/**
	 * start league and generate round matches.
	 * @param leagueId
	 */
	@Override
	public void generateFirstRound(Long leagueId) {
		try {
			logger.info("com.digital.factory.service.impl.RoundServiceImpl generateFirstRound "
					+ "Start with leagueId = " + leagueId);
			
			// find league entity by league id.
			League league = leagueService.findById(leagueId);
			
			// check if league didn't start yet.
			if(!league.getStatus().equals(Statuses.NEW)) {
				throw new LeagueException("You can generate groups only for leagues with status NEW");
			}
			
			// check if total participants reached to max participants or not yet.
			if(league.getTotalParticipants() >= league.getMaxParticipants()) {
				List<Participant> participants = league.getParticipants();
				
				// get random participants list.
				List<Participant> randomParticipantsList = this.getRandomParticipant(participants, league.getMaxParticipants());
				
				// create round and round matches.
				this.createRound(league, randomParticipantsList);
			}else {
				throw new LeagueException("Participants count should be more than 12");
			}
			
			logger.info("com.digital.factory.service.impl.RoundServiceImpl generateFirstRound End");
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl generateFirstRound "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl generateFirstRound "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to generate matches");
		}	
	}
	
	/**
	 * select random participants from all registered participants.
	 * @param participants
	 * @param selectedParticipantCount
	 * @return
	 */
	 private List<Participant> getRandomParticipant(List<Participant> participants, Long selectedParticipantCount){
		 try {
			logger.info("com.digital.factory.service.impl.RoundServiceImpl getRandomParticipant "
					+ "Start with selectedParticipantCount = " + selectedParticipantCount);
			Random rand = new Random();
	 
			List<Participant> randomParticipantsList = new ArrayList<Participant>();
			for (int i = 0; i < selectedParticipantCount; i++) {
				int randomIndex = rand.nextInt(participants.size());
				randomParticipantsList.add(participants.get(randomIndex));
				participants.remove(randomIndex);
			}
		 
			logger.info("com.digital.factory.service.impl.RoundServiceImpl getRandomParticipant"
					+ " End randomParticipantsList = " + randomParticipantsList.size());
			return randomParticipantsList;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl getRandomParticipant "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to select random participants");
		}		
    }

	 /**
	  * create round and round matches.
	  * @param league
	  * @param participants
	  */
	 private void createRound(League league, List<Participant> participants) {
		 try {
			 logger.info("com.digital.factory.service.impl.RoundServiceImpl createRound Start");
			 Round round = new Round();
			 round.setLeague(league);
			 round.setStatus(Statuses.NEW);
			 round.setMatches(this.generateRoundMatches(league, round, participants));
			 league.setCurrentRound(round);
			 league.setStatus(Statuses.INPROGRESS);
			 roundRepository.save(round); 
		 } catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl createRound "
						+ "END With Exception with " + e.getMessage());
			throw e;
		 } catch (Exception e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl createRound "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to create round");
		 }
	 }
	 
	 /**
	  * create round matches.
	  * @param league
	  * @param round
	  * @param participants
	  * @return
	  */
	 private List<Match> generateRoundMatches(League league, Round round, List<Participant> participants) {
		 try {
			 logger.info("com.digital.factory.service.impl.RoundServiceImpl generateRoundMatches Start");
			 List<Match> roundMatches = new ArrayList<Match>();
			 LocalDate matchDate = LocalDate.now();
			 int dayMatchesCount = 0;
			 
			 /* check if count of participants is odd number, get the last participant of list and shift it to next round
			 to make count of participants is even number.*/
			 if(participants.size() % 2 != 0) {
				 int lastIndex = participants.size() - 1;
				 Match match = matchService.createMatch(league, round, participants.get(lastIndex), participants.get(lastIndex), matchDate);
				 match.setWinner(participants.get(lastIndex));
				 match.setStatus(Statuses.COMPLETED);
				 roundMatches.add(match);
				 participants.remove(lastIndex);
			 }
			 
			 // create matches between participants.
			 for(int i=0; i < participants.size(); i+=2) {
				 Match match = matchService.createMatch(league, round, participants.get(i), participants.get(i+1), matchDate);
				 roundMatches.add(match);
				 dayMatchesCount++;
				 if(dayMatchesCount == 3) {
					 matchDate = matchDate.plusDays(1);
					 dayMatchesCount = 0;
				 }
			 }
			 return roundMatches;
		 } catch (LeagueException e) {
			 logger.error("com.digital.factory.service.impl.RoundServiceImpl generateRoundMatches "
						+ "END With Exception with " + e.getMessage());
			 throw e;
		} catch (Exception e) {
	 		logger.error("com.digital.factory.service.impl.RoundServiceImpl generateRoundMatches "
					+ "END With Exception with " + e.getMessage());
	 		throw new LeagueException("Failed to create round");
	 	}
	 }

	 /**
	 * close league current round.
	 * @param leagueId
	 */
	@Override
	public void closeRound(Long leagueId) {
		try {
			logger.info("com.digital.factory.service.impl.RoundServiceImpl closeRound "
					+ "Start with leagueId = " + leagueId);
			
			// find league entity by league id.
			League league = leagueService.findById(leagueId);
			
			// check if league status is in progress.
			if(!league.getStatus().equals(Statuses.INPROGRESS)) {
				throw new LeagueException("You can close round of inprogress leagues only");
			}
			
			// get league current round.
			Round round = league.getCurrentRound();
			
			// check if round didn't close it yet.
			if(round.getStatus().equals(Statuses.NEW)) {
				
				// check if there are any matches not completed yet.
				long inCompletedMatchesCount = round.getMatches().stream().filter(match -> match.getWinner() == null || match.getStatus().equals(Statuses.NEW)).count();
				if(inCompletedMatchesCount > 0) {
					throw new LeagueException("You cannot close round and there are incompleted matches.");
				}else {
					// get winners from current round matches.
					List<Participant> winnerList = round.getMatches().stream().map(match -> {
						return match.getWinner();
					}).collect(Collectors.toList());
					
					/* if size of winners more than one mean you need to generate the next round matches.
					 * if size equal 1 mean you got your champion.
					 */
					if(winnerList.size() > 1) {
						this.createRound(league, winnerList);
						round.setStatus(Statuses.COMPLETED);
						roundRepository.save(round);
					}else if(winnerList.size() == 1){
						round.setStatus(Statuses.COMPLETED);
						round.getLeague().setTheChampion(winnerList.get(0));
						round.getLeague().setStatus(Statuses.COMPLETED);
						roundRepository.save(round);
						
						// send mail to league champion.
						emailService.sendSimpleMessage(winnerList.get(0).getEmail(), "The champion league", "congratulations !! you are the champion.");
					}
				}
			}else {
				throw new LeagueException("You can close round with status NEW only");
			}
			logger.info("com.digital.factory.service.impl.RoundServiceImpl closeRound End");
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl closeRound "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.RoundServiceImpl closeRound "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to generate matches");
		}	
	}
}
