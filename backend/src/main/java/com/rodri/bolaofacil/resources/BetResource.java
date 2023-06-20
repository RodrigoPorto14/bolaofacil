package com.rodri.bolaofacil.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.services.BetService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/palpites")
public class BetResource {
	
	@Autowired
	BetService service;
	
	@GetMapping
	public ResponseEntity<List<BetDTO>> findAllBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findAllBySweepstake(sweepstakeId));
	}
}
