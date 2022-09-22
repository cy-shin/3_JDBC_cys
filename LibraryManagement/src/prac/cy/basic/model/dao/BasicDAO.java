package prac.cy.basic.model.dao;

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

public class BasicDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public BasicDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("basic-query.xml"));
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
	public List<Library> keywordSearch(Connection conn, String keyword) throws Exception {
		List<Library> bookList = new ArrayList<>();
		
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
