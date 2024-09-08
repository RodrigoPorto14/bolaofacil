package com.rodri.bolaofacil.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.MatchInsertDTO;
import com.rodri.bolaofacil.dto.MatchSampleDTO;
import com.rodri.bolaofacil.dto.MatchUpdateDTO;
import com.rodri.bolaofacil.entities.Match;
import com.rodri.bolaofacil.entities.Participant;
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
	public MatchUpdateDTO findById(Long id)
	{
		Match match = matchRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		authService.participantIsOwnerOrAdmin(match.getSweepstake().getId());
		return new MatchUpdateDTO(match);
	}
	
	@Transactional(readOnly=true)
	public List<MatchSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		Participant participant = authService.participantIsOwnerOrAdmin(sweepstakeId);
		List<Match> matches = matchRep.findAllBySweepstakeOrderByStartMoment(participant.getSweepstake());
		return matches.stream().map( match -> new MatchSampleDTO(match)).collect(Collectors.toList());
	}
	
	@Transactional
	public MatchInsertDTO insert(MatchInsertDTO dto)
	{
		Participant participant = authService.participantIsOwnerOrAdmin(dto.getSweepstakeId());
		authService.sweepstakeIsCustom(participant.getSweepstake());
		Match match = new Match();
		copyInsertDtoToEntity(match, dto);
		match.setSweepstake(participant.getSweepstake());
		matchRep.save(match);
		return new MatchInsertDTO(match);
	}
	
	@Transactional
	public MatchUpdateDTO update(Long id, MatchUpdateDTO dto)
	{
		try
		{
			Match match = matchRep.getReferenceById(id);
			authService.participantIsOwnerOrAdmin(match.getSweepstake().getId());
			copyDtoToEntity(match,dto);
			matchRep.save(match);
			return new MatchUpdateDTO(match);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public void delete(Long id) 
	{
		try
		{
			Match match = matchRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
			authService.participantIsOwnerOrAdmin(match.getSweepstake().getId());
			matchRep.delete(match);
		}
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException();}
		catch(DataIntegrityViolationException e) 
		{
			throw new DataBaseException("Não foi possível deletar pois existem palpites nessa partida");
		}
	}
	
	private void copyInsertDtoToEntity(Match entity, MatchInsertDTO dto)
	{
		try
		{
			entity.setRule(ruleRep.getReferenceById(dto.getRuleId()));
			entity.setHomeTeam(teamRep.getReferenceById(dto.getHomeTeamId()));
			entity.setAwayTeam(teamRep.getReferenceById(dto.getAwayTeamId()));
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
	
