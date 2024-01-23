package com.rodri.bolaofacil.services;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.rodri.bolaofacil.dto.LeagueDTO;
import com.rodri.bolaofacil.dto.MatchDTO;
import com.rodri.bolaofacil.enitities.League;
import com.rodri.bolaofacil.repositories.ExternalBetRepository;
import com.rodri.bolaofacil.repositories.LeagueRepository;

@Service
public class LeagueService {
	
	@Autowired
	LeagueRepository leagueRep;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ExternalBetRepository externalBetRep;
	
	@Value("${leagues.api}")
	private String leaguesAPI;
	
	@Transactional(readOnly=true)
	public List<LeagueDTO> findAll()
	{
		List<League> leagues = leagueRep.findByIsActiveTrue();
		return leagues.stream().map(league -> new LeagueDTO(league)).collect(Collectors.toList());
	}
	
	public List<MatchDTO> getMatches(String endpoint, String season){ return request(leaguesAPI + endpoint + "/" + season); }
		
	private List<MatchDTO> request(String url)
	{
		HttpHeaders headers = new HttpHeaders();
		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
		ResponseEntity<List<MatchDTO>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<MatchDTO>>() {});
		return response.getBody();
	}
}
	
