package com.digital.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.factory.model.Round;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Repository
public interface RoundRepository extends JpaRepository<Round, Long>{

}
