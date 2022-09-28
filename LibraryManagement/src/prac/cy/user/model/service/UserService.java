package prac.cy.user.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;
import prac.cy.user.model.dao.UserDAO;

public class UserService {

	UserDAO userDAO = new UserDAO();
	
	/** 3(1). 내 정보 출력
	 * @param myNo
	 * @return list
	 * @throws Exception
	 */
	public List<User> printMyInfo(int myNo) throws Exception{
		Connection conn = getConnection();
		
		List<User> myInfoList = userDAO.printMyInfo(conn, myNo);
		
		close(conn);
		
		return myInfoList;
	}
	
	/** 3-1. 내 정보 수정
	 * @param myNo
	 * @return int
	 * @throws Exception
	 */
	public int updateMyInfo(int myNo, int type, String edit) throws Exception{
		Connection conn = getConnection();
		
		int result = userDAO.updateMyInfo(conn, myNo, type, edit);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		
		close(conn);
		
		return result;
	}
	
	/** 3-1(2). 전체 정보 수정
	 * @param myNo
	 * @return int
	 * @throws Exception
	 */
	public int updateMyInfoAll(User user) throws Exception{
		Connection conn = getConnection();
		
		int result = userDAO.updateMyInfoAll(conn, user);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	/** 3-2. 내 대출 목록
	 * @param myNo
	 * @return list
	 * @throws Exception
	 */
	public List<Library> printMyLentList(int myNo) throws Exception{
		Connection conn = getConnection();
		
		List<Library> myLentList = userDAO.printMyLentList(conn, myNo);
		
		close(conn);
		
		return myLentList;
	}
	
	/** 3-3. 탈퇴
	 * @param myNo
	 * @return int
	 * @throws Exception
	 */
	public int deleteMe(int myNo) throws Exception {
		Connection conn = getConnection();
		
		int result = userDAO.deleteMe(conn, myNo);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	/** 3-3(2). 탈퇴 전 비밀번호 확인
	 * @param myNo
	 * @param myPw
	 * @return
	 * @throws Exception
	 */
	public int pwCheck(int myNo, String myPw) throws Exception{
		Connection conn = getConnection();
		
		int pwCheck = userDAO.pwCheck(conn, myNo, myPw);
			
		close(conn);
		
		return pwCheck;
	}
	
}
