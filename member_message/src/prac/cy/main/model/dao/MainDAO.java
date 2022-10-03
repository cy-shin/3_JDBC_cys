package prac.cy.main.model.dao;

import static prac.cy.common.JDBCTemplate.*;

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
	
	
	/** 1. 로그인
	 * @param conn
	 * @param userId
	 * @param userPw
	 * @return user
	 * @throws Exception
	 */
	public User login(Connection conn, String userId, String userPw) throws Exception {
		User user = null;
//		User user = new User(); user 객체를 생성한 후, user 객체를 참조변수 user에 저장함
//								이러면 참조변수 user 내의 멤버변수들이 null일 뿐 참조변수 user 자체는 null이 아님!
		try {
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User();
				
				String userNo = rs.getString("USER_NO");
				String userName = rs.getString("USER_NAME");
				
				user.setUserNo(userNo); // 유저 번호
				user.setUserId(userId); // 유저 아이디
				user.setUserName(userName); // 유저 이름
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return user;
	}
	
	/** 1. 로그인시간 업데이트
	 * @param conn
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public int loginDate(Connection conn, String userNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("loginDate");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,userNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
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
		List<User> tempList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("checkDuplicate");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				User temp = new User();
				temp.setUserFl(rs.getString("USER_FL"));
				
				tempList.add(temp);
				
			}
			if(tempList.isEmpty()) {
				idNameCheck = 1;
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
		int noCheck = 0;
		List<User> tempList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("makeUserNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ran);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				User temp = new User();
				temp.setUserFl(rs.getString("USER_FL"));
				
				tempList.add(temp);
				
			}
			if(tempList.isEmpty()) {
				noCheck = 1;
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return noCheck;
	}
}
