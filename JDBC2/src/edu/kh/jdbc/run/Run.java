package edu.kh.jdbc.run;

import java.sql.SQLException;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run {
	public static void main(String[] args) {
		
		TestService service = new TestService();
		
		// TB_TEST 테이블에 INSERT 수행하는 구문을 작성
		
		TestVO vo1 = new TestVO(1, "제목1", "내용1");
		
		// TB_TEST 테이블에 INSERT를 수행하는 서비스 메서드를 호출 후,
		// 결과를 반환받음
		
		try {
			int result = service.insert(vo1);
			// dao.insert에서 발생한 SQLException이
			// dao.insert -> testService.insert -> Run까지 throw되어 왔음
			
			if(result > 0) {
				System.out.println("insert 성공");
			} else {
				System.out.println("insert 실패"); // 서브쿼리 사용 시에 실패할 수 있음
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 수행 중 오류 발생");
			e.printStackTrace();
		}
		
	}
}
