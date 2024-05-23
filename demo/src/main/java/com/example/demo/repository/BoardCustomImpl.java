package com.example.demo.repository;
import java.util.List;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.QBoard;
import com.example.demo.entity.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BoardCustomImpl implements BoardCustom{

	private final JPAQueryFactory queryFactory;
	
	
	 
	@Override
	public List<BoardResponse> findBoardByMember(Long id) {
		QMember member =QMember.member;
		QBoard board =QBoard.board;
		
		
		JPAQuery<Tuple> query = queryFactory.select(
				member.id,
				member.name,
				board.boardid,
				board.boardwrite,
				board.boardcontext,
				board.boardname,
				board.boardschedule
				).from(member)
				.join(board).on(member.id.eq(board.id))
				.where(member.id.eq(id))
				.orderBy(member.id.desc());
		
		return query.fetchJoin().fetch().stream().map(tupple -> BoardResponse.builder()
				.id(tupple.get(member.id))
				.name(tupple.get(member.name))
				.boardwrite(tupple.get(board.boardwrite))
				.boardcontext(tupple.get(board.boardcontext))
				.boardname(tupple.get(board.boardname))
				.boardid(tupple.get(board.boardid))
				.boardschedule(tupple.get(board.boardschedule))
				.build()).toList();
		
	}
	
	
}
