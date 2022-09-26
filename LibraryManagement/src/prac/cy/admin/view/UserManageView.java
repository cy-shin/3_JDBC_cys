package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.library.vo.User;

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
				System.out.println("3. 이용자 상세 조회");
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
	
	
	/** A. 이용자 전체 조회
	 * @param userList
	 */
	public void printUser(List<User> userList) {
		System.out.println();
		System.out.printf("%-5s|%-15s|%-6s|%-6s|%-6s|%-6s|%-6s|%-6s\n",
				"번호","아이디","이름","신분","상태","최대권수","대출권수","잔여권수");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<userList.size(); i++) {
			System.out.printf("%-5d|%-15s|%-6s|%-6s|%-6s|%-6d|%-6d|%-6d\n",
					userList.get(i).getUserNo(),
					userList.get(i).getUserId(),
					userList.get(i).getUserName(),
					userList.get(i).getIdentityName(),
					userList.get(i).getStatusName(),
					userList.get(i).getIdentityLimit(),
					userList.get(i).getLentNum(),
					userList.get(i).getAvailNum());
		}
		System.out.println();
	}
	
	/** B. 유저 상세 조회용
	 * 
	 */
	public void printDetailUser(List<User> userList) {
		System.out.println("----------------------------------------------------------------------------");
		System.out.printf("회원번호 : %-5d| 이름 : %-15s| 신분 : %-6s\n",userList.get(0).getUserNo(), userList.get(0).getUserName(), userList.get(0).getIdentityName() );
		System.out.printf("상태 : %-6s\n", userList.get(0).getStatusName());
		System.out.printf("최대권수 : %-6s| 현재대출권수 : %-6s| 대출가능권수 : %-6s\n", userList.get(0).getIdentityLimit(), userList.get(0).getLentNum(), userList.get(0).getAvailNum());
		System.out.println("----------------------------------------------------------------------------");
	}
}
