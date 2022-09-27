package prac.cy.main.model.service;

import java.sql.Connection;

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

	/** 3. 회원 가입
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int signUp(User user) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.signUp(conn, user);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	/** 3-1. 아이디 중복 체크
	 * @param userId
	 * @return int
	 */
	public int duplicateName(String userId) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = dao.duplicateName(conn, userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		
		return result;
	}


}
