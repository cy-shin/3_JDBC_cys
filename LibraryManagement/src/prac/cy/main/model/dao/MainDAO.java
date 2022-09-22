package prac.cy.main.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prac.cy.library.vo.Library;
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
				
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				int lentNum = rs.getInt("LENT_NUM");
				
				loginUser.setUserId(userId);
				loginUser.setUserName(userName);
				loginUser.setIdentityName(identityName);
				loginUser.setLentNum(lentNum);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginUser;
	}
	
}
