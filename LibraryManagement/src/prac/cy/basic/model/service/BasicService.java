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
	public List<Book> keywordSearch(String keyword) throws Exception {
		Connection conn = getConnection();
		
		List<Book> bookList = bDao.keywordSearch(conn, keyword);
		
		close(conn);
		
		return bookList;
	}
}