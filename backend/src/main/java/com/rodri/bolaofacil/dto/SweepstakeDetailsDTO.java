package com.rodri.bolaofacil.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class SweepstakeDetailsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Page<BetDTO> bets;
	private List<CompetitorDTO> ranking;
	
	public SweepstakeDetailsDTO() {}
	
	public SweepstakeDetailsDTO(Page<BetDTO> bets, List<CompetitorDTO> ranking)
	{
		this.bets = bets;
		this.ranking = ranking;
	}

	public Page<BetDTO> getBets() {
		return bets;
	}

	public List<CompetitorDTO> getRanking() {
		return ranking;
	}

}
