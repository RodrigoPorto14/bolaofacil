package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long>{
	
	List<Team> findAllBySweepstake(Sweepstake sweepstake);
}
