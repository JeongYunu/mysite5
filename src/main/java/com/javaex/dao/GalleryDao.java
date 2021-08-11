package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;
	
	//전체리스트
	public List<GalleryVo> galleryList(){
		System.out.println("[GalleryDao.galleryList]");
		
		return sqlSession.selectList("gallery.selectList");
	}
	
	//사진정보 저장
	public int addImg(GalleryVo galleryVo) {
		System.out.println("[GalleryDao.addImg]");
		
		return sqlSession.insert("gallery.addImgInfo", galleryVo);
	}
	
	//이미지조회
	public GalleryVo selectImg(int no) {
		System.out.println("[GalleryDao.selectImg]");
		
		return sqlSession.selectOne("gallery.selectImg", no);
	}
	
	//이미지삭제
	public int deleteImg(int no) {
		System.out.println("[GalleryDao.deleteImg]");
		
		return sqlSession.delete("gallery.deleteImg", no);
	}
	
}
