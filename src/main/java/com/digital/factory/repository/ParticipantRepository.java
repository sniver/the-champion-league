package com.digital.factory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.factory.model.Participant;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long>{
	
	/**
	 * find list of participants by email
	 * @param email
	 * @return
	 */
	List<Participant> findByEmail(String email);
	
	/**
	 * find list of participants by phone.
	 * @param phone
	 * @return
	 */
	List<Participant> findByPhoneNumber(String phone);
}
