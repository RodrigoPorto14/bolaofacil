package com.rodri.bolaofacil.services;

import static com.rodri.bolaofacil.factories.BetFactory.betWithMatchStarted;
import static com.rodri.bolaofacil.factories.BetFactory.existingBet;
import static com.rodri.bolaofacil.factories.BetFactory.nonExistingBet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.services.BetService;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;

@SpringBootTest
@Transactional
public class BetServiceIT {
	
	private Long customSweepstakeId = 2L;
	private BetInsertDTO betDto;
	
	@Autowired
	BetService betService;
	
	@Autowired
	BetRepository betRep;
	
	@BeforeEach
	public void setUp() throws Exception
	{
		Authentication authentication = new UsernamePasswordAuthenticationToken("rodrigo@gmail.com", "654321");
        SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private void assertBetDto(BetInsertDTO newBetDto)
	{
		assertEquals(betDto.getMatchId(), newBetDto.getMatchId());
		assertEquals(betDto.getHomeTeamScore(), newBetDto.getHomeTeamScore());
		assertEquals(betDto.getAwayTeamScore(), newBetDto.getAwayTeamScore());
	}
	
	@Test
	public void insertOrUpdateShouldThrowDataBaseExceptionWhenBetHasStartedMatch()
	{
		betDto = new BetInsertDTO(betWithMatchStarted());
		
		assertThrows(DataBaseException.class, () -> {
			betService.insertOrUpdate(customSweepstakeId, betDto);
	    });
		
	}
	
	@Test
	public void insertOrUpdateShouldInsertAndReturnBetDtoWhenBetDoesNotExist()
	{
		betDto = new BetInsertDTO(nonExistingBet());
		long numberOfBetsBefore = betRep.count();
		
		BetInsertDTO insertedBetDto = betService.insertOrUpdate(customSweepstakeId, betDto);
		
		assertEquals(numberOfBetsBefore + 1, betRep.count());
		assertBetDto(insertedBetDto);
	}
	
	@Test
	public void insertOrUpdateShouldUpdateAndReturnBetDtoWhenBetExist()
	{
		betDto = new BetInsertDTO(existingBet());
		long numberOfBetsBefore = betRep.count();
		
		BetInsertDTO updatedBetDto = betService.insertOrUpdate(customSweepstakeId, betDto);
		
		assertEquals(numberOfBetsBefore, betRep.count());
		assertBetDto(updatedBetDto);
	}

}
