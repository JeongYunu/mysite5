package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
public class UserController {
	//필드
	@Autowired
	private UserService userService;

	//로그인폼
	@RequestMapping(value ="/user/loginForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginForm() {
		System.out.println("[UserController.loginForm]");
		
		return "user/loginForm";
	}
	
	//로그인
	@RequestMapping(value ="/user/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("[UserController.login]");
		UserVo authUser = userService.getUser(userVo);
		
		if(authUser != null) {		//로그인성공
			System.out.println("[로그인성공]");
			session.setAttribute("authUser", authUser);
			return "redirect:/main";
		}else {		//로그인실패
			System.out.println("[로그인실패]");
			return "redirect:/user/loginForm?result=fail";
		}
	}
	
	//로그아웃
	@RequestMapping(value ="/user/logOut", method = {RequestMethod.GET, RequestMethod.POST})
	public String logOut(HttpSession session) {
		System.out.println("[UserController.logOut]");
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/main";
	}
	
	//회원가입폼
	@RequestMapping(value ="/user/joinForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String joinForm() {
		System.out.println("[UserController.joinForm]");
		
		return "user/joinForm";
	}
	
	//회원가입
	@RequestMapping(value ="/user/joinOk", method = {RequestMethod.GET, RequestMethod.POST})
	public String joinOk(@ModelAttribute UserVo userVo) {
		System.out.println("[UserController.joinOk");
		userService.insertUser(userVo);
		
		return "user/joinOk";
	}
	
	//회원정보수정
	@RequestMapping(value ="/user/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(HttpSession session, Model model) {
		System.out.println("[UserController.modifyForm");
		int no = ((UserVo)session.getAttribute("authUser")).getNo();
		UserVo userVo = userService.userInfo(no);
		model.addAttribute("userInfo", userVo);
		
		return "user/modifyForm";
	}
	
	//회원정보저장
	@RequestMapping(value ="/user/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(HttpSession session, @ModelAttribute UserVo userVo) {
		int no = ((UserVo)session.getAttribute("authUser")).getNo();
		userVo.setNo(no);
		userService.updateUser(userVo);
		((UserVo)session.getAttribute("authUser")).setName(userVo.getName());
		return "redirect:/main";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
