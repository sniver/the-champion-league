package com.digital.factory.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digital.factory.common.Statuses;
import com.digital.factory.dto.request.ParticipantDto;
import com.digital.factory.exception.LeagueException;
import com.digital.factory.model.League;
import com.digital.factory.model.Participant;
import com.digital.factory.repository.ParticipantRepository;
import com.digital.factory.service.LeagueService;
import com.digital.factory.service.ParticipantService;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Service
public class ParticipantServiceImpl implements ParticipantService{
	private static final Logger logger = LoggerFactory.getLogger(ParticipantServiceImpl.class);
	
	@Autowired
	ParticipantRepository participantRepository;
	
	
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	LeagueService leagueService;
	
	/**
	 * register new participant to specific league.
	 * @param participantDto holding participant basic information
	 * @param leagueId determine which league.
	 */
	@Override
	public void createParticipant(ParticipantDto participantDto, Long leagueId) {
		try {
			logger.info("com.digital.factory.service.impl.ParticipantServiceImpl createParticipant "
					+ "Start with participantDto = " + participantDto.toString());
			
			// find league entity by league id.
			League league = leagueService.findById(leagueId);
			
			// check if league didn't start yet.
			if(!league.getStatus().equals(Statuses.NEW)) {
				throw new LeagueException("You can submit your request only for leagues with status NEW");
			}
			
			// check email uniqueness.
			this.checkEmailUniqueness(participantDto.getEmail());
			
			// check phone uniqueness.
			this.checkPhoneUniqueness(participantDto.getPhoneNumber());
			
			// map participantDto to participant entity.
			Participant entity = mapper.map(participantDto, Participant.class);
			entity.setId(null);
			entity.setLeague(league);
			
			// update total participants count.
			league.setTotalParticipants(league.getTotalParticipants() + 1);
			participantRepository.save(entity);
			
			logger.info("com.digital.factory.service.impl.ParticipantServiceImpl createParticipant End");
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl createParticipant "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl createParticipant "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to create participant");
		}
	}
	
	/**
	 * List all participants for specific league.
	 * @param leagueId
	 * @return
	 */
	@Override
	public List<ParticipantDto> findAllParticipants(Long leagueId) {
		try {
			logger.info("com.digital.factory.service.impl.ParticipantServiceImpl findAllParticipants"
					+ " Start leagueId = " + leagueId );
			
			// find league entity by league id.
			League league = leagueService.findById(leagueId);
			
			// fetch list of participants.
			List<Participant> participants = league.getParticipants();
			
			// map list of participants to list of participantsDto.
			List<ParticipantDto> participantsDto = participants.stream().map(participant -> {
				return mapper.map(participant, ParticipantDto.class);
			}).collect(Collectors.toList());
			
			logger.info("com.digital.factory.service.impl.ParticipantServiceImpl findAllParticipants "
					+ "End with participantsDto = " + participantsDto.size());
			return participantsDto;
			
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl findAllParticipants "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl findAllParticipants "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to find all participants");
		}
	}
	
	/**
	 * check email uniqueness
	 * @param email
	 */
	private void checkEmailUniqueness(String email) {
		try {
			logger.info("com.digital.factory.service.impl.ParticipantServiceImpl checkEmailUniqueness "
					+ "Start with email = " + email);
			
			// list all participants by email.
			List<Participant> participants= participantRepository.findByEmail(email);
			
			// if returned list size more than zero mean there are participants registered with the same email.
			if(participants.size() > 0) {
				throw new LeagueException("Email should be unique");
			}
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl checkEmailUniqueness "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl checkEmailUniqueness "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to check email uniqueness");
		}
	}
	
	/**
	 * check phone uniqueness
	 * @param phone
	 */
	private void checkPhoneUniqueness(String phone) {
		try {
			logger.info("com.digital.factory.service.impl.ParticipantServiceImpl checkPhoneUniqueness "
					+ "Start with phone = " + phone);
			
			// list all participants by phone.
			List<Participant> participants= participantRepository.findByPhoneNumber(phone);
			
			// if returned list size more than zero mean there are participants registered with the same phone.
			if(participants.size() > 0) {
				throw new LeagueException("Phone number should be unique");
			}
		} catch (LeagueException e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl checkPhoneUniqueness "
					+ "END With Exception with " + e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("com.digital.factory.service.impl.ParticipantServiceImpl checkPhoneUniqueness "
					+ "END With Exception with " + e.getMessage());
			throw new LeagueException("Failed to check phone number uniqueness");
		}
	}
}
