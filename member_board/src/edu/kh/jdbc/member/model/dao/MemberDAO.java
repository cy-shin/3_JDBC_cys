package edu.kh.jdbc.member.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

import static edu.kh.jdbc.common.JDBCTemplate.*;

public class MemberDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 2. 회원 목록 조회
	 * @param conn
	 * @return memberList
	 * @throws Exception
	 */
	public List<Member> selectAll(Connection conn) throws Exception {
		// DAO를 작성할 때는 가장 먼저 결과를 저장할 객체를 만들어주기
		List<Member> memberList = new ArrayList<>();
		
		try {
			
			// SQL문을 준비 -> Property 객체를 이용해서 값을 가져옴
			String sql = prop.getProperty("selectAll2");
			
			// Statement 객체를 준비함 Connection을 이용 <- Connection은 미리 작성해둔 JDBCTemplate를 이용했음
			stmt = conn.createStatement();
			
			// stmt에 sql을 적재해서 처리
			// ResultSet에 sql처리 결과를 저장함
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				// 객체 생성
				Member member = new Member();
				String memberId = rs.getString("MEMBER_ID");
				String memberName = rs.getString("MEMBER_NM");
				String memberGender = rs.getString("MEMBER_GENDER");
				String lastLoginDate = rs.getString("LAST_LOGIN_DATE");
				String loginStatus = rs.getString("LOGIN_STATUS");
				
				// 객체에 저장 (생성자가 없으므로 SETTER 이용했음)
				member.setMemberId(memberId);
				member.setMemberName(memberName);
				member.setMemberGender(memberGender);
				member.setLastLoginDate(lastLoginDate);
				member.setLoginStatus(loginStatus);
				
				// 리스트에 객체 추가함
				memberList.add(member);
			}
			
		} finally {
			// 연 순서대로 닫아주기
			close(rs);
			close(stmt);
		}
		return memberList;

	}


	/** 3. 회원 정보 수정
	 * @param conn
	 * @param member
	 * @throws Exception
	 * @return result
	 * 
	 */
	public int updateMember(Connection conn, Member member) throws Exception{
		// 1. 반환값을 저장할 변수 선언
		int result = 0;
		// 2. try - catch문 준비
		try {
			String sql = prop.getProperty("updateMember");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberGender());
			pstmt.setInt(3, member.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		// 3. sql문 불러옴
		// 4. pstmt 준비하고 sql 적재
		// 5. pstmt placeholder에 저장
		// 6. 결과를 반환할 변수에 저장
		// 7. 사용한 pstmt 객체 닫기
		
		return result;
	}


	/** 4. 비밀번호 변경
	 * @param conn
	 * @param currPw
	 * @param newPw1
	 * @param memberNo
	 * @throw Exception
	 * @return
	 */
	public int updatePw(Connection conn, String currPw, String newPw1, int memberNo) throws Exception {
		int result = 0;

		try {
			String sql = prop.getProperty("updatePw");

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, newPw1);
			pstmt.setInt(2, memberNo);
			pstmt.setString(3, currPw);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;

	}


	/** 5. 회원 탈퇴 DAO
	 * @param conn
	 * @param memberPw
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public int secession(Connection conn, String memberPw, int memberNo) throws Exception {
		int result = 0;

		try {
			String sql = prop.getProperty("secession");

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, memberNo);
			pstmt.setString(2, memberPw);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
