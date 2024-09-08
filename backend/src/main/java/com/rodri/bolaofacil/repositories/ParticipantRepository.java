package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.entities.Participant;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.pk.ParticipantPK;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant,ParticipantPK>{
	
	@Query("SELECT p "
		 + "FROM Participant p "
		 + "JOIN FETCH p.id.user "
		 + "WHERE p.id.sweepstake = :sweepstake AND p.role <> 2")
	List<Participant> findAllBySweepstakeExceptOwner(Sweepstake sweepstake);
	
	@Query("SELECT p "
		 + "FROM Participant p "
		 + "JOIN FETCH p.id.user "
		 + "WHERE p.id.sweepstake = :sweepstake")
	List<Participant> findAllBySweepstake(Sweepstake sweepstake);
}
