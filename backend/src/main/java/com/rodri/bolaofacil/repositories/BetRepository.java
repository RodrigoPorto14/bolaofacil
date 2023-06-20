package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.pk.BetPK;

@Repository
public interface BetRepository extends JpaRepository<Bet,BetPK>{
	
	@Query("SELECT b "
		 + "FROM Bet b "
		 + "JOIN FETCH b.id.match m "
		 + "JOIN FETCH m.homeTeam "
		 + "JOIN FETCH m.awayTeam "
		 + "WHERE b.id.sweepstake = :sweepstake AND b.id.user = :user ")
	List<Bet> findAllByParticipant(Sweepstake sweepstake, User user);
	
	@Query("SELECT b "
		 + "FROM Bet b "
		 + "JOIN FETCH b.id.match m "
		 + "JOIN FETCH m.rule "
		 + "WHERE b.id.sweepstake = :sweepstake "
		 + "ORDER BY b.id.user.id ")
	List<Bet> findAllBySweepstake(Sweepstake sweepstake);
	
	
}
