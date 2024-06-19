package com.example.demo.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.RefreshToken;



public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long>{
	
	
	
	boolean existsByRefresh(String refresh);
	 @Transactional
	 RefreshToken deleteByRefresh(String refresh);

}
