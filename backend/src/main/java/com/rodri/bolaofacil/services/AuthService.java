package com.rodri.bolaofacil.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.enitities.Participant;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.enums.Role;
import com.rodri.bolaofacil.enitities.pk.ParticipantPK;
import com.rodri.bolaofacil.repositories.ParticipantRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.repositories.UserRepository;
import com.rodri.bolaofacil.services.exceptions.ForbiddenException;
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
		catch(Exception e) { throw new UnauthorizedException("Invalid user"); }
	}
	
	public Participant validateParticipant(Long sweepstakeId)
	{
		Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
		return participantRep.findById(new ParticipantPK(authenticated(),sweepstake)).orElseThrow(() -> new ForbiddenException("Access denied"));
	}
	
	private void validateRoles(Long sweepstakeId, Role... roles)
	{
		Participant participant = validateParticipant(sweepstakeId);
		Role participantRole = participant.getRole();
		
		for(Role role : roles) 
			if(participantRole == role) return;
		
		throw new ForbiddenException("Access denied");
	}
	
	public void participantIsOwner(Long sweepstakeId) { validateRoles(sweepstakeId, Role.OWNER); }
}