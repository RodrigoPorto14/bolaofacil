package com.rodri.bolaofacil.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.rodri.bolaofacil.services.exceptions.UnauthorizedException;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRep;
	
	@Autowired
	private SweepstakeRepository sweepstakeRep;
	
	@Autowired
	private ParticipantRepository participantRep;
	
	@Transactional(readOnly = true)
	public User authenticated()
	{
		try
		{
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRep.findByEmail(username);
		}
		catch(Exception e) { throw new UnauthorizedException(); }
	}
	
	public void sweepstakeIsCustom(Sweepstake sweepstake)
	{
		if(!sweepstake.getLeague().isCustom())
			throw new DataBaseException("O bolão não é personalizado");
	}
	
	public Participant validateParticipant(Long sweepstakeId)
	{
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			User user = authenticated();
			ParticipantPK participantId = new ParticipantPK(user,sweepstake);
			return participantRep.findById(participantId).orElseThrow(() -> new ForbiddenException());
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	private Participant validateRoles(Long sweepstakeId, Role... roles)
	{
		Participant participant = validateParticipant(sweepstakeId);
		Role participantRole = participant.getRole();
		
		for(Role role : roles) 
			if(participantRole == role) 
				return participant;
		
		throw new ForbiddenException();
	}
	
	public Participant participantIsOwner(Long sweepstakeId) { return validateRoles(sweepstakeId, Role.OWNER); }
	
	public Participant participantIsOwnerOrAdmin(Long sweepstakeId) { return validateRoles(sweepstakeId, Role.OWNER, Role.ADMIN); }
	
	public void resourceBelongsSweepstake(Long resourceSweepstakeId, Long sweepstakeId)
	{
		if(!resourceSweepstakeId.equals(sweepstakeId))
			throw new ForbiddenException();	
	}
	
}
