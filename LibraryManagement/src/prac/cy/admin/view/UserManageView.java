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
	
	/** 이용자 관리 메인 메뉴
	 * @param myName
	 */
	public void userManageMenu(String myName) {

		do {
			try {
				System.out.printf("\n| %-15s| %-5s|\n", "이용자 관리 메뉴", myName);
				System.out.println("----------------------------");
				System.out.println("1. 이용자 조회");
//				System.out.println("2. 인증 관리");
				System.out.println("0. 뒤로가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: searchUserMenu(); break;
//				case 9: updateUserMenu(); break;
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
	private void searchUserMenu() {
		int condition = -1;
		
		do {
			try {
				System.out.println("\n[이용자 조회]\n");
				System.out.println("1. 전체 조회");
				System.out.println("2. 조회 옵션 설정");
				System.out.println("0. 돌아가기");
				System.out.print("\n메뉴 선택 > ");
				condition = sc.nextInt();
				sc.nextLine();
				
				switch(condition) {
				case 1: searchUser(condition); break;
				case 2: searchUser(condition); break;
				case 0: break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
				}
				
			} catch (Exception e) {
				System.out.println("\n[알림] 이용자 조회 중 오류가 발생했습니다. \n");
				e.printStackTrace();
			}
		} while(condition != 0);
	}
	
	/** 1-1 or 1-2
	 * @param condition
	 *   1 : 전체 조회
	 *   2 : 옵션 조회
	 * @throws Exception
	 */
	private void searchUser(int condition) throws Exception {
		String userKeyword = "0";
		String identityName = "0";
		String statusName = "0";
		String delayOpt = "0";
		
		if(condition == 2) {
		
			System.out.println("\n[조회 옵션 설정]\n");
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
		
		}
		
		List<User> userList = UMService.searchUser(userKeyword, identityName, statusName, delayOpt);
		
		if(userList.isEmpty()) {
			System.out.println("\n[알림] 검색 결과가 없습니다.\n");
			searchUserMenu();
		} else {
			printUser(userList);
			searchUserSubMenu();
			searchUserMenu();
		}
		
	}
	
	/**
	 *  조회 화면 서브 메뉴
	 */
	private void searchUserSubMenu() {
		do {
			try {
				System.out.println("1. 상세 조회");
				System.out.println("0. 돌아가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: searchUserDetail(); break;
				case 0: input = -1; break;
				}
				
			} catch (Exception e) {
				System.out.println("\n[알림] 이용자 조회 중 오류가 발생했습니다. \n");
				e.printStackTrace();
			}
		} while(input != 0);
	}
	
	/**  sub - 1. 상세 조회
	 * 
	 */
	private void searchUserDetail() {
		System.out.print("이용자 번호 입력 : ");
		int userNo = sc.nextInt();
		sc.nextLine();
		
		try {
			List<User> userList = UMService.searchUserDetail(userNo);


			if(userList.isEmpty()) {
				System.out.println("\n[알림] 검색 결과가 없습니다.\n");
			} else {
				printDetailUser(userList);
				searchUserDetailSubMenu(userList.get(0).getUserNo());
			}
		
		} catch (Exception e) {
			System.out.println("\n[알림] 조회 중 오류가 발생했습니다. \n");
		}
	}
	
	/** 상세 조회 화면 서브 메뉴
	 * 
	 */
	private void searchUserDetailSubMenu(int userNo) {
		do {
				System.out.println("1. 정보 수정");
				System.out.println("0. 돌아가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: updateUser(userNo); break;
				case 0: input = -1; break;
				}
				
		} while(input != 0);
	}
	
	/**
	 *  정보 수정 메뉴
	 */
	private void updateUser(int userNo) {
		int condition = 0;
		do {
			try {
				while(true) {
					System.out.println("1. 상태 변경");
					System.out.println("2. 신분 변경");
					System.out.println("0. 돌아가기");
					System.out.print("\n메뉴 선택 > ");
					condition = sc.nextInt();
					sc.nextLine();
					switch(condition) {
					case 1:
						statusList();
						break;
					case 2:	
						identityList();
						break;
					default : 
						System.out.println("\n[알림] 메뉴에 있는 정보만 수정가능합니다. \n");
						continue;
					}
					break;
				}
				// 현재 정보를 출력하기
				System.out.print("수정할 값 입력 : ");
				String edit = sc.nextLine();
				
				int result = UMService.updateUser(condition, edit, userNo);
				
				if(result > 0) {
					System.out.println("\n[알림] 정보가 수정되었습니다. \n");
				} else {
					System.out.println("\n[알림] 상태 코드 또는 신분 코드를 확인해주세요. \n");
				}
				
			} catch (Exception e) {
				System.out.println("\n[알림] 정보 수정 중 오류가 발생했습니다. \n");
				e.printStackTrace();
			}
		} while(input != 0);
	}
	
	
	/** A. 이용자 리스트 출력
	 * @param userList
	 */
	public void printUser(List<User> userList) {
		System.out.println();
		System.out.printf("%-5s|%-6s|%-6s|%-6s|%-6s|%-6s|%-6s\n",
				"번호","이름","신분","상태","최대권수","대출권수","잔여권수");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<userList.size(); i++) {
			System.out.printf("%-5d|%-6s|%-6s|%-6s|%-6d|%-6d|%-6d\n",
					userList.get(i).getUserNo(),
					userList.get(i).getUserName(),
					userList.get(i).getIdentityName(),
					userList.get(i).getStatusName(),
					userList.get(i).getIdentityLimit(),
					userList.get(i).getLentNum(),
					userList.get(i).getAvailNum());
		}
		System.out.println();
	}
	
	/** 유저 상세 정보 출력
	 * 
	 */
	public void printDetailUser(List<User> userList) {
		System.out.println("----------------------------------------------------------------------------");
		System.out.printf("회원번호 : %-5d| 이름 : %-15s| 신분 : %-6s\n",userList.get(0).getUserNo(), userList.get(0).getUserName(), userList.get(0).getIdentityName() );
		System.out.printf("이메일 : %-15d| 전화번호 : %-15s\n",userList.get(0).getUserEmail(), userList.get(0).getUserPhone() );
		System.out.printf("상태 : %-6s\n", userList.get(0).getStatusName());
		System.out.printf("최대권수 : %-6s| 현재대출권수 : %-6s| 대출가능권수 : %-6s\n", userList.get(0).getIdentityLimit(), userList.get(0).getLentNum(), userList.get(0).getAvailNum());
		System.out.println("----------------------------------------------------------------------------");
	}

	
	
	/**
	 *  기타2: 신분 코드
	 */
	private void identityList() {
		try {
			List<User> identityList = UMService.identityList();
			System.out.printf("\n%-3s| %s\n", "기호", "신분명");
			System.out.println("------------");
			for(User u : identityList) {
				if(u.getIdentityCode().equals("S0")) continue;
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
	 *  기타1: 상태 코드
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
	
	/** 외부. 유저 상세 조회용 for Book
	 * 
	 */
	public void printOneUser(List<User> userList) {
		System.out.println("----------------------------------------------------------------------------");
		System.out.printf("회원번호 : %-5d| 이름 : %-15s| 신분 : %-6s\n",userList.get(0).getUserNo(), userList.get(0).getUserName(), userList.get(0).getIdentityName() );
		System.out.printf("상태 : %-6s\n", userList.get(0).getStatusName());
		System.out.printf("최대권수 : %-6s| 현재대출권수 : %-6s| 대출가능권수 : %-6s\n", userList.get(0).getIdentityLimit(), userList.get(0).getLentNum(), userList.get(0).getAvailNum());
		System.out.println("----------------------------------------------------------------------------");
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
}
