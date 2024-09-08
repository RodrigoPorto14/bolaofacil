package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rodri.bolaofacil.entities.Bet;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.factories.SweepstakeFactory;
import com.rodri.bolaofacil.repositories.BetRepository;

@DataJpaTest
public class BetRepositoryTests {
	
	@Autowired
	private BetRepository betRepository;
	
	@Test
	public void findAllBySweepstakeShouldReturnAllSweepstakeBetsWhenSweepstakeExists()
	{
		int expectedNumberOfBets = 4;
		Sweepstake sweepstake = SweepstakeFactory.existingSweepstake();
		
		List<Bet> bets = betRepository.findAllBySweepstake(sweepstake);
		
		Assertions.assertEquals(expectedNumberOfBets, bets.size());
		bets.forEach((bet) -> {
			Assertions.assertEquals(sweepstake.getId(), bet.getSweepstake().getId());
		});
	}
	
	@Test
	public void findAllBySweepstakeShouldReturnEmptyListWhenSweepstakeDoesNotExists()
	{
		int expectedNumberOfBets = 0;
		Sweepstake sweepstake = SweepstakeFactory.nonExistingSweepstake();
		
		List<Bet> bets = betRepository.findAllBySweepstake(sweepstake);
		
		Assertions.assertEquals(expectedNumberOfBets, bets.size());
		
	}

}
