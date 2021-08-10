package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;
	
	//전체 리스트
	public List<GuestbookVo> getList(){
		System.out.println("[gBookDao.getList]");
		
		return sqlSession.selectList("guestBook.list");
	}
	
	//저장
	public int write(GuestbookVo gBookVo) {
		System.out.println("[gBookDao.write]");
		
		return sqlSession.insert("guestBook.add", gBookVo);
	}
	
	//삭제
	public int delete(GuestbookVo gBookVo) {
		System.out.println("[gBookDao.delete]");
		
		return sqlSession.delete("guestBook.delete", gBookVo);
	}
	
	//방명록 글 저장
	public int inserGuestbookKey(GuestbookVo gBookVo) {
		System.out.println("[gBookDao.inserGuestbookKey]");
		
		return sqlSession.insert("guestBook.insertGuestbookKey", gBookVo);
	}
	
	//방금저장한 글 가져오기 -ajax
	public GuestbookVo selectGuestbook(int no) {
		System.out.println("[gBookDao.selectGuestBook]");
		
		return sqlSession.selectOne("guestBook.selectGuestbook", no);
	}
	
	
	
	
	
	
}
