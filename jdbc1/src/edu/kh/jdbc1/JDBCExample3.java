package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Emp;


//부서명을 입력 받아, 같은 부서에 있는 사원의 사원명, 부서명, 급여를 조회함
public class JDBCExample3 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		
		// JDBC 객체 참조 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			System.out.print("부서명 입력 : ");
			String input = sc.nextLine();
			
			// JDBC 참조변수에 알맞은 객체를 대입
			
			// 1. connection 만들기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String id = "kh_cy";
			String pw = "kh1234";
			
			// url = type + ip + port + sid (jdbc:oracle:thin:@localhost:1521:XE)
			conn = DriverManager.getConnection(type + ip + port + sid, id, pw);
			
			// 별칭을 잘 이용해보자
			// 이어쓰기 할 때 띄어쓰기에 주의
			String sql = "SELECT EMP_NAME, NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE, SALARY"
					+ " FROM EMPLOYEE"
					+ " LEFT JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID)"
					+ " WHERE NVL(DEPT_TITLE, '부서없음') = '" + input + "'";
			//  **** (중요!) 홑따옴표 표기 주의할 것 ****
			// Java에서 작성되는 SQL에 문자열(String) 변수를 추가할 경우
			// '' (DB 문자열 리터럴)이 누락되지 않도록 신경써야한다
			// 만약 '' 미입력시 String 값이 컬럼명으로 인식되어서
			// 부적합한 식별자 오류가 발생한다!
			
			stmt = conn.createStatement(); // statement 객체 생성
			
			// Statement 객체를 이용해서 
			// SQL(SELECT)을 DB에 전달하여 실행한 후 
			// ResultSet을 반환받아 rs변수에 대입
			rs = stmt.executeQuery(sql);
			
			// 조회 결과(rs)를 List에 옮겨 담기
			List<Emp> list = new ArrayList<>();
			
			while(rs.next()) { // 다음행으로 이동해서 해당 행에 데이터가 있으면 true
				// 현재 행에 존재하는 컬럼 값 얻어오기
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				int salary = rs.getInt("SALARY");
				
				// Emp 객체를 생성해서 컬럼값을 담아보자..
				Emp emp = new Emp(empName, deptTitle, salary);
				
				// 생성된 Emp객체를 List에 추가
				list.add(emp);
			}
			
			// 만약 List에 추가된 Emp객체가 없다면, "조회 결과가 없습니다"
			// 있다면 순차적으로 출력
			if(list.isEmpty()) { // List가 비어있는 경우 true를 반환
				System.out.println("조회 결과가 없습니다.");
			} else {
				
				// 향상된 for문
				for(Emp emp : list) {
					System.out.println(emp);
					// System.out.println(emp.toString());
				}
			}
			
		} catch (Exception e) {
			// 예외 최상위 부모인 Exception을 catch문에 작성해서
			// 발생하는 모든 예외를 처리할 수 있음
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (SQLException e) {

			}
		}
	}
}
