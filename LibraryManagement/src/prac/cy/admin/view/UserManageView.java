package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;

import prac.cy.admin.model.service.UserManageService;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.User;

public class UserManageView {
	UserManageService UMService = new UserManageService();
	
	
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	public void userManageMenu(String myName) {

		do {
			try {
				System.out.printf("\n| %-15s| %-5s|\n", "이용자 관리 메뉴", myName);
				System.out.println("----------------------------");
				System.out.println("1. 이용자 조회");
				System.out.println("9. 이용자 관리");
				System.out.println("0. 뒤로가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: searchUser(); break;
				case 2: break;
				case 3: break;
				case 9: break;
				case 0: input = 0; break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while (input != 0);
		
	}
	
	
	
	/**
	 *  1. 이용자 조회
	 */
	private void searchUser() {
		int condition = -1;
		
		do {
			try {
				System.out.println("\n[이용자 조회]\n");
				System.out.println("1. 전체 조회");
				System.out.println("2. 상세 조회");
				System.out.println("0. 돌아가기");
				System.out.print("\n메뉴 선택 > ");
				condition = sc.nextInt();
				sc.nextLine();
				
				switch(condition) {
				case 1: searchUserAll(); break;
				case 2: searchUserOpt(); break;
				case 0: break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
				}
				
			} catch (Exception e) {
				System.out.println("\n[알림] 이용자 조회 중 오류가 발생했습니다. \n");
			}
		} while(condition != 0);
	}




	/**
	 *  1-1. 전체 조회
	 *  @throws Exception
	 */
	private void searchUserAll() throws Exception {
		List<User> userList = UMService.searchUserAll();
		
		if(userList.isEmpty()) {
			System.out.println("\n[알림] 검색 결과가 없습니다.\n");
		} else {
			printUser(userList);
		}
		
	}

	/** 1-2. 상세 조회
	 * @throws Exception
	 */
	private void searchUserOpt() throws Exception {
		String userKeyword = "0";
		String identityName = "0";
		String statusName = "0";
		String delayOpt = "0";
		
		System.out.println("\n[검색 옵션]\n");
		System.out.print("이름 또는 아이디 옵션(Y/N) : ");
		char confirm1 = sc.nextLine().toUpperCase().charAt(0);
		if(confirm1=='Y') {
			System.out.print("이름 또는 아이디 입력 : ");
			userKeyword = sc.nextLine();
		}
		
		System.out.print("신분 옵션 사용 (Y/N) : ");
		char confirm2 = sc.nextLine().toUpperCase().charAt(0);
		if(confirm2=='Y') {
			identityList();
			System.out.print("신분 입력 : ");
			identityName = sc.nextLine();
		}
		
		System.out.print("상태 옵션 사용 (Y/N) : ");
		char confirm3 = sc.nextLine().toUpperCase().charAt(0);
		if(confirm3=='Y') {
			statusList();
			System.out.print("상태 입력 : ");
			statusName = sc.nextLine();
		}
		
		System.out.print("연체 여부 옵션 사용(Y/N) : ");
		char confirm4 = sc.nextLine().toUpperCase().charAt(0);
		if(confirm4=='Y') {
			delayOpt = "1";
			userKeyword = sc.nextLine();
		}
		
		List<User> userList = UMService.searchUserOpt(userKeyword, identityName, statusName, delayOpt);
		
		if(userList.isEmpty()) {
			System.out.println("\n[알림] 검색 결과가 없습니다.\n");
		} else {
			printUser(userList);
		}
	}
	
	/** A. 이용자 리스트 출력
	 * @param userList
	 */
	public void printUser(List<User> userList) {
		System.out.println();
		System.out.printf("%-5s|%-6s|%-6s|%-6s\n",
				"번호","이름","신분","상태");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<userList.size(); i++) {
			System.out.printf("%-5d|%-6s|%-6s|%-6s\n",
					userList.get(i).getUserNo(),
					userList.get(i).getUserName(),
					userList.get(i).getIdentityName(),
					userList.get(i).getStatusName());
		}
		System.out.println();
	}

	/** C. 이용자 리스트 출력
	 * @param userList
	 */
	public void printUserOld(List<User> userList) {
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
	
	/**
	 *  기타1: 신분 코드
	 */
	private void identityList() {
		try {
			List<User> identityList = UMService.identityList();
			System.out.printf("\n%-3s| %s\n", "기호", "신분명");
			System.out.println("------------");
			for(User u : identityList) {
				System.out.printf("%-3s | %s\n",
						u.getIdentityCode(),
						u.getIdentityName());
			}
			System.out.println("------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  기타2: 상태 코드
	 */
	private void statusList() {
		try {
			List<User> statusList = UMService.statusList();
			System.out.printf("\n%-3s| %s\n", "기호", "상태명");
			System.out.println("------------");
			for(User u : statusList) {
				System.out.printf("%-3s | %s\n",
						u.getStatusCode(),
						u.getStatusName());
			}
			System.out.println("------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
