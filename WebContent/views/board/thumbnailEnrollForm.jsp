<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        height:700px;
        margin:auto;
        margin-top:50px;
    }

    #enroll-form table {border:1px solid white;}
    #enroll-form input, #enroll-form textarea {width:100%; box-sizing:border-box;}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
    <div class="outer">
        <br>
            <h2 align="center">사진게시판 작성하기</h2>
        <br>

        <form action="<%= contextPath %>/insert.th" id="enroll-form" method="post" enctype="multipart/form-data">
        
        	<!-- 현재 로그인한 사용자(작성자)의 회원번호를 hidden 요소로 넘길 예정 -->
        	<input type="hidden" name="userNo" value="<%= loginUser.getUserNo() %>">
            <table align="center">

                <!-- (tr>th+td+td+td)*4 -->

                <tr>
                    <th width="100">제목</th>
                    <td colspan="3"><input type="text" name="boardTitle" required></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"><textarea name="boardContent" style="resize:none" rows="5" required></textarea></td>
                </tr>
                <tr>
                    <th>대표이미지</th> <!-- 미리보기 영역 -->
                    <td colspan="3" align="center">
                        <img id="titleImg" width="250" height="170">
                    </td>
                </tr>
                <tr>
                    <th>상세이미지</th>
                    <td><img id="contentImg1" width="150" height="120"></td>
                    <td><img id="contentImg2" width="150" height="120"></td>
                    <td><img id="contentImg3" width="150" height="120"></td>
                </tr>

            </table>

            <div id="file-area">
                <!-- input[type=file id=file$ name=file$]*4 -->
                <input type="file" id="file1" name="file1" onchange="loadImg(this, 1);" required> <!-- 대표이미지(썸네일) 은 필수 -->
                <input type="file" id="file2" name="file2" onchange="loadImg(this, 2);">
                <input type="file" id="file3" name="file3" onchange="loadImg(this, 3);">
                <input type="file" id="file4" name="file4" onchange="loadImg(this, 4);">
                <!-- onchange : input 태그의 내용물이 변경될 시 발생하는 이벤트 -->
                <!-- loadImg()  함수는 우리가 정의할것, 매개변수는 각각의 이미지의 위치 -->
            </div>

            <script>
            
            	$(function(){
            		$("#file-area").hide();
            		
            		$("#titleImg").click(function(){
            			$("#file1").click();
            		});
            		$("#contentImg1").click(function(){
            			$("#file2").click();
            		});
            		$("#contentImg2").click(function(){
            			$("#file3").click();
            		});
            		$("#contentImg3").click(function(){
            			$("#file4").click();
            		});
            	});

                function loadImg(inputFile, num) {
                    // inputFile : 현재 변화가 생긴 input type="file" 요소 객체
                    // num : 몇번째 input 요소인지 확인 후 해당 그 영역에 미리보기 하기 위한 매개변수

                    // console.log(inputFile.files.length);
                	<!-- 파일 선택 시 1, 파일 선택 취소 시 0 이 찍히는 것을 확인 가능하다. => 즉, 파일의 존재 유무를 알 수 있다. --> 
                	<!-- files 속성은 업로드된 파일의 정보들을 배열 형식으로 여러개 묶어서 반환, length 는 배열 크기 -->
                	
                	if(inputFile.files.length == 1) {
                		// 선택된 파일이 존재할 경우
                		// 선택된 파일을 읽어들여서 그 영역에 맞는 곳에 미리보기
                		
                		// 파일을 읽어들일 FileReader 객체 생성
                		var reader = new FileReader();
                		
                		// 파일을 읽어들이는 메소드 => 어느 파일을 읽어들일껀지 매개변수로 제시해야함
                		// 0번 인덱스에 담긴 파일 정보를 제시
                		// => 해당 파일을 읽어들이는 순간 해당 그 파일만의 고유한 url 이 부여됨 
                		// => 해당 url 을 src 속성으로 부여
                		reader.readAsDataURL(inputFile.files[0]);
                		
                		// 파일 읽기가 완료되었을 때 실행할 함수를 정의
                		reader.onload = function(e){ // e 의 target.result 에 각 파일의 url 이 담긴다.
                			// 각 영역에 맞춰서 이미지 미리보기
                			switch(num) {
                			case 1 : $("#titleImg").attr("src", e.target.result); break;
                			case 2 : $("#contentImg1").attr("src", e.target.result); break;
                			case 3 : $("#contentImg2").attr("src", e.target.result); break;
                			case 4 : $("#contentImg3").attr("src", e.target.result); break;
                			}
                		}
                	}
                	else {
                		// 선택된 파일이 사라졌을 경우
                		// 미리보기 사라지게 하기
                		switch(num) {
               			case 1 : $("#titleImg").attr("src", null); break;
               			case 2 : $("#contentImg1").attr("src", null); break;
               			case 3 : $("#contentImg2").attr("src", null); break;
               			case 4 : $("#contentImg3").attr("src", null); break;
               			}
                	}
                }

            </script>

            <div align="center">
                <button type="submit">등록하기</button>
            </div>
        
        </form>
	
</body>
</html>