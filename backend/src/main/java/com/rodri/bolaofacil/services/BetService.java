package com.rodri.bolaofacil.services;

import java.time.Instant;

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
import com.rodri.bolaofacil.enitities.pk.BetPK;
import com.rodri.bolaofacil.enitities.pk.ExternalBetPK;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.ExternalBetRepository;
import com.rodri.bolaofacil.repositories.MatchRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
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
	public BetInsertDTO insertOrUpdate(Long sweepstakeId, BetInsertDTO dto) 
	{
		Participant participant = authService.validateParticipant(sweepstakeId);
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException());
		
		if(sweepstake.getLeague().isCustom())
		{
			Match match = matchRep.findById(dto.getMatchId()).orElseThrow(() -> new ResourceNotFoundException());
			authService.resourceBelongsSweepstake(match.getSweepstake().getId(), sweepstakeId);
			checkMatchAlreadyStarted(match);
			try
			{
				BetPK id = new BetPK(participant.getUser(), sweepstake, match);
				Bet bet = betRep.findById(id).orElseThrow(() -> new EntityNotFoundException());
				bet.setHomeTeamScore(dto.getHomeTeamScore());
				bet.setAwayTeamScore(dto.getAwayTeamScore());
				betRep.save(bet);
				return new BetInsertDTO(bet);
			}
			catch(EntityNotFoundException e) 
			{ 
				Bet bet = new Bet(participant.getUser(), sweepstake, match, dto.getHomeTeamScore(), dto.getAwayTeamScore());
				betRep.save(bet);
				return new BetInsertDTO(bet);
			}
		}
		else
		{
			try
			{
				ExternalBetPK id = new ExternalBetPK(participant.getUser(), sweepstake, dto.getMatchId());
				ExternalBet bet = externalBetRep.findById(id).orElseThrow(() -> new EntityNotFoundException());
				bet.setHomeTeamScore(dto.getHomeTeamScore());
				bet.setAwayTeamScore(dto.getAwayTeamScore());
				externalBetRep.save(bet);
				return new BetInsertDTO(bet);
			}
			catch(EntityNotFoundException e)
			{
				ExternalBet bet = new ExternalBet(participant.getUser(),sweepstake,dto.getMatchId(),dto.getHomeTeamScore(), dto.getAwayTeamScore());
				externalBetRep.save(bet);
				return new BetInsertDTO(bet);
			}	
		}	
	}
	
	private void checkMatchAlreadyStarted(Match match)
	{
		if(match.getStartMoment().compareTo(Instant.now()) <= 0)
			throw new DataBaseException("Essa partida já começou");
	}
}
