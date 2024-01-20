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
import com.rodri.bolaofacil.enitities.Rule;
import com.rodri.bolaofacil.enitities.Sweepstake;
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
	public RuleDTO findById(Long sweepstakeId, Long id)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		Rule rule = ruleRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		authService.resourceBelongsSweepstake(rule.getSweepstake().getId(), sweepstakeId);
		return new RuleDTO(rule);
	}
	
	@Transactional(readOnly=true)
	public List<RuleSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Rule> rules = ruleRep.findAllBySweepstake(sweepstake);
			return rules.stream().map( rule -> new RuleSampleDTO(rule)).collect(Collectors.toList());
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public RuleDTO insert(Long sweepstakeId, RuleDTO dto)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Rule rule = copyDtoToEntity(new Rule(), dto);
			rule.setSweepstake(sweepstake);
			ruleRep.save(rule);
			return new RuleDTO(rule);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public RuleDTO update(Long sweepstakeId, Long id, RuleDTO dto)
	{
		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
		try
		{
			Rule rule = ruleRep.getReferenceById(id);
			authService.resourceBelongsSweepstake(rule.getSweepstake().getId(), sweepstakeId);
			rule = copyDtoToEntity(rule,dto);
			ruleRep.save(rule);
			return new RuleDTO(rule);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	public void delete(Long sweepstakeId, Long id) 
	{
		try
		{
			Rule rule = ruleRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
			authService.resourceBelongsSweepstake(rule.getSweepstake().getId(), sweepstakeId);
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
	
