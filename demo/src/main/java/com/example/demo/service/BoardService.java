package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardResponse;
import com.example.demo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	
	public List<BoardResponse> findBoardByMember (Long id) {
		return boardRepository.findBoardByMember(id);
	}
}
