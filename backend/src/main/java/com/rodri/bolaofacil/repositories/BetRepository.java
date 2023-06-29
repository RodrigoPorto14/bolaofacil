package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.dto.BetDTO;
import com.rodri.bolaofacil.enitities.Bet;
import com.rodri.bolaofacil.enitities.Sweepstake;
import com.rodri.bolaofacil.enitities.User;
import com.rodri.bolaofacil.enitities.pk.BetPK;

@Repository
public interface BetRepository extends JpaRepository<Bet,BetPK>{
	
	@Query("SELECT new com.rodri.bolaofacil.dto.BetDTO(m, b.homeTeamScore, b.awayTeamScore) "
		 + "FROM Match m "
	     + "LEFT JOIN Bet b ON m.id = b.id.match AND b.id.user = :user "
	     + "WHERE m.id.sweepstake = :sweepstake "
	     + "ORDER BY m.startMoment ")
	Page<BetDTO> findAllMatchBySweepstakeWithParticipantBet(Sweepstake sweepstake, User user, Pageable pageable);
	
	@Query("SELECT b "
		 + "FROM Bet b "
		 + "WHERE b.id.sweepstake = :sweepstake "
		 + "ORDER BY b.id.user.id ")
	List<Bet> findAllBySweepstake(Sweepstake sweepstake);
	
	
}
