package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserManageView {
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	public void userManageMenu(String myName) {

		do {
			try {
				System.out.printf("\n| %-15s| %-5s|\n", "이용자 관리 메뉴", myName);
				System.out.println("----------------------------");
				System.out.println("1. 이용자 검색");
				System.out.println("2. 이용자 전체 조회");
				System.out.println("3. 연체 이용자 조회");
				System.out.println("4. 이용자 관리");
				System.out.println("0. 뒤로가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: break;
				case 2: break;
				case 3: break;
				case 4: break;
				case 0: input = 0; break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while (input != 0);
		
	}
}
