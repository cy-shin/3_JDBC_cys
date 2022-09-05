package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		// 1단계 Connection, Statement, ResultSet을 설정
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// 2단계
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			String id = "kh_cy";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(type + ip + port + sid, id, pw);
			
			// 중간확인
			// System.out.println(conn);
			
			System.out.println("<사원을 조회>");
			System.out.print("이름 입력 : ");
			String input = sc.nextLine();
			
			
//			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_TITLE FROM EMPLOYEE JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID) WHERE DEPT_TITLE = \'" + input + "\'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				System.out.printf("사번 : %s / 이름 : %s / 부서 : %s\n", empId, empName, deptTitle);
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("잘못된 경로입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		
			try {
				// 연 순서대로 닫기
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			

		
		
	}
}
