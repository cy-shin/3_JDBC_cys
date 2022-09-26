package prac.cy.admin.model.service;

import static prac.cy.common.JDBCTemplate.close;
import static prac.cy.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import prac.cy.admin.model.dao.UserManageDAO;
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
}
