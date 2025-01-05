package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardService {

	public int selectListCount() {

		Connection conn = getConnection();
		int listCount = new BoardDao().selectListCount(conn);
		
		close(conn);
		
		return listCount;
	}

	public ArrayList<Board> selectList(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		close(conn);
		
		return list;
	}

	public ArrayList<Category> selectCategoryList() {

		Connection conn = getConnection();
		
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		close(conn);
		
		return list;
	}

	public int insertBoard(Board b, Attachment at) {

		Connection conn = getConnection();
		
		int result1 = new BoardDao().insertBoard(conn, b);
		
		int result2 = 1;
		// 미리 선언과 초기화는 해줘야함 => 하단에 트랜잭션 처리할때 써야할 변수이기 때문
		
		if(at != null) {
			result2 = new BoardDao().insertAttachment(conn, at);
		}
		
		// 트랜잭션 처리
		if(result1 > 0 && result2 > 0) {
			// 첨부파일이 없는경우, insert 가 
			// 성공했을때도 result2 는 여전히 0이라 rollback 처리 될 것이기 때문에
			// 애초에 result2 = 1; 로 초기화 시켜줘야한다.
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2; 
		// 혹시라도 하나라도 실패하여 0 이 될 경우 아예 실패값을 반환하기 위해 곱셈 결과를 리턴
	}

	public int increaseCount(int boardNo) {
		
		Connection conn = getConnection();
		
		int result = new BoardDao().increaseCount(conn, boardNo);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public Board selectBoard(int boardNo) {
		
		Connection conn = getConnection();
		
		Board b = new BoardDao().selectBoard(conn, boardNo);
		
		close(conn);
		
		return b;
	}

	public Attachment selectAttachment(int boardNo) {
		
		Connection conn = getConnection();
		
		Attachment at = new BoardDao().selectAttachment(conn, boardNo);
		
		close(conn);
		
		return at;
	}

	public int updateBoard(Board b, Attachment at) {

		Connection conn = getConnection();
		
		int result1 = new BoardDao().updateBoard(conn, b);
		
		int result2 = 1;
		if(at != null) { // 새롭게 첨부된 파일이 있을 경우
			
			if(at.getFileNo() != 0) { // 기존의 첨부파일이 있었을 경우 => Attachment Update
				
				result2 = new BoardDao().updateAttachment(conn, at);
			}
			else { // 기존의 첨부파일이 없었을 경우 => Attachment Insert
				
				result2 = new BoardDao().insertNewAttachment(conn, at);
			}
		}
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	}

	public int deleteBoard(int boardNo) {

		Connection conn = getConnection();
		
		int result = new BoardDao().deleteBoard(conn, boardNo);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public int insertThumbnailBoard(Board b, ArrayList<Attachment> list) {

		Connection conn = getConnection();
		
		int result1 = new BoardDao().insertThumbnailBoard(conn, b);
		
		int result2 = new BoardDao().insertAttachmentList(conn, list);
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
	}

	public ArrayList<Board> selectThumbnailList() {

		Connection conn = getConnection();
		
		ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
		
		close(conn);
		
		return list;
	}

	public ArrayList<Attachment> selectAttachmentList(int boardNo) {

		Connection conn = getConnection();
		
		ArrayList<Attachment> list = new BoardDao().selectAttachmentList(conn, boardNo);
		
		close(conn);
		
		return list;
	}

	public ArrayList<Reply> selectReplyList(int boardNo) {

		Connection conn = getConnection();
		
		ArrayList<Reply> list = new BoardDao().selectReplyList(conn, boardNo);
		
		close(conn);
		
		return list;
	}

	public int insertReply(Reply r) {

		Connection conn = getConnection();
		
		int result = new BoardDao().insertReply(conn, r);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		return result;
	}
	
}
