package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.emp.model.vo.Employee;

// DAO(Data Access Object, 데이터 접근 객체)
// -> 데이터베이스에 접근(==연결)하는 객체
// -> 주로 JDBC코드를 작성함
public class EmployeeDAO {
	
	// JDBC 객체 참조 변수로 필드를 선언(Class 내부에서 공통으로 사용할 수 있음)
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	// 메서드 안에 생성되는 변수는 지역변수로, Stack 영역에 생성되며 변수가 빈 채로 존재할 수 있음
	// 그러나 Heap 영역에 생성되는 멤버 변수(=필드)는 값이 빈 채로 존재할 수 없으며, 컴파일러가 자료형의 기본값으로 초기화를 해놓음
	// 따라서 참조형 멤버변수의 경우, null로 직접 초기화하지 않아도 됨!
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "kh_cy";
	private String pw = "kh1234";

	
	/** 전체 사원 정보를 조회 DAO
	 * @return empList
	 */
	public List<Employee> selectAll() {
		// 1. 결과 저장용 변수를 선언
		List<Employee> empList = new ArrayList<>();

		
		try {
			// 2. JDBC 참조변수에 객체를 대입
			// conn -> stmt -> rs
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw);
			// 오라클 jdbc 드라이버 객체를 이용하여 DB 접속 방법을 생성
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, NVL(PHONE, '     -     ') AS PHONE , NVL(DEPT_TITLE, '부서없음') AS DEPT_TITLE, JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING(JOB_CODE)";
			
			// conn을 이용해서 stmt객체를 생성
			stmt = conn.createStatement();
			
			// SQL을 수행 후 결과(ResultSet)를 반환 받음
			rs = stmt.executeQuery(sql);
			// SELECT문 = DQL = QUERY
			
			
			// 3. 조회 결과를 얻어와 한 행씩 접근하여
			// Employee객체 생성 후 컬럼 값 옮겨 닮기
			// -> List에 추가
			
			while(rs.next()) {
				
				int empId = rs.getInt("EMP_ID");
				// EMP_ID 컬럼은 문자열 컬럼이지만 저장된 값이 모두 숫자형태
				// -> DB에서 자동으로 형변환 진행한 후 얻어옴
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
			
				Employee emp = new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
				empList.add(emp); // List에 담기
			
			} // while문 종료
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 4. jdbc 객체 자원 반환
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {

			}
		}
		
		// 5. 결과 반환
		return empList;
	}
	
	
	
	
	
	
	
	
	
	
	
}
