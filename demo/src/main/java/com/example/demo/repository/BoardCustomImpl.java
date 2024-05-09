package com.example.demo.repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;

import com.example.demo.dto.BoardResponse;
import com.example.demo.entity.QBoard;
import com.example.demo.entity.QMember;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;



public class BoardCustomImpl implements BoardCustom{

	private final JPAQueryFactory queryFactory;
	
	
	  public BoardCustomImpl(@Qualifier("JpaQueryFactory")JPAQueryFactory
	  queryFactory) { this.queryFactory = queryFactory; }
	 

	@Override
	public List<BoardResponse> findBoardByMember(Long id) {
		QMember member =QMember.member;
		QBoard board =QBoard.board;
		
		JPAQuery<Tuple> query = queryFactory.select(
				member.id,
				board.boardid,
				board.boardwrite,
				board.boardcontext,
				board.boardname
				).from(member)
				.join(board).on(member.id.eq(board.id))
				.where(member.id.eq(id))
				.orderBy(member.id.desc());
		
		return query.fetchJoin().fetch().stream().map(tupple -> BoardResponse.builder()
				.id(tupple.get(member.id))
				.boardwrite(tupple.get(board.boardwrite))
				.boardcontext(tupple.get(board.boardcontext))
				.boardname(tupple.get(board.boardname))
				.boardid(tupple.get(board.boardid))
				.build()).toList();
		
	}
	
	
}
