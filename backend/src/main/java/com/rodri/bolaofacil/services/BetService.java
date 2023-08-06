package com.rodri.bolaofacil.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.ExternalBet;
import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.enums.Tournament;
import com.rodri.bolaofacil.enitities.pk.BetPK;
import com.rodri.bolaofacil.enitities.pk.ExternalBetPK;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.ExternalBetRepository;
import com.rodri.bolaofacil.repositories.MatchRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class BetService {
	
	@Autowired
	BetRepository betRep;
	
	@Autowired
	ExternalBetRepository externalBetRep;
	
	@Autowired
	MatchRepository matchRep;
	
	@Autowired 
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;

	@Transactional
	public BetInsertDTO insert(Long sweepstakeId, BetInsertDTO dto) 
	{
		Participant participant = authService.validateParticipant(sweepstakeId);
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException());
		
		if(sweepstake.getTournament() == Tournament.PERSONALIZADO)
		{
			try
			{
				Match match = matchRep.getReferenceById(dto.getMatchId());
				Bet bet = new Bet(participant.getUser(), sweepstake, match, dto.getHomeTeamScore(), dto.getAwayTeamScore());
				betRep.save(bet);
				return new BetInsertDTO(bet);
			}
			catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
			
		}
		else
		{
			ExternalBet bet = new ExternalBet(participant.getUser(),sweepstake,dto.getMatchId(),dto.getHomeTeamScore(), dto.getAwayTeamScore());
			externalBetRep.save(bet);
			return new BetInsertDTO(bet);
		}	
	}
	
	@Transactional
	public BetInsertDTO update(Long sweepstakeId, BetInsertDTO dto)
	{
		Participant participant = authService.validateParticipant(sweepstakeId);
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException());

		if(sweepstake.getTournament() == Tournament.PERSONALIZADO)
		{
			try
			{
				Match match = matchRep.getReferenceById(dto.getMatchId());
				Bet bet = betRep.getReferenceById(new BetPK(participant.getUser(), sweepstake, match));
				bet.setHomeTeamScore(dto.getHomeTeamScore());
				bet.setAwayTeamScore(dto.getAwayTeamScore());
				betRep.save(bet);
				return new BetInsertDTO(bet);
			}
			catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
			
		}
		else
		{
			ExternalBet bet = externalBetRep.getReferenceById(new ExternalBetPK(participant.getUser(), sweepstake, dto.getMatchId()));
			bet.setHomeTeamScore(dto.getHomeTeamScore());
			bet.setAwayTeamScore(dto.getAwayTeamScore());
			externalBetRep.save(bet);
			return new BetInsertDTO(bet);
		}	
	}
}
