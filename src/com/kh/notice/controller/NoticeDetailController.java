package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 클릭했을때 공지사항 글번호 nno
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		
		// 조회수 증가용 서비스 호출.
		int result = new NoticeService().increaseCount(noticeNo);
		
		if(result > 0) {// 성공했을경우 => 해당 공지사항 상세조회
			Notice n = new NoticeService().selectNotice(noticeNo);
			request.setAttribute("n",n);
			request.getRequestDispatcher("views/notice/noticeDetailView.jsp").forward(request, response);
		}else { // 실패시 에러페이지.
			request.setAttribute("errorMsg", "공지사항 조회 실패");
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
