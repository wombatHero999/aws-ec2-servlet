package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member loginMember(Connection conn, String userId, String userPwd) {

		// SELECT 문 => ResultSet 객체 (unique key 조건에 의해 한 행만 조회됨) => Member 객체
		Member m = null;

		//PreparedStatement pstmt = null;
		
		Statement stmt = null;
		
		ResultSet rset = null;
		
		//String sql = prop.getProperty("loginMember");
		String sql = "SELECT * "+ 
				"FROM MEMBER " + 
				"WHERE USER_ID = '"+userId +"'"+
				  " AND USER_PWD = '"+userPwd +"'"+
				  " AND STATUS = 'Y'";
		try {
			stmt = conn.createStatement();
			
			//pstmt = conn.prepareStatement(sql);
			
			//pstmt.setString(1, userId);
			//pstmt.setString(2, userPwd);
			
			//rset = pstmt.executeQuery();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) {
				m = new Member(rset.getInt("USER_NO"), 
							   rset.getString("USER_ID"), 
							   rset.getString("USER_PWD"), 
							   rset.getString("USER_NAME"),
							   rset.getString("PHONE"), 
							   rset.getString("EMAIL"),
							   rset.getString("ADDRESS"), 
							   rset.getString("INTEREST"),
							   rset.getDate("ENROLL_DATE"),
							   rset.getDate("MODIFY_DATE"), 
							   rset.getString("STATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			//JDBCTemplate.close(pstmt);
			JDBCTemplate.close(stmt);
		}
		
		return m;
	}

	public int insertMember(Connection conn, Member m) {
		
		// INSERT 문 => 처리된 행의 수
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7,  m.getInterest());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
				
		return result;
	}

	public int updateMember(Connection conn, Member m) {
		
		// UPDATE 문 => 처리된 행의 수
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getInterest());
			pstmt.setString(6, m.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public Member selectMember(Connection conn, String userId) {

		// SELECT 문 => ResultSet 객체 (unique key 조건에 의해 한 행만 조회됨) => Member 객체
		Member m = null;

		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("USER_NO"), 
							   rset.getString("USER_ID"), 
							   rset.getString("USER_PWD"), 
							   rset.getString("USER_NAME"),
							   rset.getString("PHONE"), 
							   rset.getString("EMAIL"),
							   rset.getString("ADDRESS"), 
							   rset.getString("INTEREST"),
							   rset.getDate("ENROLL_DATE"),
							   rset.getDate("MODIFY_DATE"), 
							   rset.getString("STATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m;
	}
	
	
	public int updatePwdMember(String userId, String userPwd, String updatePwd, Connection conn) {
		
		int result = 0;
		
		PreparedStatement psmt = null;
		
		String sql = prop.getProperty("updatePwdMember");
		
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, updatePwd);
			psmt.setString(2, userId);
			psmt.setString(3, userPwd);
			
			result = psmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(psmt);
		}
		
		
		return result;
	}
	
	public int deleteMember(String userId, String userPwd, Connection conn) {
		
		int result = 0;
		
		PreparedStatement psmt = null;
		
		String sql = prop.getProperty("deleteMember");
		
		try {
			psmt = conn.prepareStatement(sql);
			
			psmt.setString(1, userId);
			psmt.setString(2, userPwd);
			
			result = psmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(psmt);
		}
		
		return result;
	}
	
	public int idCheck(Connection conn, String checkId) {
		
		// select-> ResultSET (숫자 하나)
		int count = 0;
		
		PreparedStatement psmt = null;
		
		ResultSet rset = null;
		
		String sql = prop.getProperty("idCheck");
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, checkId);
			
			rset = psmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(psmt);
		}
		
		return count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
