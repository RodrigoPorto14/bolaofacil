package com.rodri.bolaofacil.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodri.bolaofacil.dto.CompetitorDTO;
import com.rodri.bolaofacil.services.RankingService;

@RestController
@RequestMapping(value = "/boloes/{sweepstakeId}/ranking")
public class RankingResource {
	
	@Autowired
	RankingService service;
	
	@GetMapping
	public ResponseEntity<List<CompetitorDTO>> findRankingBySweepstake(@PathVariable Long sweepstakeId)
	{
		return ResponseEntity.ok().body(service.findRankingBySweepstake(sweepstakeId));
	}
}
