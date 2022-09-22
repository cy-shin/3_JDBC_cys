package prac.cy.main.model.service;

import java.sql.Connection;
import java.util.List;

import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;
import prac.cy.main.model.dao.MainDAO;

import static prac.cy.common.JDBCTemplate.*;


public class MainService {
	MainDAO dao = new MainDAO();
	
	/* 1. 키워드로 통합 검색 -> Basic
	 * 
	 */

	/** 2. 로그인
	 * @param memberId
	 * @param memberPw
	 * @return
	 */
	public User login(String userId, String userPw) throws Exception {
		Connection conn = getConnection();
		
		User loginUser = dao.login(conn, userId, userPw);
		
		close(conn);
		
		return loginUser;
	}

}
