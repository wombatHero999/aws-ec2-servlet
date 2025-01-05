package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardInsertController() {
	    super();
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		// String category = request.getParameter("category");
		// String boardTitle = request.getParameter("boardTitle");
		
		// 폼 전송을 일반 방식이 아닌 multipart/form-data 로 전송하는 경우
		// request 로 부터 값 뽑기 불가
		// multipart 라는 객체에 값을 이관시켜서 다뤄야 한다.
		
		// enctype 이 multipart/form-data 로 잘 전송되었을 경우 전반적인 내용들이 수정되도록
		if(ServletFileUpload.isMultipartContent(request)) {
			// System.out.println("잘 실행되나?");
			
			// 파일 업로드를 위한 라이브러리 : cos.jar
			
			// 1. 전송되는 파일을 처리할 작업 내용 (전송되는 파일의 용량제한, 전달된 파일을 저장할 폴더 경로)
			// 1_1. 전송파일 용량 제한 (int maxSize => byte 단위의 값을 기술해야한다.) => 10Mbyte 로 제한
			/*
			 * 단위정리
			 * byte -> kbyte -> mbyte -> gbyte -> tbyte -> ...
			 * 1 kbyte == 1024 byte (2의 10승)
			 * 1 mbyte == 1024 kbyte == 1024 * 1024 byte (2의 20승)
			 */
			int maxSize = 10 * 1024 * 1024;
			
			// 1_2. 전달된 파일을 저장할 서버의 폴더 경로 알아내기 (String savePath)
			// 세션객체에서 제공하는 getRealPath 메소드를 통해 알아내기
			// 다만 WebContents 폴더로부터 board_upfiles 폴더까지의 경로는 매개변수로 제시해줘야한다.
			// 그리고 결론적으로는 board_upfiles 폴더 내부에 파일들이 저장될 것이기 때문에 / 를 추가로 붙여줘야한다.
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
			// System.out.println(savePath);
			
			/*
			 * 2. 전달된 파일명 수정 및 서버에 업로드 작업
			 *  - HttpServletRequest request => MultipartRequest multiRequest 로 변환
			 *  
			 *  매개변수 생성자로 생성 (cos.jar 에서 제공하는 객체)
			 *  MultipartRequest multiRequest = new MultipartRequest(request객체, 저장할폴더경로, 용량제한, 인코딩값, 파일명을수정시켜주는객체);
			 * 
			 *  위 구문 한줄 실행만으로 넘어온 첨부파일들이 해당 폴더에 무조건 업로드됨!!
			 *  그리고 사용자가 올린 파일명은 그대로 해당 폴더에 업로드 하지 않는게 일반적임!!
			 *  - 같은 파일명이 있을 경우 덮어씌워질 수 있고, 한글 / 특수문자 / 띄어쓰기가 포함된 파일명일 경우 서버에 따라 문제 발생 가능
			 *  
			 *  기본적으로 파일명 수정 작업을 해주는 객체
			 *  - DefaultFileRenamePolicy 객체 (cos.jar 에서 제공하는 객체)
			 *  - 내부적으로 rename() 이라는 메소드가 실행이 되면서 파일명 수정이 진행됨
			 *  - 기본적으로 동일한 파일명이 존재할 경우 뒤에 카운팅된 숫자를 붙여서 파일명 수정을 진행함
			 *    예) aaa.jpg, aaa1.jpg, aaa2.jpg, ...
			 *    
			 *  - 하지만 우리 입맛대로 절대 안겹치게끔 rename 해볼것임
			 *    즉, DefaultFileRenamePolicy 객체 사용 X
			 *    내 입맛대로 나만의 파일명 생성 규칙을 만들어 바꿔주는 객체를 만들어서 쓸것임!!
			 *    나만의 com.kh.common.MyFileRenamePolicy 클래스를 만들어서 rename 메소드 정의
			 */
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 3. DB 에 기록할 데이터들을 뽑아서 Attachment 객체에 담기
			// - 카테고리번호, 제목, 내용, 작성자회원번호를 뽑아서 Board 테이블에 INSERT
			// - 넘어온 첨부파일이 있다면 원본명, 수정명, 폴더경로를 뽑아서 Attachment 테이블에 INSERT
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			Attachment at = null; // 처음에는 null 로 초기화, 첨부파일이 있다면 그때 객체 생성
			
			// multiRequest.getOriginalFileName("키");
			// => 첨부파일이 있었을 경우 원본명 / 첨부파일이 없었을 경우 null
			if(multiRequest.getOriginalFileName("upfile") != null) {
				
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 수정명(실제 서버에 업로드되어있는 파일명)
				at.setFilePath("resources/board_upfiles/");
			}
			
			// 4. 서비스요청
			int result = new BoardService().insertBoard(b, at);
			
			if(result > 0) { // 성공 => list.bo?currentPage=1 요청 (가장 최신글이므로)
				
				request.getSession().setAttribute("alertMsg", "게시글 작성 성공");
				response.sendRedirect(request.getContextPath() + "/list.bo?currentPage=1");
			}
			else { // 실패 => 첨부파일이 있었을 경우 이미 업로드 된 첨부파일을 굳이 서버에 보관할 이유가 없다! (용량만 차지)
				
				if(at != null) {
					// 삭제시키고자 하는 파일 객체 생성 => delete 메소드 호출 
					new File(savePath + at.getChangeName()).delete();
				}
				
				request.setAttribute("errorMsg", "게시글 작성 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
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
