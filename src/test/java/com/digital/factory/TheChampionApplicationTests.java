package com.digital.factory;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.digital.factory.dto.Response;
import com.digital.factory.dto.request.ParticipantDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
class TheChampionApplicationTests {
	
	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@SuppressWarnings("unchecked")
	@Test
	public void createParticipants() throws Exception {
	   String uri = "/leagues/1/participants";
	   ParticipantDto participantDto = new ParticipantDto();
	   participantDto.setFirstName("test");
	   participantDto.setLastName("test");
	   participantDto.setEmail("test@yahoo.com");
	   participantDto.setPhoneNumber("01234567891");
	   participantDto.setAge(29L);
	   
	   
	   String inputJson = this.mapToJson(participantDto);
	  
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   Response<String> response = this.mapFromJson(content, Response.class);
	   assertEquals(response.getStatus(), true);
	   assertEquals(response.getCode(), "SUCCESS");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getParticipants() throws Exception {
	   String uri = "/leagues/1/participants";
	   	  
	   MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	      .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	   
	   int status = mvcResult.getResponse().getStatus();
	   assertEquals(200, status);
	   String content = mvcResult.getResponse().getContentAsString();
	   Response<String> response = this.mapFromJson(content, Response.class);
	   assertEquals(response.getStatus(), true);
	   assertEquals(response.getCode(), "SUCCESS");
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	protected <T> T mapFromJson(String json, Class<T> clazz)
	      throws JsonParseException, JsonMappingException, IOException {
	      
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
