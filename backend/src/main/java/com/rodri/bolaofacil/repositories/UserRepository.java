package com.rodri.bolaofacil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodri.bolaofacil.enitities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByEmail(String email);
	User findByNickname(String nickname);
}
