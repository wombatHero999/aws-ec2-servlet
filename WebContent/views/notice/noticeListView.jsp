<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import ="java.util.ArrayList , com.kh.notice.model.vo.Notice" %>
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");

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
    .list-area{
    	border: 1px solid white;
    	text-align : center;
    }
    .list-area>tbody>tr:hover{
    	background-color:gray;
    	cursor:pointer;
    }
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 style="text-align:center">공지사항</h2>
		<br>
		
		<!-- 공지사항은 관리자만 작성가능. -->
		<% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
			<div align="right" style="width:850px;">
				<!-- a태그를 쓰고도 버튼 모양으로 만들수가 있음.(부트스트랩에서 제공하는 btn class) -->
				<a class="btn btn-secondary" href="<%=contextPath %>/enrollForm.no">글작성</a>
			</div>
		<% } %>
		
		<table class="list-area" align="center">
			<thead>
				<tr>
					<th>글번호</th>
					<th width="400">글제목</th>
					<th width="100">작성자</th>
					<th>조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
			<% if(list.isEmpty()){ %>
				<!-- 리스트가 비어있는경우. -->
				<tr>
					<td colspan="5">존재하는 공지사항이 없습니다.</td>
				</tr>
			<%} else { %>
				<% for(Notice n : list) { %>
					<tr>
						<td><%= n.getNoticeNo() %></td>
						<td><%= n.getNoticeTitle() %></td>
						<td><%= n.getNoticeWriter() %></td>
						<td><%= n.getCount() %></td>
						<td><%= n.getCreateDate() %></td>
					</tr>
				<% } %>
			<% }%>
			</tbody>
		</table>
		
	</div>
	
	<script>
		$(function(){
			$(".list-area>tbody>tr").click(function(){
				// 클릭시 해당 공지사항의 번호를 넘겨야함.
				// 해당 tr요소의 자손중에서 첫번째 td의 영역의 내용이 필요.
				
				let nno = $(this).children().eq(0).text();// 1 , 2
				//현재내가클릭한tr의 자손들중 0번째에 위치한 자식의 textnode내용을 가져온다.
				
				//요청할 url?키=밸류&키=밸류&키=밸류
				// 물음표 뒤의 내요을 쿼리스트링이라고 부른다. => 직접 만들어서 넘겨야함.
				location.href= '<%=contextPath %>/detail.no?nno='+ nno;				
			});
		})
		
	
	</script>
	
	
	
	
	
	
	
	
	
	

</body>
</html>