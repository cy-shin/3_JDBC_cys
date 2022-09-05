package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample {
	 public static void main(String[] args) {
		
		 // JDBC  :자바에서 DB에 연결(접근)할 수 있게 해주는 JAVA Programming API
		 //												(Java에 기본 내장된 인터페이스)
		 // java.sql에서 제공
		 
		 // JDBC를 이용한 애플리케이션을 만들 때 필요한 것
		 // 1. Java의 JDBC 관련 인터페이스
		 // 2. DBMS(Oracle)
		 // 3. Oracle에서 Java 애플리케이션과 연결할 때 사용할
		 //    JDBC를 상속 받아 구현한 클래스 모음(ojdbc11.jar 라이브러리)
		 //    -> OracleDraiver.class (JDBC 드라이버) 이용
		 
		 // 1단계 : JDBC 객체 참조 변수를 선언( java.sql패키지의 인터페이스 )
		 //         -> 
		 
		 Connection conn = null;
		 // DB 연결 정보를 담은 객체
		 // -> DBMS 타입, 이름, IP, port, 계정명, 비밀번호 등이 저장됨
		 // -> DBeaver의 계정 접속 방법과 유사함
		 // * Java와 DB 사이를 연결해주는 통로(Stream과 유사함)
		 
		 Statement stmt = null;
		 // Connection을 통해
		 // 정적 SQL문을 DB에 전달해서 실행( = Statement )하고, 생성된 결과를 반환하는데 사용되는 개체
		 // 생성된 결과 : ResultSet 또는 성공한 행의 개수
		 
		 ResultSet rs = null;
		 // SELECT 질의 성공 시 반환되는데
		 // 조회 결과 집합을 나타내는 객체
		 
		 try {
			 // 2단계 : 참조 변수에 알맞은 객체를 대입
			 
			 // 2-1). DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드하기
			 										// = 객체로 만들어 두기
			 // Class.forName(패키지명+클래스명); // class를 생성하는 구문으로 new class와 비슷하지만 String명을 값으로 적기 때문에 컴파일러가 오류를 감지하지 못함
			 
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 // -> ( ) 안에 작성된 클래스의 객체를 반환
			 // -> 메모리에 객체가 생성되어지고 JDBC 필요 시 알아서 참조해서 사용
			 // -> 생략해도 자동으로 메모리 로드가 진행됨 (명시적으로 작성하는 것을 권장함)
			 
			 // 2-2). 연결정보를 담은 Connection을 생성 (DriverManager 객체를 거치지 않고서는 직접 만들지 못함)
			 //      -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴!
			 
			 String type = "jdbc:oracle:thin:@"; // JDBC 드라이버의 종류
			 
			 String ip = "localhost"; // DB 서버 컴퓨터 IP
			 // localhost 대신 loop back ip(127.0.0.1)를 작성해도 됨
			 
			 String port = ":1521"; // 포트번호
			 // 1521(기본값)
			 
			 String sid = ":XE"; // DB 이름
			 
			 String user = "kh_cy";
			 
			 String pw = "kh1234";
			 
			 // DriverManager :
			 // 메모리에 로드된 JDBC 드라이버를 이용해서
			 // Connection 객체를 만드는 역할
			 
			 								 // type + ip + port + sid = url
			 conn = DriverManager.getConnection(type + ip + port + sid, user, pw);
			 // SQLException : DB 관련 최상위 예외
			 
			 // 중간 확인
			 // System.out.println(conn);
			 // oracle.jdbc.driver.T4Connection@~~~~~~~~ 라고 출력되면 데이터베이스와 연결이 된 것
			 
			 // 2-3). SQL 작성
			 // ** JAVA에서 작성되는 SQL은 SQL문마지막에 세미콜론; 을 찍지 않아야 한다!!! **
			 
			 String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			 
			 // 2-4). Statement 객체를 생성
			 // -> Connection 객체를 통해서 생성
			 
			 stmt = conn.createStatement();
			 
			 // 2-5). 생성된 Statement 객체에 SQL을 적재해서 실행한 후
			 // 결과를 반환받아와서 rs변수에 저장
			 rs = stmt.executeQuery(sql);
			 // executeQuery : SELECT문을 수행하는 메서드로 ResultSet을 반환함
			 
			 // 3단계 : SQL을 수행해서 반환받은 결과(ResultSet)를 
			 //			한 행씩 접근해서 컬럼값을 얻어오기
			 
			 while(rs.next()) {
				 // re.next() : rs가 참조하는 ResultSet 객체의
				 //			    첫 번째 컬럼부터 순서대로 한 행씩 이동하며
				 //				다음 행이 있을 경우 true를 반환
				 //						  없을 경우 false를 반환
				 
				 // rs.get자료형("컬럼명")
				 String empId = rs.getString("EMP_ID");
				 // 현재 행의 "EMP_ID" 문자열의 컬럼값을 얻어옴
				 
				 String empName = rs.getString("EMP_NAME"); 
				 // 현재 행의 "EMP_NAME" 문자열의 컬럼값을 얻어옴
				 
				 int salary = rs.getInt("SALARY"); 
				 // 현재 행의 "Salary"  문자열의 컬럼값을 얻어옴
				 
				 // java.sql.Date를 사용할 것
				 Date hireDate = rs.getDate("HIRE_DATE");
				 // java.util.Date를 사용해도 되기는 함(jave.util이 jave.sql보다 
				 
				 // Retrieves the value of the designated column in the current rowof this ResultSet object 
				 // asa java.sql.Date object in the Java programming language

				 // 조회 결과 출력
				 // System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %d");
				 System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n",
						 	       empId, empName, salary, hireDate.toString());
				 
				 // java.sql.Data의 toString ()은 yyyy-mm-dd형식으로 오버라이딩 되어있음
			 }
		 } catch(ClassNotFoundException e) {
			 System.out.println("JDBC 드라이버 경로가 잘못 작성되었습니다.");

		 } catch(SQLException e) {
			 e.printStackTrace(); // 어떤 예외가 발생했는지 보여줌
		 
		 } finally {
			// 4단계 : 사용한 JDBC 객체의 자원을 반환 ( close() )
			// ResultSet, Statement, Connection을 닫아야 함!
			// 생성 순서는 Connection -> ResultSet 순서
			// 생성 순서와 반대로 닫아주는 것이 제일 좋음!
			 try {
				 if(rs != null) rs.close();
				 if(stmt != null) stmt.close();
				 if(conn != null) conn.close(); // 전부 정상적으로 진행됐을 경우 특정한 값을 가지고 있음
			 } catch(SQLException e) {
				 e.printStackTrace();
			 }
		 } 
	}
}
