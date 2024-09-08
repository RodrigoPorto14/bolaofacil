package com.rodri.bolaofacil.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.RuleDTO;
import com.rodri.bolaofacil.dto.RuleSampleDTO;
import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Rule;
import com.rodri.bolaofacil.repositories.RuleRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class RuleService {
	
	@Autowired
	RuleRepository ruleRep;
	
	@Autowired
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional(readOnly=true)
	public RuleDTO findById(Long id)
	{
		Rule rule = ruleRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		authService.participantIsOwnerOrAdmin(rule.getSweepstake().getId());
		return new RuleDTO(rule);
	}
	
	@Transactional(readOnly=true)
	public List<RuleSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		Participant participant = authService.participantIsOwnerOrAdmin(sweepstakeId);
		List<Rule> rules = ruleRep.findAllBySweepstake(participant.getSweepstake());
		return rules.stream().map( rule -> new RuleSampleDTO(rule)).collect(Collectors.toList());
	}
	
	@Transactional
	public RuleDTO insert(RuleDTO dto)
	{
		Participant participant = authService.participantIsOwnerOrAdmin(dto.getSweepstakeId());
		authService.sweepstakeIsCustom(participant.getSweepstake());
		Rule rule = copyDtoToEntity(new Rule(), dto);
		rule.setSweepstake(participant.getSweepstake());
		ruleRep.save(rule);
		return new RuleDTO(rule);
	}
	
	@Transactional
	public RuleDTO update(Long id, RuleDTO dto)
	{
		try
		{
			Rule rule = ruleRep.getReferenceById(id);
			authService.participantIsOwnerOrAdmin(rule.getSweepstake().getId());
			rule = copyDtoToEntity(rule,dto);
			ruleRep.save(rule);
			return new RuleDTO(rule);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public void delete(Long id) 
	{
		try
		{
			Rule rule = ruleRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
			authService.participantIsOwnerOrAdmin(rule.getSweepstake().getId());
			ruleRep.delete(rule);
		}
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException();}
		catch(DataIntegrityViolationException e) 
		{
			throw new DataBaseException("Não foi possível deletar pois esta regra pertence à uma partida");
		}
	}
	
	private Rule copyDtoToEntity(Rule entity, RuleDTO dto)
	{
		entity.setName(dto.getName());
		entity.setExactScore(dto.getExactScore());
		entity.setWinnerScore(dto.getWinnerScore());
		entity.setScoreDifference(dto.getScoreDifference());
		entity.setLoserScore(dto.getLoserScore());
		entity.setWinner(dto.getWinner());
		return entity;
	}
}
	
