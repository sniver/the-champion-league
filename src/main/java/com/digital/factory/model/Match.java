package com.digital.factory.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.digital.factory.common.Statuses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Entity
@Table(name = "matches")
@Setter @Getter @NoArgsConstructor @ToString
public class Match {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "league_id", nullable = false)
	private League league;
	
	@OneToOne
	@JoinColumn(name = "round_id", nullable = true)
	private Round round;
	
	@ManyToOne
	@JoinColumn(name = "player1_id", nullable = false)
	private Participant player1;
	
	@ManyToOne
	@JoinColumn(name = "player2_id", nullable = false)
	private Participant player2;
	
	
	@ManyToOne
	@JoinColumn(name = "winner", nullable = true)
	private Participant winner;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Statuses status;
	
	@Column(name = "match_date", nullable = false)
	private LocalDate matchDate;
	
	
	@Column(name = "player1_score", nullable = true)
	private Long player1Score = 0L;
	
	
	@Column(name = "player2_score", nullable = true)
	private Long player2Score = 0L;

}
