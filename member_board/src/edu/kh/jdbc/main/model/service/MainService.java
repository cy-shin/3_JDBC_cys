package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.vo.Member;

// Service : 데이터 가공, 트랜잭션 제어 처리

public class MainService {
	
	private MainDAO dao = new MainDAO();

	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return
	 *  0 : 중복 X
	 *  1 : 중복 O
	 *  @throws Exception
	 *  
	 *  사용? 2.회원가입(singUp)
	 */
	public int idDupCheck(String memberId) throws Exception { // 예외는 전부 view로 던질 예정
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. dao 메서드 호출 후 결과 반환 받기
		int result = dao.idDupCheck(conn, memberId);
		
		// DQL이므로 트랜잭션 처리할 필요 없이 CONN 반환할 예정
		
		// 3. Connection 반환(SELECT 구문은 트랜잭션 제어 필요 X)
		close(conn);
		
		// 4. 조회 결과를 반환함
		return result;
	}

	/** 회원 가입 서비스
	 * @param member
	 * @return result
	 *  0 : 실패
	 *  1 : 성공
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception {
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.signUp(conn, member);
		
		// 3. 트랜잭션 제어 처리
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		
		// 4. Connection 반환
		close(conn);
		
		// 5. 삽입 결과를 반환함
		return result;
	}

	/** 로그인 서비스......
	 * @param memberId
	 * @param memberPw
	 * @return
	 * @throws Exception
	 */
	public Member login(String memberId, String memberPw) throws Exception{
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. DAO 호출
		Member loginMember = dao.login(conn, memberId, memberPw);
		
		// 3. 커넥션 반환
		close(conn);
		
		// 4. 조회 결과 반환
		return loginMember;
	}

	/** 로그아웃
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public int logout(int memberNo) throws Exception {
		// 1. Connection 생성
		Connection conn = getConnection();

		// 2. DAO 호출
		int result = dao.logout(conn, memberNo);

		// 3. 커넥션 반환
		close(conn);

		// 4. 조회 결과 반환
		return result;
	}
}
