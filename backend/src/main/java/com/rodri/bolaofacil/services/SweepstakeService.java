package com.rodri.bolaofacil.services;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.SweepstakeDTO;
import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.enums.Role;
import com.rodri.bolaofacil.repositories.ParticipantRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class SweepstakeService {

	@Autowired
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	ParticipantRepository participantRep;
	
	@Autowired
	AuthService authService;
	
	@Transactional(readOnly = true)
	public SweepstakeDTO findById(Long id)
	{
		authService.participantIsOwner(id);
		Sweepstake entity = sweepstakeRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return new SweepstakeDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public List<SweepstakeDTO> findAllByAuthenticated()
	{
		User user = authService.authenticated();
		return sweepstakeRep.findAllByAuthenticated(user);
	}
	
	@Transactional(readOnly = true)
	public List<SweepstakeDTO> findByName(String name)
	{
		User user = authService.authenticated();
		return sweepstakeRep.findByName(name.trim(), user);
	}
	
	@Transactional
	public SweepstakeDTO insert(SweepstakeDTO dto)
	{
		User user = authService.authenticated();
		Sweepstake sweepstake = copyDtoToEntity(new Sweepstake(), dto);
		sweepstake.setTournament(dto.getTournament());
		sweepstakeRep.save(sweepstake);
		participantRep.save(new Participant(user,sweepstake,Role.OWNER,Instant.now()));
		return new SweepstakeDTO(sweepstake);
	}
	
	@Transactional
	public SweepstakeDTO update(Long id, SweepstakeDTO dto)
	{
		authService.participantIsOwner(id);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(id);
			sweepstake = copyDtoToEntity(sweepstake,dto);
			sweepstakeRep.save(sweepstake);
			return new SweepstakeDTO(sweepstake);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}

	private Sweepstake copyDtoToEntity(Sweepstake entity, SweepstakeDTO dto) 
	{
		entity.setName(dto.getName());
		entity.setPrivate(dto.getPrivate_());
		return entity;
	}
}
