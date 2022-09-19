package prc.jdbc.model.service;

import static prc.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prc.jdbc.model.dao.BookDAO;
import prc.jdbc.model.vo.Book;

// 비즈니스 로직 처리, 프로그램이 제공하는 기능을 모아둔 클래스
// DAO는 SQL문을 한 행씩 처리하고 결과를 반환하는데 이 결과를 Service내에 임시 보관했다가 오류가 없으면 한번에 commit
public class BookService {

	BookDAO dao = new BookDAO();
	

	/** 1. 통합 검색
	 * @param keyword
	 * @return bookList
	 */
	public List<Book> unifiedSearch(String keyword) throws Exception {
		// 1. Connection 객체 생성
		// 2. List 생성해서 null로 놔둠
		// 3. DAO로 keyword, conn 전달하고 결과를 반환받아 list에 저장
		// 5. Connetciont 객체 닫고 List 반환함
		Connection conn = getConnection();
		
		List<Book> bookList = dao.unifiedSearch(conn, keyword);
		
		close(conn);
		
		return bookList;
	}

}
