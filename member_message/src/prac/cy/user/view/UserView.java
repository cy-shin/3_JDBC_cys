package prac.cy.user.view;

import java.util.Scanner;

import prac.cy.user.vo.User;

public class UserView {
	MsgView msg = new MsgView();
	
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	/**
	 *  User Main
	 */
	public User userMenu(User loginUser) {
		String myNo = loginUser.getUserNo();
		String myName = loginUser.getUserName();
		
		do {
			System.out.println("\n-----------");
			System.out.println("1. 메세지 작성 ");
			System.out.println("2. 받은 메세지");
			System.out.println("3. 보낸 메세지");
			System.out.println("4. 내 정보 ");
			System.out.println("0. 로그아웃 ");
			System.out.println("-----------");
			System.out.print("선택 > ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			case 1: write(1, myNo, myName); break;
			case 2: box(2, myNo, myName); break;
			case 3: myInfo(myNo, myName); break;
			case 0: loginUser = logout(loginUser); break;
			default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
			}
		} while(input != 0);
		return loginUser;
	}
	
	
	/** 1. 메세지 작성
	 * @param myNo
	 * @param myName
	 */
	private void write(int select, String myNo, String myName) {
		msg.msgMenu(select, myNo, myName);
	}
	
	/** 2. 메세지 보관함 보기
	 * @param myNo
	 * @param myName
	 */
	private void box(int select, String myNo, String myName) {
		msg.msgMenu(select, myNo, myName);
	}
	
	/** 3. 내 정보 확인
	 * @param myNo
	 * @param myName
	 */
	private void myInfo(String myNo, String myName) {
		System.out.println("준비중");
	}
	
	/** 0. 로그아웃
	 * @param loginUser
	 */
	private User logout(User loginUser) {
		loginUser = null;
		System.out.println("\n[알림] 로그아웃되었습니다.");
		return loginUser;
	}
}
