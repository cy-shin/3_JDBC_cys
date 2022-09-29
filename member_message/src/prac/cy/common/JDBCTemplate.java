package prac.cy.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	/* DB 연결(Connection 생성), 자동 커밋 off,
	 * 트랜잭션 제어, JDBC 객체 자원 반환(close)
	 * 
	 * 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	 * 
	 * * 모든 필드, 메서드가 static *
	 * -> 어디서든지 클래스명.필드명 / 클래스명.메서드명으로 호출 가능
	 * -> 별도 객체 생성 x
	 * 
	 * 
	 * */
	
	private static Connection conn = null;
	// -> static 메서드에서 필드를 사용하기 위해서는 필드도 static 필드로 선언되어야만 한다.
	
	
	/** DB 연결 정보를 담고있는 Connection 객체 생성 및 반환 메서드 
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			// 현재 커넥션 객체가 없는 경우 -> 새 커넥션 객체를 생성함
			if(conn == null || conn.isClosed()) {
				// conn.isClosed() : Connection이 close 상태이면 true 반환

				Properties prop = new Properties();
				// Properties : Map인데 key/value가 전부 String으로 제한된 Map (Map<String, String>)
				//				XML입출력 작업에 특화되어있음
				
				// driver.xml 파일 읽어오기
				prop.loadFromXML( new FileInputStream("driver.xml") );
				// -> XML 파일에 작성된 내용이 Properties 객체에 모두 저장됨
				
				// XML에서 읽어온 값을 모두 String 변수에 저장
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				
				// 커넥션 생성
				Class.forName(driver); //Oracle JDBC Driver 객체를 메모리에 로드함
				
				// DriverManager를 이용해 Connection 객체를 생성
				conn = DriverManager.getConnection(url, user, password);
				
				// 개발자가 직접 트랜잭션을 제어할 수 있도록 자동 커밋 비활성하
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			System.out.println("[Connection 생성 중 예외 발생]");
			e.printStackTrace();
		}
		return conn;
	}
	
	/** Connection 객체 자원 반환 메서드
	 * @param conn 
	 */
	public static void close(Connection conn) {
		
		try {
			// 전달 받은 conn이 참조하는 Connection 객체가 있고
			// 그 Connection 객체가 close 상태가 아니라면
			if(conn != null && !conn.isClosed()) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/** Statement, PreparedStatement 객체 자원 반환 메서드
	 * @param conn 
	 */
	public static void close(Statement stmt) { // Overloading, 다형성, 동적 바인딩 적용 중(Statement가 PreparedStatement의 부모 클래스임) 
		
		try {
			// 전달 받은 stmt가 참조하는 Statement 객체가 있고
			// 그 Statement 객체가 close 상태가 아니라면
			if(stmt != null && !stmt.isClosed()) stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/** ResultSet 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {

		try {
			// 전달 받은 rs가 참조하는 ResultSet 객체가 있고
			// 그 ResultSet 객체가 close 상태가 아니라면
			if(rs != null && !rs.isClosed()) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/** 트랜잭션 Commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		
		try {
			// 연결되어있는 Connection 객체인 경우에만 commit 진행
			if(conn != null && !conn.isClosed()) conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** 트랜잭션 Rollback 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		
		try {
			// 연결되어있는 Connection 객체인 경우에만 rollback 진행
			if(conn != null && !conn.isClosed()) conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
