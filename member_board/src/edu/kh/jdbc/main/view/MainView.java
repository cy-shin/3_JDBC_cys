package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.vo.Member;

// 메인 화면
public class MainView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MainService service = new MainService();
	
	/**
	 *  메인 메뉴 출력 메서드
	 */
	public void mainMenu() {
		
		int input = -1;
		
		do {
			try {
				
				System.out.println("\n***** 회원제 게시판 프로그램 *****\n");
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n메뉴 선택 : ");
				
				input = sc.nextInt();
				sc.nextLine(); // 입력버퍼에 남아있는 개행문자를 제거함
				
				System.out.println();
				
				switch(input) {
				case 1: break;
				case 2: signUP(); break;
				case 0: System.out.println("프로그램을 종료합니다. "); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요. ");
				}
			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>");
				sc.nextLine(); // 입력버퍼에 남아있는 잘못된 문자열을 제거함
			}
		} while(input != 0);
	}


	/** 2. 회원가입 화면
	 * 
	 */
	private void signUP() {
		System.out.println("[회원 가입]");
		
		// 회원가입에 사용할 변수 선언
		String memberId = null;
		
		String memberPw1 = null;
		String memberPw2 = null;
		
		String memberName = null;
		String memberGender = null;
		
		try {
			// 아이디를 입력 받아 중복이 아닐 때 까지 반복함
			while(true) {
				System.out.print("아이디 입력 : ");
				memberId = sc.next();
				
				// 입력받은 아이디를 매개변수로 전달하여
				// 중복여부를 검사하는 서비스를 호출 후 결과를 반환 받기
				int result = service.idDupCheck(memberId);
				
				// 중복이 아닌 경우
				if(result == 0) {
					System.out.println("[사용 가능한 아이디입니다.]");
					break; // while문 종료
				} else {
					System.out.println("[이미 사용중인 아이디입니다.]");
				}
			}
			
			// 비밀번호 입력
			// 비밀번호/비밀번호 확인이 일치할 때 까지 무한 반복
			
			while(true) {
				System.out.print("비밀번호 : ");
				memberPw1 = sc.next();

				System.out.print("비밀번호 확인: ");
				memberPw2 = sc.next();
				
				System.out.println();
				if(memberPw1.equals(memberPw2)) { // 일치할 경우
					System.out.println("[일치합니다.]");
					break;
				} else { // 일치하지 않을 경우
					System.out.println("[비밀번호가 일치하지 않습니다. 다시 입력해주세요.]");
				}
				System.out.println();
			}
			
			// 이름 입력
			System.out.print("이름 : ");
			memberName = sc.next();
			
			// 성별 입력
			// M/F가 입력될 때 까지 무한반복
			while(true) {
				System.out.print("성별(M/F) : ");
				memberGender = sc.next().toUpperCase(); // 입력받자마자 대문자로 변경
				
				System.out.println();
				if(memberGender.equals("M") || memberGender.equals("F")) {
					break;
				} else {
					System.out.println("[M 또는 F만 입력해주세요.]");
				}
				System.out.println();
			}
			
			// -------------- 아이디, 비밀번호, 이름, 성별 입력 완료 ------------------
			// -> 하나의 VO에 담아서 서비스를 호출 후 결과를 반환받기
			Member member = new Member(memberId, memberPw2, memberName, memberGender);
			
			int result = service.signUp(member);
			
			// 서비스 처리 결과에 따라서 출력 화면을 제어
			System.out.println();
			if(result > 0) {
				System.out.println("*****회원 가입 성공*****");
			} else {
				System.out.println("<<회원 가입 실패>>");
			}
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("\n<<회원 가입 중 예외가 발생>>");
			e.printStackTrace();
		}
	}
	
	
}
