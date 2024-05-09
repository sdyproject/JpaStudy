package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JWTFilter;
import com.example.demo.filter.LoginFilter;
import com.example.demo.utils.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	
	private final JwtUtil jwtUtil;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,JwtUtil jwtUtil ) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((auth) -> auth.disable());
		http
			.formLogin((auth) -> auth.disable());
		http
			.httpBasic((auth) -> auth.disable());
		http
		  .authorizeHttpRequests((auth) -> auth
				  .requestMatchers("/login", "/", "/join","/member","/api/demo","/calendar","/joinForm","/board/**").permitAll()
						 .requestMatchers("/admin").hasRole("ADMIN") 
				  .anyRequest().authenticated());
		http
		 	.sessionManagement((session) ->
         	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http
		.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		
		http
		.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	}
