package prac.cy.main.model.service;

import java.sql.Connection;
import java.util.List;

import prac.cy.lib.vo.LibVO;
import prac.cy.main.model.dao.MainDAO;

import static prac.cy.common.JDBCTemplate.*;


public class MainService {
	MainDAO dao = new MainDAO();
	
	/** 1. 키워드 통합 검색
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<LibVO> keywordSearch(String keyword) throws Exception {
		Connection conn = getConnection();
		
		List<LibVO> bookList = dao.keywordSearch(conn, keyword);
		
		close(conn);
		
		return bookList;
	}

	/** 2. 로그인
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public LibVO login(String memberId, String memberPw) throws Exception {
		Connection conn = getConnection();
		
		LibVO loginUser = dao.login(conn, memberId, memberPw);
		
		close(conn);
		
		return loginUser;
	}

}
