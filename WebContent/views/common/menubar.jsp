<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member" %>
<%
	// System.out.println(request.getContextPath());
	String contextPath = request.getContextPath();

	Member loginUser = (Member)session.getAttribute("loginUser");
	// 로그인 전 menubar.jsp 로딩 시 : null
	// 로그인 후 menubar.jsp 로딩 시 : 로그인 한 회원의 정보가 담긴 Member 객체
	
	String alertMsg = (String)session.getAttribute("alertMsg");
	
	// 서비스 요청 전 menubar.jsp 로딩 시 : null
	// 서비스 요청 성공 후 menubar.jsp 로딩 시 : alert 로 띄워줄 메시지 문구
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome D Class</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
    #login-form, #user-info {float:right}
    #user-info a {
        text-decoration:none; /* a 태그의 밑줄 없애기 */
        color:black;
        font-size:12px;
    }

    .nav-area {background:black;}
    .menu {
        display:table-cell; /* 인라인요소처럼 배치 가능 */
        height:50px;
        width:150px;
    }
    .menu a {
        text-decoration:none;
        color:white;
        font-size:20px;
        font-weight:bold;
        display:block;
        width:100%;
        height:100%; /* display:block 으로 지정 후 width height 를 100% 로 지정하면 
                        해당 블록 안에 마우스 포인터가 들어갔을 경우 a 태그 영역으로 인식된다. */
        line-height:50px; /* 장평조절 : 위~아래 에서 가운데로 조정 */
    }
    .menu a:hover {
        background:darkgray;
    }
</style>
</head>
<body>

	<script>
		// script 태그 내에서도 스크립틀릿과 같은 jsp 요소를 쓸 수 있다.
		
		var msg = "<%= alertMsg %>"; // "성공적으로 로그인이 되었습니다." / "null"
		
		if(msg != "null") {
			alert(msg);
			// 알림창을 띄워준 후 session 에 담긴 해당 메시지는 지워줘야 함
			// 안그러면 menubar.jsp 가 로딩될때마다 매번 alert 가 계속 뜰 것
			
			<% session.removeAttribute("alertMsg"); %>
		}
		
	</script>
    
    <h1 align="center">Welcome D Class</h1>

    <div class="login-area">

        <% if(loginUser == null) { %>
        
	        <!-- 로그인 전에 보여지는 로그인 form -->
	        <form id="login-form" action="<%= request.getContextPath() %>/login.me" method="post">
	            <table>
	                <tr>
	                    <th>아이디 : </th>
	                    <td><input type="text" name="userId" required></td>
	                </tr>
	                <tr>
	                    <th>비밀번호 : </th>
	                    <td><input type="password" name="userPwd" required></td>
	                </tr>
	                <tr>
	                    <th colspan="2">
	                        <button type="submit">로그인</button>
	                        <button type="button" onclick="enrollPage();">회원가입</button>
	                    </th>
	                </tr>
	            </table>
	        </form>
	        
	        <script>
	        	function enrollPage() {
	        		// location.href = "<%= contextPath %>/views/member/memberEnrollForm.jsp";
	        		// 웹 애플리케이션의 디렉토리 구조가 url 에 노출되면 보안에 취약
	        		
	        		// 단순한 정적인 페이지 요청이라고 해도 반드시 servlet 을 거쳐갈 것! => 
					//url 에 서블릿 매핑값만 노출됨
	        		location.href = "<%= contextPath %>/enrollForm.me";
	        	}
	        </script>
        
        <% } else { %>

	        <!-- 로그인 성공 후 -->
	        <div id="user-info">
	            <b><%= loginUser.getUserName() %>님</b> 환영합니다. <br><br>
	            <div align="center">
	                <a href="<%= contextPath %>/myPage.me">마이페이지</a>
	                <a href="<%= contextPath %>/logout.me">로그아웃</a>
	            </div>
	        </div>
        
        <% } %>

    </div>

    <br clear="both"> <!-- float 속성 해제 -->
    <br>

    <div class="nav-area" align="center">

        <!-- (.menu>a)*4 -->
        <div class="menu"><a href="<%= contextPath %>">HOME</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.no">공지사항</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="<%= contextPath %>/list.th">사진게시판</a></div>

    </div>

</body>
</html>