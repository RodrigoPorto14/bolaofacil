package com.rodri.bolaofacil.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodri.bolaofacil.dto.LeagueDTO;
import com.rodri.bolaofacil.services.LeagueService;

@RestController
@RequestMapping(value = "/leagues")
public class LeagueResource {

	@Autowired
	LeagueService service;
	
	@GetMapping()
	public ResponseEntity<List<LeagueDTO>> findAll()
	{
		return ResponseEntity.ok().body(service.findAll());
	}
}
