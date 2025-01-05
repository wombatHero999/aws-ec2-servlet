package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	// 1. Connection 객체 생성 (DB 접속) 한 후 해당 Connection 을 반환하는 메소드
	public static Connection getConnection() {
		
		Properties prop = new Properties(); // Map 계열 컬렉션 (key-value)
		
		// 읽어들이고자 하는 driver.properties 파일의 물리적인 경로
		String fileName = JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath(); // 맨 처음의 / 는 classes 폴더를 의미한다.
		// C:\Web-workspace2\JSP_Project\WebContent\WEB-INF\classes\sql\driver\driver.properties
		
		try {
			prop.load(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		
		try {
			// 1) jdbc driver 등록
			Class.forName(prop.getProperty("driver"));

			// 2) DB 와 접속된 Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"),
											   prop.getProperty("username"),
											   prop.getProperty("password"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	// 2. 전달받은 Connection 객체를 가지고 commit 해주는 메소드
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	// 3. 전달받은 Connection 객체를 가지고 rollback 해주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 4. 전달받은 Connection 객체를 반납시켜주는 메소드
	public static void close(Connection conn) {

		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 5. 전달받은 Statment 객체를 반납시켜주는 메소드 => 다형성에 의해 자식클래스인 PreparedStatment 도 반납 가능
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 6. 전달받은 ResultSet 객체를 반납시켜주는 메소드
	public static void close(ResultSet rset) {

		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
