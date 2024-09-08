package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.entities.Rule;
import com.rodri.bolaofacil.entities.Sweepstake;

@Repository
public interface RuleRepository extends JpaRepository<Rule,Long>{
	
	List<Rule> findAllBySweepstake(Sweepstake sweepstake);
}
