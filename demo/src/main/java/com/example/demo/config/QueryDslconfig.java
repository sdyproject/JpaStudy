package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Configuration
public class QueryDslconfig {

	 @Autowired
	 private EntityManager entityManager;

	 @Bean
	    public JPAQueryFactory JpaQueryFactory() {
	        return new JPAQueryFactory(entityManager);
	    }
}
