package prc.jdbc.model.dao;

import static prc.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prc.jdbc.model.vo.Book;

// DATA ACCESS OBJECT 데이터가 저장된 DB에 접근하는 객체로 SQL 수행 및 결과를 반환받는 기능 수행
public class BookDAO {
	// 필드 준비하기
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	// properties를 사용하려면 xml파일로부터 불러오는 구문을 반드시 작성
	public BookDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("book-query.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public List<Book> unifiedSearch(Connection conn, String keyword) throws Exception {
		// 1. 결과 저장할 리스트 선언
		List<Book> bookList = new ArrayList<>();
		
		try {
			// 2. SQL문을 XML파일로부터 가져와 저장함
			String sql = prop.getProperty("unifiedSearch");
			
			// 3. 입력값이 있으므로 pstmt를 준비
			//    pstmt는 호출할 때 sql을 적재함
			pstmt = conn.prepareStatement(sql);
			
			// 4. pstmt의 placeholder에 저장
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			
			// 5. pstmt 처리
			//  ...해서 rs에 저장
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bookNo = rs.getInt("BOOK_NO");
				String callNo = rs.getString("CALL_NO");
				String bookName = rs.getString("BOOK_NAME");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				String location = rs.getString("LOCATION");
				String availability = rs.getString("AVAILABILITY");
				String dueDate = rs.getString("DUE_DATE");
				
				Book book = new Book(bookNo, callNo, bookName, author, publisher, location, availability, dueDate);
				
				bookList.add(book);

			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		// 결과 반환
		return bookList;
	}

}
