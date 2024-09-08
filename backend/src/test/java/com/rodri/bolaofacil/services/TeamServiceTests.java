package com.rodri.bolaofacil.services;

import static com.rodri.bolaofacil.factories.ParticipantFactory.participant;
import static com.rodri.bolaofacil.factories.SweepstakeFactory.sweepstake;
import static com.rodri.bolaofacil.factories.TeamFactory.team;
import static com.rodri.bolaofacil.factories.UserFactory.user;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rodri.bolaofacil.dto.TeamInsertDTO;
import com.rodri.bolaofacil.dto.TeamUpdateDTO;
import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.Team;
import com.rodri.bolaofacil.repositories.TeamRepository;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class TeamServiceTests {
	
	private Long existingTeamId = 1L;
	private Long nonExistingTeamId = 2L;
	private Long sweepstakeId = 1L;
	private Team team;
	
	@InjectMocks
	TeamService teamService;
	
	@Mock
	TeamRepository teamRep;
	
	@Mock
	AuthService authService;
	
	@BeforeEach
	public void setUp()
	{
		Sweepstake sweepstake = sweepstake(sweepstakeId);
		team = team(existingTeamId);
		team.setSweepstake(sweepstake);
		Participant participant = participant(user(1L), sweepstake);
		
		when(teamRep.findById(existingTeamId)).thenReturn(Optional.of(team));
		when(teamRep.findById(nonExistingTeamId)).thenThrow(ResourceNotFoundException.class);
		when(teamRep.save(any())).thenReturn(team);
		doNothing().when(teamRep).delete(any());
		when(teamRep.getReferenceById(existingTeamId)).thenReturn(team);
		when(teamRep.getReferenceById(nonExistingTeamId)).thenThrow(ResourceNotFoundException.class);
		when(authService.participantIsOwnerOrAdmin(sweepstakeId)).thenReturn(participant);
		doNothing().when(authService).sweepstakeIsCustom(any());
	}
	
	@Test
	public void findByIdShouldReturnTeamDtoWhenTeamIdExist()
	{
		TeamInsertDTO teamDto = teamService.findById(existingTeamId);
		
		assertEquals(team.getId(), teamDto.getId());
		assertEquals(team.getName(), teamDto.getName());
		assertEquals(team.getImgUri(), teamDto.getImgUri());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenTeamIdDoesNotExist()
	{
		assertThrows(ResourceNotFoundException.class, () -> {
			teamService.findById(nonExistingTeamId);
		});
	}
	
	@Test
	public void findAllBySweepstakeShouldReturnTeamDtoList()
	{
		List<Team> teams = new ArrayList<>(Arrays.asList(team));
		when(teamRep.findAllBySweepstake(any())).thenReturn(teams);
		
		List<TeamUpdateDTO> teamsDto = teamService.findAllBySweepstake(sweepstakeId);
		
		assertEquals(1, teamsDto.size());
		assertEquals(team.getId(), teamsDto.get(0).getId());
		assertEquals(team.getName(), teamsDto.get(0).getName());
	}
	
	@Test
	public void insertShouldReturnTeamDto()
	{
		TeamInsertDTO teamDto = new TeamInsertDTO(team);
		
		TeamInsertDTO insertedTeamDto = teamService.insert(teamDto);
		
		assertEquals(teamDto.getName(), insertedTeamDto.getName());
		assertEquals(teamDto.getImgUri(), insertedTeamDto.getImgUri());
	}
	
	@Test
	public void updateShouldReturnTeamDtoWhenTeamIdExist()
	{
		TeamUpdateDTO teamDto = new TeamUpdateDTO(team);
		
		TeamUpdateDTO updatedTeamDto = teamService.update(sweepstakeId, teamDto);
		
		assertEquals(teamDto.getName(), updatedTeamDto.getName());
		assertEquals(teamDto.getImgUri(), updatedTeamDto.getImgUri());
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenTeamIdDoesNotExist()
	{
		TeamUpdateDTO teamDto = new TeamUpdateDTO(team);
		
		assertThrows(ResourceNotFoundException.class, () -> {
			teamService.update(nonExistingTeamId, teamDto);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenTeamIdExist()
	{
		assertDoesNotThrow(() -> {
			teamService.delete(existingTeamId);
		});
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenTeamIdDoesNotExist()
	{
		assertThrows(ResourceNotFoundException.class, () -> {
			teamService.delete(nonExistingTeamId);
		});
	}
}
