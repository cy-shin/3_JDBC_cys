package prac.cy.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import prac.cy.main.model.service.MainService;
import prac.cy.user.view.MsgView;
import prac.cy.user.vo.User;

public class MainView {
	
	MainService service = new MainService();
	MsgView view = new MsgView();
	private User loginUser;
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	/**
	 *  Main
	 */
	public void mainMenu() {
		do {
			try {
				if(loginUser == null) {
					System.out.println("\n-----------");
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
				}
				if(loginUser != null) {
					loginUser = view.msgMenu(3, loginUser);
				}
			} catch (InputMismatchException e) {
				System.out.println("종료하시려면 0을 입력해주세요");
				sc.nextLine();
			}
		} while (input != 0);
	}
	
	/** 
	 *  Main 1. 로그인
	 */
	private void login() {
		boolean loopFL = true;
		try {
			String userId;
			String userPw;
				System.out.println("\n[로그인]");
				System.out.print("아이디 : ");
				userId = sc.nextLine();
				
				System.out.print("비밀번호 : ");
				userPw = sc.nextLine();
				
				User user = service.login(userId, userPw); // 유저 정보를 얻어옴
			
			
				if(user!=null) {
					service.loginDate(user.getUserNo()); // 유저 시간 업데이트
					loginUser = new User();
					
					loginUser.setUserNo(user.getUserNo());
					loginUser.setUserName(user.getUserName());
					System.out.printf("\n%s님, 환영합니다.\n", user.getUserName());
					loopFL = false;
					
				}
				if(loopFL) {
					System.out.println("\n[알림] 아이디 또는 비밀번호가 일치하지 않습니다. \n");
				}
				
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 로그인 과정 \n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  Main 2. 회원가입
	 */
	private void signUp() {
		try {
			String userId = "";
			String userPw = "";
			String userName = "";
			String userNo = "";
			
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
			System.out.print("비밀번호 : ");
			userPw = sc.nextLine();
			System.out.print("비밀번호 확인 : ");
			String userPw2 = sc.nextLine();
			if(!(userPw.equals(userPw2))) {
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
		System.out.printf("아이디 : %s\n", userId);
		System.out.printf("이름 : %s\n", userName);
		
		while(true) { // LoopYN
			System.out.print("회원 가입 진행[Y/N] : ");
			char agree = sc.nextLine().toUpperCase().charAt(0);
			System.out.println("---------------------");
			if(agree == 'Y') {
					int idNameCheck = service.checkDuplicate(userName, userId);
					if(idNameCheck == 0) {
						System.out.println("중복되는 아이디 또는 이름이 있습니다.");
					} else {
						while(true) {
							int temp = (int)(Math.random() * 9000000) + 1000000;
							String ran = temp + "";
							int noCheck = service.makeUserNo(ran);
							if(noCheck > 0)	{
								userNo = ran;
								break;
							}
						}
						int signUpResult = service.signUp(userNo, userName, userPw, userId);
						
						if(signUpResult > 0) {
							System.out.println("\n[알림] 회원가입이 완료되었습니다. \n");
						} else {
							System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
							System.out.println("\n[위치] 회원가입 과정 \n");
						}
					}
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
