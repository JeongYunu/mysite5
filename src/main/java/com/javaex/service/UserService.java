package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	//로그인 사용자정보 가져오기
	public UserVo getUser(UserVo userVo) {
		System.out.println("[UserService.getUser]");
		UserVo authUser = userDao.selectUser(userVo);
		
		return authUser;
	}
	
	//insertUser
	public int insertUser(UserVo userVo) {
		System.out.println("[UserService.userInsert]");
		int count = userDao.insertUser(userVo);
		
		return count;
	}
	
	//유저정보회
	public UserVo userInfo(int no) {
		System.out.println("[UserService.userInsert]");
		UserVo userVo = userDao.userInfo(no);
		
		return userVo;
	}
	
	//유저db수정
	public int updateUser(UserVo userVo) {
		System.out.println("[UserService.updateUser]");
		int count = userDao.updateUser(userVo);
		
		return count;
	}
	
	
	
	
}
