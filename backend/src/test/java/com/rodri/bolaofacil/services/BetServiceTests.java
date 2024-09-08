package com.rodri.bolaofacil.services;

import static com.rodri.bolaofacil.factories.BetFactory.existingBet;
import static com.rodri.bolaofacil.factories.ExternalBetFactory.existingExternalBet;
import static com.rodri.bolaofacil.factories.MatchFactory.futureMatch;
import static com.rodri.bolaofacil.factories.MatchFactory.pastMatch;
import static com.rodri.bolaofacil.factories.ParticipantFactory.existingParticipant;
import static com.rodri.bolaofacil.factories.SweepstakeFactory.customSweepstake;
import static com.rodri.bolaofacil.factories.SweepstakeFactory.existingSweepstake;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.entities.Bet;
import com.rodri.bolaofacil.entities.Match;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.ExternalBetRepository;
import com.rodri.bolaofacil.repositories.MatchRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.AuthService;
import com.rodri.bolaofacil.services.BetService;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class BetServiceTests {

	private Long existingSweepstakeId = 1L;
	private Long nonExistingSweepstakeId = 2L;
	private BetInsertDTO betDto;
	
	@InjectMocks
	BetService betService;
	
	@Mock
	AuthService authService;
	
	@Mock
	SweepstakeRepository sweepstakeRep;
	
	@Mock
	MatchRepository matchRep;
	
	@Mock
	BetRepository betRep;
	
	@Mock
	ExternalBetRepository externalBetRep;
	
	@BeforeEach
	private void setUp()
	{
		Bet bet = existingBet();
		betDto = new BetInsertDTO(bet);
		when(authService.validateParticipant(existingSweepstakeId)).thenReturn(existingParticipant());
		when(sweepstakeRep.findById(nonExistingSweepstakeId)).thenReturn(Optional.empty());
		when(betRep.save(any(Bet.class))).thenReturn(bet);	
	}
	
	private void mockSetUp(Match match, Sweepstake sweepstake)
	{
		when(sweepstakeRep.findById(existingSweepstakeId)).thenReturn(Optional.of(sweepstake));
		when(matchRep.findById(betDto.getMatchId())).thenReturn(Optional.of(match));
		doNothing().when(authService).resourceBelongsSweepstake(match.getSweepstake().getId(), existingSweepstakeId);
	}
	
	private void assertBetDto(BetInsertDTO newBetDto)
	{
		assertNotNull(newBetDto);
		assertEquals(betDto.getMatchId(), newBetDto.getMatchId());
	    assertEquals(betDto.getHomeTeamScore(), newBetDto.getHomeTeamScore());
	    assertEquals(betDto.getAwayTeamScore(), newBetDto.getAwayTeamScore());
	}
	
	
	@Test
	public void insertOrUpdateShouldThrowResourceNotFoundExceptionWhenSweepstakeIdDoesNotExists()
	{
		assertThrows(ResourceNotFoundException.class, () -> {
			betService.insertOrUpdate(nonExistingSweepstakeId, betDto);
	    });
	}
	
	@Test
	public void insertOrUpdateShouldThrowDataBaseExceptionWhenSweepstakeIsCustomAndMatchAlreadyStarted()
	{
		mockSetUp(pastMatch(), customSweepstake());
		
		assertThrows(DataBaseException.class, () -> {
	        betService.insertOrUpdate(existingSweepstakeId, betDto);
	    });
	}
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIsCustomAndBetDoesNotExists()
	{
		mockSetUp(futureMatch(), customSweepstake());
		when(betRep.findById(any())).thenReturn(Optional.empty());
		
		BetInsertDTO insertedBetDto = betService.insertOrUpdate(existingSweepstakeId, betDto);

	    assertBetDto(insertedBetDto);
	}
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIsCustomAndBetExists()
	{
		mockSetUp(futureMatch(), customSweepstake());
		when(betRep.findById(any())).thenReturn(Optional.of(existingBet()));
		
		BetInsertDTO updatedBetDto = betService.insertOrUpdate(existingSweepstakeId, betDto);
		
		assertBetDto(updatedBetDto);
	}
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIsNotCustomAndExternalBetDoesNotExists()
	{
		mockSetUp(futureMatch(), existingSweepstake());
		when(externalBetRep.findById(any())).thenReturn(Optional.empty());
		
		BetInsertDTO insertedBetDto = betService.insertOrUpdate(existingSweepstakeId, betDto);

	    assertBetDto(insertedBetDto);
	}
	
	@Test
	public void insertOrUpdateShouldReturnBetDtoWhenSweepstakeIsNotCustomAndExternalBetExists()
	{
		mockSetUp(futureMatch(), existingSweepstake());
		when(externalBetRep.findById(any())).thenReturn(Optional.of(existingExternalBet()));
		
		BetInsertDTO updatedBetDto = betService.insertOrUpdate(existingSweepstakeId, betDto);
		
		assertBetDto(updatedBetDto);
	}
	
}
