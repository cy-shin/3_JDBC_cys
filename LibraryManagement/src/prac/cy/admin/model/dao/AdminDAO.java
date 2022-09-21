package prac.cy.admin.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static prac.cy.common.JDBCTemplate.*;

import prac.cy.lib.vo.LibVO;

public class AdminDAO {

	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public AdminDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("admin-query.xml"));
			
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
	
	
	/**
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<LibVO> searchAll(Connection conn) throws Exception {
		List<LibVO> bookList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchAll");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
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

}
