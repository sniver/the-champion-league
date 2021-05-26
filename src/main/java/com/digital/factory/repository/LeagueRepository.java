package com.digital.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digital.factory.model.League;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Repository
public interface LeagueRepository extends JpaRepository<League, Long>{

}
