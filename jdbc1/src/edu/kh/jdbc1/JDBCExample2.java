package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		// 1단계 : JDBC 객체 참조 변수를 생성 ( java.sql 패키지 )
		Connection conn = null; // 연결 정보
		Statement stmt = null; // sql 실행, 반환받아옴
		ResultSet rs = null; // select 결과를 저장
		
		try {
			// 2단계 : 참조변수에 알맞은 객체를 대입함
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String user = "kh_cy";
			String pw = "kh1234";

			conn = DriverManager.getConnection(type + ip + port + sid, user, pw); // conn객체를 생성함
			
			System.out.println("<입력 받은 급여보다 많이 받는(초과) 직원만 조회>");
			System.out.print("급여 입력 > ");
			
			int input = sc.nextInt();
			sc.nextLine();
			
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " + input;
			
			stmt = conn.createStatement(); // stmt 는 conn만 만들 수 있음
			
			rs = stmt.executeQuery(sql);
			
			// 3단계 : SQL을 수행해서 반환받은 결과(ResultSet)를 한 행씩 접근해서 컬럼값을 얻어오기
			
			// 얼마나 반복해야할지 모르므로 while문이 바람직
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d\n", empId, empName, salary);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				// 4단계 : 사용한 JDBC 객체 자원을 반환
				if(rs!=null) rs.close(); // rs가 참조하는 객체가 있을 때에만 rs를 닫겠음~
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
