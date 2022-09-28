package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.admin.model.service.BookManageService;
import prac.cy.admin.model.service.UserManageService;
import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;

public class UserManageView {
	UserManageService UMService = new UserManageService();
	BookManageService BMService = new BookManageService();
	
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
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요.\n");
				sc.nextLine();
			}
		} while (input != 0);
		
	}
	
	/**
	 *  1. 이용자 조회(1)
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
				
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while(condition != 0);
	}
	
	/** 1. 이용자 조회(2)
	 * @param condition
	 *   1 : 전체 조회
	 *   2 : 옵션 조회
	 * @throws Exception
	 */
	private void searchUser(int condition) {
		String userKeyword = "0";
		String identityName = "0";
		String statusName = "0";
		String delayOpt = "0";
		try {
			

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
				}

			}

			List<User> userList = UMService.searchUser(userKeyword, identityName, statusName, delayOpt);

			if(userList.isEmpty()) {
				System.out.println("\n[알림] 검색 결과가 없습니다.\n");
			} else {
				printUser(userList);
				searchUserSubMenu();
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 이용자 조회 중 오류가 발생했습니다.\n");
		}
		
	}
	
	/**
	 *  1-A. 이용자 조회 화면 서브 메뉴
	 */
	private void searchUserSubMenu() {
		do {
			try {
				System.out.println("-------------");
				System.out.println("1. 상세 조회   |" );
				System.out.println("0. 돌아가기    |");
				System.out.println("-------------");
				System.out.print("메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: searchUserDetail(); break;
				case 0: break;
				}
				
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while(input != 0);
	}
	
	/** 2. 상세 조회
	 * 
	 */
	private void searchUserDetail() {
		System.out.print("이용자 번호 입력 : ");
		int userNo = sc.nextInt();
		sc.nextLine();
		
		try {
			List<User> userList = UMService.searchUserDetail(userNo);


			if(userList.isEmpty() || (userList.get(0).getIdentityName().equals("관리자"))) {
				System.out.println("\n[알림] 검색 결과가 없습니다.\n");
			} else {
					printDetailUser(userList);
					userNo = userList.get(0).getUserNo();
					searchUserBookList(userNo);
					searchUserDetailSubMenu(userList);
			}
		
		} catch (Exception e) {
			System.out.println("\n[알림] 조회 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
	}
	
	/** 2-A. 상세 조회 화면 서브 메뉴
	 * 
	 */
	private void searchUserDetailSubMenu(List<User> userList) {
		try {
			do {
				System.out.println("-------------");
				System.out.println("1. 정보 수정   |");
				System.out.println("0. 돌아가기    |");
				System.out.println("-------------");
				System.out.print("메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();

				switch(input) {
				case 1: updateUser(userList); break;
				case 0: break;
				}

			} while(input != 0);
		} catch (InputMismatchException e) {
			System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
			sc.nextLine();
		}
	}
	
	/**
	 * 	2-B(1) . 상세 조회 중 특정 이용자의 대출 목록 출력
	 */
	public void searchUserBookList(int userNo) {
		try {
			List<Library> lentList = BMService.searchUserBookList(userNo);
			printlentList(lentList);
		} catch (Exception e) {
			System.out.println("\n[알림] 유저 조회 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
	}
	
	
	/** 2-B(1) . 상세 조회 중 특정 이용자의 대출 목록 출력
	 * @param lentList
	 */
	public void printlentList(List<Library> lentList) {
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.printf("%-5s|%-7s|%-12s|%-8s|%-8s|%-10s|%-10s|%-10s\n",
				"번호", "청구기호", "제목", "이용자 번호", "이용자 이름", "대출일", "반납예정일", "반납일");
		System.out.println("-------------------------------------------------------------------------------------------");
		for(Library l : lentList) {
			System.out.printf("%-5d|%-8s|%-12s|%-9s|%-8s|%-11s|%-11s|%-10s\n",
					l.getBookNo(),
					l.getCallNo(),
					l.getBookName(),
					l.getUserNo(),
					l.getUserName(),
					l.getLentDate(),
					l.getDueDate(),
					l.getReturnDate());
		}
		System.out.println("-------------------------------------------------------------------------------------------\n");
	}
	
	/**
	 *  3. 정보 수정 메뉴
	 */
	private void updateUser(List<User> userList) {
		int condition = 0;
		MainLoop : do {
			try {
				while(true) {
					System.out.println("-------------");
					System.out.println("1. 상태 변경   |");
					System.out.println("2. 신분 변경   |");
					System.out.println("0. 돌아가기    |");
					System.out.println("-------------");
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
					case 0 :
						break MainLoop;
					default : 
						System.out.println("\n[알림] 메뉴에 있는 정보만 수정가능합니다.\n");
						continue;
					}
					break;
				}
				// 현재 정보를 출력하기
				System.out.print("수정할 값 입력 : ");
				String edit = sc.nextLine();
				try {
					int result = UMService.updateUser(condition, edit, userList.get(0).getUserNo());
					if(result > 0) {
						System.out.println("\n[알림] 정보가 수정되었습니다. \n");
						userList = UMService.searchUserDetail(userList.get(0).getUserNo());
						printDetailUser(userList);//
						break MainLoop;
					} else {
						System.out.println("\n[알림] 상태 코드 또는 신분 코드를 확인해주세요.\n");
					}
				} catch (Exception e) {
					System.out.println("\n[알림] 유저 조회 중 오류가 발생했습니다.\n");
					e.printStackTrace();
				}
				
				
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while(condition != 0);
	}
	
	
	/** A. 이용자 리스트 출력
	 * @param userList
	 */
	public void printUser(List<User> userList) {
		System.out.println();
		System.out.printf("%-5s|%-6s|%-6s|%-6s|%-6s|%-6s|%-6s\n",
				"번호","이름","신분","상태","최대권수","대출권수","잔여권수");
		System.out.println("-------------------------------------------------------------------------------------------");
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
	
	/** B. 이용자 정보 상세 정보 출력
	 * 
	 */
	public void printDetailUser(List<User> userList) {
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.printf("회원번호 : %-5d| 이름 : %-15s| 신분 : %-6s\n",userList.get(0).getUserNo(), userList.get(0).getUserName(), userList.get(0).getIdentityName() );
		System.out.printf("이메일 : %-15s| 전화번호 : %-15s\n",userList.get(0).getUserEmail(), userList.get(0).getUserPhone() );
		System.out.printf("상태 : %-6s\n", userList.get(0).getStatusName());
		System.out.printf("최대권수 : %-6d| 현재대출권수 : %-6d| 대출가능권수 : %-6d\n", userList.get(0).getIdentityLimit(), userList.get(0).getLentNum(), userList.get(0).getAvailNum());
		System.out.println("-------------------------------------------------------------------------------------------");
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
	
	/** 기타3 : 유저 상세 조회용 for Book
	 * 
	 */
	public void printOneUser(List<User> userList) {
		System.out.println("----------------------------------------------------------------------------");
		System.out.printf("회원번호 : %-5d| 이름 : %-15s| 신분 : %-6s\n",userList.get(0).getUserNo(), userList.get(0).getUserName(), userList.get(0).getIdentityName() );
		System.out.printf("상태 : %-6s\n", userList.get(0).getStatusName());
		System.out.printf("최대권수 : %-6s| 현재대출권수 : %-6s| 대출가능권수 : %-6s\n", userList.get(0).getIdentityLimit(), userList.get(0).getLentNum(), userList.get(0).getAvailNum());
		System.out.println("----------------------------------------------------------------------------");
	}
	
	
}
