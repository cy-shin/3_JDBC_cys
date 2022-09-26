package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.admin.model.service.AdminService;
import prac.cy.admin.model.service.BookManageService;
import prac.cy.admin.model.service.UserManageService;
import prac.cy.basic.view.BasicView;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.Library;
import prac.cy.library.vo.User;

public class BookManageView {
	private int input = -1;
	private Scanner sc = new Scanner(System.in);
	
	BasicView basicView = new BasicView();
	UserManageView UMView = new UserManageView();
	
	BookManageService BMService = new BookManageService();
	UserManageService UMService = new UserManageService();
	
	AdminService adminService = new AdminService(); 
	
	public void BookManageMenu(String myName) {
		do {
			input = -1;
			try {
				System.out.println("-------------------------------------------------------------------------------------------");
				System.out.printf("\n| %-15s| %-5s|\n", "도서 관리 메뉴", myName);
				System.out.println("----------------------------");
				System.out.println("1. 도서 검색");
				System.out.println("2. 도서 전체 조회");
				System.out.println("3. 연체 도서 조회");
				System.out.println("4. 도서 대출 반납");
				System.out.println("5. 도서 신규 등록");
				System.out.println("6. 도서 상태 변경");
				System.out.println("0. 뒤로가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: basicView.keywordSearch(); break;
				case 2: searchAll(); break;
				case 3: searchOverdue(); break;
				case 4: bookLentReturn(); break;
				case 5:  break;
				case 6:  break;
				case 0: input = 0; break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
				}
			} catch (InputMismatchException e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while (input != 0);
		
	}
	
	// 1. 도서 검색 서비스
	
	/**
	 *  2. 도서 전체 조회 서비스
	 */
	private void searchAll() {
		System.out.println("\n[도서 전체 조회]\n");
		
		System.out.println("\n검색중...\n");
		try {
			List<Book> bookList = BMService.searchAll();
			
			if(bookList.isEmpty()) {
				System.out.println("[알림] 검색 결과가 없습니다.");
			} else {
				printBook(bookList);
			}
			
		} catch(Exception e) {
			System.out.println("\n[알림] 도서를 조회하는 과정에서 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  3. 연체 도서 조회 서비스
	 */
	private void searchOverdue() {
		try {
			List<Library> overDueList = BMService.searchOverdue(-1);
			
			printOverdue(overDueList);
			
		} catch (Exception e) {
			System.out.println("\n[알림] 도서를 조회하는 과정에서 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  4. 도서 대출 / 반납 서비스
	 */
	private void bookLentReturn() {
		try {
			// 6. 만약 연체중인 책이 있는 경우, 대출 불가 경고 메세지 띄움
			// 7. 만약 대출가능권수를 초과한 경우, 대출 불가 경고 메세지 띄움
			// 8. 대출 처리 시 반납기한 띄움
			
			// 1. 청구기호 입력해서 도서 정보 불러옴(청구기호, 책이름, 책대출가능여부 등)
			System.out.print("도서 청구 기호 : ");
			String callNo = sc.next();
			List<Book> book = BMService.bookInfo(callNo);
			if(book.isEmpty()) {
				System.out.println("\n[알림] 해당 도서를 찾을 수 없습니다. 청구 기호를 확인해주세요.\n");
			}
			if(!(book.isEmpty())) {
				printBook(book);
				String avail = book.get(0).getAvailName(); // 도서 상태를 가져옴
				
				switch(avail) {
				case "대출가능" :
					bookLent(book.get(0).getBookNo());
					break;
				case "대출중" : // 반납처리
					while(true) {
						System.out.print("반납 처리 하시겠습니까(Y/N)? : ");
						char confirm = sc.next().toUpperCase().charAt(0);
						if(confirm != 'Y' && confirm != 'N') { // y/n입력 오류
							System.out.println("\n[알림]Y 또는 N 만 입력해주세요. \n");
							continue;
						}
						if(confirm == 'Y') { // 반납처리
							returnBook(book.get(0).getBookNo());
						}
						if(confirm == 'N') { // 취소
						System.out.println("\n[알림] 작업이 취소되었습니다. \n");
						}
						
						break;
					}
					break;
				case "대출불가" :
					System.out.println("\n[알림] 해당 도서는 대출불가 도서입니다.\n");
					break;
				case "분실" :
					System.out.println("\n[알림] 해당 도서는 분실처리된 도서입니다.\n");
					break;
				case "파손" :
					System.out.println("\n[알림] 해당 도서는 파손처리된 도서입니다.\n");
					break;
				default : System.out.println("\n[알림] 해당 도서의 도서상태를 확인해주세요.\n");
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("\n[알림] 대출 / 반납 처리 중 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
		
	}
	
	/** 4-1. 대출 처리
	 * @throws Exception
	 */
	private void bookLent(int bookNo) throws Exception {
		System.out.print("회원 정보 입력(아이디 또는 전화번호) : ");
		String userInput = sc.next();
		List<User> user =UMService.userInfo(userInput);
		
		if(!(user.isEmpty())) {
			System.out.println("\n[사용자 정보]");
			UMView.printDetailUser(user); // 사용자 정보 출력
			
			List<Library> overDueList = BMService.searchOverdue(user.get(0).getUserNo());
			System.out.println("\n[연체 목록]");
			printOverdue(overDueList);
			
			boolean flag = true;
			
			if(!(user.get(0).getStatusName().equals("정상"))) System.out.println("\n[알림] 대출 불가 - 사유 : 회원 상태 확인\n"); flag = false;
			if(!(user.get(0).getAvailNum() > 0)) System.out.println("\n[알림] 대출 불가 - 사유 : 도서 대출 권수 초과\n"); flag = false;
			if(!(overDueList.isEmpty())) System.out.println("\n[알림] 대출 불가 - 사유 : 연체중인 도서 확인\n"); flag = false;
			
			while(flag) {
				System.out.print("\n대출 처리 하시겠습니까(Y/N)? : ");
				char confirm = sc.next().toUpperCase().charAt(0);
				if(confirm != 'Y' && confirm != 'N') { // y/n입력 오류
					System.out.println("\n[알림]Y 또는 N 만 입력해주세요. \n");
					continue;
				}
				
				if(confirm == 'Y') { // 대출처리
					try {
						int result = BMService.bookLent(user.get(0).getUserNo(), bookNo);
						
						if(result > 0) {
							System.out.println("\n[알림] 대출 처리되었습니다.\n");
						} else {
							System.out.println("\n[알림] 대출 처리 중 문제가 발생했습니다. 다시 시도해주세요. \n");
						}
					} catch (Exception e) {
						System.out.println("\n[알림] 대출 처리 중 문제가 발생했습니다.\n");
						System.out.println("\n      문제가 지속될 경우 담당자에게 문의해주세요.\n");
						e.printStackTrace();
					}
				}
				
				if(confirm == 'N') { // 취소
				System.out.println("\n[알림] 작업이 취소되었습니다. \n");
				}
				
				break;
				
			}
		}
	}
	

	/**
	 *  A. 도서 목록 출력 
	 */
	private void printBook(List<Book> bookList) {
		System.out.println();
		System.out.printf("%-5s|%-7s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
				"번호","청구기호","주제","제목","저자","출판사","위치","상태","반납예정일");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<bookList.size(); i++) {
			System.out.printf("%-5d|%-8s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
					bookList.get(i).getBookNo(),
					bookList.get(i).getCallNo(),
					bookList.get(i).getTopicName(),
					bookList.get(i).getBookName(),
					bookList.get(i).getAuthor(),
					bookList.get(i).getPublisher(),
					bookList.get(i).getLocName(),
					bookList.get(i).getAvailName(),
					bookList.get(i).getDueDate());
		}
		System.out.println();
	}
	
	/** B. 연체 도서 출력
	 * @param overdueList
	 */
	public void printOverdue(List<Library> overdueList) {
		if(overdueList.isEmpty()) {
			System.out.println("[알림] 연체중인 도서가 없습니다.");
		} else {
			System.out.println("-------------------------------------------------------------------------------------------");
			System.out.printf("%-5s|%-7s|%-12s|%-8s|%-8s|%-10s|%-10s|%-10s\n",
					"번호", "청구기호", "제목", "이용자 번호", "이용자 이름", "대출일", "반납예정일", "반납일");
			System.out.println("-------------------------------------------------------------------------------------------");
			for(int i=0; i<overdueList.size(); i++) {
				System.out.printf("%-5d|%-8s|%-12s|%-9s|%-8s|%-11s|%-11s|%-10s\n",
						overdueList.get(i).getBookNo(),
						overdueList.get(i).getCallNo(),
						overdueList.get(i).getBookName(),
						overdueList.get(i).getUserNo(),
						overdueList.get(i).getUserName(),
						overdueList.get(i).getLentDate(),
						overdueList.get(i).getDueDate(),
						overdueList.get(i).getReturnDate());
			}
		}
	}
	
	
	/**
	 *  C. 책 1권 조회 서비스(by 청구기호)
	 */
	 
	/** E. 책 1권 반납 서비스
	 * @param bookNo
	 */
	private void returnBook(int bookNo) {
		try {
			int result = BMService.returnBook(bookNo);
			
			if(result > 0) {
				System.out.println("\n[알림] 반납 처리되었습니다.\n");
			} else {
				System.out.println("\n[알림] 반납 처리 중 문제가 발생했습니다. 다시 시도해주세요. \n");
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 반납 처리 중 문제가 발생했습니다.\n");
			System.out.println("\n      문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
	
	
}
