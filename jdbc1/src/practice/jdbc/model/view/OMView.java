package practice.jdbc.model.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import practice.jdbc.model.service.OMService;

public class OMView {
	
	private Scanner sc = new Scanner(System.in);
	OMService service = new OMService();
	
	public void displayMenu() {
		int input = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String id = "kh_cy";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, id, pw);
			
			do {
				
				System.out.println(" ====== 도서 관리 ======");
				System.out.println(" 1. 도서 검색");
				System.out.println(" 0. 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남아있는 공백을 제거함
				
				switch(input) {
				case 1 : break;
				case 0 : System.out.println("종료"); break;
				default : System.out.println("잘못된 입력");
				}

				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			
			} while(input!=0);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void bookSelect() {
		System.out.print("검색어 입력 : ");
		String input = sc.nextLine();
		
		service.bookSelect(input);
	}
	
	
}
