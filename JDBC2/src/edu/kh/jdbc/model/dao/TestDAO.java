package edu.kh.jdbc.model.dao;

import static edu.kh.jdbc.commom.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.model.vo.TestVO;


// Data Access Object 데이터 접근 객체 : 데이터가 저장된 DB에 접근하는 객체
//										-> SQL 수행, 결과 반환 받는 기능을 수행

public class TestDAO {

	// JDBC 객체를 참조하기 위한 참조변수를 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// XML로 SQL을 다룰 예정 -> Properties 객체 사용
	private Properties prop;
	
	// 기본 생성자
	public TestDAO() {
		// 외부에 작성된 파일을 읽어와야하므로 IO & IO에 대한 IOException 예외처리 필요함
		
		// TestDAO 객체 생성 시
		// test-query.xml 파일의 내용을 읽어와
		// Properties 객체에 저장
		
		try {
			prop = new Properties(); // Properties 객체 생성
			prop.loadFromXML(new FileInputStream("test-query.xml")); //xml파일에서 읽어온 K:V 가져옴
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 1행 삽입 DAO
	 * @param conn
	 * @param vo1
	 * @return result
	 */
	public int insert(Connection conn, TestVO vo1) throws SQLException { 
		// 호출한 곳으로 발생한 SQLException을 던짐
		// 발생한 예외를 한 곳에 모아 처리하기 위해서 throws 구문을 사용함! 
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성 (test-query.xml에 작성된 QL 얻어오기 --> prop이 저장하고 있음!!)
			
			String sql = prop.getProperty("insert");
			// INSERT INTO TB_TEST VALUES(?, ?, ?)
			
			// 3.PreparedStatement 객체를 생성
			pstmt = conn.prepareStatement(sql);
			// -> throws 예외 처리 사용!			
			
			// 4. ? (placeholder)에 알맞은 값을 세팅
			pstmt.setInt(1, vo1.getTestNo());
			pstmt.setString(2, vo1.getTestTitle());
			pstmt.setString(3, vo1.getTestContent());
			
			// 5. SQL 수행 후 결과 반환 받기
			// executeQuery : resultSet 반환
			result = pstmt.executeUpdate(); // 반영된 행의 개수
			
		} finally {
			// 6. 사용한 JDBC 객체 자원을 반환 ( close() )
			// Connection은 service에서 닫을 예정이고, insert 메서드에서 사용한 PreparedStatement pstmt만 닫으면 됨
			
			close(pstmt); // JDBCTemplate의 Statement, PreparedStatement 객체 자원 반환 메서드
		}
		// 7. SQL 수행 결과 반환
		return result;
	}

	public int update(Connection conn, TestVO voUpdate) throws SQLException {
		int result = 0;
		
		try {
			String sql = prop.getProperty("update");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, voUpdate.getTestTitle());
			pstmt.setString(2, voUpdate.getTestContent());
			pstmt.setInt(3, voUpdate.getTestNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
