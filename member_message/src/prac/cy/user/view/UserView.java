package prac.cy.user.view;

import java.util.Scanner;

import prac.cy.user.vo.User;

public class UserView {
//	MsgView msg = new MsgView();
//	
//	private Scanner sc = new Scanner(System.in);
//	private String input = "-1";
//	
//	/**
//	 *  User Main
//	 */
//	public User userMenu(User loginUser) {
//		String myNo = loginUser.getUserNo();
//		String myName = loginUser.getUserName();
//		
//		do {
//			msg.msgMenu(3, myNo, myName);
//			
//			System.out.println("\n-----------");
//			System.out.println("1. 메세지 작성 ");
//			System.out.println("2. 전체 메세지");
//			System.out.println("3. 받은 메세지"); // 기본값
//			System.out.println("4. 보낸 메세지");
//			System.out.println("5. 내 정보 ");
//			System.out.println("0. 로그아웃 ");
//			System.out.println("-----------");
//			System.out.print("선택 > ");
//			input = sc.nextLine();
//			
//			switch(input) {
//			case "1": write(1, myNo, myName); break;
//			case "2": boxRecd(2, myNo, myName); break;
//			case "3": boxSend(3, myNo, myName); break;
//			case "4": myInfo(4, myNo, myName); break;
//			case "0": loginUser = logout(loginUser); break;
//			default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
//			}
//		} while(!(input.equals("0")));
//		return loginUser;
//	}
//	
//	
//	/** 1. 메세지 작성
//	 * @param myNo
//	 * @param myName
//	 */
//	private void write(int select, String myNo, String myName) {
//		msg.msgMenu(select, myNo, myName);
//	}
//	
//	/** 2. 전체 메세지
//	 * @param myNo
//	 * @param myName
//	 */
//	private void boxAll(int select, String myNo, String myName) {
//		msg.msgMenu(select, myNo, myName);
//	}
//	
//	/** 3. 받은 메세지
//	 * @param myNo
//	 * @param myName
//	 */
//	private void boxRecd(int select, String myNo, String myName) {
//		msg.msgMenu(select, myNo, myName);
//	}
//	
//	/** 4. 보낸 메세지
//	 * @param myNo
//	 * @param myName
//	 */
//	private void boxSend(int select, String myNo, String myName) {
//		msg.msgMenu(select, myNo, myName);
//	}
//	
//	/** 5. 내 정보 확인
//	 * @param myNo
//	 * @param myName
//	 */
//	private void myInfo(int select, String myNo, String myName) {
//		msg.msgMenu(select, myNo, myName);
//	}
//	
//	/** 0. 로그아웃
//	 * @param loginUser
//	 */
//	private User logout(User loginUser) {
//		loginUser = null;
//		System.out.println("\n[알림] 로그아웃되었습니다.");
//		return loginUser;
//	}
}
