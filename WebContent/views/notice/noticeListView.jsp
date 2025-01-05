<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.notice.model.vo.Notice"%>
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
        height:500px;
        margin:auto;
        margin-top:50px;
    }
    .list-area {
        border:1px solid white;
        text-align:center;
    }

    .list-area>tbody>tr:hover {
        background:gray;
        cursor:pointer;
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">

        <br>
        <h2 align="center">공지사항</h2>
        <br>

		<!-- 공지사항은 관리자만 작성 가능하므로 -->
		<% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
	        <div align="right" style="width:850px"> <!-- 글 작성 버튼을 오른쪽에 보이게 하고싶으나 div가 너무 길 경우는 가로 길이를 줄인다. -->
	            <!-- <button onclick="location.href='';">글작성</button> -->
	            <!-- a 태그는 href 속성이 있지만, 버튼은 없으므로 이 경우 다른 페이지로 이동하려면
	            	클릭 이벤트 발생 시 location.href='요청할 url'; 을 제시한다. -->
	            <!-- a 태그를 쓰고도 버튼 모양으로 만들고싶다면? 부트스트랩 활용하기 -->
	            <a href="<%= contextPath %>/insert.no" class="btn btn-secondary">글작성</a>
	            <br><br>
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
                <!-- 보통 작성일 기준 내림차순, 즉, 최근 글이 제일 위에 오게끔 구현한다. -->
                <!-- 
	                <tr>
	                    <td>3</td>
	                    <td>제목ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ</td>
	                    <td>admin</td>
	                    <td>120</td>
	                    <td>2021-05-07</td>
	                </tr>
	                <tr>
	                    <td>2</td>
	                    <td>공지사항임</td>
	                    <td>admin</td>
	                    <td>70</td>
	                    <td>2021-04-18</td>
	                </tr>
	                <tr>
	                    <td>1</td>
	                    <td>참고사항!</td>
	                    <td>admin</td>
	                    <td>192</td>
	                    <td>2021-01-02</td>
	                </tr>
	            -->
	            
	            <% if(list.isEmpty()) { %>
	            	<!-- 리스트가 비어있을 경우 -->
					<tr>
						<td colspan="5">존재하는 공지사항이 없습니다.</td>
					</tr>
	            
	            <% } else { %>
	            	<!-- 리스트가 비어있지 않을 경우 -->
	            	<% for(Notice n : list) { %> <!-- 향상된 for 문 -->
		            	<tr>
		                    <td><%= n.getNoticeNo() %></td>
		                    <td><%= n.getNoticeTitle() %></td>
		                    <td><%= n.getNoticeWriter() %></td>
		                    <td><%= n.getCount() %></td>
		                    <td><%= n.getCreateDate() %></td>
		                </tr>
		            <% } %>
	            <% } %>
            </tbody>
        </table>
    </div>
    
    <script>	
    	$(function(){
    		
    		$(".list-area>tbody>tr").click(function(){
    			
    			// console.log("클릭됨");
    			// 클릭했을 때 해당 공지사항의 번호를 넘겨야 함
    			// 해당 tr 요소의 자손 중에서도 첫번째 td 영역의 내용이 필요
    			var nno = $(this).children().eq(0).text();
    			// console.log(nno);
    			
    			// 요청할 url?키=밸류&키=밸류&...
    			// 물음표 뒤의 내용들을 쿼리스트링이라고 한다. => 직접 만들어서 넘겨야 함
    			// /jsp/detail.no?nno=클릭했을때의글번호
    			location.href = '<%= contextPath %>/detail.no?nno=' + nno;
    		});
    	});
    
    </script>

</body>
</html>