package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.BookManageDAO;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;

public class BookManageService {

	BookManageDAO BMDAO = new BookManageDAO();
	
	/* 1. 키워드로 통합 검색 -> Basic
	 * 
	 */
	
	
	/** 2. 도서 전체 조회 서비스
	 * @return bookList
	 * @throws Exception
	 */
	public List<Book> searchAll() throws Exception {
		Connection conn = getConnection();
		
		List<Book> bookList = BMDAO.searchAll(conn);
		
		close(conn);
		
		return bookList; 
	}

	/** 3. 연체 도서 조회 서비스
	 * @return overdueList
	 * @throws Exception
	 */
	public List<Library> searchOverdue() throws Exception {
		Connection conn = getConnection();
		
		List<Library> overdueList = BMDAO.searchOverdue(conn);
		
		close(conn);
		
		return overdueList;
	}

	/** B. 책 1권 조회 서비스(by 청구기호)
	 * @param callNo
	 * @return
	 * @throws Exception
	 */
	public List<Book> bookInfo(String callNo) throws Exception {
		Connection conn = getConnection();
		
		List<Book> bookSingle = BMDAO.bookInfo(conn, callNo);
		
		close(conn);
		
		return bookSingle;
	}

	/** C. 1권 반납 처리
	 * @param bookNo
	 * @return result
	 * @throws Exception
	 */
	public int returnBook(int bookNo) throws Exception {
		Connection conn = getConnection();
		
		int result = BMDAO.returnBook(conn,bookNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	/** D. 1권 대출 처리
	 * @param userNo
	 * @param bookNo
	 * @return
	 * @throws Exception
	 */
	public int lentBook(int userNo, int bookNo) throws Exception {
		Connection conn = getConnection();
		
		int result = BMDAO.lentBook(conn,userNo,bookNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	/** 이용자 1명 조회
	 * @param userId
	 * @return user
	 * @throws Exception
	 */
	public List<User> userInfo(String userId) throws Exception {
		Connection conn = getConnection();
		
		List<User> userSingle = BMDAO.userInfo(conn, userId);
		
		close(conn);
		
		return userSingle;
	}
}
