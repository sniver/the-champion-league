package com.digital.factory;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author Mohamed Mahdy
 *
 */
@SpringBootApplication
public class TheChampionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheChampionApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}
