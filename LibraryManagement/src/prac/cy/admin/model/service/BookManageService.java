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

	/** 3. 상세 정보 조회 서비스
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
	
	/** 4. 도서 정보 수정
	 * @param bookNo
	 * @param edit
	 * @throws Exception
	 */
	public int bookUpdate(int bookNo, int type, String edit) throws Exception  {
		Connection conn = getConnection();
		
		int result = BMDAO.bookUpdate(conn, bookNo, type, edit);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}
	
	/** 4-1. 도서 정보 전체 수정
	 * @param book
	 * @return
	 * @throws Excpetion
	 */
	public int bookUpdateAll(Book book) throws Exception {
		Connection conn = getConnection();
		
		int result = BMDAO.bookUpdateAll(conn, book);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	
	/** 5. 연체 도서 조회 서비스
	 * @return overdueList
	 * @throws Exception
	 */
	public List<Library> searchOverdue(int userNo) throws Exception {
		Connection conn = getConnection();
		
		List<Library> overdueList = BMDAO.searchOverdue(conn, userNo);
		
		close(conn);
		
		return overdueList;
	}

	/** 6-1. 대출 서비스
	 * @param userNo
	 * @param bookNo
	 * @return
	 * @throws Exception
	 */
	public int bookLent(int userNo, int bookNo) throws Exception {
		Connection conn = getConnection();
		
		int result = BMDAO.bookLent(conn, userNo, bookNo);
		
		if(result >= 2) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	/** 6-2. 반납 서비스
	 * @param bookNo
	 * @return result
	 * @throws Exception
	 */
	public int bookReturn(int bookNo) throws Exception {
		Connection conn = getConnection();
		
		int result = BMDAO.bookReturn(conn,bookNo);
		
		if(result >= 2) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	/** 7. 신규 도서 등록
	 * @param book
	 * @return
	 * @throws Excpetion
	 */
	public int bookAdd(Book book) throws Exception {
		Connection conn = getConnection();
		
		int result = BMDAO.bookAdd(conn, book);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	/** 특정 이용자의 대출 목록 조회
	 * @return overdueList
	 * @throws Exception
	 */
	public List<Library> searchUserBookList(int userNo) throws Exception {
		Connection conn = getConnection();
		
		List<Library> lentList = BMDAO.searchUserBookList(conn, userNo);
		
		close(conn);
		
		return lentList;
	}
	

	/** D. 이용자 1명 조회
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
	

	/**
	 *  기타1: 분류 코드
	 *  
	 */
	public List<Book> topicList() throws Exception {
		Connection conn = getConnection();
		
		List<Book> topicList = BMDAO.topicList(conn);
		
		close(conn);
		
		return topicList;
	}

	/**
	 *  기타2: 위치 코드
	 */
	public List<Book> locList() throws Exception {
		Connection conn = getConnection();
		
		List<Book> locList = BMDAO.locList(conn);
		
		close(conn);
		
		return locList;
	}

	/**
	 *  기타3: 상태 코드
	 */
	public List<Book> availList() throws Exception {
		Connection conn = getConnection();
		
		List<Book> availList = BMDAO.availList(conn);
		
		close(conn);
		
		return availList;
	}

	
}
