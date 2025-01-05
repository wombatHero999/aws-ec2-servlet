<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "com.kh.board.model.vo.*" %>
<%
	Board b = (Board) request.getAttribute("b");
	// 게시글번호, 카테고리명, 제목, 내용 , 작성자아이디, 작성일	

	Attachment at = (Attachment) request.getAttribute("at");
	// 파일번호, 원본명 , 수정명 , 저장경로
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer {
        background:black;
        color:white;
        width:1000px;
        margin:auto; /* 가운데 자동 정렬 */
        margin-top:50px; /* 위로부터 50px 아래로 여백 만들기 */
    }
</style>
</head>
<body>
	<%@ include file= "../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 style="text-align:center;">일반게시판 상세보기</h2>
		<br>
		
		<table id="detail-area" align="center" style="border: 1px solid white;">
			
			<tr>
				<th width="70">카테고리</th>
				<td width="70"><%= b.getCategory() %></td>
				<th width="70">제목</th>
				<td width="350"><%= b.getBoardTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%= b.getBoardWriter() %></td>
				<th>작성일</th>
				<td><%= b.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:200px;"><%= b.getBoardContent() %></p>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					<% if(at == null) { %>
						<!-- 첨부파일이 없는경우 -->
						첨부파일이 없습니다.
					<% } else { %>
						<!-- 첨부파일이 있는경우  -->
						<!-- 브라우저에서 http://localhost:8001/jsp/resources/board_upfiles/xxx.jpg -->
						<a href="<%=contextPath %>/<%= at.getFilePath() + at.getChangeName() %>" 
						download="<%= at.getOriginName() %>">
							<%= at.getOriginName() %>
						</a>
					<% } %>
				</td>
			</tr>
		</table>
			
		<br>
		
		<div align="center">
			<a href="<%=contextPath %>/list.bo?currentPage=1" class="btn btn-secondary btn-sm">목록</a>
			<% if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
				<!--  현재 로그인한 사용자가 해당 글을 작성한 작성자일 경우에만 보여진다. -->
				<a href="<%=contextPath %>/updateForm.bo?bno=<%=b.getBoardNo() %>" class="btn btn-warning btn-sm">수정하기</a>
				<a href="<%=contextPath %>/delete.bo?bno=<%=b.getBoardNo() %>" class="btn btn-danger btn-sm">삭제하기</a>
			<% } %>
		</div>
		
		<br>
		
		<!-- 우선 화면구현먼저 진행.기능구현은 ajax 배우고나서 할것  -->
		<div id="reply-area">
		
			<table border="1" align="center">
				<thead>
					<% if(loginUser != null){ %>
						<!-- 로그인이 되어있을 경우. -->
						<tr>
							<th>댓글작성</th>
							<td>
								<textarea id="replyContent" cols="50" rows="3" style="resize:none;"></textarea>
							</td>
							<td><button onclick="insertReply();">댓글등록</button></td>
						</tr>
					<% } else { %>
						<tr>
							<th>댓글작성</th>
							<td>
								<textarea cols="50" rows="3" style="resize:none;" readonly>로그인 후 이용가능한 서비스 입니다.</textarea>
							</td>
							<td><button disabled>댓글등록</button></td>
						</tr>
					<% } %>
				</thead>
				<tbody>
				</tbody>
			</table>
		
		</div>
		
	
	</div>
	
	<script>
		$(function(){
			selectReplyList();
			
			setInterval(selectReplyList , 1000);
		});
	
		
		function insertReply(){
			$.ajax({
				url : "rinsert.bo",
				data : {
					content : $("#replyContent").val() ,	
					bno     : ${b.boardNo}
				},
				type : "post",
				success : function(result){
					if(result > 0){// 댓글등록 성공 => 갱신된 댓글리스트 조회
						selectReplyList();
						$("#replyContent").val("");						
					}
				}, error : function(){
					console.log("댓글작성용 ajax 통신 실패")	
				}
			})
		}
		
		function selectReplyList(){
			$.ajax({
				url : "rlist.bo",
				data : {bno : ${b.boardNo} },
				success : (list) => {
					
					let result = "";
					for(let i of list){
						result += "<tr>"
									   +"<td>"+i.boardWriter +"</td>"
									   +"<td>"+i.replyContent +"</td>"
									   +"<td>"+i.createDate +"</td>"
							   +  "</tr>";
					}
					$("#reply-area tbody").html(result);
					
					
				},
				error : function(){
					console.log("댓글리스트조회용 ajax통신 실패~");
				}
				
			});
			
			
			
		}
		
	
	
	</script>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

</body>
</html>