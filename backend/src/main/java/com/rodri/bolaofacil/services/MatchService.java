package com.rodri.bolaofacil.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		Match match = matchRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		authService.resourceBelongsSweepstake(match.getSweepstake().getId(), sweepstakeId);
		return new MatchUpdateDTO(match);
	}
	
	@Transactional(readOnly=true)
	public List<MatchSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Match> matches = matchRep.findAllBySweepstakeOrderByStartMoment(sweepstake);
			return matches.stream().map( match -> new MatchSampleDTO(match)).toList();
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public MatchInsertDTO insert(Long sweepstakeId, MatchInsertDTO dto)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Match match = new Match();
			copyInsertDtoToEntity(match, dto);
			match.setSweepstake(sweepstake);
			matchRep.save(match);
			return new MatchInsertDTO(match);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public MatchUpdateDTO update(Long sweepstakeId, Long id, MatchUpdateDTO dto)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			Match match = matchRep.getReferenceById(id);
			authService.resourceBelongsSweepstake(match.getSweepstake().getId(), sweepstakeId);
			copyDtoToEntity(match,dto);
			matchRep.save(match);
			return new MatchUpdateDTO(match);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	public void delete(Long sweepstakeId, Long id) 
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			//Match match = matchRep.getReferenceById(id);
			Match match = matchRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
			authService.resourceBelongsSweepstake(match.getSweepstake().getId(), sweepstakeId);
			matchRep.delete(match);
		}
		catch(DataIntegrityViolationException e) 
		{
			throw new DataBaseException("Não foi possível deletar pois existem palpites nessa partida");
		}
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
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	private void copyDtoToEntity(Match entity, MatchUpdateDTO dto)
	{
		copyInsertDtoToEntity(entity,dto);
		entity.setHomeTeamScore(dto.getHomeTeamScore());
		entity.setAwayTeamScore(dto.getAwayTeamScore());
	}
}
	
