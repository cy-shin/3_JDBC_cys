package prac.cy.main.view;

import java.util.Scanner;

import prac.cy.main.model.service.MainService;
import prac.cy.user.UserView;

public class MainView {
	
	MainService service = new MainService();
	UserView view = new UserView();
	
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	/**
	 *  Main
	 */
	public void mainMenu() {
		try {
			do {
				
				System.out.println("-----------");
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");
				System.out.println("0. 종료");
				System.out.println("-----------");
				System.out.print("선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: login(); break;
				case 2: signUp(); break;
				case 0: System.out.println("\n[알림] 종료합니다.\n");break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
				}
			} while (input != 0);
		} catch (Exception e) {
			System.out.println("종료하시려면 0을 입력해주세요");
		}
	}
	
	/** 
	 *  Main 1. 로그인
	 */
	private void login() {
		System.out.println("없음");
	}
	
	/**
	 *  Main 2. 회원가입
	 */
	private void signUp() {
		try {
			String userId = "";
			String userPw = "";
			String userName = "";
			
			System.out.println("\n[회원가입]");
			System.out.println("--------------");
			
		LoopId : while(true) { // 아이디 작성
				boolean flag = true;
				System.out.print("아이디 : ");
				userId = sc.nextLine();
				for(int i=0; i<userId.length(); i++) {
					char check = userId.charAt(i);
					if ((userId.length() > 20) || (!(90 >= check && check >=65) && !(122 >= check && check >= 97) && !(57 >= check && check >= 48))) {
						System.out.println("\n[알림] 아이디는 20자리 이내의 영문자와 숫자의 조합으로만 작성할 수 있습니다. \n");
						flag = false;
						break;
					}
				}
				if(flag) break LoopId;
			}

		LoopPw : while(true) { // 비밀번호 작성
			boolean flag = true;
			System.out.print("비밀번호 : ");
			userPw = sc.nextLine();
			System.out.print("비밀번호 확인 : ");
			String userPw2 = sc.nextLine();
			if(userPw != userPw2) {
				System.out.println("비밀번호가 일치하지 않습니다. ");
				continue LoopPw;
			}
			break;
		}
		
		
		LoopName : while(true) { // 이름 작성
			boolean flag = true;
			System.out.print("이름 : ");
			userName = sc.nextLine();
			for(int i=0; i<userName.length(); i++) {
				char check = userName.charAt(i);
				if ((userName.length() > 10) || (!(90 >= check && check >=65) && !(122 >= check && check >= 97) && !(57 >= check && check >= 48))) {
					System.out.println("\n[알림] 이름은 10자리 이내의 영문자와 숫자의 조합으로만 작성할 수 있습니다. \n");
					flag = false;
					break;
				}
			}
			if(flag) break LoopName;
		}
		
		System.out.println("\n[입력한 정보 확인]");
		System.out.println("---------------------");
		System.out.printf("아이디 : %s", userId);
		System.out.printf("이름 : %s", userName);
		
		LoopYN : while(true) {
			System.out.print("회원 가입 진행[Y/N] : ");
			char agree = sc.nextLine().toUpperCase().charAt(0);
			if(agree == 'Y') {
					int checkResult = service.checkDuplicate(userName, userId);
					if(checkResult == 0) {
						System.out.println("중복되는 아이디가 있습니다.");
					}
					int signUpResult = service.signUp(userName, userPw, userId);
				break;
			}
			if(agree == 'N') {
				System.out.println("\n[알림] 가입 취소\n");
				break;
			}
			if(agree != 'Y' && agree != 'N') {
				System.out.println("\n[알림] Y 또는 N만 입력해주세요.\n");
				continue;
			}
		}
				
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 회원가입 과정 \n");
			e.printStackTrace();
		}
	}
}
