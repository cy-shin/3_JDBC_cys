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
import prac.cy.library.vo.Library;

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
	public List<Library> searchOverdue(Connection conn) throws Exception {
		List<Library> overdueList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("searchOverdue");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				String callNo = rs.getString("CALL_NO");
				String bookName = rs.getString("BOOK_NAME");
				int userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				String lentDate = rs.getString("LENT_DATE");
				String dueDate = rs.getString("DUE_DATE");
				String returnDate = rs.getString("RETURN_DATE");
				
				Library library = new Library(bookNo, callNo, bookName, userNo, userName, lentDate, dueDate, returnDate);
				
				overdueList.add(library);
			}
			
			
		} finally {
			close(rs);
			close(stmt);
		}
		return overdueList;
	}

	/** B. 책 1권 조회 서비스(by 청구기호)
	 * @param conn
	 * @param callNo
	 * @return
	 */
	public List<Book> bookInfo(Connection conn, String callNo) throws Exception {
		List<Book> bookSingle = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("bookInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, callNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				callNo = rs.getString("CALL_NO");
				String topic = rs.getString("TOPIC");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String loc = rs.getString("LOC");
				String avail = rs.getString("AVAIL");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book();
				
				book.setCallNo(callNo);
				book.setTopic(topic);
				book.setBookName(bookName);
				book.setAuthor(author);
				book.setPublisher(publisher);
				book.setLoc(loc);
				book.setAvail(avail);
				book.setDueDate(dueDate);
				
				bookSingle.add(book);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return bookSingle;
	}

	/**
	 * @param conn
	 * @param bookNo
	 * @return
	 * @throws Exception
	 */
	public int returnBook(Connection conn, int bookNo) throws Exception {
		int result = 0;
	
		try {
			String sql1 = prop.getProperty("returnBook");
			
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, bookNo);
			result += pstmt.executeUpdate();

			
			String sql2 = prop.getProperty("returnLentRec");
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bookNo);
			result += pstmt.executeUpdate();
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	/** 반납처리
	 * @param conn
	 * @param userNo
	 * @param bookNo
	 * @return result
	 * @throws Exception
	 */
	public int lentBook(Connection conn, int userNo, int bookNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("lentBook");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
}
