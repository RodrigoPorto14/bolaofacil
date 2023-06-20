package com.rodri.bolaofacil.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.MatchInsertDTO;
import com.rodri.bolaofacil.dto.MatchSampleDTO;
import com.rodri.bolaofacil.dto.MatchUpdateDTO;
import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.repositories.MatchRepository;
import com.rodri.bolaofacil.repositories.RuleRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.repositories.TeamRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ForbiddenException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class MatchService {
	
	@Autowired
	MatchRepository matchRep;
	
	@Autowired
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	RuleRepository ruleRep;
	
	@Autowired
	TeamRepository teamRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional(readOnly=true)
	public MatchUpdateDTO findById(Long sweepstakeId, Long id)
	{
		authService.participantIsOwner(sweepstakeId);
		Match match = matchRep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		if(match.getSweepstake().getId() != sweepstakeId) 
			throw new ForbiddenException("Access denied");
		
		return new MatchUpdateDTO(match);
	}
	
	@Transactional(readOnly=true)
	public List<MatchSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Match> matches = matchRep.findAllBySweepstakeOrderByStartMoment(sweepstake);
			return matches.stream().map( match -> new MatchSampleDTO(match)).toList();
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	@Transactional
	public MatchInsertDTO insert(Long sweepstakeId, MatchInsertDTO dto)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Match match = new Match();
			copyInsertDtoToEntity(match, dto);
			match.setSweepstake(sweepstake);
			matchRep.save(match);
			return new MatchInsertDTO(match);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	@Transactional
	public MatchUpdateDTO update(Long sweepstakeId, Long id, MatchUpdateDTO dto)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Match match = matchRep.getReferenceById(id);
			
			if(match.getSweepstake().getId() != sweepstakeId) 
				throw new ForbiddenException("Access denied");
			
			copyDtoToEntity(match,dto);
			matchRep.save(match);
			return new MatchUpdateDTO(match);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+id); }
	}
	
	public void delete(Long sweepstakeId, Long id) 
	{
		authService.participantIsOwner(sweepstakeId);
		try{matchRep.deleteById(id);}
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException("Id not found " +id);}
		catch(DataIntegrityViolationException e) {throw new DataBaseException("Integrity violation");}
	}
	
	private void copyInsertDtoToEntity(Match entity, MatchInsertDTO dto)
	{
		Long id = dto.getRuleId();;
		try
		{
			entity.setRule(ruleRep.getReferenceById(id));
			
			id = dto.getHomeTeamId();
			entity.setHomeTeam(teamRep.getReferenceById(id));
			
			id = dto.getAwayTeamId();
			entity.setAwayTeam(teamRep.getReferenceById(id));
			
			entity.setType(dto.getType());
			entity.setStartMoment(dto.getStartMoment());
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+id); }
	}
	
	private void copyDtoToEntity(Match entity, MatchUpdateDTO dto)
	{
		copyInsertDtoToEntity(entity,dto);
		entity.setHomeTeamScore(dto.getHomeTeamScore());
		entity.setAwayTeamScore(dto.getAwayTeamScore());
	}
}
	
