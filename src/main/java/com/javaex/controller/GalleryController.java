package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;

	//갤러리 리스트
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("[GalleryController.list]");
		List<GalleryVo> galleryVo = galleryService.galleryList();
		model.addAttribute("galleryInfo", galleryVo);

		return "/gallery/list";
	}
	
	//이미지등록
	@RequestMapping(value="/addImg", method = {RequestMethod.GET, RequestMethod.POST})
	public String addImg(@RequestParam("file") MultipartFile file,
						@RequestParam("content") String content, HttpSession session) {
		System.out.println("[Gallerycontroller.addImg]");
		int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
		galleryService.restore(file, content, userNo);
		
		return "redirect:/gallery/list";
	}
	
	
}
