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
import prac.cy.library.vo.User;

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
	/* 1. 도서 검색 서비스 -> Basic
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
				String topicName = rs.getString("TOPIC_NAME");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String locName = rs.getString("LOC_NAME");
				String availName = rs.getString("AVAIL_NAME");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book(bookNo, callNo, topicName, bookName, author, publisher, locName, availName, dueDate);
				
				bookList.add(book);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return bookList;
	}
	
	/** 3. 상세 조회 서비스(by 청구기호)
	 * @param conn
	 * @param callNo
	 * @return
	 * @throws Exception
	 */
	public List<Book> bookInfo(Connection conn, String callNo) throws Exception {
		List<Book> bookSingle = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("bookInfo");
			
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
				
				bookSingle.add(book);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return bookSingle;
	}
	
	/** 4. 도서 정보 수정
	 * @param conn
	 * @param bookNo
	 * @param type
	 * @param edit
	 * @return
	 * @throws Exception
	 */
	public int bookUpdate(Connection conn, int bookNo, int type, String edit) throws Exception {
		int result = 0;
		
		try {
			String keyName = "bookUpdate" + type;
			String sql = prop.getProperty(keyName);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, edit);
			pstmt.setInt(2, bookNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/** 4-1. 도서 정보 전체 수정
	 * @param conn
	 * @param book
	 * @return result
	 * @throws Exception
	 */
	public int bookUpdateAll(Connection conn, Book book) throws Exception {
		int result = 0;
		try {
			String sql  = prop.getProperty("bookUpdateAll");
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, book.getCallNo());
			pstmt.setString(2, book.getTopicCode());
			pstmt.setString(3, book.getBookName());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getLocCode());
			pstmt.setInt(7, book.getBookNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	/** 5. 연체 도서 조회 서비스
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Library> searchOverdue(Connection conn, int userNo) throws Exception {
		List<Library> overdueList = new ArrayList<>();
		
		try {
			String sql = null;
			
			if(userNo > -1) {
				sql = prop.getProperty("searchOverdue1")
					  + prop.getProperty("searchOverdue2")
					  + prop.getProperty("searchOverdue3");
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, userNo);
			}

			if(userNo == -1) {
				sql = prop.getProperty("searchOverdue1");
				pstmt = conn.prepareStatement(sql);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				String callNo = rs.getString("CALL_NO");
				String bookName = rs.getString("BOOK_NAME");
				userNo = rs.getInt("USER_NO");
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

	/** 6-1. 대출 서비스
	 * @param conn
	 * @param userNo
	 * @param bookNo
	 * @return result
	 * @throws Exception
	 */
	public int bookLent(Connection conn, int userNo, int bookNo) throws Exception {
		int result = 0;
		
		try {
			String sql  = prop.getProperty("bookLent");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			pstmt.setInt(2, userNo);
			
			result += pstmt.executeUpdate();
			
			sql  = prop.getProperty("updateAvailL");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			
			result += pstmt.executeUpdate();
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	/** 6-2. 반납 서비스
	 * @param conn
	 * @param bookNo
	 * @return
	 * @throws Exception
	 */
	public int bookReturn(Connection conn, int bookNo) throws Exception {
		int result = 0;
	
		try {
			String sql = prop.getProperty("bookReturn");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			result += pstmt.executeUpdate();
			
			sql  = prop.getProperty("updateAvailY");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNo);
			
			result += pstmt.executeUpdate();
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	/** 7. 신간 도서 등록
	 * @param conn
	 * @param book
	 * @return result
	 * @throws Exception
	 */
	public int bookAdd(Connection conn, Book book) throws Exception {
		int result = 0;
		try {
			String sql  = prop.getProperty("bookAdd");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getCallNo());
			pstmt.setString(2, book.getTopicCode());
			pstmt.setString(3, book.getBookName());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getLocCode());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	/** 특정 이용자의 대출 목록
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Library> searchUserBookList(Connection conn, int userNo) throws Exception {
		List<Library> lentList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("searchUserBookList");

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				String callNo = rs.getString("CALL_NO");
				String bookName = rs.getString("BOOK_NAME");
				userNo = rs.getInt("USER_NO");
				String userName = rs.getString("USER_NAME");
				String lentDate = rs.getString("LENT_DATE");
				String dueDate = rs.getString("DUE_DATE");
				String returnDate = rs.getString("RETURN_DATE");
				
				Library library = new Library(bookNo, callNo, bookName, userNo, userName, lentDate, dueDate, returnDate);
				
				lentList.add(library);
			}
			
			
		} finally {
			close(rs);
			close(stmt);
		}
		return lentList;
	}
	

	/** D. 이용자 조회 서비스
	 * @param conn
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<User> userInfo(Connection conn, String userId) throws Exception {
		List<User> userSingle = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("userInfo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int userNo = rs.getInt("USER_NO");
				userId = rs.getString("USER_ID");
				String userName = rs.getString("USER_NAME");
				String identityName = rs.getString("IDENTITY_NAME");
				String statusName = rs.getString("STATUS_NAME");
				int identityLimit = rs.getInt("IDENTITY_LIMIT");
				int lentNum = rs.getInt("LENT_NUM");
				int availNum = rs.getInt("AVAIL_NUM");
				
				User user = new User();
				
				user.setUserNo(userNo);
				user.setUserId(userId);
				user.setUserName(userName);
				user.setIdentityName(identityName);
				user.setStatusName(statusName);
				user.setIdentityLimit(identityLimit);
				user.setLentNum(lentNum);
				user.setAvailNum(availNum);
				
				userSingle.add(user);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return userSingle;
	}
	
	/**
	 *  기타1: 분류 코드
	 */
	public List<Book> topicList(Connection conn) throws Exception {
		List<Book> topicList = new ArrayList<>();
		try {
			String sql = prop.getProperty("topicList");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Book topic = new Book();
				topic.setTopicCode(rs.getString("TOPIC_CODE"));
				topic.setTopicName(rs.getString("TOPIC_NAME"));
				
				topicList.add(topic);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return topicList;
	}
	
	/**
	 *  기타2: 위치 코드
	 */
	public List<Book> locList(Connection conn) throws Exception {
		List<Book> locList = new ArrayList<>();
		try {
			String sql = prop.getProperty("locList");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Book loc = new Book();
				loc.setLocCode(rs.getString("LOC_CODE"));
				loc.setLocName(rs.getString("LOC_NAME"));
				
				locList.add(loc);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return locList;
	}

	/**
	 *  기타3: 상태 코드
	 */
	public List<Book> availList(Connection conn) throws Exception {
		List<Book> availList = new ArrayList<>();
		try {
			String sql = prop.getProperty("availList");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Book loc = new Book();
				loc.setAvailCode(rs.getString("AVAIL_CODE"));
				loc.setAvailName(rs.getString("AVAIL_NAME"));
				
				availList.add(loc);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}
		return availList;
	}


}
