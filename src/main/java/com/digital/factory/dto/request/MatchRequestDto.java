package com.digital.factory.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@Getter @Setter @NoArgsConstructor @ToString
public class MatchRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "winnder is required")
	@Min(value = 1, message = "winnder min value is 1")
	@NumberFormat(style = Style.NUMBER)
	private Long winnerId;
	
	@NotNull(message = "player 1 score is required")
	@Min(value = 0, message = "player 1 score min value is 0")
	@NumberFormat(style = Style.NUMBER)
	private Long player1Score;
	
	@NotNull(message = "player 2 score is required")
	@Min(value = 0, message = "player 2 score min value is 0")
	@NumberFormat(style = Style.NUMBER)
	private Long player2Score;
}
