package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BoardResponse;
import com.example.demo.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

	private final BoardService bservice;
	
	@GetMapping("/board/{id}")
	public ResponseEntity<List<BoardResponse>> getBoardByMember (@PathVariable(name = "id") Long id){
	List<BoardResponse> boardlist = bservice.findBoardByMember(id);
	return ResponseEntity.ok(boardlist);
	}
}
