package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.vo.Member;

public class MemberService {
	
	private MemberDAO dao = new MemberDAO();
	
	/**	2. 회원 목록 조회
	 * @return memberList
	 * @throws Exception
	 */
	public List<Member> selectAll() throws Exception {
		// 1. Connextion 객체 생성
		Connection conn = getConnection();
		
		// 2. dao로 전달해서 리스트 받아옴
		List<Member> memberList = dao.selectAll(conn);
		
		// 3. connection 닫기
		close(conn);
		
		// 4. 반환
		return memberList;
	}

	/** 3. 회원 정보 수정
	 * @param member
	 * @return result
	 */
	public int updateMember(Member member) throws Exception {
		// 1. 커넥션
		Connection conn = getConnection();
		
		// 2. dao로 커넥션이랑 member 보내고 결과 반환받기
		int result = dao.updateMember(conn, member);
		
		// 3. 트랜잭션 처리 해야함(DML이므로)
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		// 4. conn 닫기
		close(conn);
		
		// 5. 결과 반환
		return result;
	}
	
	
	/** 4. 비밀번호 변경 서비스
	 * @param currPw
	 * @param newPw1
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(String currPw, String newPw1, int memberNo) throws Exception {
		// 1. 커넥션
		Connection conn = getConnection();

		// 2. dao로 커넥션이랑 member 보내고 결과 반환받기
		int result = dao.updatePw(conn, currPw, newPw1, memberNo);

		// 3. 트랜잭션 처리 해야함(DML이므로)
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		// 4. conn 닫기
		close(conn);

		// 5. 결과 반환
		return result;
	}

	
	
	/** 5. 회원 탈퇴 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public int secession(String memberPw, int memberNo) throws Exception {
		// 1. 커넥션
		Connection conn = getConnection();

		// 2. dao로 커넥션이랑 member 보내고 결과 반환받기
		int result = dao.secession(conn, memberPw, memberNo);

		// 3. 트랜잭션 처리 해야함(DML이므로)
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		// 4. conn 닫기
		close(conn);

		// 5. 결과 반환
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
