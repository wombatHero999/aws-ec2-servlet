<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.*" %>
<%
	ArrayList<Category> list = (ArrayList<Category>) request.getAttribute("list");
	Board b = (Board) request.getAttribute("b");
	Attachment at = (Attachment) request.getAttribute("at");
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
    
    #update-form>table {border:1px solid white}
    #update-form input , #update-form textarea {
    	width:100%;
    	box-sizing : border-box;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

	<div class="outer">
		<br>
		<h2 style="text-align:center;">일반게시판 수정하기</h2>
		<br>
		
		<form action="<%= contextPath %>/update.bo" id="update-form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="bno" value="<%= b.getBoardNo() %>">
			
			<table align="center">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
							<% for(Category c : list) { %>
								<option value="<%= c.getCategoryNo() %>"
								<%--  <%if(c.getCategoryName().equals(b.getCategory())){ %> 
									selected="selected"
								<%} %> --%>
								 ><%= c.getCategoryName() %></option>
							<% } %>
						</select>
						
						<script>
							$(function(){
								$("#update-form option").each(function(){
									//현재 반복 진행중인 option태그의 text값(공통,게임,낚씨..)과
									//db에서 가져온 categoryname값이
									//일치하는 경우 선택되도록
									if($(this).text() == "<%=b.getCategory() %>"){
										//일치하는경우에만 option태그를 선택상태로 변경
										$(this).attr("selected",true);
									}
								})
							})
						</script>						
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input type ="text" name="title" required value="<%= b.getBoardTitle() %>" ></td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" rows="10" required><%= b.getBoardContent() %></textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<% if(at != null ) { %>
							<%= at.getOriginName() %>
							<!-- 원본파일의 파일번호, 수정명을 hideen으로 넘길것. -->
							<input type="hidden" name="originFileNo" value="<%= at.getFileNo() %>">
							<input type="hidden" name="originFileName" value="<%= at.getChangeName() %>">
						<% } %>
						<input type="file" name="upfile">					
					</td>
				</tr>
			</table>
			
			<br>
			
			<div align="center">
				<button>수정하기</button>
			</div>
		</form>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	








</body>
</html>