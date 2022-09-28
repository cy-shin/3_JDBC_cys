package prac.cy.main.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import prac.cy.library.vo.User;

import static prac.cy.common.JDBCTemplate.*;

public class MainDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public MainDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 1. 키워드로 통합 검색 -> Basic
	 * 
	 */
	
	/** 2. 로그인
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public User login(Connection conn, String userId, String userPw) throws Exception {
		User loginUser = null;
		
		try {
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginUser = new User();
				
				int userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				
				loginUser.setUserNo(userNo);
				loginUser.setUserId(userId);
				loginUser.setUserName(userName);
				loginUser.setIdentityName(identityName);
				
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginUser;
	}
	
	/** 3. 회원 가입
	 * @param conn
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int signUp(Connection conn, User user) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserPhone());
			pstmt.setString(5, user.getUserEmail());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 3.1 아이디 중복 체크
	 * @param userId
	 * @return int result
	 * 	0  : 중복 없음
	 *  1~ : 중복 있음
	 * @throws Exception
	 */
	public int duplicateName(Connection conn, String userId) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("duplicateName");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result++;
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}


	
}
