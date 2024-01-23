package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.enitities.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long>{
	
	List<League> findByIsActiveTrue();
}
