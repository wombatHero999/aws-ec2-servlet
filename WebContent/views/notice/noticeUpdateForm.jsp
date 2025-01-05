<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice"%>
<%
	Notice n = (Notice)request.getAttribute("n");
	// 글번호, 제목, 내용, 작성자아이디, 작성일
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
    
    #update-form>table {border:1px solid white;}
    #update-form input, #update-form textarea {
        width:100%; /* 가로길이를 부모요소의 100% 가 되도록 */
        box-sizing:border-box; /* content 영역 기준으로 100% 인데 그 밖에 padding 이나 border 영역까지 있을 경우 테두리까지 포함한 기준으로 100% 를 지정해라 라는것 */
    }
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">
        <br> <h2 align="center">공지사항 수정하기</h2>

        <form id="update-form" action="<%= contextPath %>/update.no" method="post">
        	<input type="hidden" name="nno" value="<%= n.getNoticeNo() %>">
			<table align="center">
                <tr>
                    <th width="50">제목</th>
                    <td width="350"><input type="text" name="title" required value="<%= n.getNoticeTitle() %>"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea name="content" rows="10" style="resize:none" required><%= n.getNoticeContent() %></textarea>
                    </td>
                </tr>
            </table>
            <br><br>
            <div align="center">
                <button type="submit">수정하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button> 
                <!-- history.back() : 이전 페이지로 돌아가게 해주는 함수 -->
            </div>

        </form>

    </div>

</body>
</html>