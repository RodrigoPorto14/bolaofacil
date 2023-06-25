package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.enitities.Match;
import com.rodri.bolaofacil.enitities.Sweepstake;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long>{
	
	@Query("SELECT m "
		 + "FROM Match m "
		 + "JOIN FETCH m.homeTeam "
		 + "JOIN FETCH m.awayTeam "
		 + "WHERE m.sweepstake = :sweepstake "
		 + "ORDER BY m.startMoment")
	List<Match> findAllBySweepstakeOrderByStartMoment(Sweepstake sweepstake);	
	
	@Query("SELECT COUNT(m) "
		 + "FROM Match m "
		 + "WHERE m.sweepstake = :sweepstake AND m.startMoment < CURRENT_TIMESTAMP")
	Integer matchesBeforeNowBySweepstake(Sweepstake sweepstake);
}
