package edu.kh.jdbc.run;

import java.util.Scanner;

import edu.kh.jdbc.model.service.TestService;
import edu.kh.jdbc.model.vo.TestVO;

public class Run3 {
	// DBeaver와 JAVA에서 트랜잭션 충돌이 발생할 수 있음(무기한 대기 상태가 됨)
	public static void main(String[] args) {
		// 번호, 제목, 내용을 입력받아 번호가 일치하는 행의 제목, 내용을 수정
		
		// 수정 성공 시 -> 수정되었습니다.
		// 수정 실패 시 -> 일치하는 번호가 없습니다.
		// 예외 발생 시 -> 수정 중 예외가 발생했습니다.
		
		Scanner sc = new Scanner(System.in);
		
		// 1. service 객체를 생성함 
		// service 클래스는 실제 프로그램이 제공하는 기능을 모아둔 클래스임
		TestService service = new TestService();
		
		// 2. 사용자의 요청을 받음
		System.out.println("번호, 제목, 내용을 입력하세요");
		System.out.print("번호 > ");
		int num = sc.nextInt();
		sc.nextLine();
		
		System.out.print("수정할 제목 > ");
		String inputTitle = sc.nextLine();
		
		System.out.print("수정할 내용 > ");
		String inputContent = sc.nextLine();
		
		try {
			// 3. 요청을 받아서 Service 객체의 Update 메서드로 전달함
			
			// 3-1. 요청을 처리하기 위해서 VO의 매개변수 생성자를 활용해보려고 함
			//      Q. 매개변수 생성자를 거치지 않고 할 수도 있을까?
			TestVO voUpdate = new TestVO(num, inputTitle, inputContent);
			
			int result = service.update(voUpdate);
			// 3-1. 현재 작업은 수정 작업으로 DML에 해당함
			//		DML은 반영된 행의 개수를 반환하므로 결과를 int타입에 변수에 저장하자!
			
			
			// 반환받은 결과에 따라 성공, 실패, 예외 발생으로 나누어서 처리함
			if(result == 1) System.out.println("수정되었습니다."); // 4-1. 성공 시
			else System.out.println("일치하는 번호가 없습니다."); // 4-2. 실패 시
			
		} catch (Exception e) {
			// 예외 발생 시
			System.out.println("수정 중 예외가 발생했습니다.");
			e.printStackTrace();
		}
		
	}
}
