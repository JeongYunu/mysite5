package com.javaex.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;

@Controller
@RequestMapping(value="/api/gallery")
public class ApiGalleryContorller {

	@Autowired
	private GalleryService galleryService;
	
	//이미지조회
	@ResponseBody
	@RequestMapping(value="/selectImg", method = {RequestMethod.GET, RequestMethod.POST})
	public GalleryVo selectImg(@RequestParam("galleryNo") int no) {
		System.out.println("[ApiGalleryController.selectImg]");
		GalleryVo imgInfo = galleryService.selectImg(no);
		//System.out.println(imgInfo);
		
		return imgInfo;
	}
	
	//사진삭제
	@ResponseBody
	@RequestMapping(value="/deleteImg", method = {RequestMethod.GET, RequestMethod.POST})
	public int deleteImg(@RequestParam("galleryNo") int no) {
		System.out.println("[ApiGalleryController.deleteImg]");
		int count = galleryService.deleteImg(no);
		
		return count;
	}
	
	
	
}
