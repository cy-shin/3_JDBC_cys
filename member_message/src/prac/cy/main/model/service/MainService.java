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
	 *   -1  : 중복된 아이디/이름
	 *    1  : 정상
	 * @throws Exception
	 */
	public int signUp(String userNo, String userName, String userPw, String userId) throws Exception {
		Connection conn =  getConnection();
		
		int signUpResult = dao.signUp(conn, userNo, userName, userPw, userId);
		
		if(signUpResult > 0) {
			commit(conn);
		}
		if(signUpResult == 0) {
			rollback(conn);
		}
		close(conn);
		
		return signUpResult;
	}

	/** 2. 회원가입 전 아이디 / 이름 중복 체크
	 * @param userName
	 * @param userId
	 * @return checkResult
	 *    2   :  중복된 아이디/이름
	 *    1   :  정상
	 * @throws Exception
	 */
	public int checkDuplicate(String userName, String userId) throws Exception {
		Connection conn = getConnection();
		
		int idNameCheck = dao.checkDuplicate(conn, userName, userId); 
		
		close(conn);
		
		return idNameCheck;
	}

	/** 2. 유저 번호 부여
	 * @param ran
	 * @return
	 *    0  : 중복
	 *    1  : 성공
	 * @throws Exception
	 */
	public int makeUserNo(String ran) throws Exception {
		Connection conn = getConnection();
		
		int noCheck1 = dao.makeUserNo(conn, ran); 
		int noCheck2 = dao.createBoxSend(conn, ran); 
		
		int noCheck = noCheck1 + noCheck2;
		close(conn);
		
		return noCheck;
	}

}