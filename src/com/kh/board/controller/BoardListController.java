package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardListController() {
	    super();
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ---------------- 페이징 처리 ----------------
		int listCount; 		// 현재 총 게시글 갯수
		int currentPage; 	// 현재 페이지(즉, 사용자가 요청한 페이지)
		int pageLimit; 		// 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit;  	// 한 페이지에 보여질 게시글의 최대갯수 (몇개 단위씩)
	
		int maxPage; 		// 가장 마지막 페이지가 몇번페이지인지 (총 페이지 수)
		int startPage;		// 페이지 하단에 보여질 페이징바의 시작수
		int endPage; 		// 페이지 하단에 보여질 페이징바의 끝수
		
		// * listCount : 총 게시글 갯수
		listCount = new BoardService().selectListCount();

		// System.out.println(listCount);
		
		// * currentPage : 현재페이지 (즉, 사용자가 요청한 페이지)
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// * pageLimit : 페이지 하단에 보여질 페이징바의 페이지 최대 갯수(페이지 목록들 몇개 단위씩)
		pageLimit = 10;
		
		// * boardLimit : 한 페이지에 보여질 게시글의 최대갯수 (게시글 몇개 단위씩)
		boardLimit = 10;
		
		// * maxPage : 가장 마지막 페이지가 몇번페이지인지 (총 페이지 수)
		/*
		 * listCount, boardLimit 에 영향을 받음
		 * 
		 * - 공식 구하기
		 *   단, boardLimit 이 10 이라는 가정 하에 규칙을 구해보자!
		 *   
		 *   총 갯수           boardLimit       maxPage
		 *   100.0개	 /  10개	 => 10.0	 10번 페이지
		 *   101.0개	 /  10개	 => 10.1	 11번 페이지
		 *   105.0개	 /  10개	 => 10.5     11번 페이지
		 *   109.0개   /  10개	 => 10.9	 11번 페이지
		 *   110.0개   /  10개	 => 11.0     11번 페이지
		 * 	 111.0개	 /  10개	 => 11.1     12번 페이지
		 *   => 나눗셈 연산한 결과를 올림처리한다면? maxPage의 값이 나온다.
		 *   
		 *   1) listCount 를 double 로 형변환
		 *   2) listCount / boardLimit
		 *   3) 결과에 올림처리 Math.ceil() 메소드 호출
		 *   4) 결과값을 int 로 형변환
		 */
		maxPage = (int)Math.ceil(((double)listCount / boardLimit));
		
		// * startPage : 페이지 하단에 보여질 페이징바의 시작수
		/*
		 * pageLimit, currentPage 에 영향을 받음
		 * 
		 * - 공식 구하기
		 *   단, pageLimit 이 10 이라는 가정 하에 규칙을 구해보자!
		 * 
		 *   startPage : 1, 11, 21, 31, 41, ... => n * 10 + 1 => 10의 배수 + 1
		 * 
		 *   만약에 pageLimit 가 5 라면 1, 6, 11, 16, ... => 5의 배수  + 1
		 * 
		 *   즉, n * pageLimit + 1
		 * 
		 *   currentPage    startPage
		 *   1			    1 		 => 0 * pageLimit + 1 => n = 0
		 *   5			    1 		 => 0 * pageLimit + 1 => n = 0
		 *   10			    1		 => 0 * pageLimit + 1 => n = 0
		 *   
		 *   11				11		 => 1 * pageLimit + 1 => n = 1
		 *   15				11		 => 1 * pageLimit + 1 => n = 1
		 *   20				11		 => 1 * pageLimit + 1 => n = 1
		 *   => 1~10  : n = 0
		 *   => 11~20 : n = 1
		 *   => 21~30 : n = 2
		 *   			n = (currentPage - 1) / pageLimit
		 *   				 0~9 / 10 = 0
		 *   				 10~19 / 10 = 1
		 *   				 20~29 / 10 = 2
		 *   
		 *   startPage = n * pageLimit + 1 
		 *			   = (currentPage - 1) / pageLimit * pageLimit + 1
		 */
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// * endPage : 페이지 하단에 보여질 페이징바의 끝수
		/*
		 * startPage, pageLimit 에 영향을 받음 (단, maxPage 도 영향을 받기 함)
		 * 
		 * - 공식 구하기
		 *   단, pageLimit 이 10 이라는 가정 하에 규칙을 구해보자!
		 *   
		 *   startPage : 1 => endPage : 10
		 *   startPage : 11 => endPage : 20
		 *   startPage : 21 => endPage : 30
		 * 
		 *   endPage = startPage + pageLimit - 1
		 */
		endPage = startPage + pageLimit - 1;
		// startPage가 11 이여서 endPage가 20으로 됨 (근데 maxPage가 고작 13까지밖에 안된다면?)
		// => endPage를 maxPage로 변경
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 페이지 정보들을 하나의 공간에 담아서 보내자
		// 페이지 정보를 담을 VO 클래스를 하나 더 만들것 
		// => 공지사항이나 사진게시판 등에서도 쓰일것이므로 common 패키지에 만들것임
		// 1. 페이징 바 만들때 필요한 객체
		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, 
								   boardLimit, maxPage, startPage, endPage);
		
		// 2. 현재 사용자가 요청한 페이지(currentPage) 에 보여질 게시글 리스트 요청하기
		ArrayList<Board> list = new BoardService().selectList(pi);
		
		request.setAttribute("pi", pi);
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("views/board/boardListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
