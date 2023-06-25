package com.rodri.bolaofacil.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.CompetitorDTO;
import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.ParticipantRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class RankingService {
	
	@Autowired
	BetRepository betRep;
	
	@Autowired
	ParticipantRepository participantRep;
	
	@Autowired 
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional(readOnly=true)
	public List<CompetitorDTO> findRankingBySweepstake(Long sweepstakeId)
	{
		authService.validateParticipant(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Participant> participants = participantRep.findAllBySweepstake(sweepstake);
			List<Bet> bets = betRep.findAllBySweepstake(sweepstake);
			return generateRanking(bets, participants);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	private List<CompetitorDTO> generateRanking(List<Bet> bets, List<Participant> participants)
	{
		HashMap<Long, CompetitorDTO> competitors = createCompetitors(participants);
		
		for(Bet bet : bets)
		{
			updateCompetitor(bet, competitors.get(bet.getUser().getId()));
		}
		
		List<CompetitorDTO> ranking = new ArrayList<>(competitors.values());
		Collections.sort(ranking);
		return ranking;
	}
	
	private HashMap<Long,CompetitorDTO> createCompetitors(List<Participant> participants)
	{
		HashMap<Long, CompetitorDTO> competitors = new HashMap<>();
		for(Participant participant: participants)
		{
			competitors.put(participant.getUser().getId(), new CompetitorDTO(participant.getUser().getNickname()));
		}
		return competitors;
	}
	
	private void updateCompetitor(Bet bet, CompetitorDTO competitor)
	{
		Integer homeScore = bet.getMatch().getHomeTeamScore();
		Integer awayScore = bet.getMatch().getAwayTeamScore();
		
		if(homeScore == null || awayScore == null)
			return;
		
		int homeScoreBetted = bet.getHomeTeamScore();
		int awayScoreBetted = bet.getAwayTeamScore();
		
		boolean draw = homeScore == awayScore;
		boolean drawBetted = homeScoreBetted == awayScoreBetted;
		
		boolean hitWinner =  !draw && !drawBetted && (homeScore > awayScore) == (homeScoreBetted > awayScoreBetted);
		
		int winner = Math.max(homeScore,awayScore);
		int winnerBetted = Math.max(homeScoreBetted,awayScoreBetted);
		
		int loser = Math.min(homeScore,awayScore);
		int loserBetted = Math.min(homeScoreBetted,awayScoreBetted);
		
		if(homeScore == homeScoreBetted && awayScore == awayScoreBetted)
			competitor.upExactScore(bet.getMatch().getRule().getExactScore());
		
		else if(hitWinner && winner == winnerBetted)
			competitor.upWinnerScore(bet.getMatch().getRule().getWinnerScore());
		
		else if( (homeScore - awayScore) == (homeScoreBetted - awayScoreBetted) )
			competitor.upScoreDifference(bet.getMatch().getRule().getScoreDifference());
		
		else if(hitWinner && loser == loserBetted)
			competitor.upLoserScore(bet.getMatch().getRule().getLoserScore());
		
		else if(hitWinner)
			competitor.upWinner(bet.getMatch().getRule().getWinner()); 	
		
	}
}
