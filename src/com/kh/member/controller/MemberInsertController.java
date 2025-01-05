package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.common.AESCryptor;
import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet(name="memberInsertServlet" , urlPatterns =  "/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 1) 인코딩 작업
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값을 뽑아서 변수 및 객체에 기록하기
		String userId = request.getParameter("userId"); // 필수입력
		String userPwd = request.getParameter("userPwd"); // 필수입력
		String userName = request.getParameter("userName"); // 필수입력
		String phone = request.getParameter("phone"); // 빈 문자열이 담길 수 있다.
		String email = request.getParameter("email"); // 빈 문자열이 담길 수 있다.
		String address = request.getParameter("address"); // 빈 문자열이 담길 수 있다.
		String[] interestArr = request.getParameterValues("interest"); // ["운동", "등산", ...] / null
		
		email = AESCryptor.encrypt(email);
		
		// String[] --> String
		// ["운동", "등산"] --> "운동, 등산"
		String interest = "";
		
		
		
		if(interestArr != null) {
			interest = String.join(", ", interestArr);
		}
		
		// 매개변수 생성자를 이용해서 Member 객체에 담기
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
		
		// 3) 요청 처리 (서비스 메소드 호출 및 결과 받기)
		int result = new MemberService().insertMember(m);
		
		// 4) 처리 결과를 가지고 사용자가 보게 될 응답 뷰 지정
		if(result > 0) { // 성공 => /jsp
						 // url 재요청 => index.jsp
			
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			
			response.sendRedirect(request.getContextPath());
		}
		else { // 실패 => 에러페이지
			
			request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
