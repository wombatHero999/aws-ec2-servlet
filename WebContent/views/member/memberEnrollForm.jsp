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
        margin:auto; /* 가운데 자동 정렬 */
        margin-top:50px; /* 위로부터 50px 아래로 여백 만들기 */
    }

    #enroll-form table {margin:auto;}
    #enroll-form input {margin:5px;}
</style>
</head>
<body>

	<%@ include file="../common/menubar.jsp" %> 
	<!-- ../ : 현재 폴더로부터 한번 빠져나감 (즉, 현재 폴더로부터 한단계 상위폴더로 이동)  -->
	
	<div class="outer">

        <br>
        <h2 align="center">회원가입</h2>

        <form id="enroll-form" action="<%= contextPath %>/insert.me" method="post"> 
        <!-- menubar.jsp 를 위에서 include 했기 때문에 contextPath 변수를 가져다 쓸 수 있다. -->

            <!-- 아이디, 비밀번호, 이름, 전화번호, 이메일, 주소, 취미 -->
            <table>
                <!-- (tr>td*3)*8 -->
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" required></td>
                    <td><button type="button" onclick="idCheck();">중복확인</button></td>
                </tr>
                <tr>
                    <td>* 비밀번호</td>
                    <td><input type="password" name="userPwd" maxlength="15" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>* 비밀번호 확인</td>
                    <td><input type="password" maxlength="15" required></td> <!-- 단순 비교 확인 용도라 key 값을 부여 안해도 됨 -->
                    <td></td>
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력"><td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
                        <!-- (input[type=checkbox name=interest id= value=]+label)*6 -->

                        <input type="checkbox" name="interest" id="sports" value="운동">
                        <label for="sports">운동</label>

                        <input type="checkbox" name="interest" id="hiking" value="등산">
                        <label for="hiking">등산</label>

                        <input type="checkbox" name="interest" id="fishing" value="낚시">
                        <label for="fishing">낚시</label>

                        <br>

                        <input type="checkbox" name="interest" id="cooking" value="요리">
                        <label for="cooking">요리</label>

                        <input type="checkbox" name="interest" id="game" value="게임">
                        <label for="game">게임</label>

                        <input type="checkbox" name="interest" id="movie" value="영화">
                        <label for="movie">영화</label>
                    </td>
                </tr>
            </table>
            
            <br><br>

            <div align="center">
                <button type="submit" disabled>회원가입</button>
                <button type="reset">초기화</button>
            </div>

            <br><br>

        </form>

    </div>
    
    <script>
    	function idCheck() {
    		
    		// 아이디를 입력하는 input 요소 객체
    		var $userId = $("#enroll-form input[name=userId]"); 
    		// name 이 userId 인 요소가 menubar.jsp 에도 있어서 확실하게 어디에 속해있는 요소인지 잘 적어줘야함
    		
    		$.ajax({
    			url : "idCheck.me",
    			data : {checkId : $userId.val()},
    			success : function(result) {
    				if(result == "NNNNN") { // 사용불가
    					alert("이미 존재하거나 탈퇴한 회원의 아이디입니다.");
    					$userId.focus(); // 다시 입력 유도
    				}
    				else { // 사용 가능
    					if(confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")) { // 사용함
    						
    						$("#enroll-form :submit").removeAttr("disabled"); // 회원가입버튼 활성화
    						$userId.attr("readonly", true); // 아이디값 확정
    					}
    					else { // 사용 안함 => 다시입력
        					$userId.focus(); // 다시 입력 유도
    					}
    				}
    			},
    			error : function() {
    				console.log("아이디 중복체크용 ajax 통신 실패!");
    			}
    		});
    		
    	}
    </script>
	
</body>
</html>