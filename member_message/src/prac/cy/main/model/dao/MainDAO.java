package prac.cy.main.model.dao;

import static prac.cy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class MainDAO {

	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	
	Properties prop;
	
	
	public MainDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
	}	
	
	/** 2. 회원가입
	 * @param conn
	 * @param userName
	 * @param userPw
	 * @param userId
	 * @return signUpResult
	 *    0   :  중복
	 *    1   :  성공
	 * @throws Exception
	 */
	public int signUp(Connection conn, String userName, String userPw, String userId) throws Exception {
		int signUpResult = 0;
		try {
			String sql = prop.getProperty("createUser");
			
		} finally {

		}
		return signUpResult;
	}
}
