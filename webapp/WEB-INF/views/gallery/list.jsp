<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>

</head>


<body>
	<div id="wrap">

		<!-- 해더 네비 -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //해더 네비 -->


		<div id="container" class="clearfix">
			<!-- 게시판 aside -->
			<c:import url="/WEB-INF/views/includes/asideGallery.jsp"></c:import>
			<!-- //게시판 aside -->

			<div id="content">

				<div id="content-head">
					<h3>갤러리</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>갤러리</li>
							<li class="last">갤러리</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->

				<div id="gallery">
					<div id="list">
						
						<c:if test="${ !empty authUser }">
							<button id="btnImgUpload">이미지올리기</button>
						</c:if>
						
						<div class="clear"></div>


						<ul id="viewArea">

							<c:forEach items="${ galleryInfo }" var="galleryVo">
								<!-- 이미지반복영역 -->
								<li id="l-${ galleryVo.no }">
									<div class="view">
										<img data-no="${ galleryVo.no }" class="imgItem" src="${ pageContext.request.contextPath }/upload/${ galleryVo.saveName}">
										<div class="imgWriter">
											작성자: <strong>${ galleryVo.userName }</strong>
										</div>
									</div>
								</li>
								<!-- 이미지반복영역 -->
							</c:forEach>									
 							
						</ul>

					</div>
					<!-- //list -->
				</div>
				<!-- //gallery -->


			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->


		<!-- 푸터 -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //푸터 -->
	</div>
	<!-- //wrap -->









	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지등록</h4>
				</div>

				<form method="post" action="${ pageContext.request.contextPath }/gallery/addImg" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글작성</label> 
							<input id="addModalContent" type="text" name="content" value="">
						</div>
						<div class="form-group">
							<label class="form-text">이미지선택</label> 
							<input id="file" type="file" name="file" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
						<!--  <input tyep="text" name="userNo" value="${ authUser.no }"> -->
					</div>
				</form>


			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">

					<div class="formgroup">
						<img id="viewModelImg" src="">
						<!-- ajax로 처리 : 이미지출력 위치-->
					</div>

					<div class="formgroup">
						<p id="viewModelContent"></p>
					</div>

				</div>
				<form method="" action="">
					<div class="modal-footer">
						<input type="text" name="galleryNo" value="">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-danger" id="btnDel">삭제</button>
					</div>
				</form>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


</body>

	<script type="text/javascript">
		
	//이미지올리기 버튼을 클릭할때
	$("#btnImgUpload").on("click", function() {
		console.log("이미지올리기 버튼 클릭");
				
		//입력 초기화
		$("#addModalContent").val("");
		
		//모달창 보이기
		$("#addModal").modal();
	});
	
	//이미지 모달창띠우기
	$(".view>.imgItem").on("click", function(){
		console.log("이미지 클릭");
		
		var no = $(this).data("no");
		$("[name = 'galleryNo']").val(no);
		console.log(no);
		
		var url = "${ pageContext.request.contextPath}/upload/";
		console.log(url);
		
		//로그인사용자 no
		var authUserNo = "${ authUser.no }";
		console.log(authUserNo);
		
		//사진 가져오기
		$.ajax({
			
			url : "${pageContext.request.contextPath }/api/gallery/selectImg",		
			type : "post",
			//contentType : "application/json",
			data : {galleryNo: no},

			dataType : "json",
			success : function(imgInfo){
				console.log(imgInfo.saveName);
				$("#viewModelImg").attr("src", url + imgInfo.saveName);
				$("#viewModelContent").html(imgInfo.content);
				
				//본인글 검증
				if(imgInfo.userNo != authUserNo){
					//삭제버튼 remove
					$("#btnDel").remove();
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
		//모달창 보이기
		$("#viewModal").modal();
		
	});
	
	//모달창 삭제클릭
	$("#btnDel").on("click", function(){
		console.log("모달창 삭제클릭");
		
		//기존 form 기능 상실
		event.preventDefault();
		
		var no = $("[name = 'galleryNo']").val();
		console.log(no);
		
		//사진 삭제하기
		$.ajax({
			
			url : "${pageContext.request.contextPath }/api/gallery/deleteImg",		
			type : "post",
			//contentType : "application/json",
			data : {galleryNo: no},

			dataType : "json",
			success : function(count){
				console.log(count);
				if(count === 1){
					//모달창닫기
					$("#viewModal").modal("hide");
					
					//리스트에 삭제했던 리스트 화면에서 지운다
					$("#l-" + no).remove();
				}else{
					//모달창닫기
					$("#viewModal").modal("hide");
				};
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
		
	});
	
	</script>

</html>

