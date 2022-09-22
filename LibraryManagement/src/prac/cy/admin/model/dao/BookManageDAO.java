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

public class BookManageDAO {
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public BookManageDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("bookManage-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* 1. 키워드로 통합 검색 -> Basic
	 * 
	 */
	
	/** 2. 도서 전체 조회
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Book> searchAll(Connection conn) throws Exception {
		List<Book> bookList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchAll");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				String callNo = rs.getString("CALL_NO");
				String topic = rs.getString("TOPIC");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String loc = rs.getString("LOC");
				String avail = rs.getString("AVAIL");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book(bookNo, callNo, topic, bookName, author, publisher, loc, avail, dueDate);
				
				bookList.add(book);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return bookList;
	}

	/** 3. 연체 도서 조회 서비스
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Book> searchOverdue(Connection conn) throws Exception {
		List<Book> overdueList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchOverdue");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				String callNo = rs.getString("CALL_NO");
				String topic = rs.getString("TOPIC");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String loc = rs.getString("LOC");
				String avail = rs.getString("AVAIL");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book(bookNo, callNo, topic, bookName, author, publisher, loc, avail, dueDate);
				
				overdueList.add(book);
			}
			
			
		} finally {
			close(rs);
			close(stmt);
		}
		return overdueList;
	}
}
