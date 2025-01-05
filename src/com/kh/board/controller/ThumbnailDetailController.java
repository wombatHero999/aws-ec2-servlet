package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class ThumbnailDetailController
 */
@WebServlet("/detail.th")
public class ThumbnailDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThumbnailDetailController() {
	    super();
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		// 일반게시판에서 조회수 증가 메소드와 쿼리문은 이미 작성했었음
		int result = new BoardService().increaseCount(boardNo);
		
		if(result > 0) { // 조회수 증가 성공 => 게시글 정보, 첨부파일들 정보 조회 => 상세조회
			
			// 일반게시판용 selectBoard 쿼리 활용 => 내부 조인
			// 내부조인의 경우 일치하는 컬럼만을 가져오는 구조였는데
			// 사진게시판의 경우 카테고리가 NULL 이여서 일치하는것이 없었기 때문에
			// 반환되는것이 없는것이다!! 
			// => 카테고리 컬럼을 기준으로
			//	   일치하는 컬럼도, 일치하지 않는 컬럼도 가져오려면 외부조인으로 변경
			//    즉, 왼쪽 테이블 기준으로 조인하겠다.
			//    기존의 selectBoard 쿼리에서 JOIN 을 LEFT JOIN 으로 변경하면됨!!
			// => 굳이 새로운 메소드들을 다시 짤 필요가 없음!!
			
			Board b = new BoardService().selectBoard(boardNo);
			ArrayList<Attachment> list = new BoardService().selectAttachmentList(boardNo);
			
			request.setAttribute("b", b);
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("views/board/thumbnailDetailView.jsp").forward(request, response);
			
		}
		else { // 실패 => 에러페이지

			request.setAttribute("errorMsg", "사진게시글 조회 실패");
			
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
