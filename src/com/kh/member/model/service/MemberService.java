package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	/**
	 * 로그인 요청 서비스
	 * @param userId => 사용자가 입력했던 아이디값
	 * @param userPwd => 사용자가 입력했던 비밀번호값
	 */
	public Member loginMember(String userId, String userPwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		JDBCTemplate.close(conn);
		
		return m;
	}

	/**
	 * 회원 가입용 서비스
	 * @param m => 회원 가입할 회원의 정보를 담은 Member 객체
	 * @return => 처리된 행 수 (int)
	 */
	public int insertMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn, m);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/**
	 * 회원 정보 수정용 서비스
	 * @param m => 수정할 회원의 정보를 담은 Member 객체
	 * @return => 수정한 회원의 갱신된 정보
	 */
	public Member updateMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn, m);
		
		Member updateMem = null;
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
			
			// 갱신된 회원 객체 다시 조회해오기
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateMem;
	}


	/**
	 * 비밀번호 수정용 서비스
	 * @param userId => 수정할 회원의 아이디
	 * @param userPwd => 수정할 회원의 원래 비밀번호
	 * @param updatePwd => 수정할 새로운 비밀번호
	 * @return => 수정된 회원의 갱신된 정보
	 */
	public Member updatePwdMember(String userId , String userPwd, String updatePwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Member updateMem = null;
		
		int result = 0;
		
		result = new MemberDao().updatePwdMember(userId, userPwd, updatePwd, conn);
		
		if(result > 0) {// 성공시
			JDBCTemplate.commit(conn);
			
			// 갱신된 회원객체 다시 조회해오기.
			updateMem = new MemberDao().selectMember(conn, userId);
		}else {//실패시
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateMem;		
	}
	
	
	
	/** 
	 * 회원탈퇴용 서비스
	 * @param userId => 탈퇴 요청한 회원의 아이디.
	 * @param userPwd => 탈퇴 요청한 회원의 비밀번호.
	 * @return => 처리된 행의 수
	 */
	public int deleteMember(String userId, String userPwd) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().deleteMember(userId, userPwd, conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public int idCheck(String checkId) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int count = new MemberDao().idCheck(conn, checkId);
		
		JDBCTemplate.close(conn);
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
