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

	/** 받은 메세지 목록
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
	
	/** 받은 메세지 열람
	 * @param msgNo
	 */
	public void msgReaded(String msgNo) throws Exception {
		Connection conn = getConnection();

		int result = dao.msgReaded(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);

	}
	
	
	/** 보낸 메세지 목록
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
	
	/** 모든 메세지 목록
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> msgBoxAll(String myNo) throws Exception{
		Connection conn = getConnection();
		
		List<MsgBox> boxList = dao.msgBoxAll(conn, myNo);
		
		close(conn);
		
		return boxList;
	}
	
	/** 휴지통 목록
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> boxBin(String myNo) throws Exception {
		Connection conn = getConnection();
		
		List<MsgBox> boxList = dao.boxBin(conn, myNo);
		
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

	/** Sub : 메세지를 휴지통으로(임시삭제)
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int sendToBin(String msgNo) throws Exception {
		Connection conn = getConnection();
		int result = 0;

		result = dao.sendToBin(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);

		return result;
	}

	/** Sub : 메세지를 휴지통으로(임시삭제)
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int recdToBin(String msgNo) throws Exception {
		Connection conn = getConnection();
		int result = 0;

		result = dao.recdToBin(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);

		return result;
	}

	/** 메세지 완전 삭제
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int sendDel(String msgNo) throws Exception {
		Connection conn = getConnection();
		int result = 0;

		result = dao.sendDel(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);

		return result;
	}

	/** 메세지 완전 삭제
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int recdDel(String msgNo) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		
		result = dao.recdDel(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);
		
		return result;
	}
	
	/** 보낸 메세지 복원
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int binToSend(String msgNo) throws Exception {
		Connection conn = getConnection();
		int result = 0;

		result = dao.binToSend(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);

		return result;
	}

	/** 받은 메세지 복원
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int binToRecd(String msgNo) throws Exception {
		Connection conn = getConnection();
		int result = 0;
		
		result = dao.binToRecd(conn, msgNo);
		
		if(result > 0) commit(conn);
		if(result <= 0) rollback(conn);
		
		return result;
	}
	


	
	
	
}
