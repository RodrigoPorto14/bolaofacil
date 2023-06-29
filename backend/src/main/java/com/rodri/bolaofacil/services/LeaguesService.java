package com.rodri.bolaofacil.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodri.bolaofacil.cblol.CblolData;
import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.dto.MatchDTO;
import com.rodri.bolaofacil.enitities.ExternalBet;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.repositories.ExternalBetRepository;

@Service
public class LeaguesService 
{
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ExternalBetRepository externalBetRep; 
	
	@Transactional(readOnly = true)
	public List<MatchDTO> findCBLOLMatches(Sweepstake sweepstake, User user, Integer page, int size)
	{
		String URL = "http://localhost:5000/api/cblol";
		List<MatchDTO> matches = request(URL);
		return matches;
	}
	
	private List<MatchDTO> request(String url)
	{
		HttpHeaders headers = new HttpHeaders();
		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));
		ResponseEntity<List<MatchDTO>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<MatchDTO>>() {});
		return response.getBody();
	}
	
	public List<BetDTO> toBetsDTO(List<MatchDTO> matches, Sweepstake sweepstake, User user)
	{
		List<ExternalBet> bets = externalBetRep.findAllByParticipant(sweepstake, user);
		return matches.stream().map(match -> toBetDTO(match,bets)).toList();
	}
	
	private BetDTO toBetDTO(MatchDTO match, List<ExternalBet> bets)
	{
		for(ExternalBet bet : bets)
		{
			if(match.getId().equals(bet.getExternalMatchId()))
				return new BetDTO(match, bet.getHomeTeamScore(), bet.getAwayTeamScore());	
		}
		
		return new BetDTO(match, null, null);
	}
	
	public Page<BetDTO> toPaged(List<BetDTO> bets, Integer page, int size)
	{
        int qtdMatches = bets.size();

        if (page == null)
            page = 0;

        if (page < 0 || page >= qtdMatches / size)
            throw new IllegalArgumentException("Número da página inválido.");

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, qtdMatches);

        if (fromIndex < toIndex)
            bets = bets.subList(fromIndex, toIndex);
        else 
            bets = List.of();
        
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(bets, pageable, qtdMatches);
	}
}
