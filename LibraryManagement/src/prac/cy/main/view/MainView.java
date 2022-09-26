package prac.cy.main.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.admin.view.AdminView;
import prac.cy.basic.model.service.BasicService;
import prac.cy.basic.view.BasicView;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;
import prac.cy.main.model.service.MainService;
import prac.cy.user.view.UserView;

public class MainView {
	MainService mainService = new MainService();
	
	BasicView basicView = new BasicView(); 			// 공통 기능(main, admin, user 모두 사용하는 기능)
	AdminView adminView = new AdminView(); 			// 관리자 기능
	UserView userView = new UserView(); 			// 회원 기능
	
	private Scanner sc = new Scanner(System.in); 	// 스캐너
	
	private int input = -1;							// 입력값
	
	public static User loginUser = null;			// 로그인 유저 정보
	
	public static boolean userFlag = false;			// 관리자 계정 식별
	
	/**
	 *  메인 메뉴
	 */
	public void mainMenu() {
		
		do {
			try {
				if(loginUser == null) {
					System.out.println("***** 도서관 서비스 *****");
					System.out.println("1. 도서검색");
					System.out.println("2. 로그인");
					System.out.println("3. 회원가입");
					System.out.println("0. 종료");

					System.out.print("메뉴 선택 > ");
					input = sc.nextInt();
					sc.nextLine();

					switch(input) {
					case 1: basicView.searchKeyword(); break;
					case 2: login(); break;
					case 3: break;
					case 0: System.out.println("\n[알림] 서비스를 종료합니다.\n"); break;
					default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
					}
				}
				if(loginUser != null && userFlag == true) {
					adminView.adminMenu();
				}
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n ");
				sc.nextLine();
			}
			
		} while(input != 0);
	}
	
	
	/**
	 *  1. 도서 검색 서비스 -> Basic
	 */
	
	/**
	 *  2. 로그인
	 */
	private void login() {
		System.out.println("\n[로그인]\n");
		
		System.out.print("아이디 : ");
		String userId = sc.next();
		
		System.out.print("비밀번호 : ");
		String userPw = sc.next();
		
		try {
		loginUser = mainService.login(userId, userPw);
		
		if(loginUser != null) {
			if(loginUser.getIdentityName().equals("관리자")) {
				userFlag = true;
			}
			System.out.printf("\n%s님, 안녕하세요\n", loginUser.getUserName());
		} else {
			System.out.println("\n[알림] 아이디 또는 비밀번호가 일치하지 않습니다.\n");
		}
		} catch (Exception e) {
			System.out.println("\n[알림] 로그인 중 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
}
