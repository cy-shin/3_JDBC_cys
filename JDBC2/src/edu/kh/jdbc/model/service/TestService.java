package edu.kh.jdbc.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

// import static 구문?
// -> static이 붙은 필드, 메서드를 호출할 때 클래스명을 생략할 수 있게 해줌

import java.sql.Connection;
import java.sql.SQLException;

import edu.kh.jdbc.model.dao.TestDAO;
import edu.kh.jdbc.model.vo.TestVO;

// Service : 비즈니스 로직(데이터 가공, 트랜잭션 제어) 처리
// -> 실제 프로그램이 제공하는 기능을 모아놓은 클래스

// 하나의 서비스 메서드에서 n개의 DAO 메서드(지정된 SQL을 수행를 호출하여
// 이를 하나의 트랜잭션 단위로 취급하여
// 한번에 commit, rollback을 수행할 수 있다.
// (여러 SQL문장 수행 중, 하나라도 문제가 생기면 rollback)

// 여러 DML을 수행하지 않는 경우(단일 DML, SELECT 등)라도
// 코드의 통일성을 지키기 위해서 Service에서 Connection 객체를 생성한다.
// -> Connection 객체가 commit, rollback 메서드를 제공함


public class TestService {

	private TestDAO dao = new TestDAO();
	
	/** 1행 삽입 서비스
	 * @param vo1
	 * @return result
	 */
	public int insert(TestVO vo1) throws SQLException {
		// 커넥션 생성 : 서비스 클래스에 메서드 작성 시 첫 줄에 항상 커넥션을 먼저 생성
		Connection conn = getConnection(); // static 메서드이므로 클래스명.메서드명으로 호출할 수 있음
		
		// INSERT DAO 메서드를 호출하여 수행 후 결과 반환 받기
		int result = dao.insert(conn, vo1); // service에서 만든 Connection 객체를 반드시 dao로 전달해야, 
											// dao에서 Connection 객체를 만들지 않아도 됨
											// dao.insert에서 SQLEXCEPTION을 던졌기 때문에 오류 발생
											// -> 다시 throws해서 상위 호출장소(Run)로 던짐
		// result에는 SQL 수행 후 반영된 결과 행의 개수가 담겨있으며
		// 0이면 반영되지 않았음을 의미
		
		// 트랜잭션 제어
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		// 커넥션 반환(close) : return문 앞에 자원 반환 구문 작성
		close(conn);
		
		return result;
	}

	/** 동시에 3행 삽입 서비스
	 * @param vo1
	 * @param vo2
	 * @param vo3
	 * @return result
	 */
	public int insert(TestVO vo1, TestVO vo2, TestVO vo3) throws Exception {
		// throws Exception
		// -> 아래 catch문에서 강제 발생되 예외를
		//    호출부로 던진다는 구문
		
		// 왜 예외를 강제로 발생시켰는가?
		// -> Run2에서 예외 상황에 따른 결과를 다르게 출력하기 위해서
		
		
		Connection conn = getConnection();
		// insert 중 하나라도 오류가 발생하면 모든 insert내용을 rollback
		// -> try ~ catch로 예외가 발생했다는 것을 인지함.
		int res = 0; // insert 3회 모두 성공 시 1, 아니면 0
		
		try {
			int result1 = dao.insert(conn, vo1); // 성공 시 1, 실패 시 0 반환
			int result2 = dao.insert(conn, vo2); // 성공 시 1, 실패 시 0 반환
			int result3 = dao.insert(conn, vo3); // 성공 시 1, 실패 시 0 반환
			
			// 트랜잭션 제어
			if(result1 + result2 + result3 == 3 ) { // 모두 insert 성공한 경우
				commit(conn);
				res = 1;
				
			} else {
				rollback(conn);
				// res = 0; 은 굳이 적을 필요 없음!
			}
			
		} catch (Exception e) { // dao 수행 중 예외 발생 시 
			rollback(conn);
			// 실패한 데이터를 DB에 삽입하지 않음
			// -> DB에는 성공된 데이터만 저장된다 = DB에 저장된 데이터의 신뢰도가 상승한다.
			
			e.printStackTrace();
			
			// Run2 클래스로 예외를 전달할 수 있도록 예외를 강제 발생시킴 
			
			throw new Exception("DAO 수행 중 예외 발생"); // 새 예외 강제 발생시킴
			
		} finally { // 성공 실패 여부 상관 없이 무조건 conn 반환하기
			close(conn);
		}
		return res; // insert 3회의 결과를 반환한다.
	}
	
	public int update(TestVO voUpdate) throws Exception {
		// 4-1. Service 메서드에서 먼저 Connection 객체를 생성함
		//	  Connection 객체를 생성해야 DB와 연결할 수 있음
		Connection conn = getConnection(); // 이때, 미리 작성해둔 JDBCTemplate의 내용을 이용함
		
		int result = 0; // 정상적으로 처리되었는지 확인하기 위해서?
		
		try {
			result = dao.update(conn, voUpdate);
			if(result > 0) commit(conn);
			else 		  rollback(conn);
			
		} catch (Exception e) {
			rollback(conn);
			throw new Exception("DAO 수행 중 예외 발생");
		} finally {
			close(conn);
		}
		
		return result;
	}
	
}
