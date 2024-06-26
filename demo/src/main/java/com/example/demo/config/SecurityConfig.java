package com.example.demo.config;



import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.demo.filter.CustomLogoutFilter;
import com.example.demo.filter.JWTFilter;
import com.example.demo.filter.LoginFilter;
import com.example.demo.service.RefreshService;
import com.example.demo.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	
	private final JwtUtil jwtUtil;
	
	private final RefreshService refreshService;
	
	/* private final RedisTemplate<String, Object> redisTemplate; */
	
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,JwtUtil jwtUtil,
    		RefreshService refreshService ) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshService = refreshService;
		/* this.redisTemplate=redisTemplate; */
    }
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.cors((cors) -> cors
					.configurationSource(new CorsConfigurationSource() {
						
						@Override
						public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						
							CorsConfiguration configuration =new CorsConfiguration();
							
							configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
	                        configuration.setAllowedMethods(Collections.singletonList("*"));
	                        //쿠키 허용
	                        configuration.setAllowCredentials(true);
	                        configuration.setAllowedHeaders(Collections.singletonList("*"));
	                        configuration.addExposedHeader("*");
	                        configuration.setMaxAge(3600L);

							configuration.setExposedHeaders(Collections.singletonList("Authorization"));

							
							
							
							
							return configuration;
						}
					}));
		
		
		
		
		http
			.csrf((auth) -> auth.disable());
		http
			.formLogin((auth) -> auth.disable());
		http
			.httpBasic((auth) -> auth.disable());
		
			
		
		http
		  .authorizeHttpRequests((auth) -> auth
				  .requestMatchers("/login", "/","/member","/reissue", "/board/*","/current-member","/board").permitAll()
						 .requestMatchers("/admin").hasRole("ADMIN") 
				  .anyRequest().authenticated());
		http
		 	.sessionManagement((session) ->
         	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http
		.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		
		http
		.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil,refreshService), UsernamePasswordAuthenticationFilter.class);
		http 
		.addFilterBefore(new CustomLogoutFilter(jwtUtil,refreshService), LogoutFilter.class);
		return http.build();
	}
	
	}
