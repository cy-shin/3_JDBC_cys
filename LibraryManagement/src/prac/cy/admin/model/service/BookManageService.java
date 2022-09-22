package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.close;
import static prac.cy.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.BookManageDAO;
import prac.cy.library.vo.Book;

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
	public List<Book> searchOverdue() throws Exception {
		Connection conn = getConnection();
		
		List<Book> overdueList = BMDAO.searchOverdue(conn);
		
		close(conn);
		
		return overdueList;
	}
}
