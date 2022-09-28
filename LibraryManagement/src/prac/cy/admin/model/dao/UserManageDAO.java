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

import prac.cy.library.vo.User;

public class UserManageDAO {
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	private Properties prop;
	
	
	/** Properties
	 * 
	 */
	public UserManageDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("userManage-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 1. 이용자 조회
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
			
			sql += prop.getProperty("searchUserOrder");
			
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

	
	/** 2. 상세 조회
	 * @param conn
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public List<User> searchUserDetail(Connection conn, int userNo) throws Exception {
		List<User> userList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchUserDetail");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				User user = new User();
				
				userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				String userPhone = rs.getString("USER_PHONE");
				String userEmail = rs.getString("USER_EMAIL");
				String statusName = rs.getString("STATUS_NAME");
				int identityLimit = rs.getInt("IDENTITY_LIMIT");
				int lentNum = rs.getInt("LENT_NUM");
				int availNum = rs.getInt("AVAIL_NUM");
				
				user.setUserNo(userNo);
				user.setUserName(userName);
				user.setIdentityName(identityName);
				user.setUserPhone(userPhone);
				user.setUserEmail(userEmail);
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
	
	/** 3. 정보 수정
	 * @param conn
	 * @param condition
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public int updateUser(Connection conn, int condition, String edit, int userNo) throws Exception {
		int result = 0;
		String sql = "";

		try {

			if(condition == 1) {
				sql = prop.getProperty("updateUserOpt1")
					  + prop.getProperty("updateUser");
			}
			
			if(condition == 2) {
				sql = prop.getProperty("updateUserOpt2")
					  + prop.getProperty("updateUser");
			}
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, edit);
			pstmt.setInt(2, userNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		return result;
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
				iden.setStatusCode(rs.getString("STATUS_CODE"));
				iden.setStatusName(rs.getString("STATUS_NAME"));
				
				statusList.add(iden);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return statusList;
	}

	/** 기타3 : 이용자 1명 조회
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<User> oneUser(Connection conn, String userInput) throws Exception {
		List<User> userSingle = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("userInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userInput);
//			pstmt.setString(2, userInput);
//			pstmt.setString(3, userInput);
//			pstmt.setString(4, userInput);
//			pstmt.setString(5, userInput);
////			pstmt.setString(6, userInput);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				String statusName = rs.getString("STATUS_NAME");
				String userPhone = rs.getString("USER_PHONE");
				String userEmail = rs.getString("USER_EMAIL");
				int identityLimit = rs.getInt("IDENTITY_LIMIT");
				int lentNum = rs.getInt("LENT_NUM");
				int availNum = rs.getInt("AVAIL_NUM");
				
				User user = new User();
				
				user.setUserNo(userNo);
				user.setUserName(userName);
				user.setIdentityName(identityName);
				user.setStatusName(statusName);
				user.setUserEmail(userEmail);
				user.setUserPhone(userPhone);
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
}


