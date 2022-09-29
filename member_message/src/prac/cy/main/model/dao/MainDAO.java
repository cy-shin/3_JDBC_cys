package prac.cy.main.model.dao;

import static prac.cy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import prac.cy.temp.Temp;
import prac.cy.user.vo.User;

public class MainDAO {

	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	
	Properties prop;
	
	
	public MainDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
	}	
	
	/** 2. 회원가입
	 * @param conn
	 * @param userName
	 * @param userPw
	 * @param userId
	 * @return signUpResult
	 *    0   :  중복
	 *    1   :  성공
	 * @throws Exception
	 */
	public int signUp(Connection conn, String userNo, String userName, String userPw, String userId) throws Exception {
		int signUpResult = 0;
		try {
			String sql = prop.getProperty("createUser");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userNo);
			pstmt.setString(2, userId);
			pstmt.setString(3, userPw);
			pstmt.setString(4, userName);
			
			signUpResult = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return signUpResult;
	}
	
	/** userNo + "S"
	 * @param conn
	 * @param userName
	 * @param userPw
	 * @param userId
	 * @return signUpResult
	 *    0   :  중복
	 *    1   :  성공
	 * @throws Exception
	 */
	public int createBoxSend(Connection conn, String userNo) throws Exception {
		int boxSendResult = 0;
		try {
			String sql = prop.getProperty("createBoxSend");
			
			String temp = "S" + userNo;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, temp);
			
			boxSendResult = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return boxSendResult;
	}
	
	

	/** 2. 회원가입 전 아이디 / 이름 중복 체크
	 * @param conn
	 * @param userName
	 * @param userId
	 * @return checkResult
	 *   -1   :  중복된 아이디/이름
	 *    1   :  정상
	 * @throws Exception
	 */
	public int checkDuplicate(Connection conn, String userName, String userId) throws Exception {
		int idNameCheck = 0;
		
		try {
			String sql = prop.getProperty("checkDuplicate");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			pstmt.setString(2, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				idNameCheck++;
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return idNameCheck;
	}

	/** 2. 유저 번호 부여
	 * @param ran
	 * @return
	 *    0  : 중복
	 *    1  : 성공
	 * @throws Exception
	 */
	public int makeUserNo(Connection conn, String ran) throws Exception {
		int noCheck = 1;
		User temp = new User();
		
		try {
			String sql = prop.getProperty("makeUserNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ran);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				temp.setUserFl(rs.getString("USER_FL"));
			}
			
			if(temp != null) {
				noCheck = -1;
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return noCheck;
	}
}
