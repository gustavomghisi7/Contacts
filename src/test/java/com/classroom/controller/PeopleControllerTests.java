package com.classroom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.classroom.entity.People;
import com.classroom.services.PeopleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PeopleControllerTests {
	
	private long existingId;
	private long nonExistentId;
	private People existingPerson;
	private People personNew;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PeopleService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		existingId = 1L;
		nonExistentId = 100L;
		
		personNew = new People();
		existingPerson = new People("Maria");
		existingPerson.setId(1L);
		
		Mockito.when(service.consultPeopleById(existingId)).thenReturn(existingPerson);
		Mockito.doThrow(EntityNotFoundException.class).when(service).consultPeopleById(nonExistentId);
		Mockito.when(service.savePeople(any())).thenReturn(existingPerson);
		Mockito.when(service.alterPeople(eq(existingId), any())).thenReturn(existingPerson);
		Mockito.when(service.alterPeople(eq(nonExistentId), any())).thenThrow(EntityNotFoundException.class);
	}
	
	@Test
	public void shouldReturnPersonWhenQueryExistingId() throws Exception {
		ResultActions result = mockMvc.perform(get("/people/{idpeople}", existingId)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturn404WhenQueryExistingId() throws Exception {
		ResultActions result = mockMvc.perform(get("/people/{idpeople}", nonExistentId)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturn204SaveSuccessfully() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(personNew);
		ResultActions result = mockMvc.perform(post("/people")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isCreated());
				
	}
	
	@Test
	public void shouldReturnOkWhenChangeSuccessfully() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(existingPerson);
		ResultActions result = mockMvc.perform(put("/people/{idpeople}", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
			result.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturn404WhenChangingNonExistentContact() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(personNew);
		ResultActions result = mockMvc.perform(put("/people/{idpeople}", nonExistentId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
			result.andExpect(status().isNotFound());
	}
	
	@Test
	public void returnsListWhenItSuccessfullyQueriesAll() throws Exception {
		Mockito.when(service.consultAll()).thenReturn(new ArrayList<>());
		ResultActions result = mockMvc.perform(get("/people"));
		result.andExpect(status().isOk());
	}
	
}
