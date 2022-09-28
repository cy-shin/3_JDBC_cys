package prac.cy.admin.view;

import java.util.InputMismatchException;

import java.util.Scanner;

import prac.cy.admin.model.service.AdminService;
import prac.cy.basic.model.service.BasicService;
import prac.cy.basic.view.BasicView;
import prac.cy.main.view.MainView;

public class AdminView {
	
	// service
	BasicService basicService = new BasicService();
	AdminService adminService = new AdminService();
	
	// view
	BasicView basicView = new BasicView();
	BookManageView BMView = new BookManageView();
	UserManageView UMView = new UserManageView();
	
	private int input = -1;
	private Scanner sc = new Scanner(System.in);
			
	/** 관리자 메뉴
	 * 
	 */
	public void adminMenu() {
		do {
			try {
				String myName = MainView.loginUser.getUserName();
				
				System.out.print("----------------------------");
				System.out.printf("\n| %-15s| %-5s|\n", "관리자 메뉴", myName);
				System.out.println("----------------------------");
//				System.out.println("1. 공지사항");
				System.out.println("1. 도서 관리");
				System.out.println("2. 이용자 관리");
				System.out.println("0. 로그아웃");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
//				case 1: break;
				case 1: BMView.BookManageMenu(myName); break;
				case 2: UMView.userManageMenu(myName); break;
				case 0:
					MainView.loginUser = null;
					MainView.userFlag = false;
					input = 0;
					System.out.println("\n[알림] 로그아웃 되었습니다.\n");
					break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	 
				}
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while (input != 0);
	}
	
}
