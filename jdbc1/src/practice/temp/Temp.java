package practice.temp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Temp {
	public static void main(String[] args) {
		// 1. JDBC 참조변수를 준비
		Connection conn = null; // DB의 연결 정보를 담게될 객체의 참조변수 conn
		Statement stmt = null; // Statement는 생성된 SQL문을 DB에 전달하고, 그 결과로 만들어진 ResultSet을 받아옴
		ResultSet rs = null; // select문이 실행해서 얻어진 조회된 행들의 집합
		
		// 2. connection 객체 준비
		try { // connection 객체 준비 과정에서 사용되는 메서드 Class.forName()에 예외 발생 가능성이 있음
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 클래스의 객체를 반환되어 메모리에 생성됨 이후 JDBC가 필요할때마다 가져다 씀..
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE"; // type + ip + port + sid로 구성됨
			String user = "my_study";
			String pw = "DNJSGMD713";
			// type : JDBC 드라이버의 종류
			conn = DriverManager.getConnection(url, user, pw); // DriveManager 객체를 이용해서 Connection 객체를 생성해야 함
			
			String sql = "SELECT EMP_NAME FROM EMPLOYEE"; // sql문 생성
			
			stmt = conn.createStatement(); // conn 객체를 이용해 Statement를 생성
			rs = stmt.executeQuery(sql); // Statement를 sql을 입력받아 DB로 전달하고 ResultSet을 결과로 받아오는데
										 // 이때 executeQuery메서드를 이용해서 sql에 작성된 SELECT문을 실행함 (Select = Data Query Language)
										 // 결과는 ResultSet의 참조변수 rs에 저장함
			// rs에 저장된 컬럼에 순차접근함
			// rs.next() : 다음 행을 확인해서 있으면 true, 없으면 false를 반환함
			while(rs.next()) {
				String empName = rs.getString("EMP_NAME");
				// 자바는 낙타법, DB는 _를 이용해 구분하였음!
				// ResultSet에 저장된 컬럼에서 EMP_NAME을 가져와 변수 empName에 저장
				System.out.println("이름 : " + empName);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				// 각 참조변수에 값이 있는 경우에만 자원반환 구문을 실행함
				// 참조변수 최초 작성 순서와 역순으로 닫아주는 것이 좋음. 그렇지 않으면 데이터가 처리되기 전에 close구문이 실행돼, 데이터 손실이 발생할 수 있음
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
