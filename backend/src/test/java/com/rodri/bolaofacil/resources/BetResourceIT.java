package com.rodri.bolaofacil.resources;

import static com.rodri.bolaofacil.factories.BetFactory.betWithMatchStarted;
import static com.rodri.bolaofacil.factories.BetFactory.existingBet;
import static com.rodri.bolaofacil.factories.BetFactory.nonExistingBet;
import static com.rodri.bolaofacil.factories.UserFactory.ownerUser;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.entities.User;
import com.rodri.bolaofacil.tests.MockMvcRequests;
import com.rodri.bolaofacil.tests.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BetResourceIT {
	
	private Long existingSweepstakeId = 2L;
	private Long nonExistingSweepstakeId = 100L;
	private String insertOrUpdateUrl = "/boloes/{sweepstakeId}/bets";
	private User ownerUser = ownerUser();
	private BetInsertDTO betDto;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	private MockMvcRequests mockMvcRequests;
	
	@BeforeEach
    public void setUp() {
        this.mockMvcRequests = new MockMvcRequests(mockMvc, objectMapper, tokenUtil);
    }
	
	private void assertBetDto(ResultActions result) throws Exception
	{
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.matchId").value(betDto.getMatchId()));
		result.andExpect(jsonPath("$.homeTeamScore").value(betDto.getHomeTeamScore()));
		result.andExpect(jsonPath("$.awayTeamScore").value(betDto.getAwayTeamScore()));
	}
	
	
	@Test
	public void insertOrUpdateShouldReturnBadRequestWhenBetHasMatchStarted() throws Exception {
		
		betDto = new BetInsertDTO(betWithMatchStarted());
		ResultActions result = mockMvcRequests.privateMvcPost(ownerUser, betDto, insertOrUpdateUrl, existingSweepstakeId);
		
		result.andExpect(status().isBadRequest());	
	}
	
	@Test
	public void insertOrUpdateShouldReturnNotFoundWhenSweepstakeIdDoesNotExist() throws Exception {
		
		betDto = new BetInsertDTO(existingBet());
		ResultActions result = mockMvcRequests.privateMvcPost(ownerUser, betDto, insertOrUpdateUrl, nonExistingSweepstakeId);
		
		result.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIdExist() throws Exception {
		
		betDto = new BetInsertDTO(existingBet());
		ResultActions result = mockMvcRequests.privateMvcPost(ownerUser, betDto, insertOrUpdateUrl, existingSweepstakeId);
		
		assertBetDto(result);
	}
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIdExistAndBetDoesNotExist() throws Exception {
		
		betDto = new BetInsertDTO(nonExistingBet());
		ResultActions result = mockMvcRequests.privateMvcPost(ownerUser, betDto, insertOrUpdateUrl, existingSweepstakeId);
		
		assertBetDto(result);
	}
}
