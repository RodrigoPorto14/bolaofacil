package com.rodri.bolaofacil.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rodri.bolaofacil.dto.SweepstakeDetailsDTO;
import com.rodri.bolaofacil.services.SweepstakeDetailsService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/details")
public class SweepstakeDetailsResource {
	
	@Autowired
	SweepstakeDetailsService service;
	
	@GetMapping
	public ResponseEntity<SweepstakeDetailsDTO> findSweepstakeDetails(@PathVariable Long sweepstakeId,
																      @RequestParam(value = "page", required = false) Integer page)
	{
		return ResponseEntity.ok().body(service.findSweepstakeDetails(sweepstakeId, page));
	}
}
