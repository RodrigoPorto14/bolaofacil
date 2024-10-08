package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.entities.Request;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.pk.RequestPK;

@Repository
public interface RequestRepository extends JpaRepository<Request,RequestPK>{
	
	@Query("SELECT r "
		 + "FROM Request r "
		 + "JOIN FETCH r.id.user "
		 + "WHERE r.id.sweepstake = :sweepstake")
	List<Request> findAllBySweepstake(Sweepstake sweepstake);
	
}
