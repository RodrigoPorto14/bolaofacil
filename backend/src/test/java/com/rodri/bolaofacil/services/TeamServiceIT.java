package com.rodri.bolaofacil.services;

import static com.rodri.bolaofacil.factories.TeamFactory.existingTeam;
import static com.rodri.bolaofacil.factories.UserFactory.ownerUser;
import static com.rodri.bolaofacil.factories.UserFactory.playerUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.rodri.bolaofacil.dto.TeamInsertDTO;
import com.rodri.bolaofacil.entities.User;
import com.rodri.bolaofacil.services.exceptions.ForbiddenException;
import com.rodri.bolaofacil.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class TeamServiceIT {
	
	private Long existingId = 1L;
	private Long nonExistingId = 100L;
	
	@Autowired
	TeamService teamService;
	
	public void makeLogin(User user)
	{
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	@Test
	public void findByIdShouldReturnTeamDtoWhenIdExist()
	{
		makeLogin(ownerUser());
		TeamInsertDTO expectedTeamDto = new TeamInsertDTO(existingTeam());
		
		TeamInsertDTO teamDto = teamService.findById(expectedTeamDto.getId());
		
		assertEquals(expectedTeamDto.getId(), teamDto.getId());
		assertEquals(expectedTeamDto.getName(), teamDto.getName());
		assertEquals(expectedTeamDto.getImgUri(), teamDto.getImgUri());
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist()
	{
		makeLogin(ownerUser());
		
		assertThrows(ResourceNotFoundException.class, () -> {
			teamService.findById(nonExistingId);
		});
	}
	
	@Test
	public void findByIdShouldThrowForbiddenExceptionWhenUserIsNotOwnerOrAdmin()
	{
		makeLogin(playerUser());
		
		assertThrows(ForbiddenException.class, () -> {
			teamService.findById(existingId);
		});
	}

}
