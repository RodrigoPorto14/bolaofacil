package com.rodri.bolaofacil.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.services.BetService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/bets")
public class BetResource {
	
	@Autowired
	BetService service;
	
	@PostMapping()
	public ResponseEntity<BetInsertDTO> insert(@PathVariable Long sweepstakeId, @RequestBody BetInsertDTO dto)
	{
		dto = service.insert(sweepstakeId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getMatchId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping()
	public ResponseEntity<BetInsertDTO> update(@PathVariable Long sweepstakeId, @RequestBody BetInsertDTO dto)
	{
		return ResponseEntity.ok().body(service.update(sweepstakeId, dto));
	}
}
