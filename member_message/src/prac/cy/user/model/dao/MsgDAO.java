package prac.cy.user.model.dao;

import static prac.cy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import prac.cy.user.vo.MsgBox;
import prac.cy.user.vo.User;

public class MsgDAO {
	
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	Properties prop;
	
	/**
	 *  0. 기본생성자
	 */
	public MsgDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("box-query.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 1-1. 사용자 확인 후 회원번호 반환
	 * @param conn
	 * @param userName
	 * @return userNo
	 * @throws Exception
	 */
	public String msgUserCheck(Connection conn, String userName) throws Exception {
		String userNo = "";
		try {
			String sql = prop.getProperty("msgUserCheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userNo = rs.getString("USER_NO");
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return userNo;
	}
	
	/** 1-2. 메세지 번호에 사용할 난수 생성
	 * @param conn
	 * @param userName
	 * @return noCheck
	 *  0  : 중복
	 *  1  : 중복아님
	 * @throws Exception
	 */
	public int makeMsgNo(Connection conn, String msgNo) throws Exception {
		int noCheck = 0;
		List<User> tempList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("makeMsgNo");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				User temp = new User();
				temp.setUserFl(rs.getString("USER_FL"));
				
				tempList.add(temp);
				
			}
			if(tempList.isEmpty()) {
				noCheck = 1;
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return noCheck;
	}

	/** 1-3-1. 메세지를 박스에 저장
	 * @param conn
	 * @param msgNo
	 * @param title
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public int msgBox(Connection conn, String msgNo, String title, String content) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("insertMsgBox");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 1-3-2. 전송기록 생성
	 * @param conn
	 * @param msgNo
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public int msgSend(Connection conn, String msgNo, String myNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("insertMsgSend");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			pstmt.setString(2, myNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
			
		return result;
	}

	/** 1-3-3. 수신기록 생성
	 * @param conn
	 * @param msgNo
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	public int msgRecd(Connection conn, String msgNo, String userNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("insertMsgRecd");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			pstmt.setString(2, userNo);

			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	
	/** 받은 메세지 목록
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> msgBoxRecd(Connection conn, String myNo) throws Exception {
		List<MsgBox> boxList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("msgBoxRecd");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, myNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MsgBox msg = new MsgBox();
				
				String msgNo = rs.getString("MSG_NO");
				String userName = rs.getString("USER_NAME"); 
				String title = rs.getString("TITLE"); 
				String msgDate = rs.getString("MSG_DATE"); 
				String readFl = rs.getString("READ_FL"); 
				String boxType = rs.getString("BOX_TYPE");
				
				msg.setMsgNo(msgNo);
				msg.setUserName(userName);
				msg.setTitle(title);
				msg.setMsgDate(msgDate);
				msg.setReadFl(readFl);
				msg.setBoxType(boxType);
				
				boxList.add(msg);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boxList;
	}

	/** 받은 메세지 열람
	 * @param conn
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int msgReaded(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("msgReaded");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** 보낸 메세지 목록
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> msgBoxSend(Connection conn, String myNo) throws Exception {
		List<MsgBox> boxList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("msgBoxSend");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, myNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MsgBox msg = new MsgBox();
				
				String msgNo = rs.getString("MSG_NO"); 
				String userName = rs.getString("USER_NAME"); 
				String title = rs.getString("TITLE"); 
				String msgDate = rs.getString("MSG_DATE"); 
				String boxType = rs.getString("BOX_TYPE");
				
				msg.setMsgNo(msgNo);
				msg.setUserName(userName);
				msg.setTitle(title);
				msg.setMsgDate(msgDate);
				msg.setBoxType(boxType);
				
				boxList.add(msg);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boxList;
	}
	
	/** 전체 메세지 목록
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> msgBoxAll(Connection conn, String myNo) throws Exception {
		List<MsgBox> boxList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("msgBoxAll");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, myNo);
			pstmt.setString(2, myNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MsgBox msg = new MsgBox();
				
				String msgNo = rs.getString("MSG_NO"); 
				String userName = rs.getString("USER_NAME"); 
				String title = rs.getString("TITLE"); 
				String msgDate = rs.getString("MSG_DATE"); 
				String boxType = rs.getString("BOX_TYPE");
				
				msg.setMsgNo(msgNo);
				msg.setUserName(userName);
				msg.setTitle(title);
				msg.setMsgDate(msgDate);
				msg.setBoxType(boxType);
				
				boxList.add(msg);
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boxList;
	}
	
	/** 휴지통 목록
	 * @param conn
	 * @param myNo
	 * @return
	 * @throws Exception
	 */
	public List<MsgBox> boxBin(Connection conn, String myNo) throws Exception {
		List<MsgBox> boxList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("boxBin");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, myNo);
			pstmt.setString(2, myNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MsgBox msg = new MsgBox();
				
				String msgNo = rs.getString("MSG_NO"); 
				String userName = rs.getString("USER_NAME"); 
				String title = rs.getString("TITLE"); 
				String msgDate = rs.getString("MSG_DATE"); 
				String boxType = rs.getString("BOX_TYPE");
				
				msg.setMsgNo(msgNo);
				msg.setUserName(userName);
				msg.setTitle(title);
				msg.setMsgDate(msgDate);
				msg.setBoxType(boxType);
				
				boxList.add(msg);
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		return boxList;
	}

	
	/** 메세지 상세내용 조회
	 * @param conn
	 * @param msgNo
	 * @return
	 */
	public String msgDetail(Connection conn, String msgNo) throws Exception {
		String content = "";
		
		try {
			String sql = prop.getProperty("msgDetail");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String temp = rs.getString("CONTENT");
				
				content = temp;
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return content;
	}

	/** Sub : 메세지를 휴지통으로(임시삭제)
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int sendToBin(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("sendToBin");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);

			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** Sub : 메세지를 휴지통으로(임시삭제)
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int recdToBin(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("recdToBin");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}

	/** 메세지 완전 삭제
	 * @param conn
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int sendDel(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("sendDel");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** 메세지 완전 삭제
	 * @param conn
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int recdDel(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("recdDel");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** 보낸 메세지 복원
	 * @param conn
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int binToSend(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("binToSend");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	/** 받은 메세지 복원
	 * @param conn
	 * @param msgNo
	 * @return
	 * @throws Exception
	 */
	public int binToRecd(Connection conn, String msgNo) throws Exception {
		int result = 0;
		try {
			String sql = prop.getProperty("binToRecd");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, msgNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	

	
	
}
