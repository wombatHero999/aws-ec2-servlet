package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(name="loginServlet" , urlPatterns = "/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*
		 * < HttpServletRequest 객체와 HttpServletResponse 객체 >
		 * - request : 서버로 요청할 때의 정보들이 담겨있음 (요청 시 전달값, 요청 전송 방식 등)
		 * - response : 요청에 대해 응답할 때 필요한 객체
		 * 
		 * < Get 방식과 Post 방식 차이 >
		 * - Get 방식 : 사용자가 입력한 값들이 url 노출 O / 데이터 길이제한 O / 대신 즐겨찾기 편리
		 * - Post 방식: 					     노출 X / 데이터 길이제한 X / 대신 Timeout 이 존재
		 */
		
		// 1) 전달값에 한글이 있을 경우 인코딩 처리해야 함 (Post 방식일 경우)
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값 (request 의 parameter 영역) 꺼내서 변수 또는 객체에 기록하기
		// request.getParameter("키값") : String 밸류값
		// request.getParameterValues("키값") : String[] 밸류값들
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// 3) 해당 요청을 처리하는 서비스 클래스의 메소드 호출
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		
		// 4) 처리된 결과를 가지고 사용자가 보게 될 뷰 지정 후 포워딩 또는 url 재요청
		// System.out.println(loginUser);
		
		/*
		 * * 응답 페이지에 전달할 값이 있을 경우 값을 어딘가에 담아야 함 (담아줄 수 있는 Servlet Scope 내장객체 4 종류)
		 * 1) application : application 에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내 쓸 수 있음
		 * 2) session : session 에 담은 데이터는 모든 jsp 와 servlet 에서 꺼내 쓸 수 있음
		 * 				한번 담은 데이터는 내가 직접 지우기 전까지, 서버가 멈추기 전까지, 브라우저가 종료되기 전까지
		 * 				접근해서 꺼내 쓸 수 있음
		 * 3) request : request 에 담은 데이터는 해당 request 를 포워딩한 응답 jsp 에서만 꺼내 쓸 수 있음
		 * 4) page : 해당 jsp 페이지에서만 꺼내 쓸 수 있음
		 * 
		 * => session 과 request 를 주로 쓴다! 
		 * 
		 * 공통적으로 데이터를 담고자 한다면 .setAttribute("키", "밸류");
		 * 		     데이터를 꺼내고자 한다면 .getAttribute("키");
		 * 		     데이터를 지우고자 한다면 .removeAttribute("키");
		 */
		
		if(loginUser == null) { // 로그인 실패 => 에러페이지 응답
			
			request.setAttribute("errorMsg", "로그인에 실패했습니다.");
			
			// 응답 페이지 jsp 에 위임 시 필요한 객체 (RequestDispatcher)
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			
			// 포워딩 방식 : 해당 경로로 선택된 뷰가 보여질 뿐 url 은 절대 변경되지 않음 (요청했을때의 url 이 여전히 남아있음)
			view.forward(request, response);
		}
		else { // 로그인 성공 => index 페이지 응답
			
			// 로그인 한 회원의 정보를 로그아웃 하기 전까지 계속 가져다 쓸 것이기 때문에 session 에 담기
			
			// Servlet 에서 JSP 내장 객체인 session 에 접근하고자 한다면 우선 session 객체를 얻어와야 함
			HttpSession session = request.getSession();
			
			session.setAttribute("loginUser", loginUser);
			
			session.setAttribute("alertMsg", "성공적으로 로그인이 되었습니다.");
			
			// 1. 포워딩 방식 응답 뷰 출력하기
			//	    해당 선택된 jsp 가 보여질 뿐 url 에는 여전히 현재 이 서블릿 매핑값이 남아있을 것
			// 	  localhost:8888/jsp/login.me
			// RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			// view.forward(request, response);
			
			// 2. url 재요청방식 (sendRedirect 방식)
			// 	  localhost:8888/jsp
			// response.sendRedirect("/jsp");
			response.sendRedirect(request.getContextPath());
			
			// 각 서비스 마다 사용되는 방식이 다름
			// => 로그인 시에는 2번 방식이 쓰인다!
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
