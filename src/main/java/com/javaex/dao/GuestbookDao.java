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
	
	
	
	
	
	
	
}
