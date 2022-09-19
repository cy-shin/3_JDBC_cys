package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

// DAO Data Access Object 데이터 접근 객체 = DB 연결용 객체
public class MainDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	// Map<String, String> 제한, XML 파일 읽기/쓰기 특화
	
	// 기본 생성자
	public MainDAO() {
		
		try {
			prop = new Properties(); // Properties 객체 생성
			prop.loadFromXML(new FileInputStream("main-query.xml")); // main-query.xml의 내용을 읽어와 Properties 객체인 prop에 저장
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception{
		// 1. 결과를 저장할 저장용 변수
		int result = 0;
		
		try {
			// 2. sql 얻어오기
			
			String sql = prop.getProperty("idDupCheck");
			
			// 3. PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			
			// 4. placeholder에 값 세팅
			pstmt.setString(1, memberId);
			
			// 5. SQL 수행 후 결과 반환받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과 옮겨담기 :  지금은 한 행만 조회되어 반복할 필요가 없으므로 if문 사용
			if(rs.next()) {
				// rs.getInt("COUNT(*)"); // 컬럼명
				result = rs.getInt(1); // 첫 번째 컬럼의 값을 가져옴
			}
			
		} finally {
			// 7. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
			// conn은 service에서 호출했으니 service에서 닫을 예정
		}
		
		// 8. 결과 반환
		return result;
	}

	/** 회원 가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Connection conn, Member member) throws Exception{
		// 1. 결과를 저장할 저장용 변수
		int result = 0;
		
		// 2. SQL 읽어오기
		try {
			String sql = prop.getProperty("signUp");
			
			// 3. PreparedStatement 객체 준비
			pstmt = conn.prepareStatement(sql);
			
			// 4. placeholder에 값 대입
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			// 5. SQL 처리 후 결과를 저장함
			result = pstmt.executeUpdate();
			
		} finally {
			// 6. 사용한 jdbc객체 자원 반환
			close(pstmt);
			
		}
		return result;
	}

	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		// 1. 결과 저장용 변수
		Member loginMember = null;
		
		try {
			// 2. SQL 얻어오기
			String sql = prop.getProperty("login");
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?에 알맞은 값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			// 5. SQL(SELECT) 수행 후 결과(ResultSet) 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과가 있을 경우
			//    컬럼 값을 모두 얻어와
			//    Member 객체를 생성해서 loginMember에 대입
			if(rs.next()) {
				loginMember = new Member();
				
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberId(rs.getString("MEMBER_ID"));
				loginMember.setMemberName(rs.getString("MEMBER_NM"));
				loginMember.setMemberGender(rs.getString("MEMBER_GENDER"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));
				
				// 생성자를 만드는 방법도 있음 ~
				// loginMember = new Member(매개변수...)
			}
			
		} finally {
			// 7. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
		}
		// 8. 조회 결과 반환
		return loginMember;
	}
}
