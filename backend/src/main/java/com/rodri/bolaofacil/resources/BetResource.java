package com.rodri.bolaofacil.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.dto.BetInsertDTO;
import com.rodri.bolaofacil.services.BetService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/bets")
public class BetResource {
	
	@Autowired
	BetService service;
	
	@GetMapping
	public ResponseEntity<Page<BetDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId,
															@RequestParam(value = "page", required = false) Integer page,
															Pageable pageable)
	{
		return ResponseEntity.ok().body(service.findAllMatchBySweepstakeWithParticipantBet(sweepstakeId, page, pageable));
	}
	
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
