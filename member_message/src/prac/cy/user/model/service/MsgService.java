package prac.cy.user.model.service;

import static prac.cy.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import prac.cy.user.model.dao.MsgDAO;
import prac.cy.user.vo.MsgBox;

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

	/** 2. 받은 메세지 리스트
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> msgBoxRecd(String myNo) throws Exception {
		Connection conn = getConnection();
		
		List<MsgBox> boxList = dao.msgBoxRecd(conn, myNo);
		
		close(conn);
		
		return boxList;
	}
	
	/** 3. 보낸 메세지 리스트
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> msgBoxSend(String myNo) throws Exception {
		Connection conn = getConnection();
		
		List<MsgBox> boxList = dao.msgBoxSend(conn, myNo);
		
		close(conn);
		
		return boxList;
	}

	/** 메세지 내용 상세보기
	 * @param msgNo
	 * @return
	 */
	public String msgDetail(String msgNo) throws Exception {
		Connection conn = getConnection();
		
		String content = dao.msgDetail(conn, msgNo);
		
		close(conn);
		
		return content;
	}
	
	
}
