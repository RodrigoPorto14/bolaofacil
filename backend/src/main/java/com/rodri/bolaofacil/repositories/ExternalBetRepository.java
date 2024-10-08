package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.entities.ExternalBet;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;
import com.rodri.bolaofacil.entities.pk.ExternalBetPK;

@Repository
public interface ExternalBetRepository extends JpaRepository<ExternalBet,ExternalBetPK>{
	
	@Query("SELECT b "
		 + "FROM ExternalBet b "
	     + "WHERE b.id.sweepstake = :sweepstake AND b.id.user = :user")
	List<ExternalBet> findAllByParticipant(Sweepstake sweepstake, User user);
	
	@Query("SELECT b "
		 + "FROM ExternalBet b "
	     + "WHERE b.id.sweepstake = :sweepstake")
	List<ExternalBet> findAllBySweepstake(Sweepstake sweepstake);
	
	
}
