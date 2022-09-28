package prac.cy.basic.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.basic.model.dao.BasicDAO;
import prac.cy.library.vo.Book;

public class BasicService {
	
	BasicDAO bDao = new BasicDAO();
	
	/** 1. 키워드 통합 검색
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<Book> searchKeyword(String keyword) throws Exception {
		Connection conn = getConnection();
		
		List<Book> bookList = bDao.searchKeyword(conn, keyword);
		
		close(conn);
		
		return bookList;
	}
	
	/** 2. 상세 정보 조회 서비스
	 * @param callNo
	 * @return
	 * @throws Exception
	 */
	public List<Book> searchDetail(String callNo) throws Exception {
		Connection conn = getConnection();
		
		List<Book> bookDetail = bDao.searchDetail(conn, callNo);
		
		close(conn);
		
		return bookDetail;
	}
}
