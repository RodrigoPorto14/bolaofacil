package com.rodri.bolaofacil.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.RequestDTO;
import com.rodri.bolaofacil.entities.Request;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;
import com.rodri.bolaofacil.entities.pk.RequestPK;
import com.rodri.bolaofacil.repositories.RequestRepository;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.repositories.UserRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class RequestService {
	
	@Autowired
	RequestRepository requestRep;
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;

	@Transactional(readOnly = true)
	public List<RequestDTO> findAllBySweepstake(Long sweepstakeId) 
	{
		authService.participantIsOwnerOrAdmin(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Request> requests = requestRep.findAllBySweepstake(sweepstake);
			return requests.stream().map(request -> new RequestDTO(request)).collect(Collectors.toList());
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}

	@Transactional
	public RequestDTO insert(Long sweepstakeId) 
	{
		User user = authService.authenticated();
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Request request = new Request(user,sweepstake);
			requestRep.save(request);
			return new RequestDTO(request);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	public void delete(Long sweepstakeId, Long userId) 
	{
		authService.participantIsOwnerOrAdmin(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			User user = userRep.getReferenceById(userId);
			requestRep.deleteById(new RequestPK(user,sweepstake));
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException();}
		catch(DataIntegrityViolationException e) {throw new DataBaseException();}
	}
	
	
}
