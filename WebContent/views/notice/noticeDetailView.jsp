<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice" %>
<%
	Notice n = (Notice) request.getAttribute("n");
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
        height: 500px;
        margin:auto; /* 가운데 자동 정렬 */
        margin-top:50px; /* 위로부터 50px 아래로 여백 만들기 */
    }
    
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	<div class="outer">
		<br><h2 style="text-align:center;">공지사항 상세보기</h2><br>
	
		<table id="detail-area" align="center" border="1">
			<tr>
				<th width="70">제목</th>
				<td width="350" colspan="3"><%=n.getNoticeTitle() %></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=n.getNoticeWriter() %></td>
				<th>작성일</th>
				<td><%=n.getCreateDate()%></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:150px"><%= n.getNoticeContent() %></p>
				</td>
			</tr>
		</table>
	
		<br><br>
		
		<div align="center">
			<a href="<%=contextPath %>/list.no" class="btn btn-secondary btn-sm">목록으로가기</a>
			
			<% if(loginUser != null && loginUser.getUserId().equals(n.getNoticeWriter())) { %>
				<!--  현재 로그인한 사용자가 해당 글을 작성한 작성자일 경우에만 보여진다. -->
				<a href="<%=contextPath %>/updateForm.no?nno=<%=n.getNoticeNo() %>" class="btn btn-warning btn-sm">수정하기</a>
				<a href="<%=contextPath %>/delete.no?nno=<%=n.getNoticeNo() %>" class="btn btn-danger btn-sm">삭제하기</a>
			<% } %>
		</div>		
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>