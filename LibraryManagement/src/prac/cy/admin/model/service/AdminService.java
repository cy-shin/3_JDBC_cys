package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.AdminDAO;
import prac.cy.library.vo.Library;

public class AdminService {
	AdminDAO adminDAO = new AdminDAO();
	
	/* 1. 키워드로 통합 검색 -> Basic
	 * 
	 */
	
	
	/** 2. 도서 전체 조회 서비스
	 * @return
	 * @throws Exception
	 */
	public List<Library> searchAll() throws Exception {
		Connection conn = getConnection();
		
		List<Library> bookList = adminDAO.searchAll(conn);
		
		close(conn);
		
		return bookList; 
	}

}
