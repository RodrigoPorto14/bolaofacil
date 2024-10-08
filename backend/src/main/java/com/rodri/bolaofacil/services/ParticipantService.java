package com.rodri.bolaofacil.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.ParticipantDTO;
import com.rodri.bolaofacil.dto.ParticipantSampleDTO;
import com.rodri.bolaofacil.dto.ParticipantUpdateDTO;
import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;
import com.rodri.bolaofacil.entities.enums.Role;
import com.rodri.bolaofacil.entities.pk.ParticipantPK;
import com.rodri.bolaofacil.repositories.ParticipantRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.repositories.UserRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ForbiddenException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class ParticipantService {
	
	@Autowired
	ParticipantRepository participantRep;
	
	@Autowired
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional
	public ParticipantDTO findAuthenticatedBySweepstake(Long sweepstakeId)
	{
		try
		{
			Participant participant = authService.validateParticipant(sweepstakeId);
			participant.setLastAccess(Instant.now());
			participantRep.save(participant);
			return new ParticipantDTO(participant);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional(readOnly=true)
	public List<ParticipantSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Participant> participants = participantRep.findAllBySweepstakeExceptOwner(sweepstake);
			return participants.stream().map(participant -> new ParticipantSampleDTO(participant)).collect(Collectors.toList());
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public ParticipantSampleDTO insertAuthenticated(Long sweepstakeId) 
	{
		User user = authService.authenticated();
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException());
		
		if(sweepstake.isPrivate())
			throw new ForbiddenException();
		
		Participant participant = new Participant(user, sweepstake, Role.PLAYER, Instant.now());
		participantRep.save(participant);
		return new ParticipantSampleDTO(participant);
	}
	
	@Transactional
	public ParticipantSampleDTO insert(Long sweepstakeId, Long userId) 
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			User user = userRep.getReferenceById(userId);
			Participant participant = new Participant(user, sweepstake, Role.PLAYER, Instant.now());
			participantRep.save(participant);
			return new ParticipantSampleDTO(participant);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public ParticipantSampleDTO update(Long sweepstakeId, Long userId, ParticipantUpdateDTO dto)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			User user = userRep.getReferenceById(userId);
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Participant participant = participantRep.getReferenceById(new ParticipantPK(user,sweepstake));
			participant.setRole(dto.getRole());
			participantRep.save(participant);
			return new ParticipantSampleDTO(participant);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	public void delete(Long sweepstakeId, Long id) 
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			User user = userRep.getReferenceById(id);
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			participantRep.deleteById(new ParticipantPK(user,sweepstake));
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException();}
		catch(DataIntegrityViolationException e) {throw new DataBaseException();}
	}
}
