<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- el/jstl -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<link href="${ pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${ pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${ pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${ pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>


</head>

<body>
	<div id="wrap">

		<!-- 해더 네비 -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>

		<div id="container" class="clearfix">

			<c:import url="/WEB-INF/views/includes/asideGuestbook.jsp"></c:import>
			<!-- //aside -->

			<div id="content">

				<div id="content-head" class="clearfix">
					<h3>ajax방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">ajax방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<form id="form-reset" action="" method="">
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label></th>
									<td><input id="input-uname" type="text" name="name"></td>
									<th><label class="form-text" for="input-pass">패스워드</label></th>
									<td><input id="input-pass" type="password" name="pass"></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4" class="text-center">
										<button id="btnSubmit" type="submit">등록</button>
									</td>
								</tr>
							</tbody>

						</table>
						<!-- //guestWrite -->

					</form>

					<!-- 리스트 그려지는 영역 -->
					<div id="listArea">
						<!-- 리스트 -->
					</div>
					<!-- //리스트 그려지는 영역 -->

				</div>
				<!-- //guestbook -->

			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

	<!-- ---------------------------------------------------------------------------------------- -->
	<!-- ---------------------------------------------------------------------------------------- -->
	<!-- ---------------------------------------------------------------------------------------- -->
	<!-- 삭제 모달창 -->
	<div id="delModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">방명록 삭제</h4>
				</div>
				<div class="modal-body">

					<label for="modalPassword">비밀번호</label> 
					<input id="modalPassword" type="password" name="password">

				</div>
				<div class="modal-footer">
					<button id="modalBtnDel" type="button" class="btn btn-primary">삭제</button>
					<input type="text" name="no" value="">
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- ---------------------------------------------------------------------------------------- -->
	<!-- ---------------------------------------------------------------------------------------- -->
	<!-- ---------------------------------------------------------------------------------------- -->



</body>

<script type="text/javascript">
	//화면 로딩되기 직전
	$(document).ready(function() {
		console.log("화면 로딩 직전");

		//ajax요청
		fetchList();

	});

	//로딩이 끝난 후
	//등록버튼 클릭할떄
	$("#btnSubmit").on("click", function() {

		//기존 form 기능 상실
		event.preventDefault();

		console.log("등록버튼 클릭");

		/* 
		//name값 읽어오기
		var userName = $("#input-uname").val();
		console.log(userName);
		//password값 읽어오기
		var userPw = $("#input-pass").val();
		console.log(userPw);
		//content값 읽어오기
		var userCont = $("[name = 'content']").val();	//속성값으로 읽어오기
		console.log(userCont);
		 */

		//파라미터값 객체 생성
		var guestbookVo = {
			name : $("#input-uname").val(),
			password : $("#input-pass").val(),
			content : $("[name = 'content']").val()
		};

		//데이터 ajax방식으로 서버에 전송
		$.ajax({

			//url : "${pageContext.request.contextPath }/api/guestbook/write?name=" + userName + "&password=" + password + "&content=" + content,		
			url : "${pageContext.request.contextPath }/api/guestbook/write",
			type : "get",
			//contentType : "application/json",
			//data : {name: userName, password: userPw, content: userCont},
			data : guestbookVo,

			//dataType : "json",
			success : function(resultVo) {
				/*성공시 처리해야될 코드 작성*/
				render(resultVo, "up");

				/* 
				$("#input-uname").val("");
				$("#input-pass").val("");
				$("[name = 'content']").val("");
				 */

				//특정폼 하나만 리셋
				$("#form-reset")[0].reset();
				//문서내에 모든폼 리셋
				$('form').each(function() {
					this.reset();
				});

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});

	});

	//삭제버튼을 클릭할때
	$("#listArea").on("click", ".btnDel", function() {
		console.log("삭제버튼 클릭");

		//hidden에 no넣
		//var tag = $(this).data("no");
		//var no = tag.data("no");
		var no = $(this).data("no");
		$("[name = 'no']").val(no);
				
		//비밀번호 창 초기화
		$("#modalPassword").val("");
		
		//모달창 보이기
		$("#delModal").modal();

	});
	
	//삭제 모달창의 삭제버튼 클릭할때
	$("#modalBtnDel").on("click", function(){
		console.log("모달창 삭제버튼 클릭");
		
		var no = $("[name = 'no']").val();
		var guestbookVo = {
			no: $("[name = 'no']").val(),
			password: $("[name = 'password']").val()
		};
		console.log(guestbookVo);
		
		//서버에 삭제요청(no, password 전달)
		$.ajax({
			
			url : "${ pageContext.request.contextPath }/api/guestbook/remove",		
			type : "post",
			//contentType : "application/json",
			data : guestbookVo,

			dataType : "json",
			success : function(count){
				/*성공시 처리해야될 코드 작성*/
				
				if(count === "1"){
					//모달창닫기
					$("#delModal").modal("hide");
					
					//리스트에 삭제했던 테이블을 화면에서 지운다
					$("#t-" + no).remove();
				}else{
					//모달창닫기
					$("#delModal").modal("hide");
				};
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
			
		});
		
	});
	
	//리스트 가져오기
	function fetchList(){
		$.ajax({

			url : "${ pageContext.request.contextPath }/api/guestbook/list",
			type : "post",
			//contentType : "application/json",
			//data : {name: ”홍길동"},

			dataType : "json",
			success : function(guestList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestList);

				//화면에 그리기
				for (var i = 0; i < guestList.length; i++) {
					render(guestList[i], "down");
				};

			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	};

	//리스트 함수
	function render(guestVo, type) {
		var str = '';
		str += '<table id="t-' + guestVo.no + '" class="guestRead">';
		str += '	<colgroup>';
		str += '		<col style="width: 10%;">';
		str += '		<col style="width: 40%;">';
		str += '		<col style="width: 40%;">';
		str += '	</colgroup>';
		str += '	<tr>';
		str += '		<td>' + guestVo.no + '</td>';
		str += '		<td>' + guestVo.name + '</td>';
		str += '		<td>' + guestVo.regDate + '</td>';
		str += '		<td><button class="btnDel" data-no="' + guestVo.no + '">삭제</button></td>';
		str += '	</tr>';
		str += '	<tr>';
		str += '		<td colspan=4 class="text-left">' + guestVo.content + '</td>';
		str += '	</tr>';
		str += '</table>';

		if (type === 'down') {
			$("#listArea").append(str);
		} else if (type === 'up') {
			$("#listArea").prepend(str);
		} else {
			console.log("타입미스");
		}
		;
	};
</script>

</html>

