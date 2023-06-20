package com.rodri.bolaofacil.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.repositories.BetRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class BetService {
	
	@Autowired
	BetRepository betRep;
	
	@Autowired 
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional(readOnly=true)
	public List<BetDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.validateParticipant(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			User user = authService.authenticated();
			List<Bet> bets = betRep.findAllByParticipant(sweepstake, user);
			return bets.stream().map(bet -> new BetDTO(bet)).toList();
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
}
