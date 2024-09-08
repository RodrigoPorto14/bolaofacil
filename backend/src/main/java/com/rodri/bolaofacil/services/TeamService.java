package com.rodri.bolaofacil.services;

import static com.rodri.bolaofacil.utils.Utils.nullCoalescence;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.TeamInsertDTO;
import com.rodri.bolaofacil.dto.TeamUpdateDTO;
import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Team;
import com.rodri.bolaofacil.repositories.TeamRepository;
import com.rodri.bolaofacil.services.exceptions.DataBaseException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@Service
public class TeamService {
	
	@Autowired
	TeamRepository teamRep;
	
	@Autowired
	AuthService authService;
	
//	@Autowired
//	S3Service s3Service;
	
	@Transactional(readOnly=true)
	public TeamInsertDTO findById(Long id)
	{
		Team team = teamRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		authService.participantIsOwnerOrAdmin(team.getSweepstake().getId());
		return new TeamInsertDTO(team);
	}
	
	@Transactional(readOnly=true)
	public List<TeamUpdateDTO> findAllBySweepstake(Long sweepstakeId)
	{
		Participant participant = authService.participantIsOwnerOrAdmin(sweepstakeId);
		List<Team> teams = teamRep.findAllBySweepstake(participant.getSweepstake());
		return teams.stream().map(team -> new TeamUpdateDTO(team)).collect(Collectors.toList());
	}
	
	@Transactional
	public TeamInsertDTO insert(TeamInsertDTO dto)
	{
		Participant participant = authService.participantIsOwnerOrAdmin(dto.getSweepstakeId());
		authService.sweepstakeIsCustom(participant.getSweepstake());
		Team team = copyDtoToEntity(new Team(), dto);
		team.setSweepstake(participant.getSweepstake());
		teamRep.save(team);
		return new TeamInsertDTO(team);
	}
	
	@Transactional
	public TeamUpdateDTO update(Long id, TeamUpdateDTO dto)
	{
		try
		{
			Team team = teamRep.getReferenceById(id);
			authService.participantIsOwnerOrAdmin(team.getSweepstake().getId());
			copyDtoToEntity(team,dto);
			teamRep.save(team);
			return new TeamUpdateDTO(team);
		}
		catch(EntityNotFoundException e) { throw new ResourceNotFoundException(); }
	}
	
	@Transactional
	public void delete(Long id) 
	{
		try
		{
			Team team = teamRep.findById(id).orElseThrow(() -> new ResourceNotFoundException());
			authService.participantIsOwnerOrAdmin(team.getSweepstake().getId());
			teamRep.delete(team);
		}
		catch(EmptyResultDataAccessException e){throw new ResourceNotFoundException();}
		catch(DataIntegrityViolationException e) 
		{
			throw new DataBaseException("Não foi possível deletar pois esse time pertence a uma partida");
		}
	}

//	public UriDTO uploadFile(Long sweepstakeId, MultipartFile file) 
//	{
//		authService.checkCustomSweepstakeResourcePermissions(sweepstakeId);
//		URL url = s3Service.uploadFile(file);
//		return new UriDTO(url.toString());
//	}
	
	private Team copyDtoToEntity(Team entity, TeamUpdateDTO dto)
	{
		entity.setName(dto.getName());
		entity.setImgUri(nullCoalescence(dto.getImgUri(), entity.getImgUri()));
		return entity;
	}
}
	
