package com.rodri.bolaofacil.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.dto.SweepstakeDTO;
import com.rodri.bolaofacil.entities.Sweepstake;
import com.rodri.bolaofacil.entities.User;

@Repository
public interface SweepstakeRepository extends JpaRepository<Sweepstake,Long>{
	
	@Query("SELECT new com.rodri.bolaofacil.dto.SweepstakeDTO(s.id, s.name, s.isPrivate, u.nickname) "
		 + "FROM Sweepstake s "
		 + "INNER JOIN Participant p ON s = p.id.sweepstake "
		 + "INNER JOIN Participant p2 ON p.id.sweepstake = p2.id.sweepstake "
		 + "INNER JOIN User u ON u = p2.id.user "
		 + "WHERE p.id.user = :user AND p2.role = 2 "
		 + "ORDER BY p.lastAccess DESC")
	List<SweepstakeDTO> findAllByAuthenticated(User user);
	
	@Query("SELECT new com.rodri.bolaofacil.dto.SweepstakeDTO(s.id, s.name, s.isPrivate, u.nickname, r.id.user IS NOT NULL) "
		 + "FROM Sweepstake s "
		 + "INNER JOIN Participant p ON s = p.id.sweepstake "
		 + "INNER JOIN User u ON u = p.id.user "
		 + "LEFT JOIN Participant p2 ON s = p2.id.sweepstake AND p2.id.user = :user "
		 + "LEFT JOIN Request r ON s = r.id.sweepstake AND r.id.user = :user "
		 + "WHERE :name <> '' AND "
		 + "((LOWER(s.name) LIKE LOWER(CONCAT('%',:name,'%'))) OR "
		 + "(LOWER(u.nickname) LIKE LOWER(CONCAT('%',:name,'%')))) AND "
		 + "p.role = 2 AND p2.id.user IS NULL "
		 + "ORDER BY s.name")
	List<SweepstakeDTO> findByName(String name, User user);
}
