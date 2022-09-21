package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.AdminDAO;
import prac.cy.lib.vo.LibVO;

public class AdminService {
	AdminDAO adminDAO = new AdminDAO();
	
	/** 1. 키워드 통합 검색
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<LibVO> keywordSearch(String keyword) throws Exception {
		Connection conn = getConnection();
		
		List<LibVO> bookList = adminDAO.keywordSearch(conn, keyword);
		
		close(conn);
		
		return bookList;
	}
	
	
	/** 2. 도서 전체 조회 서비스
	 * @return
	 * @throws Exception
	 */
	public List<LibVO> searchAll() throws Exception {
		Connection conn = getConnection();
		
		List<LibVO> bookList = adminDAO.searchAll(conn);
		
		close(conn);
		
		return bookList; 
	}

}
