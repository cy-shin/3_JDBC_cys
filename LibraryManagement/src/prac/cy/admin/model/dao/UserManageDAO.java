package prac.cy.admin.model.dao;

import static prac.cy.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prac.cy.library.vo.Book;
import prac.cy.library.vo.User;

public class UserManageDAO {
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public UserManageDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("userManage-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 이용자 1명 조회 서비스 for BookManager
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<User> userInfo(Connection conn, String userInput) throws Exception {
		List<User> userSingle = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("userInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userInput);
			pstmt.setString(2, userInput);
			pstmt.setString(3, userInput);
			pstmt.setString(4, userInput);
			pstmt.setString(5, userInput);
			pstmt.setString(6, userInput);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				String statusName = rs.getString("STATUS_NAME");
				int identityLimit = rs.getInt("IDENTITY_LIMIT");
				int lentNum = rs.getInt("LENT_NUM");
				int availNum = rs.getInt("AVAIL_NUM");
				
				User user = new User();
				
				user.setUserNo(userNo);
				user.setUserName(userName);
				user.setIdentityName(identityName);
				user.setStatusName(statusName);
				user.setIdentityLimit(identityLimit);
				user.setLentNum(lentNum);
				user.setAvailNum(availNum);
				
				userSingle.add(user);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return userSingle;

	}
	/** 1-1. 전체 조회
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<User> searchUserAll(Connection conn) throws Exception {
		List<User> userList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchUserAll");
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				User user = new User();
				
				if(rs.next()) {
					int userNo = rs.getInt("USER_NO");
					String userName = rs.getString("USER_NAME");
					String identityName = rs.getString("IDENTITY_NAME");
					String statusName = rs.getString("STATUS_NAME");
					int identityLimit = rs.getInt("IDENTITY_LIMIT");
					int lentNum = rs.getInt("LENT_NUM");
					int availNum = rs.getInt("AVAIL_NUM");
					
					user.setUserNo(userNo);
					user.setUserName(userName);
					user.setIdentityName(identityName);
					user.setStatusName(statusName);
					user.setIdentityLimit(identityLimit);
					user.setLentNum(lentNum);
					user.setAvailNum(availNum);
					
					userList.add(user);
				}
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return userList;
	}
	
	/** 1-2. 상세 조회
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<User> searchUser(Connection conn, String userKeyword, String identityName, String statusName, String delayOpt) throws Exception {
		List<User> userList = new ArrayList<>();
		try {
			String sql = prop.getProperty("searchUser");
			if(!(userKeyword.equals("0"))) 	sql += prop.getProperty("searchUserOptA");
			if(!(identityName.equals("0"))) sql += prop.getProperty("searchUserOptB");
			if(!(statusName.equals("0"))) 	sql += prop.getProperty("searchUserOptC");
			if(!(delayOpt.equals("0"))) 	sql += prop.getProperty("searchUserOptD");
			
			int idx = 0;
			
			pstmt = conn.prepareStatement(sql);
			
			
			if(!(userKeyword.equals("0"))) {
				pstmt.setString(++idx, userKeyword);
				pstmt.setString(++idx, userKeyword);
			}
			
			if(!(identityName.equals("0"))) pstmt.setString(++idx, identityName);
			if(!(statusName.equals("0"))) pstmt.setString(++idx, statusName);
			
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				User user = new User();
				
				int userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				identityName = rs.getString("IDENTITY_NAME");
				statusName = rs.getString("STATUS_NAME");
				int identityLimit = rs.getInt("IDENTITY_LIMIT");
				int lentNum = rs.getInt("LENT_NUM");
				int availNum = rs.getInt("AVAIL_NUM");
				
				user.setUserNo(userNo);
				user.setUserName(userName);
				user.setIdentityName(identityName);
				user.setStatusName(statusName);
				user.setIdentityLimit(identityLimit);
				user.setLentNum(lentNum);
				user.setAvailNum(availNum);
				
				userList.add(user);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return userList;
	}

	/**
	 *  기타1: 신분 코드
	 */
	public List<User> identityList(Connection conn) throws Exception {
		List<User> identityList = new ArrayList<>();
		try {
			String sql = prop.getProperty("identityList");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				User iden = new User();
				iden.setIdentityCode(rs.getString("IDENTITY_CODE"));
				iden.setIdentityName(rs.getString("IDENTITY_NAME"));
				
				identityList.add(iden);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return identityList;
	}
	
	/**
	 *  기타2: 상태 코드
	 */
	public List<User> statusList(Connection conn) throws Exception {
		List<User> statusList = new ArrayList<>();
		try {
			String sql = prop.getProperty("statusList");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				User iden = new User();
				iden.setStatusCode(rs.getString("IDENTITY_CODE"));
				iden.setStatusName(rs.getString("IDENTITY_NAME"));
				
				statusList.add(iden);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return statusList;
	}
	

}


