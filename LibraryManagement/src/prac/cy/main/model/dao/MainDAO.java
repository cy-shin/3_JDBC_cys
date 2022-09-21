package prac.cy.main.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import static prac.cy.common.JDBCTemplate.*;

import prac.cy.lib.vo.LibVO;

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
	
	
	/** 1. 키워드로 통합 검색
	 * @param conn
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<LibVO> keywordSearch(Connection conn, String keyword) throws Exception {
		List<LibVO> bookList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("keywordSearch");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String callNo = rs.getString("CALL_NO");
				String topic = rs.getString("TOPIC");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String loc = rs.getString("LOC");
				String avail = rs.getString("AVAIL");
				String dueDate = rs.getString("DUE_DATE");
				
				LibVO lib = new LibVO(callNo, topic, bookName, author, publisher, loc, avail, dueDate);
				
				bookList.add(lib);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return bookList;
	}


	/** 2. 로그인
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public LibVO login(Connection conn, String memberId, String memberPw) throws Exception {
		LibVO loginUser = null;
		
		try {
			String sql = prop.getProperty("login");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loginUser = new LibVO();
				
				String memberName = rs.getString("MEMBER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				int lentNum = rs.getInt("LENT_NUM");
				
				loginUser.setMemberId(memberId);
				loginUser.setMemberName(memberName);
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
