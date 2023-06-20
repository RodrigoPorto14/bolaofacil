package com.rodri.bolaofacil.services;

import java.net.URL;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rodri.bolaofacil.dto.TeamDTO;
import com.rodri.bolaofacil.dto.TeamSampleDTO;
import com.rodri.bolaofacil.dto.UriDTO;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.Team;
import com.rodri.bolaofacil.repositories.SweepstakeRepository;
import com.rodri.bolaofacil.repositories.TeamRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ForbiddenException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class TeamService {
	
	@Autowired
	TeamRepository teamRep;
	
	@Autowired
	SweepstakeRepository sweepstakeRep;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	S3Service s3Service;
	
	@Transactional(readOnly=true)
	public TeamDTO findById(Long sweepstakeId, Long id)
	{
		authService.participantIsOwner(sweepstakeId);
		Team team = teamRep.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		if(team.getSweepstake().getId() != sweepstakeId) 
			throw new ForbiddenException("Access denied");
		
		return new TeamDTO(team);
	}
	
	@Transactional(readOnly=true)
	public List<TeamSampleDTO> findAllBySweepstake(Long sweepstakeId)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			List<Team> teams = teamRep.findAllBySweepstake(sweepstake);
			return teams.stream().map(team -> new TeamSampleDTO(team)).toList();
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	@Transactional
	public TeamDTO insert(Long sweepstakeId, TeamDTO dto)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Sweepstake sweepstake = sweepstakeRep.getReferenceById(sweepstakeId);
			Team team = copyDtoToEntity(new Team(), dto);
			team.setSweepstake(sweepstake);
			teamRep.save(team);
			return new TeamDTO(team);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+sweepstakeId); }
	}
	
	@Transactional
	public TeamDTO update(Long sweepstakeId, Long id, TeamDTO dto)
	{
		authService.participantIsOwner(sweepstakeId);
		try
		{
			Team team = teamRep.getReferenceById(id);
			
			if(team.getSweepstake().getId() != sweepstakeId) 
				throw new ForbiddenException("Access denied");
			
			team = copyDtoToEntity(team,dto);
			teamRep.save(team);
			return new TeamDTO(team);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException("Id not found "+id); }
	}
	
	public void delete(Long sweepstakeId, Long id) 
	{
		authService.participantIsOwner(sweepstakeId);
		try{teamRep.deleteById(id);}
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException("Id not found " +id);}
		catch(DataIntegrityViolationException e) {throw new DataBaseException("Integrity violation");}
	}
	
	private Team copyDtoToEntity(Team entity, TeamDTO dto)
	{
		entity.setName(dto.getName());
		entity.setImgUri(dto.getImgUri());
		return entity;
	}

	public UriDTO uploadFile(Long sweepstakeId, MultipartFile file) 
	{
		authService.participantIsOwner(sweepstakeId);
		URL url = s3Service.uploadFile(file);
		return new UriDTO(url.toString());
	}
}
	
