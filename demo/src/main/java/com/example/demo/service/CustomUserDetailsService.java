package com.example.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	public CustomUserDetailsService(MemberRepository memberRepository)
	{
		this.memberRepository = memberRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member userData = memberRepository.findByUsername(username);
		System.out.println(userData);
		if (userData != null) {
			return new CustomUserDetails(userData);
			
		}
		return null;
	}

	

}
