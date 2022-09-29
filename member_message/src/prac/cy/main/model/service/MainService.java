package prac.cy.main.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;

import prac.cy.main.model.dao.MainDAO;

public class MainService {

	MainDAO dao = new MainDAO();
	
	/** 2. 회원가입
	 * @param userName
	 * @param userPw
	 * @param userId
	 * @return signUpResult
	 *    0  : 중복된 아이디/패스워드/이름
	 *    1  : 정상
	 * @throws Exception
	 */
	public int signUp(String userName, String userPw, String userId) throws Exception {
		Connection conn =  getConnection();
		
		int signUpResult = dao.signUp(conn, userName, userPw, userId);
		
		if(signUpResult > 0) {
			commit(conn);
		}
		if(signUpResult == 0) {
			rollback(conn);
		}
		close(conn);
		
		return signUpResult;
	}

}
