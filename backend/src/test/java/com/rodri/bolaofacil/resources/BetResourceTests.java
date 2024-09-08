package com.rodri.bolaofacil.resources;

import static com.rodri.bolaofacil.factories.BetFactory.existingBet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.resources.BetResource;
import com.rodri.bolaofacil.services.BetService;
import com.rodri.bolaofacil.services.UserService;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;
import com.rodri.bolaofacil.tests.MockMvcRequests;

@WebMvcTest(value = BetResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class} )
public class BetResourceTests {

	private Long existingSweepstakeId = 1L;
	private Long nonExistingSweepstakeId = 2L;
	private BetInsertDTO betDto = new BetInsertDTO(existingBet());
	private String insertOrUpdateEndpoint = "/boloes/{sweepstakeId}/bets";
	
	private MockMvcRequests mockMvcRequests;
	
	@MockBean
	private BetService betService;
	
	@MockBean
    private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
    public void setUp() {
        this.mockMvcRequests = new MockMvcRequests(mockMvc, objectMapper);
    }
	
	@Test
	public void insertOrUpdateShouldReturnNotFoundWhenSweepstakeIdDoesNotExist() throws Exception
	{
		doThrow(ResourceNotFoundException.class).when(betService).insertOrUpdate(eq(nonExistingSweepstakeId), any());
		
		ResultActions result = mockMvcRequests.mvcPost(betDto, insertOrUpdateEndpoint, nonExistingSweepstakeId);
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIdExists() throws Exception
	{
		when(betService.insertOrUpdate(eq(existingSweepstakeId), any())).thenReturn(betDto);
		
		ResultActions result = mockMvcRequests.mvcPost(betDto, insertOrUpdateEndpoint, existingSweepstakeId);
		
		result.andExpect(status().isOk())
		      .andExpect(jsonPath("$.matchId").value(betDto.getMatchId()))
		      .andExpect(jsonPath("$.homeTeamScore").value(betDto.getHomeTeamScore()))
		      .andExpect(jsonPath("$.awayTeamScore").value(betDto.getAwayTeamScore()));
	}
}
