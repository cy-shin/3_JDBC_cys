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
	
	public UserManageDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("userManage-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 이용자 1명 조회 서비스
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
				String userId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				String statusName = rs.getString("STATUS_NAME");
				int identityLimit = rs.getInt("IDENTITY_LIMIT");
				int lentNum = rs.getInt("LENT_NUM");
				int availNum = rs.getInt("AVAIL_NUM");
				
				User user = new User();
				
				user.setUserNo(userNo);
				user.setUserId(userId);
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

}


