package com.digital.factory.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.digital.factory.dto.request.ParticipantDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Getter @Setter @ToString @NoArgsConstructor
public class MatchDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private ParticipantDto player1;
	private ParticipantDto player2;
	private ParticipantDto winner;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Africa/Cairo")
	private LocalDate matchDate;

}
