package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryDao galleryDao;
	
	//전체리스트
	public List<GalleryVo> galleryList(){
		System.out.println("[GalleryService.galleryList]");
		List<GalleryVo> galleryVo = galleryDao.galleryList();
		
		return galleryVo;
	}
	
	//이미지조회
	public GalleryVo selectImg(int no) {
		System.out.println("[GalleryService.selectImg]");
		GalleryVo galleryInfo = galleryDao.selectImg(no);
		
		return galleryInfo;
	}
	
	//이미지삭제
	public int deleteImg(int no) {
		System.out.println("[GalleryService.deleteImg]");
		//db삭제
		int count = galleryDao.deleteImg(no);
		
		//서버에 저장된 사진삭제=========================================================
		
		return count;
	}
	
	//파일 업로드 처리
	public int restore(MultipartFile file, String content, int userNo) {
		System.out.println("[FileupService.restore]");
		
		//파일 저장경로
		String saveDir = "/Users/yunu/Desktop/javaStudy/upload";
		
		//원파일 이름
		String orgName = file.getOriginalFilename();
		System.out.println("orgName= " + orgName);
		
		//확장자 (파일이름 끝에서 .까지 구하는거)
		String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		System.out.println("exName= " + exName);
		
		//저장파일이름(관리떄문에 겹치지 않는 새이름 부여)
		String saveName = "HIMEDIA" + System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println("saveName= " + saveName);
		
		//파잂패스
		String filePath = saveDir + "/" + saveName;
		System.out.println("filePath= " + filePath);
		
		//파일사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize= " + fileSize);

		//1. 파일을 서버의하드디스크에 저장
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(filePath);
			BufferedOutputStream bOut = new BufferedOutputStream(out);
			
			bOut.write(fileData);
			bOut.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2. 파일정보를 db에 저장
		GalleryVo galleryVo = new GalleryVo();
		galleryVo.setUserNo(userNo);
		galleryVo.setContent(content);
		galleryVo.setFilePath(filePath);
		galleryVo.setOrgName(orgName);
		galleryVo.setSaveName(saveName);
		galleryVo.setFileSize(fileSize);
		
		int count = galleryDao.addImg(galleryVo);

		return count;
	
	}
	
}
