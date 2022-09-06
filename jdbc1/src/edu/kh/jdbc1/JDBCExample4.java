package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 직급명, 급여를 입력 받아
		// 해당 직급에서 입력받은 급여보다 많이 받는 사원의
		// 이름, 직급명, 급여, 연봉을 조회해서 출력해보자
		
		// 단, 조회 결과가 없으면
		// "조회 결과 없음" 출력
		
		// 조회 결과가 있으면
		// 선동일 / 대표 / 8000000 / 96000000
		// 선동일 / 사원 / 2000000 / 24000000 와 같은 형식으로 출력하기
		
		Scanner sc = new Scanner(System.in);
		
		// jdbc 객체의 참조변수를 선언함
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		// connection
		try {
			
			// 먼저 입력받고, 그 다음에 connection을 준비함 
			
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 입력값 입력받음
			System.out.println("[ 특정 직급의 사원 중 입력받은 급여보다 많이 받는 사원 조회 ]");
			
			System.out.print("직급명 : ");
			String inputJobName = sc.nextLine();
			
			System.out.print("급여 : ");
			int inputSalary = sc.nextInt();
			sc.nextLine();
			
//			String type = "jdbc:oracle:thin:@";
//			String ip = "localhost";
//			String port = ":1521";
//			String sid = ":XE";
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			// url = type + ip + port + sid
			String id = "kh_cy";
			String pw = "kh1234";
			
			// DriverManager를 이용해서 conn 객체를 생성함
			conn = DriverManager.getConnection(url, id, pw);
			
			
			// SQL문을 작성해서 String 타입의 sql에 저장
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY * 12  "
					+ "FROM EMPLOYEE "
					+ "JOIN JOB USING(JOB_CODE) "
					+ "WHERE JOB_NAME = '" + inputJobName + "' "
					+ "AND SALARY > " + inputSalary;
			// inputJobName은 문자열이므로 오라클의 리터럴 형식에 맞게 홑따옴표를 같이 작성
			
			// java <- stmt -> db
			stmt = conn.createStatement();
			
			// ResultSet에 sql문을  
			rs = stmt.executeQuery(sql);
			// executeQuery는 SELECT문을 입력받아 처리해서 ResultSet으로 만들어 반환하는 메서드
			// stmt(Statement)에 sql을 적재하는데 메서드를 사용함!
			
			List<Employee> list = new ArrayList<>();
			
			while(rs.next()) {
				// rs에서 조회된 값을 받아오기
				String empName = rs.getString("EMP_NAME");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				int annualIncome = rs.getInt("SALARY*12");
//				int annualIncome = rs.getInt("SALARY * 12");

				// Employee 클래스의 매개변수 생성자를 이용해서 입력받은 값을 객체에 저장
				Employee employee = new Employee(empName, jobName, salary, annualIncome);
				
				list.add(employee);
				
				}
			
			if(list.isEmpty()) {
				System.out.println("조회 결과 없음");
			} else {
				for(Employee e : list) {
					System.out.println(e);
				}
				
			}
			
			
			
		} catch (Exception e) {
			// Class...예외와 SQL...예외를 나누어 작성해도 됨
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {

			}
		}
		
	}
}
