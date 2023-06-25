package com.rodri.bolaofacil.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.pk.BetPK;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.MatchRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.ForbiddenException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class BetService {
	
	@Autowired
	BetRepository betRep;
	
	@Autowired
	MatchRepository matchRep;
	
	@Autowired 
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional(readOnly=true)
	public Page<BetDTO> findAllMatchBySweepstakeWithParticipantBet(Long sweepstakeId, Integer page, Pageable pageable)
	{
		authService.validateParticipant(sweepstakeId);
		try
		{
			Integer size = 5;
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			
			if(page==null)
				page = matchRep.matchesBeforeNowBySweepstake(sweepstake)/size;
			
			pageable = PageRequest.of(page, size);	
			User user = authService.authenticated();
			matchRep.findAllBySweepstakeOrderByStartMoment(sweepstake);
			return betRep.findAllMatchBySweepstakeWithParticipantBet(sweepstake, user, pageable);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}

	@Transactional
	public BetInsertDTO insert(Long sweepstakeId, BetInsertDTO dto) 
	{
		authService.validateParticipant(sweepstakeId);
		BetPK id = getBetPK(sweepstakeId, dto.getMatchId());
		Bet bet = new Bet(id.getUser(), id.getSweepstake(), id.getMatch(), dto.getHomeTeamScore(), dto.getAwayTeamScore());
		betRep.save(bet);
		return new BetInsertDTO(bet);
	}
	
	@Transactional
	public BetInsertDTO update(Long sweepstakeId, BetInsertDTO dto)
	{
		authService.validateParticipant(sweepstakeId);
		BetPK id = getBetPK(sweepstakeId, dto.getMatchId());
		try
		{
			Bet bet = betRep.getReferenceById(id);
			
			if(bet.getSweepstake().getId() != sweepstakeId) 
				throw new ForbiddenException("Access denied");
			
			copyDtoToEntity(dto, bet);
			betRep.save(bet);
			return new BetInsertDTO(bet);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found"); }
	}
	
	private BetPK getBetPK(Long sweepstakeId, Long matchId)
	{
		try
		{
			User user = authService.authenticated();
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Match match = matchRep.getReferenceById(matchId);
			return new BetPK(user, sweepstake, match);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "); }
	}
	
	private void copyDtoToEntity(BetInsertDTO dto, Bet entity)
	{
		entity.setHomeTeamScore(dto.getHomeTeamScore());
		entity.setAwayTeamScore(dto.getAwayTeamScore());
	}
}
