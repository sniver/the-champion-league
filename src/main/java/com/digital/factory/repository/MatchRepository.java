package com.digital.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.factory.model.Match;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{

}
