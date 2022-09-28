package prac.cy.user.model.dao;

import static prac.cy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;

public class UserDAO {

	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	Properties prop;
	
	public UserDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("user-query.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 3(1). 내 정보 출력
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<User> printMyInfo(Connection conn, int myNo) throws Exception {
		List<User> myInfoList = new ArrayList<>();
		try {
			String sql = prop.getProperty("printMyInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, myNo);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				User user = new User();
				
				int userNo = rs.getInt("USER_NO");
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
				
				myInfoList.add(user);
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return myInfoList;
	}
	
	/** 3-1. 내 정보 수정
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public int updateMyInfo(Connection conn, int myNo, int type, String edit) throws Exception {
		int result = 0;
		String sql = "";
		try {
			
			if(type == 1) {
				sql += prop.getProperty("updateMyName");
			}
			if(type == 2) {
				sql += prop.getProperty("updateMyPhone");
			}
			if(type == 3) {
				sql += prop.getProperty("updateMyEmail");
			}
			
			sql += prop.getProperty("updateMyInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, edit);
			pstmt.setInt(2, myNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/** 3-1(2). 전체 정보 수정
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public int updateMyInfoAll(Connection conn, User user) throws Exception {
		int result = 0;
		String sql = "";
		try {
			sql += prop.getProperty("updateMyAll");
			sql += prop.getProperty("updateMyInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getUserPhone());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setInt(4, user.getUserNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/** 3-2. 내 대출 목록
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<Library> printMyLentList(Connection conn, int myNo) throws Exception {
		List<Library> myLentList = new ArrayList<>();
		try {
			String sql = prop.getProperty("printMyLentList");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, myNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String callNo = rs.getString("CALL_NO");
				String bookName = rs.getString("BOOK_NAME");
				String userName = rs.getString("USER_NAME");
				String lentDate = rs.getString("LENT_DATE");
				String dueDate = rs.getString("DUE_DATE");
				String returnDate = rs.getString("RETURN_DATE");
				
				Library library = new Library();
				
				library.setCallNo(callNo);
				library.setBookName(bookName);
				library.setUserName(userName);
				library.setLentDate(lentDate);
				library.setDueDate(dueDate);
				library.setReturnDate(returnDate);
				
				myLentList.add(library);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return myLentList;
	}
	
	/** 3-3. 회원 탈퇴
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public int deleteMe(Connection conn, int myNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("deleteMe");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, myNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/** 3-3(2). 탈퇴 전 비밀번호 확인
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public int pwCheck(Connection conn, int myNo, String myPw) throws Exception {
		int pwCheck = 0;
		User temp = new User();
		try {
			String sql = prop.getProperty("pwCheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, myNo);
			pstmt.setString(2, myPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				temp.setUserNo(rs.getInt("USER_NO"));
			}
			
			if(temp!=null) {
				pwCheck++;
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return pwCheck;
	}

	
}
