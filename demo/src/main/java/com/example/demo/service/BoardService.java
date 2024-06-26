package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.utils.JwtUtil;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	private final JwtUtil jwtUtil;
	
	private final RefreshService refreshService;
	
	public List<BoardResponse> findBoardByMember (Long id) {
		
		return boardRepository.findBoardByMember(id);
	}
	
	@Transactional
	public String writeBoard(Board board, HttpServletRequest request) {
		/*
		 * String Tid = refreshService.extractTokenFromRequest(request);
		 * System.out.println(Tid); Long id = Long.valueOf(Tid);
		 */
		String accessToken =request.getHeader("access");
		String gid = accessToken.substring(7);
		Long id = Long.valueOf(gid);
		System.out.println(id);
		 
		 String boardname = board.getBoardname();
		 String boardcontext = board.getBoardcontext();
		 Date boardschedule = board.getBoardschedule();
		 
		 
		 Board data = new Board();
		 data.setBoardname(boardname);
		 data.setBoardcontext(boardcontext);
		 data.setId(id);
		 LocalDateTime localDateTime= LocalDateTime.now();
		 data.setBoardwrite(localDateTime);;
		 data.setBoardschedule(boardschedule);
		 boardRepository.save(data);
		 
		 return "SUCCESS";
	}
	
	 
}
