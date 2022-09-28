package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.UserManageDAO;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.User;

public class UserManageService {

	UserManageDAO UMDAO = new UserManageDAO();
	
	
	
	/** 1. 이용자 조회
	 * @param userKeyword
	 * @param identityName
	 * @param statusName
	 * @param delayOpt
	 * @return
	 * @throws Exception
	 */
	public List<User> searchUser(String userKeyword, String identityName, String statusName, String delayOpt) throws Exception {
		Connection conn = getConnection();
		
		List<User> userList = UMDAO.searchUser(conn, userKeyword, identityName, statusName, delayOpt);
		
		close(conn);
		
		return userList;
	}
	
	/** 2. 상세 조회
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public List<User> searchUserDetail(int userNo) throws Exception {
		Connection conn = getConnection();
		
		List<User> userList = UMDAO.searchUserDetail(conn, userNo);
		
		close(conn);
		
		return userList;
	}
	
	/** 3. 정보 수정
	 * @param condition
	 * @param userNo
	 * @throws Exception
	 */
	public int updateUser(int condition, String edit, int userNo) throws Exception {
		Connection conn = getConnection();
		
		int result = UMDAO.updateUser(conn, condition, edit, userNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	/**
	 *  기타1: 신분 코드
	 */
	public List<User> identityList() throws Exception {
		Connection conn = getConnection();
		
		List<User> identityList = UMDAO.identityList(conn);
		
		close(conn);
		
		return identityList;
	}
	
	/**
	 *  기타2: 상태 코드
	 */
	public List<User> statusList() throws Exception {
		Connection conn = getConnection();
		
		List<User> statusList = UMDAO.statusList(conn);
		
		close(conn);
		
		return statusList;
	}

	/** 기타3 : 이용자 1명 조회
	 * @param userId
	 * @return user
	 * @throws Exception
	 */
	public List<User> oneUser(String userInput) throws Exception {
		Connection conn = getConnection();
		
		List<User> userSingle = UMDAO.oneUser(conn, userInput);
		
		close(conn);
		
		return userSingle;
	}



	

}
