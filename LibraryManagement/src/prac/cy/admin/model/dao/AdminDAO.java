package prac.cy.admin.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prac.cy.library.vo.Library;

import static prac.cy.common.JDBCTemplate.*;

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
	
	/* 1. 키워드로 통합 검색 -> Basic
	 * 
	 */
	
	/**
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Library> searchAll(Connection conn) throws Exception {
		List<Library> bookList = new ArrayList<>();
		
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
				
				Library lib = new Library(callNo, topic, bookName, author, publisher, loc, avail, dueDate);
				
				bookList.add(lib);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return bookList;
	}

}
