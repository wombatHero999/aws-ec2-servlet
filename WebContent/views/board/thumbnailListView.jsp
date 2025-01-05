<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
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
        height:800px;
        margin:auto;
        margin-top:50px;
    }

    .list-area {
        width:760px;
        margin:auto;
    }

    .thumbnail {
        border:1px solid white;
        width:220px;
        display:inline-block;
        margin:14px;
    }

    .thumbnail:hover {
        cursor:pointer;
        opacity:0.7;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">
        <br>
            <h2 align="center">사진게시판</h2>
        <br>
        
        <!-- 로그인한 회원만 보여지는 버튼 -->
        <% if(loginUser != null) { %>
	        <div align="right" style="width:850px">
	            <a href="<%= contextPath %>/enrollForm.th" class="btn btn-secondary">글작성</a>
	            <br>
	            <br>
	        </div>
        <% } %>

        <div class="list-area">
        <% if(list != null) { %>
			<% for(Board b : list) { %>
	            <div class="thumbnail" align="center">
	            	<input type="hidden" value="<%= b.getBoardNo() %>">
	                <img src="<%= contextPath %>/<%= b.getTitleImg() %>" width="200px" height="150px">
	                <p>
	                    No.<%= b.getBoardNo() %> <%= b.getBoardTitle() %> <br>
	                                     조회수 : <%= b.getCount() %>
	                </p>
	            </div>
            <% } %>
        <% } else { %>
        	등록된 게시글이 없습니다.
        <% } %>
        </div>
        
        <script>
        	$(function(){
        		$(".thumbnail").click(function(){
        			location.href = "<%= contextPath %>/detail.th?bno=" + $(this).children().eq(0).val();
        		});
        	});
        </script>

    </div>
	
</body>
</html>