package com.rodri.bolaofacil.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.dto.CompetitorDTO;
import com.rodri.bolaofacil.dto.MatchDTO;
import com.rodri.bolaofacil.dto.RuleDTO;
import com.rodri.bolaofacil.dto.SweepstakeDetailsDTO;
import com.rodri.bolaofacil.enitities.ExternalBet;
import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.enums.Tournament;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.ExternalBetRepository;
import com.rodri.bolaofacil.repositories.MatchRepository;
import com.rodri.bolaofacil.repositories.ParticipantRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class SweepstakeDetailsService {
	
	@Autowired
	BetRepository betRep;
	
	@Autowired
	ExternalBetRepository externalBetRep;
	
	@Autowired
	ParticipantRepository participantRep;
	
	@Autowired 
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	MatchRepository matchRep;
	
	@Autowired
	LeaguesService leaguesService;
	
	@Autowired
	AuthService authService;
	
	
	@Transactional(readOnly=true)
	public SweepstakeDetailsDTO findSweepstakeDetails(Long sweepstakeId, Integer page)
	{
		Participant participant = authService.validateParticipant(sweepstakeId);
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		matchRep.findAllBySweepstakeOrderByStartMoment(sweepstake);
		int SIZE = 5;
		
		if(sweepstake.getTournament() == Tournament.PERSONALIZADO)
			 return findDetails(participant, sweepstake, page, SIZE);
		
		return findExternalDetails(participant, sweepstake, page, SIZE);	 
	}
	
	private SweepstakeDetailsDTO findDetails(Participant participant, Sweepstake sweepstake, Integer page, int size)
	{
		if(page == null)
			page = matchRep.matchesBeforeNowBySweepstake(sweepstake)/size;
			
		Pageable pageable = PageRequest.of(page, size);
		Page<BetDTO> pagedBets = betRep.findAllMatchBySweepstakeWithParticipantBet(sweepstake, participant.getUser(), pageable);
		
		List<BetDTO> bets = betRep.findAllBySweepstake(sweepstake).stream().map(bet -> new BetDTO(bet)).toList();
		List<CompetitorDTO> ranking = generateRanking(bets, sweepstake);
		
		return new SweepstakeDetailsDTO(pagedBets, ranking);
	}
	
	private SweepstakeDetailsDTO findExternalDetails(Participant participant, Sweepstake sweepstake, Integer page, int size)
	{
		User user = participant.getUser();
		List<MatchDTO> matches = leaguesService.findCBLOLMatches(sweepstake, user, page, size);
		
		if(page == null)
			page = matchesBeforeNow(matches)/size;
		
		List<BetDTO> bets = leaguesService.toBetsDTO(matches, sweepstake, user);
		Page<BetDTO> pagedBets = leaguesService.toPaged(bets, page, size);
		
		List<ExternalBet> externalBets = externalBetRep.findAllBySweepstake(sweepstake);
		bets = externalBets.stream().map(externalBet -> externalBetToBetDto(externalBet, matches)).toList();
		List<CompetitorDTO> ranking = generateRanking(bets, sweepstake);
		
		return new SweepstakeDetailsDTO(pagedBets, ranking);
	}
	
	private Integer matchesBeforeNow(List<MatchDTO> matches)
	{
		Instant now = Instant.now();
		int matchesBeforeNow = 0;

		for(int i = 0; i < matches.size() ; i++)
		{
			if(matches.get(i).getStartMoment().compareTo(now) < 0)
				matchesBeforeNow++;
		}
		
		return matchesBeforeNow;
	}
	
	private BetDTO externalBetToBetDto(ExternalBet bet, List<MatchDTO> matches)
	{
		for(MatchDTO match : matches)
		{
			if(bet.getExternalMatchId().equals(match.getId()))
				return new BetDTO(bet, match);
		}
		return null;
	}
	
	private List<CompetitorDTO> generateRanking(List<BetDTO> bets, Sweepstake sweepstake)
	{
		List<Participant> participants = participantRep.findAllBySweepstake(sweepstake);
		HashMap<Long, CompetitorDTO> competitors = createCompetitors(participants);
		
		for(BetDTO bet : bets)
			updateCompetitor(bet, competitors.get(bet.getUserId()));
		
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
	
	private void updateCompetitor(BetDTO bet, CompetitorDTO competitor)
	{
		Integer homeScore = bet.getMatch().getHomeTeamScore();
		Integer awayScore = bet.getMatch().getAwayTeamScore();
		
		if(homeScore == null || awayScore == null)
			return;
		
		RuleDTO rule = bet.getMatch().getRule();
		
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
			competitor.upExactScore(rule.getExactScore());
		
		else if(hitWinner && winner == winnerBetted)
			competitor.upWinnerScore(rule.getWinnerScore());
		
		else if( (homeScore - awayScore) == (homeScoreBetted - awayScoreBetted) )
			competitor.upScoreDifference(rule.getScoreDifference());
		
		else if(hitWinner && loser == loserBetted)
			competitor.upLoserScore(rule.getLoserScore());
		
		else if(hitWinner)
			competitor.upWinner(rule.getWinner()); 	
		
	}
}
