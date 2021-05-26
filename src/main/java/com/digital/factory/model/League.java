package com.digital.factory.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "leagues")
@Setter @Getter @NoArgsConstructor @ToString
public class League {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id", nullable = false)
	private Long id;
	
	@Column(name = "max_participants", nullable = false)
	private Long maxParticipants = 0L; 
	
	@Column(name = "total_participants", nullable = false)
	private Long totalParticipants = 0L; 
	
	@OneToOne
	@JoinColumn(name = "current_round_id", nullable = true)
	private Round currentRound;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Statuses status;
	
	
	@ManyToOne
	@JoinColumn(name = "the_champion_id", nullable = true)
	private Participant theChampion;
	
	
	@OneToMany(mappedBy = "league")
	private List<Participant> participants;

}
