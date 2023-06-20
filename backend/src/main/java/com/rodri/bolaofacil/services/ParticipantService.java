package com.rodri.bolaofacil.services;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.ParticipantSampleDTO;
import com.rodri.bolaofacil.dto.ParticipantUpdateDTO;
import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.enums.Role;
import com.rodri.bolaofacil.enitities.pk.ParticipantPK;
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
	
	@Transactional(readOnly=true)
	public List<ParticipantSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Participant> participants = participantRep.findAllBySweepstakeExceptOwner(sweepstake);
			return participants.stream().map(participant -> new ParticipantSampleDTO(participant)).toList();
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	@Transactional
	public ParticipantSampleDTO insertAuthenticated(Long sweepstakeId) 
	{
		User user = authService.authenticated();
		Sweepstake sweepstake = sweepstakeRep.findById(sweepstakeId).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		if(sweepstake.isPrivate())
			throw new ForbiddenException("Access denied");
		
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
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	@Transactional
	public ParticipantSampleDTO update(Long sweepstakeId, Long id, ParticipantUpdateDTO dto)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			User user = userRep.getReferenceById(id);
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Participant participant = participantRep.getReferenceById(new ParticipantPK(user,sweepstake));
			participant.setRole(dto.getRole());
			participantRep.save(participant);
			return new ParticipantSampleDTO(participant);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+id); }
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
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException("Id not found " +id);}
		catch(DataIntegrityViolationException e) {throw new DataBaseException("Integrity violation");}
	}

	

	
}
