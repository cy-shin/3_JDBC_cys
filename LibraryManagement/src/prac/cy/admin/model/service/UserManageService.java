package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.close;
import static prac.cy.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.UserManageDAO;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.User;

public class UserManageService {

	UserManageDAO UMDAO = new UserManageDAO();
	
	/** 이용자 1명 조회
	 * @param userId
	 * @return user
	 * @throws Exception
	 */
	public List<User> userInfo(String userInput) throws Exception {
		Connection conn = getConnection();
		
		List<User> userSingle = UMDAO.userInfo(conn, userInput);
		
		close(conn);
		
		return userSingle;
	}

	/** 1-1. 이용자 전체 조회
	 * @return
	 * @throws Exception
	 */
	public List<User> searchUserAll() throws Exception {
		Connection conn = getConnection();
				
		List<User> userList = UMDAO.searchUserAll(conn);
		
		close(conn);
		
		return userList;
	}
	
	/** 1-2. 사용자 조회
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

	

}
