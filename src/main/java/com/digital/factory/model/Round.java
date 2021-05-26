package com.digital.factory.model;


import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "rounds")
@Setter @Getter @NoArgsConstructor @ToString
public class Round {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "league_id", nullable = false)
	private League league;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Statuses status;
	
	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL)
	private List<Match> matches;
}
