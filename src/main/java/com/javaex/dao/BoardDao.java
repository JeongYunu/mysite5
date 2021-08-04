package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;
	
	//전체리스트
	public List<BoardVo> boardList(String keyword) {
		System.out.println("[BoardDao.boardList]");
		
		return sqlSession.selectList("board.boardList", keyword);
	}
	
	//글쓰기
	public int boardInsert(BoardVo boardVo) {
		System.out.println("[BoardDao.boardInsert]");
		
		return sqlSession.insert("board.boardInsert", boardVo);
	}
	
	//조회수 올리기
	public int updateHit(int no) {
		System.out.println("[BoardDao.updateHit]");
		
		return sqlSession.update("board.updateHit", no);
	}
	
	//게시판 1개 정보 가져오기
	public BoardVo selectBoard(int no) {
		System.out.println("[BoardDao.selectBoard]");
		
		return sqlSession.selectOne("board.selectBoard", no);
	}
	
	
	
	
	
}
