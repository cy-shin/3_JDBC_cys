package edu.kh.emp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	private PreparedStatement pstmt;
	// Statement의 자식으로, 향상된 기능을 제공한다
	// -> ?기호( placeholder / 위치홀더 )를 이용해서
	//    SQL에 작성되는 리터럴을 동적으로 제어함.
	// '문자열' / 12354(숫자) / EMP_ID(테이블명, 컬럼명) / "쌍따옴표 내부가 하나의 범위(문장)"
	// SQL ?기호에 추가되는 값은
	// 숫자인 경우 '' 없이 대입
	// 문자열인 경우 ''가 자동으로 추가되어 대입됨
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "kh_cy";
	private String pw = "kh1234";
	private String user2 = "my_study";
	private String pw2 = "DNJSGMD713";

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
					+ " JOIN JOB USING(JOB_CODE)"
					+ " ORDER BY EMP_ID";
			
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
				e.printStackTrace();
			}
		}
		// 5. 결과 반환
		return empList;
	}
	
	/** 사번이 일치하는 사원의 정보를 조회 - DAO
	 * @param empId 입력받은 인자(전달 인자)
	 * @return emp;
	 */
	public Employee selectEmpId(int empId) {
		// 항상 결과를 저장용 변수를 먼저 선언하고 return문을 작성한 다음 나머지 코드를 작성하자
		Employee emp = null;
		// 만약, 조회 결과가 있으면 Employee 객체를 생성해서 emp에 대입 (null이 아님)
		// 만약, 조회 결과가 없으면 emp에 아무것도 대입하지 않음(null)
		// 참고 : Heap영역에 생성되는게 아니라서, 기본값을 null로 가지지 않음..
		
		try {
			
			// 메서드를 실행할 때마다 connection을 열고 close를 반복하는 이유 : DB에 접속 가능 인원이 제한되어있으므로 , 원활한 접속을 위해서 기능을 사용할때만 접속되게끔 만드는 것
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 JDBC 드라이버 메모리 로드
			conn = DriverManager.getConnection(url, user, pw); // 커넥션 생성해서 얻어오기
			
			// SQL 작성
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, JOB_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE) "
					+ "JOIN JOB USING (JOB_CODE) "
					+ "WHERE EMP_ID = " + empId; // view에서 입력받은 사원
			
			// STATEMENT 작성
			stmt = conn.createStatement();
			
			// RESULTSET 작성
			rs = stmt.executeQuery(sql);
			
			// ** 조회 결과가 최대 1행이므로 while문을 사용 시 불필요한 조건 검사를 하게 됨
			// 그래서 if문을 작성함
			if(rs.next()) { // 조회 결과가 있을 경우
				// 메서드가 시작될 때 전달인자로 empId를 받아왔으므로, empId 변수를 작성할 필요 없음
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				// 조회 결과를 담은 Employee 객체 생성 후, 결과 저장용 변수 emp에 대입함
				emp = new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 결과 반환
		return emp;
	}

	/** 주민등록번호가 일치하는 사원 정보 조회 - DAO
	 * @param empNo
	 * @return
	 */
	public Employee selectEmpNo(String empNo) {
		Employee emp = null;
		try {
			// 커넥션 생성
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, NVL(DEPT_TITLE, '부서없음') DEPT_TITLE, JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)"
					+ " JOIN JOB USING (JOB_CODE)"
					+ " WHERE EMP_NO = ?"; // ? = PlaceHolder
			
			// ** Statement 객체 사용 시 순서
			// SQL 작성 -> Statement 생성 -> SQL 수행 후 결과를 반환
			
			// PreparedStatement 객체 사용 시 순서..
			// SQL 작성 
			//  -> PreparedStatement 객체 생성(?가 포함된 SQL을 매개변수로 사용함)
			//	-> ?에 알맞은 값 대입
			//  -> SQL 구문 수행 후 결과를 반환받음
			// PreparedStatement 준비
			pstmt = conn.prepareStatement(sql);
			
			// ?에 알맞은 값 대입하기
			pstmt.setString(1, empNo);
			
			// SQL 수행 후 결과를 반환받기
			rs = pstmt.executeQuery(); // 안에 resultset이 들어가지 않음. 왜?
			// PreparedStatement 는 객체 생성 시 이미 SQL이 담겨져 있는 상태임
			// SQL 수행 시 매개변수를 전달할 필요 없다
			// -> 실수로 SQL을 매개변수에 추가하면, ?에 작성 추가했던 값이 모두 사라져 수행 시 오류 발생
			// == pstmt 이전에, 앞서 작성한 SQL문이 있기 때문
			
			if(rs.next()) {
				// 메서드가 시작될 때 전달인자로 empId를 받아왔으므로, empId 변수를 작성할 필요 없음
				
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				// empNo
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				// 조회 결과를 담은 Employee 객체 생성 후, 결과 저장용 변수 emp에 대입함
				emp = new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return emp;
	}

	/** 새로운 사원 정보 추가 - DAO
	 * @param emp
	 * @return result(INSERT 성공한 행의 개수를 반환)
	 */
	public int insertEmployee(Employee emp) {
		// 1. 결과를 저장할 변수를 먼저 선언함
		int result = 0;
		
		try {
			// 커넥션 생성
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			// ** DML 수행 예정 **
			// - 트랜잭션에 DML 구문이 임시 저장된 후 COMMIT했을 때 DB에 저장됨
			// -> 정상적인 DML인지를 판별해서 개발자가 직접 COMMIT, ROLLBACK을 수행
			
			// 하지만... Connection 객체를 생성했을 때
			// AutoCommit이 활성화 되어있는 상태이기 때문에..
			// 이를 해제하는 코드를 추가해주어야 함!!!
			
			// Connection conn의 AutoCommit을 해제
			// true = AutoCommit 활성화 / false = AutoCommit 비활성화
			conn.setAutoCommit(false); // AutoCommit 비활성화!
			
			// AutoCommit을 비활성화해도, conn.close() 구문이 수행되면 자동으로 Commit이 수행됨
			// --> close() 수행 전에 트랜잭션 제어 코드를 작성해야 함
			
			// SQL 작성																						해당 컬럼에서 DEFAULT로 지정된 값이 추가됨(퇴사여부 컬럼의 DEFAULT = 'N')
			String sql = "INSERT INTO EMPLOYEE VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, NULL, DEFAULT)";
			
			// PreparedStatement 객체 생성(매개변수에 SQL 추가)
			pstmt = conn.prepareStatement(sql);
			
			// ?(placeholder)에 알맞은 값을 대입하자
			pstmt.setInt(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setString(8, emp.getSalLevel());
			pstmt.setInt(9, emp.getSalary());
			pstmt.setDouble(10, emp.getBonus());
			pstmt.setInt(11, emp.getManagerId());
			
			// pstmt를 이용해서 SQL 수행 후 결과를 반환받음
			result = pstmt.executeUpdate();
			
			// executeUpdate() : DML(INSERT, UPDATE, DELETE) 수행 후 결과 행 개수를 반환
			// exectueQuery() : SELECT문 수행 후 ResultSet을 반환함
			
			// *** 트랜잭션 제어 처리 ***
			// -> DML 성공 여부에 따라서 commit, rollback을 제어
			if(result>0) conn.commit(); // DML 성공 시(result>0) commit 수행
			else		 conn.rollback(); // DML 실패 시(result==0) rollback 수행
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/** 사번이 일치하는 사원 정보 수정(이메일, 전화번호, 급여) - DAO
	 * @param emp
	 * @return 0 수정 실패 / 1 수정 성공
	 */
	public int updateEmployee(Employee emp) {
		int result = 0;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			conn.setAutoCommit(false); // AutoCommit 비활성화
			
			String sql = "UPDATE EMPLOYEE SET "
					+ "EMAIL = ?, PHONE = ?, SALARY = ? "
					+ "WHERE EMP_ID = ?";
			
			// PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, emp.getEmail());
			pstmt.setString(2, emp.getPhone());
			pstmt.setInt(3, emp.getSalary());
			pstmt.setInt(4, emp.getEmpId());
			
			result = pstmt.executeUpdate(); // 반영된 행의 개수 반환
			
			if(result==0) conn.rollback();
			else 		 conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/** 사번이 일치하는 사원 정보 삭제 - DAO
	 * @param empId
	 * @return 0 삭제 안됨 / 1 삭제됨
	 */
	public int deleteEmployee(int empId) {
		int result = 0;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			
			conn.setAutoCommit(false);
			
			String sql = "DELETE FROM EMPLOYEE "
				    	+ "WHERE EMP_ID = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, empId);
			
			result = pstmt.executeUpdate();
			
			if(result!=0) conn.commit();
			else          conn.rollback();
					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}

	public List<Employee> selectDept(String inputDept) {
		List<Employee> result = new ArrayList<>();
		
		try {
			Class.forName(driver);
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			// url = type + ip + port + sid
			
			conn = DriverManager.getConnection(url, user2, pw2);
			
			// pstmt를 사용할지, stmt를 사용할지 결정
			// 1. stmt를 사용할 경우
//			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, DEPT_TITLE, JOB_NAME, SALARY"
//					+ " FROM EMPLOYEE"
//					+ " JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)"
//					+ " JOIN JOB USING(JOB_CODE)"
//					+ " WHERE DEPT_TITLE = '" + inputDept +"'";
//			
//			stmt = conn.createStatement(); 
//			rs = stmt.executeQuery(sql);
//			
//			while(rs.next()) {
//				int empId = rs.getInt("EMP_ID");
//				String empName = rs.getString("EMP_NAME");
//				String empNo = rs.getString("EMP_NO");
//				String email = rs.getString("EMAIL");
//				String phone = rs.getString("PHONE");
//				String deptTitle = rs.getString("DEPT_TITLE");
//				String jobName = rs.getString("JOB_NAME");
//				int salary = rs.getInt("SALARY");
//				
//				Employee emp = new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
//				result.add(emp);
			// 2. pstmt를 사용할 경우
			String sql = "SELECT EMP_ID, EMP_NAME, EMP_NO, EMAIL, PHONE, DEPT_TITLE, JOB_NAME, SALARY"
					+ " FROM EMPLOYEE"
					+ " JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)"
					+ " JOIN JOB USING(JOB_CODE)"
					+ " WHERE DEPT_TITLE = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, inputDept);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int empId = rs.getInt("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String empNo = rs.getString("EMP_NO");
				String email = rs.getString("EMAIL");
				String phone = rs.getString("PHONE");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				
				Employee emp = new Employee(empId, empName, empNo, email, phone, deptTitle, jobName, salary);
				result.add(emp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
