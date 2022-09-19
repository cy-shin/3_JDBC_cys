package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LoadXMLFile {
	public static void main(String[] args) {
		
		// XML 파일 읽어오기(Properties, FileInputStream)
		
		try {
			Properties prop = new Properties(); // Map<String, String>
			
			// driver.xml 파일을 읽어오기 위한 InputStream 객체 생성
			FileInputStream fis = new FileInputStream("driver.xml");
			
			// 연결된 driver.xml 파일에 있는 내용을 모두 읽어와
			// Properties 객체(prop)에 K:V 형식으로 저장
			prop.loadFromXML(fis);
			
			System.out.println(prop);
			
			// Property : 속성(데이터)
			
			// prop.getProperty("key") : key가 일치하는 속성(데이터)를 얻어옴
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			
			System.out.println();
			
			
			// driver.xml 파일에서 읽어온 값들을 이용해 Connection 생성
			Class.forName(driver); // oracle.jdbc.driver.OracleDriver
			Connection conn = DriverManager.getConnection(url, user, password);
			
			System.out.println(conn);
			
			/*	왜 XML파일을 이용해서 DB연결 정보를 읽어와야될까?
			 *  
			 *  1. 코드 중복 제거
			 *  
			 *  2. 별도 관리 용도(유지보수, 관리 용이) 
			 *  	- 별도 파일을 이용해서 수정이 용이함 
			 *  
			 *  3. 재 컴파일(코드를 기계어로 번역하는 작업)을 진행하지 않기 위해서
			 * 		- 코드가 길수록 컴파일에 소요되는 시간이 크다
			 * 		-> 코드 수정으로 인한 컴파일 소요시간을 없앰
			 * 		-> 자바에는 파일의 내용을 읽는 코드만 작성 
			 * 		-> 자바는 파일을 읽어오기만 하기 때문에, xml코드를 수정해도 재 컴파일 등의 작업이 없음
			 * 		-> 만약 자바에 직접 계정 정보를 작성하면, 정보를 수정할 때 마다 재 컴파일을 해야함..
			 * 
			 *  4. xml에 작성된 문자열을, 작성된 형태 그대로 읽어오기 때문에 SQL 작성 시 편리해짐
			 *  
			 * */
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
