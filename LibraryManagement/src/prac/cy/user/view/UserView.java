package prac.cy.user.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.basic.view.BasicView;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;
import prac.cy.main.view.MainView;
import prac.cy.user.model.service.UserService;

public class UserView {
	
	BasicView basicView = new BasicView();
	UserService userService = new UserService();
	
	private Scanner sc = new Scanner(System.in);
	private int input = -1;
	
	/*
	 *  도서 검색
	 *  내 대출 목록
	 *  내 정보 조회
	 *  회원 탈퇴
	 *  로그아웃
	 */
	
	
	/** 이용자 메뉴
	 * 
	 */
	public void userMenu() {
		do {
			try {
				String myName = MainView.loginUser.getUserName();
				int myNo = MainView.loginUser.getUserNo();
				
				System.out.print("----------------------------");
				System.out.printf("\n| %-15s| %-5s|\n", "이용자 메뉴", myName);
				System.out.println("----------------------------");
				System.out.println("1. 도서 검색");
				System.out.println("2. 내 정보");
				System.out.println("0. 로그아웃");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: searchKeyword(); break;
				case 2: myInfoMenu(myNo); break;
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
	
		/** 
		 *  2. 도서 검색 서비스
		 */
		private void searchKeyword() {
			basicView.searchKeyword();
		}
		
		
		/**
		 *  3. 내 정보
		 */
		private void myInfoMenu(int myNo) {
			try {
				do {
					System.out.println("\n[내 정보]\n");
					printMyInfo(myNo);
					
					System.out.println("-------------");
					System.out.println("1. 정보 수정   |");
					System.out.println("2. 대출 목록   |");
					System.out.println("3. 탈퇴하기    |");
					System.out.println("0. 뒤로가기    |");
					System.out.println("-------------");
					System.out.print("\n메뉴 선택 > ");
					input = sc.nextInt();
					sc.nextLine();

					switch(input) {
					case 1: updateMyInfo(myNo); break;
					case 2: printMyLentList(myNo); break;
					case 3: deleteMe(myNo); break;
					case 0: break;
					default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
					}
				} while (input != 0);
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		}
		
		
		/** 3(1). 내 정보 출력
		 * @param myNo
		 */
		private void printMyInfo(int myNo) {
			try {
				List<User> myInfoList = userService.printMyInfo(myNo);
				
				if(myInfoList.isEmpty()) {
					System.out.println("\n[알림] 내 정보 조회 중 오류가 발생했습니다. \n");
				} else {
					System.out.println("-------------------------------------------------------------------------------------------");
					System.out.printf("회원번호 : %-5d| 이름 : %-15s| 신분 : %-6s\n", myInfoList.get(0).getUserNo(), myInfoList.get(0).getUserName(), myInfoList.get(0).getIdentityName() );
					System.out.printf("이메일 : %-15s| 전화번호 : %-15s\n", myInfoList.get(0).getUserEmail(), myInfoList.get(0).getUserPhone() );
					System.out.printf("상태 : %-6s\n", myInfoList.get(0).getStatusName());
					System.out.printf("최대권수 : %-6d| 현재대출권수 : %-6d| 대출가능권수 : %-6d\n", myInfoList.get(0).getIdentityLimit(), myInfoList.get(0).getLentNum(), myInfoList.get(0).getAvailNum());
					System.out.println("-------------------------------------------------------------------------------------------");
				}
			} catch (Exception e) {
				System.out.println("\n[알림] 내 정보 조회 중 오류가 발생했습니다. \n");
				e.printStackTrace();
			}
		}
		
		/** 3-1. 내 정보 수정
		 * @param myNo
		 */
		private void updateMyInfo(int myNo) {
			try {
				System.out.println("\n[내 정보 수정]\n");
				System.out.println("---------------------");
				System.out.println("1. 이름");
				System.out.println("2. 전화번호");
				System.out.println("3. 이메일");
				System.out.println("9. 전체수정");
				System.out.println("0. 뒤로가기");
				System.out.println("---------------------");
				System.out.print("변경할 정보 선택 : ");
				int type = sc.nextInt();
				sc.nextLine();

				switch(type) {
				case 1: case 2: case 3:
					System.out.print("내용 입력 : ");
					String edit = sc.nextLine();
					
					try {
						
						int result = userService.updateMyInfo(myNo, type, edit);
						if(result > 0) {
							System.out.println("\n[알림] 정보가 수정되었습니다. \n");
							System.out.println();
						} else {
							System.out.println("\n[알림] 입력 정보를 확인해주세요. \n");
						}
						
					} catch (Exception e) {
						System.out.println("\n[알림] 정보 수정 중 오류가 발생했습니다. \n");
						e.printStackTrace();
					}
					break;
				case 9:
					updateMyInfoAll(myNo);
					break;
				case 0: break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
				}
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		}
		
		/**
		 *  3-1(2). 정보 전체 수정
		 */
		private void updateMyInfoAll(int myNo) {
			MainLoop : while(true) {
				System.out.print("이름 : ");
				String userName = sc.nextLine();
				System.out.print("전화번호 : ");
				String userPhone = sc.nextLine().toUpperCase();
				System.out.print("이메일 : ");
				String userEmail = sc.nextLine();
				
				System.out.println("\n[입력 내용 확인]\n");
				System.out.println("--------------------------------");
				System.out.printf("이름 : %s\n전화번호 : %s\n이메일 : %s\n",
								userName, userPhone, userEmail);
				System.out.println("--------------------------------");
				
				while(true) {
					System.out.print("정보를 수정할까요? (Y/N) : ");
					char confirm = sc.nextLine().toUpperCase().charAt(0);
					if(confirm == 'Y') {
						System.out.println("\n정보를 수정하고 있습니다...");
						try {
							User user = new User();
							
							user.setUserNo(myNo);
							user.setUserName(userName);
							user.setUserPhone(userPhone);
							user.setUserEmail(userEmail);
							
							int result = userService.updateMyInfoAll(user);
							
							if(result > 0) {
								System.out.println("\n[알림] 정보가 수정되었습니다. \n");
								System.out.println();
							} else {
								System.out.println("\n[알림] 입력 정보를 확인해주세요. \n");
							}
							
						} catch (Exception e) {
							System.out.println("\n[알림] 전체 수정 작업 중 문제가 발생했습니다. 다시 시도해주세요. \n");
							e.printStackTrace();
						}
						break MainLoop;
					}
					if(confirm == 'N') {
						System.out.println("\n[알림] 작업이 취소되었습니다. \n");
						break MainLoop;
					}
					if(confirm != 'Y' && confirm != 'N') {
						System.out.println("\n[알림] Y 또는 N만 입력하세요. \n");
					}
					
				}
			}
		}
		
		/** 3-2. 내 대출 목록
		 * @param myNO
		 */
		private void printMyLentList(int myNo) {
			try {
				List<Library> myLentList = userService.printMyLentList(myNo);
				
				System.out.println("-------------------------------------------------------------------------------------------");
				System.out.printf("%-7s|%-12s|%-8s|%-10s|%-10s|%-10s\n",
						"청구기호", "제목", "이용자 이름", "대출일", "반납예정일", "반납일");
				System.out.println("-------------------------------------------------------------------------------------------");
				for(Library l : myLentList) {
					System.out.printf("%-8s|%-12s|%-8s|%-11s|%-11s|%-10s\n",
							l.getCallNo(),
							l.getBookName(),
							l.getUserName(),
							l.getLentDate(),
							l.getDueDate(),
							l.getReturnDate());
				}
				System.out.println("-------------------------------------------------------------------------------------------\n");
				System.out.println("뒤로가려면 아무 키나 입력해주세요.");
				String back = sc.nextLine();
				
			} catch (Exception e) {
				System.out.println("\n[알림] 대출 목록 조회 중 오류가 발생했습니다.");
				e.printStackTrace();
			}
			
		}
		
		/** 3-3. 탈퇴
		 * @param myNo
		 */
		private void deleteMe(int myNo) {
			String myName = MainView.loginUser.getUserName();
			System.out.println("\n[도서관 회원 탈퇴]\n");
			
			System.out.print("비밀번호 확인 : ");
			String myPw = sc.nextLine();
			try {
				int pwCheck = userService.pwCheck(myNo, myPw);
				if(pwCheck > 0) {
					System.out.println("\n정말로 탈퇴하시겠습니까?\n");
					System.out.println("탈퇴하시려면 아래 문구를 입력해주세요.");
					System.out.printf("[%s]",myName);
					System.out.print("> ");
					String checkInput = sc.nextLine();
					if(checkInput.equals(myName)) {
						System.out.println("\n떠나신다니 아쉽습니다....");
						System.out.println("\n[알림] 탈퇴를 신청하면 다시 취소할 수 없습니다.\n");
						System.out.print("\n탈퇴 신청(Y/N) : ");
						char checkAgree = sc.nextLine().toUpperCase().charAt(0);
						if(checkAgree=='Y') {
							int result = userService.deleteMe(myNo);
							if(result > 0) {
								System.out.println("\n회원 탈퇴가 완료되었습니다. 그동안 이용해주셔서 감사합니다. \n");
								input = 0;
								MainView.loginUser = null;
							} else {
								System.out.print("\n[알림] 회원 탈퇴 중 문제가 발생했습니다.");
								System.out.print("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
							}
						} else {
							System.out.println("\n탈퇴가 취소되었습니다.\n");
						}
					}
				} else {
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
			} catch (Exception e) {
				System.out.println("\n[알림] 회원 탈퇴 중 오류가 발생했습니다.");
				e.printStackTrace();
			}
			
		}
		
}
