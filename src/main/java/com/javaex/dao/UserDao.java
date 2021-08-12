package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	//필드
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo selectUser(UserVo userVo) {
		System.out.println("[UserDao.selectUser]");
		
		return sqlSession.selectOne("user.selectUser", userVo);
	}
	
	public UserVo selectUser(String id) {
		System.out.println("[UserDao.selectUser]");
		
		return sqlSession.selectOne("user.idCheck", id);
	}
	
	//insertUser
	public int insertUser(UserVo userVo) {
		System.out.println("[UserDao.insertUser]");
		
		return sqlSession.insert("user.insertUser", userVo);
	}
	
	//유저정보조회
	public UserVo userInfo(int no) {
		System.out.println("[UserDoa.userInfo]");
		
		return sqlSession.selectOne("user.selectOne", no);
	}
	
	//user db update
	public int updateUser(UserVo userVo) {
		
		return sqlSession.update("updateUser", userVo);
	}
	
}
