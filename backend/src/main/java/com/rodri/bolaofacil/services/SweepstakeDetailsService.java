package com.rodri.bolaofacil.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.dto.CompetitorDTO;
import com.rodri.bolaofacil.dto.MatchDTO;
import com.rodri.bolaofacil.dto.RuleDTO;
import com.rodri.bolaofacil.dto.SweepstakeDetailsDTO;
import com.rodri.bolaofacil.entities.ExternalBet;
import com.rodri.bolaofacil.entities.League;
import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;
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
	LeagueService leagueService;
	
	@Autowired
	AuthService authService;
	
	
	@Transactional(readOnly=true)
	public SweepstakeDetailsDTO findSweepstakeDetails(Long sweepstakeId, Integer page)
	{
		Participant participant = authService.validateParticipant(sweepstakeId);
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException());
		matchRep.findAllBySweepstakeOrderByStartMoment(sweepstake);
		int SIZE = 5;
		
		if(sweepstake.getLeague().isCustom())
			 return findDetails(participant, sweepstake, page, SIZE);
		
		return findExternalDetails(participant, sweepstake, page, SIZE);	 
	}
	
	private SweepstakeDetailsDTO findDetails(Participant participant, Sweepstake sweepstake, Integer page, int size)
	{
		if(page == null)
			page = matchRep.matchesBeforeNowBySweepstake(sweepstake)/size;
			
		Pageable pageable = PageRequest.of(page, size);
		Page<BetDTO> pagedBets = betRep.findAllMatchBySweepstakeWithParticipantBet(sweepstake, participant.getUser(), pageable);
		
		List<BetDTO> bets = betRep.findAllBySweepstake(sweepstake).stream().map(bet -> new BetDTO(bet)).collect(Collectors.toList());
		List<CompetitorDTO> ranking = generateRanking(bets, sweepstake);
		
		return new SweepstakeDetailsDTO(pagedBets, ranking);
	}
	
	private SweepstakeDetailsDTO findExternalDetails(Participant participant, Sweepstake sweepstake, Integer page, int size)
	{
		League league = sweepstake.getLeague();
		String leagueEndpoint = league.getEndpoint();
		String leagueSeason = league.getSeason();
		List<MatchDTO> matches = leagueService.getMatches(leagueEndpoint, leagueSeason);
		
		Collections.sort(matches);
		
		if(page == null)
			page = nextMatchIndex(matches) / size;
		
		User user = participant.getUser();
		List<BetDTO> bets = toBetsDTO(matches, sweepstake, user);
		Page<BetDTO> pagedBets = toPaged(bets, page, size);
		
		List<ExternalBet> externalBets = externalBetRep.findAllBySweepstake(sweepstake);
		bets = externalBets.stream().map(externalBet -> externalBetToBetDto(externalBet, matches)).collect(Collectors.toList());
		List<CompetitorDTO> ranking = generateRanking(bets, sweepstake);
		
		return new SweepstakeDetailsDTO(pagedBets, ranking);
	}
	
	private int nextMatchIndex(List<MatchDTO> matches)
	{
		Instant now = Instant.now();
		int i;

		for(i = 0; i < matches.size() ; i++)
		{
			if(matches.get(i).getStartMoment().compareTo(now) > 0)
				return i;
		}
		
		return --i;
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
	
	private List<BetDTO> toBetsDTO(List<MatchDTO> matches, Sweepstake sweepstake, User user)
	{
		List<ExternalBet> bets = externalBetRep.findAllByParticipant(sweepstake, user);
		return matches.stream().map(match -> toBetDTO(match,bets)).collect(Collectors.toList());
	}
	
	private BetDTO toBetDTO(MatchDTO match, List<ExternalBet> bets)
	{
		for(ExternalBet bet : bets)
		{
			if(match.getId().equals(bet.getExternalMatchId()))
				return new BetDTO(match, bet.getHomeTeamScore(), bet.getAwayTeamScore());	
		}
		
		return new BetDTO(match, null, null);
	}
	
	private Page<BetDTO> toPaged(List<BetDTO> bets, Integer page, int size)
	{
        int qtdMatches = bets.size();

        if (page == null)
            page = 0;

        if (page < 0 || page > qtdMatches / size)
            throw new IllegalArgumentException("Número da página inválido.");

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, qtdMatches);

        bets = (fromIndex < toIndex) ? bets.subList(fromIndex, toIndex) : Collections.emptyList();
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(bets, pageable, qtdMatches);
	}
	
	private List<CompetitorDTO> generateRanking(List<BetDTO> bets, Sweepstake sweepstake)
	{
		List<Participant> participants = participantRep.findAllBySweepstake(sweepstake);
		HashMap<Long, CompetitorDTO> competitors = createCompetitors(participants);
		
		for(BetDTO bet : bets)
		{
			Long userId = bet.getUserId();
			if(competitors.containsKey(userId))
				updateCompetitor(bet, competitors.get(userId));
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
