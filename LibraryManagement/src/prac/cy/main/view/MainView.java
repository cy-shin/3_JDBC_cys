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
					case 1: searchKeyword(); break;
					case 2: login(); break;
					case 3: signUp(); break;
					case 0: System.out.println("\n[알림] 서비스를 종료합니다.\n"); break;
					default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
					}
				}
				if(loginUser != null && userFlag == true) {
					adminView.adminMenu();
				}
				if(loginUser != null && userFlag == false) {
					userView.userMenu();
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
	private void searchKeyword() {
		int input2 = -1;
		
		basicView.searchKeyword();
		do {
			System.out.println("-------------------");
			System.out.println("1. 상세조회");
			System.out.println("0. 돌아가기");
			System.out.println("-------------------");
			System.out.print("메뉴 선택 > ");
			input2 = sc.nextInt();
			sc.nextLine();
			switch(input2) {
			case 1: basicView.searchDetail(); continue;
			case 0: break;
			default : 
			}
		} while(input2!=0);
	}
	
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

		if(loginUser != null ) {
			if(loginUser.getIdentityName().equals("관리자")) {
				userFlag = true;
			}
			System.out.printf("\n%s님, 안녕하세요\n", loginUser.getUserName());
		} else {
			System.out.println("\n[알림] 아이디 또는 비밀번호가 일치하지 않습니다.\n");
		}
		} catch (Exception e) {
			System.out.println("\n[알림] 로그인 중 문제가 발생했습니다.");
			System.out.println("\n       해당 문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  3. 회원가입 수정본
	 */
	private void signUp() {
		
		String userId;
		String userPw;
		String userPw2;
		String userName;
		String userEmail;
		String userPhone;
		
		MainLoop : while(true) {
			//------------------------- 약관 ---------------------------
			while(true) {
				System.out.println("\n[회원가입]\n");
				System.out.println("[도서관 사이트 이용 약관]");
				System.out.println("1. 대출한 도서는 반드시 반납기한 내 반납");
				System.out.println("2. 도서를 분실 또는 훼손하지 않고 깨끗하게 이용");
				System.out.println("3. 다른 사람에게 아이디 또는 대출한 도서 대여 금지");
				System.out.println("---------------------------------------------------------------------");
				System.out.println("위 약관을 어길 시 도서관 사이트 이용에 제제를 받을 수 있습니다.");
				try {

					System.out.print("\n > 약관 동의(Y/N) : ");
					char confirm = sc.nextLine().toUpperCase().charAt(0);
					if(confirm =='Y') {
						break;
					}
					if(confirm =='N') {
						System.out.println("\\n[알림] 약관에 동의하지 않으면 회원가입을 진행할 수 없습니다.\n");
						break MainLoop;
					}
					if(confirm != 'Y' && confirm != 'N') {
						System.out.println("\n[알림] Y 또는 N만 입력해주세요. \n");
					}

				} catch (InputMismatchException e) {
					System.out.println("\n[알림] Y 또는 N만 입력해주세요. \n");
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("\n[알림] 내용이 입력되지 않았습니다. \n");
				}
			} 
			System.out.println();
			System.out.println("[유의사항] *이 붙은 항목은 필수 항목\n");

			// ----------------------- 아이디 --------------------------
			while(true) {

				System.out.print(" * 아이디 : ");
				userId = sc.nextLine();
				System.out.println();

				int result = mainService.duplicateName(userId);
				if(result != 0) {
					System.out.println("[알림] 중복된 아이디가 있습니다.\n");
					continue;
				}
				if(userId.equals("")) {
					System.out.println("[알림] 아이디가 입력되지 않았습니다.\n");
					continue;
				}
				break;
			}

			// ----------------------- 비밀번호 --------------------------
			while(true) {
				while(true) {
					System.out.print(" * 비밀번호 : ");
					userPw = sc.nextLine();

					if(userPw.equals("")) {
						System.out.println("[알림] 비밀번호가 입력되지 않았습니다.\n");
						continue;
					}						
					System.out.print(" * 비밀번호 확인 : ");
					userPw2 = sc.nextLine();
					System.out.println();
					break;
				}
				if(userPw.equals(userPw2)) {
					break;
				} else {
					System.out.println("비밀번호가 일치하지 않습니다.\n");
				}


			}
			String userPwBlind = "";
			for(int i=0; i<userPw.length(); i++) {
				userPwBlind += "●";
			}

			// ----------------------- 이름 --------------------------
			while(true) {
				System.out.print(" * 이름 : ");
				userName = sc.nextLine();
				System.out.println();

				if(userName.equals("")) {
					System.out.println("[알림] 이름이 입력되지 않았습니다.\n");
					continue;
				}
				break;
			}


			// ----------------------- 전화번호 --------------------------

				System.out.print("전화번호(숫자만 입력) : ");
				userPhone = sc.nextLine();
				System.out.println();


			// ----------------------- 이메일 --------------------------
			String userEmail2 = "";
			boolean emailflag = true;

			do {
				emailflag = false;
				System.out.println("[이메일 주소 형식 선택]");
				System.out.println("1. @one");
				System.out.println("2. @two");
				System.out.println("3. @three");
				System.out.println("4. @four");
				System.out.println("0. 직접 입력");
				System.out.print("> ");
				int opt = sc.nextInt();
				sc.nextLine();

				switch(opt) {
				case 1: userEmail2 = "@one"; break;	
				case 2: userEmail2 = "@two"; break;	
				case 3: userEmail2 = "@three"; break;	
				case 4: userEmail2 = "@four"; break;	
				case 0: break;
				default : emailflag = true; 
				System.out.println("잘못된 입력입니다.");
				}

			} while(emailflag);

				System.out.print("이메일 : ");
				String userEmail1 = sc.nextLine();
				if(userEmail1.equals("")) {
					userEmail2 = "";
				}

				userEmail = userEmail1 + userEmail2;

			// ----------------------- 내용 확인 --------------------------
			System.out.print("\n[입력 내용 확인]\n");
			System.out.print("--------------------------------\n");
			System.out.printf("아이디 : %s\n비밀번호 : %s\n이름 : %s\n전화번호 : %s\n이메일 : %s\n",
					userId, userPwBlind, userName, userPhone, userEmail);
			System.out.println("--------------------------------");

			while(true) {
				try {
					System.out.print("가입 신청 (Y/N) : ");
					char confirm = sc.nextLine().toUpperCase().charAt(0);
					if(confirm == 'Y') {
						User user = new User();

						user.setUserId(userId);
						user.setUserPw(userPw);
						user.setUserName(userName);
						user.setUserPhone(userPhone);
						user.setUserEmail(userEmail);

						int result;
						try {
							result = mainService.signUp(user);
							if(result > 0) {
								System.out.printf("\n%s님, 환영합니다. \n", userName);
								System.out.print("\n현재 회원님의 신분은 '일반회원'입니다.\n");
								System.out.print("\n학생 / 교수자 인증을 원하시는 경우\n"
//										+ "로그인 후 [내 정보 수정 - 신분 인증] 메뉴를 이용하시거나\n"
										+ "도서관으로 문의해주세요.\n\n");
							} else {
								System.out.println("\n[알림] 회원 가입 정보를 확인해주세요. \n");
							}
						} catch (Exception e) {
							System.out.println("\n[알림] 회원 가입 과정에서 오류가 발생했습니다. \n");
							e.printStackTrace();
						}
						break MainLoop;
					}
					if(confirm == 'N') {
						System.out.println("\n[알림] 가입이 취소되었습니다. \n");
						break MainLoop;
					}
					if(confirm != 'Y' && confirm != 'N') {
						System.out.println("\n[알림] Y 또는 N만 입력하세요. \n");
					}
				} catch (InputMismatchException e) {
					System.out.println("\n[알림] Y 또는 M만 입력해주세요.\n");
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("\n[알림] 내용이 입력되지 않았습니다. \n");
				}

			}
		}
	}

}
