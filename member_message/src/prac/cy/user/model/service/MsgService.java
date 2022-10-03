package prac.cy.user.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;

import prac.cy.user.model.dao.MsgDAO;

public class MsgService {
	MsgDAO dao = new MsgDAO();
	
	
	/** 1-1. 사용자 확인 후 회원번호 반환
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public String msgUserCheck(String userName) throws Exception {
		Connection conn = getConnection();
		
		String userNo = dao.msgUserCheck(conn, userName);
		
		close(conn);
		
		return userNo;
	}
	
	/** 1-2. 메세지 번호에 사용할 난수 생성
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int makeMsgNo(String msgNo) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.makeMsgNo(conn, msgNo);
		
		close(conn);
		
		return result;
	}

	/** 1-3. 메세지
	 * @param msgNo
	 * @param myNo
	 * @param userNo
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public int msgWrite(String msgNo, String myNo, String userNo, String title, String content) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		result += dao.msgBox(conn, msgNo, title, content);
		result += dao.msgSend(conn, msgNo, myNo);
		result += dao.msgRecd(conn, msgNo, userNo);
		
		if(result >= 3) commit(conn);
		if(result < 3) rollback(conn);

		return result;
	}
}
