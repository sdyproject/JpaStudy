package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.RefreshToken;



public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long>{
	
	
	
	 boolean existsByRefresh(String refresh);	
	 Optional <RefreshToken> findByRefresh(String refresh);
	 RefreshToken deleteByRefresh(String refresh);
}
