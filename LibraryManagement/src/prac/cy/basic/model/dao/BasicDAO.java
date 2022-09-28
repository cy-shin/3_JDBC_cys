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

import prac.cy.library.vo.Book;

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
	public List<Book> searchKeyword(Connection conn, String keyword) throws Exception {
		List<Book> bookList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchKeyword");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String callNo = rs.getString("CALL_NO");
				String topicName = rs.getString("TOPIC_NAME");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String locName = rs.getString("LOC_NAME");
				String availName = rs.getString("AVAIL_NAME");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book(callNo, topicName, bookName, author, publisher, locName, availName, dueDate);
				
				bookList.add(book);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return bookList;
	}
	
	/** 2. 상세 조회 서비스(by 청구기호)
	 * @param conn
	 * @param callNo
	 * @return
	 * @throws Exception
	 */
	public List<Book> searchDetail(Connection conn, String callNo) throws Exception {
		List<Book> bookDetail = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchDetail");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, callNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				callNo = rs.getString("CALL_NO");
				String topicName = rs.getString("TOPIC_NAME");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String locName = rs.getString("LOC_NAME");
				String availName = rs.getString("AVAIL_NAME");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book();
				
				book.setBookNo(bookNo);
				book.setCallNo(callNo);
				book.setTopic(topicName);
				book.setBookName(bookName);
				book.setAuthor(author);
				book.setPublisher(publisher);
				book.setLoc(locName);
				book.setAvail(availName);
				book.setDueDate(dueDate);
				
				bookDetail.add(book);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return bookDetail;
	}
}
