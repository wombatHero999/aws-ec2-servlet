package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.notice.model.service.NoticeService;

/**
 * Servlet implementation class BoardDeleteController
 */
@WebServlet("/delete.bo")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardDeleteController() {
	    super();
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int boardNo = Integer.parseInt(request.getParameter("bno"));
		
		int result = new BoardService().deleteBoard(boardNo);
		
		if(result > 0) { // 성공 => /jsp/list.bo url 재요청 => 리스트페이지가 보여지도록
			
			request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 삭제되었습니다.");
			
			response.sendRedirect(request.getContextPath() + "/list.bo?currentPage=1");
			
		}
		else { // 실패 => 에러페이지가 보여지도록 에러문구
			request.setAttribute("errorMsg", "게시글 삭제 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
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
