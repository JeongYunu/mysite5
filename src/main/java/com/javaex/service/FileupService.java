package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileupService {
	
	//파일 업로드 처리
	public String restore(MultipartFile file) {
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
	
		return saveName;
	
	}
	
}
