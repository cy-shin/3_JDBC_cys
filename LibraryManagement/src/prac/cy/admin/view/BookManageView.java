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
	private int input2 = -1;
	private Scanner sc = new Scanner(System.in);
	
	BasicView basicView = new BasicView();
	UserManageView UMView = new UserManageView();
	
	BookManageService BMService = new BookManageService();
	UserManageService UMService = new UserManageService();
	
	AdminService adminService = new AdminService(); 
	
	/** 도서 메인 메뉴
	 * @param myName
	 */
	public void BookManageMenu(String myName) {
		do {
			input = -1;
			try {
				System.out.print("-------------------------------------------------------------------------------------------");
				System.out.printf("\n| %-15s| %-5s|\n", "도서 관리 메뉴", myName);
				System.out.println("----------------------------");
				System.out.println("1. 도서 검색");
				System.out.println("2. 도서 전체 조회");
				System.out.println("3. 도서 상세 조회");
				System.out.println("4. 연체 도서 조회");
				System.out.println("5. 도서 대출 반납");
				System.out.println("6. 도서 신규 등록");
				System.out.println("0. 뒤로가기");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();
				
				switch(input) {
				case 1: searchKeyword(); break;
				case 2: searchAll(); break;
				case 3: searchDetail("-1"); break;
				case 4: searchOverdue(); break;
				case 5: bookLentReturn(); break;
				case 6: bookAdd(); break;
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
	 *  도서 서브 메뉴
	 */
	private void BookManageSubMenu() {
		System.out.println("1. 상세조회");
		System.out.println("0. 뒤로가기");
		System.out.print("메뉴 선택 : ");
		input = sc.nextInt();
		sc.nextLine();
		switch(input) {
		case 1: searchDetail("-1"); break;
		case 0: input = -1; break;
		default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
		}
	}
	
	/**
	 *  1. 도서 검색 서비스
	 */
	private void searchKeyword() {
		int result = basicView.searchKeyword();
		if(result > 0) {
			BookManageSubMenu();
		}
		
	}
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
				BookManageSubMenu();
			}
			
		} catch(Exception e) {
			System.out.println("\n[알림] 도서를 조회하는 과정에서 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  3. 도서 상세 조회 서비스
	 */
	private void searchDetail(String callNo) {
		input2 = -1;
		if(callNo.equals("-1")) {
			System.out.print("청구기호 입력 : ");
			callNo = sc.nextLine();
		}
		
		try {
			List<Book> book = BMService.bookInfo(callNo);
			
			if(book.isEmpty()) {
				System.out.println("\n[알림] 검색 결과가 없습니다. \n");
				
			} else {
				printBookDetail(book);
				do {
					System.out.println("1. 정보수정");
					System.out.println("0. 뒤로가기");
					System.out.print("메뉴 선택 : ");
					input2 = sc.nextInt();
					sc.nextLine();
					switch(input2) {
					case 1: bookUpdate(book.get(0).getBookNo(), book.get(0).getCallNo()); break;
					case 0: break;
					default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	
					}
				} while(input2 != 0);
			}
			
			
			
		} catch (Exception e) {
			System.out.println("\n[알림] 상세 조회 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  4. 도서 정보 수정
	 */
	private void bookUpdate(int bookNo, String callNo) throws Exception {
		try {
			

			System.out.println("\n[도서 정보 수정]\n");
			System.out.println("---------------------");
			System.out.println("1. 청구기호");
			System.out.println("2. 분류기호");
			System.out.println("3. 제목");
			System.out.println("4. 저자");
			System.out.println("5. 출판사");
			System.out.println("6. 위치");
			System.out.println("7. 상태");
			System.out.println("8. 전체수정");
			System.out.println("0. 뒤로가기");
			System.out.println("---------------------");
			System.out.print("변경할 정보 선택 : ");
			int type = sc.nextInt();
			sc.nextLine();

			switch(type) {
			case 7:
				availList();
			case 1: case 2: case 3: case 4: case 5: case 6:
				System.out.print("내용 입력 : ");
				String edit = sc.nextLine();
				int result = BMService.bookUpdate(bookNo, type, edit);
				if(result > 0) {
					System.out.println("\n[알림] 도서 정보가 수정되었습니다. \n");
					System.out.println();
					searchDetail(callNo);
				} else {
					System.out.println("\n[알림] 청구기호, 분류코드, 위치코드를 확인해주세요. \n");
				}
				break;

			case 8:
				bookUpdateAll(bookNo);

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
	 *  4-1. 정보 전체 수정
	 */
	private void bookUpdateAll(int bookNo) {
		MainLoop : while(true) {
			System.out.print("청구기호 : ");
			String callNo = sc.nextLine();
			topicList();
			System.out.print("분류코드 : ");
			String topicCode = sc.nextLine().toUpperCase();
			System.out.print("제목 : ");
			String bookName = sc.nextLine();
			System.out.print("저자 : ");
			String author = sc.nextLine();
			System.out.print("출판사 : ");
			String publisher = sc.nextLine();
			locList();
			System.out.print("위치코드 : ");
			String locCode = sc.nextLine().toUpperCase();
			
			System.out.println("\n[입력 내용 확인]\n");
			System.out.println("--------------------------------");
			System.out.printf("청구기호 : %s\n분류코드 : %s\n제목 : %s\n저자 : %s\n출판사 : %s\n위치코드 : %s\n",
							callNo, topicCode, bookName, author, publisher, locCode);
			System.out.println("--------------------------------");
			
			while(true) {
				System.out.print("도서 정보를 수정할까요? (Y/N) : ");
				char confirm = sc.nextLine().toUpperCase().charAt(0);
				if(confirm == 'Y') {
					System.out.println("\n도서를 수정하고 있습니다...");
					try {
						Book book = new Book();
						
						book.setBookNo(bookNo);
						book.setCallNo(callNo);
						book.setTopicCode(topicCode);
						book.setBookName(bookName);
						book.setAuthor(author);
						book.setPublisher(publisher);
						book.setLocCode(locCode);
						
						int result = BMService.bookUpdateAll(book);
						
						if(result > 0) {
							System.out.println("\n[알림] 도서가 수정되었습니다. \n");
							System.out.println();
							/////////////
							input2 = 0;
							//////////////
							searchDetail(callNo);
							
						} else {
							System.out.println("\n[알림] 청구기호, 분류코드, 위치코드를 확인해주세요. \n");
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
	/**
	 *  5. 연체 도서 조회 서비스
	 */
	private void searchOverdue() {
		try {
			List<Library> overdueList = BMService.searchOverdue(-1);
			
			
			if(overdueList.isEmpty()) {
				System.out.println("[알림] 연체중인 도서가 없습니다.");
			} else {
				printOverdue(overdueList);
				BookManageSubMenu();
			}
			
		} catch (Exception e) {
			System.out.println("\n[알림] 도서를 조회하는 과정에서 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 *  6. 도서 대출 / 반납 서비스
	 */
	private void bookLentReturn() {
		try {
			System.out.print("도서 청구 기호 : ");
			String callNo = sc.next();
			List<Book> book = BMService.bookInfo(callNo);
			if(book.isEmpty()) {
				System.out.println("\n[알림] 해당 도서를 찾을 수 없습니다. 청구 기호를 확인해주세요.\n");
			}
			if(!(book.isEmpty())) {
				printBook(book);
				String avail = book.get(0).getAvailName();
				
				switch(avail) {
				case "대출가능" :
					bookLent(book.get(0).getBookNo());
					break;
				case "대출중" :
					bookReturn(book.get(0).getBookNo());
					break;
				case "대출불가" :
					System.out.println("\n[알림] 해당 도서는 대출불가 도서입니다.\n");
					break;
				case "열람전용" :
					System.out.println("\n[알림] 해당 도서는 열람전용 도서입니다.\n");
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
	
	/** 6-1. 대출 처리
	 * @throws Exception
	 */
	private void bookLent(int bookNo) throws Exception {
		System.out.print("회원 정보 입력(아이디) : ");
		String userInput = sc.next();
		List<User> user =UMService.oneUser(userInput);
		
		if(!(user.isEmpty())) {
			System.out.println("\n[사용자 정보]");
			UMView.printDetailUser(user); // 사용자 정보 출력
			
			List<Library> overdueList = BMService.searchOverdue(user.get(0).getUserNo());
			System.out.println("\n[연체 목록]");
			printOverdue(overdueList);
			if(overdueList.isEmpty()) {
				System.out.println("-");
			}
			
			boolean flag = true;
			
			if(!(user.get(0).getStatusName().equals("정상"))) {
				System.out.println("\n[알림] 대출 불가 - 사유 : 회원 상태 확인\n");
				flag = false;
			}
			if((user.get(0).getAvailNum() <= 0)) {
				System.out.println("\n[알림] 대출 불가 - 사유 : 도서 대출 권수 초과\n"); 
				flag = false;
			}
			if(!(overdueList.isEmpty())) {
				System.out.println("\n[알림] 대출 불가 - 사유 : 연체중인 도서 확인\n"); 
				flag = false;
			}
			
			
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
						
						if(result >= 2) {
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
	
	/** 6-2. 반납 처리
	 * @param bookNo
	 */
	private void bookReturn(int bookNo) throws Exception {
			while(true) {
				System.out.print("반납 처리 하시겠습니까(Y/N)? : ");
				char confirm = sc.next().toUpperCase().charAt(0);
				if(confirm != 'Y' && confirm != 'N') { // y/n입력 오류
					System.out.println("\n[알림]Y 또는 N 만 입력해주세요. \n");
					continue;
				}
				if(confirm == 'Y') { // 반납처리
					BMService.bookReturn(bookNo);
					break;
				}
				if(confirm == 'N') { // 취소
				System.out.println("\n[알림] 작업이 취소되었습니다. \n");
				break;
				}
				
			}
			int result = BMService.bookReturn(bookNo);
			
			if(result > 0) {
				System.out.println("\n[알림] 반납 처리되었습니다.\n");
			} else {
				System.out.println("\n[알림] 반납 처리 중 문제가 발생했습니다. 다시 시도해주세요. \n");
			}
	}
	
	/**
	 *  7. 신간 도서 등록
	 */
	private void bookAdd() {
		MainLoop : while(true) {
			System.out.println("\n[신간 도서 등록]\n");
			System.out.print("청구기호 : ");
			String callNo = sc.nextLine();
			topicList();
			System.out.print("분류코드 : ");
			String topicCode = sc.nextLine().toUpperCase();
			System.out.print("제목 : ");
			String bookName = sc.nextLine();
			System.out.print("저자 : ");
			String author = sc.nextLine();
			System.out.print("출판사 : ");
			String publisher = sc.nextLine();
			locList();
			System.out.print("위치코드 : ");
			String locCode = sc.nextLine().toUpperCase();
			
			System.out.println("\n[입력 내용 확인]\n");
			System.out.println("--------------------------------");
			System.out.printf("청구기호 : %s\n분류코드 : %s\n제목 : %s\n저자 : %s\n출판사 : %s\n위치코드 : %s\n",
							callNo, topicCode, bookName, author, publisher, locCode);
			System.out.println("--------------------------------");
			
			while(true) {
				System.out.print("도서를 등록할까요? (Y/N) : ");
				char confirm = sc.nextLine().toUpperCase().charAt(0);
				if(confirm == 'Y') {
					System.out.println("\n도서를 등록하고 있습니다...");
					try {
						Book book = new Book();
						
						book.setCallNo(callNo);
						book.setTopicCode(topicCode);
						book.setBookName(bookName);
						book.setAuthor(author);
						book.setPublisher(publisher);
						book.setLocCode(locCode);
						
						int result = BMService.bookAdd(book);
						
						if(result > 0) {
							System.out.println("\n[알림] 도서가 등록되었습니다. \n");
							System.out.println();
							searchDetail(callNo);
							
						} else {
							System.out.println("\n[알림] 등록 작업 중 문제가 발생했습니다. 다시 시도해주세요. \n");
						}
						
						
					} catch (Exception e) {
						System.out.println("\n[알림] 청구기호, 분류코드, 위치코드를 확인해주세요. \n");
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
	
	
	/** A. 도서 목록 출력
	 *  @param bookList
	 */
	private void printBook(List<Book> bookList) {
		System.out.println("----------------------------------------------------------------------------------------------");
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
	
	/** B. 상세 정보 출력
	 *  @param bookList
	 */
	private void printBookDetail(List<Book> bookList) {
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.printf("관리번호 : %-13s\n청구기호 : %-13s\n분류 : %-15s\n제목 : %-14s | 저자 : %-10s | "
				+ "출판사 : %-10s \n위치 : %-14s\n상태 : %-14s\n반납예정일 : %-13s\n",
					bookList.get(0).getBookNo(),
					bookList.get(0).getCallNo(),
					bookList.get(0).getTopicName(),
					bookList.get(0).getBookName(),
					bookList.get(0).getAuthor(),
					bookList.get(0).getPublisher(),
					bookList.get(0).getLocName(),
					bookList.get(0).getAvailName(),
					bookList.get(0).getDueDate());
		System.out.println("----------------------------------------------------------------------------------------------\n");
	}
	
	/** C. 연체 도서 출력
	 * @param overdueList
	 */
	public void printOverdue(List<Library> overdueList) {
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
			System.out.println("-------------------------------------------------------------------------------------------");
		
	}
	
	
	/**
	 *  기타1: 분류 코드
	 */
	private void topicList() {
		try {
			List<Book> topicList = BMService.topicList();
			System.out.printf("\n%-3s| %s\n", "기호", "분류명");
			System.out.println("------------");
			for(Book b : topicList) {
				System.out.printf("%-3s | %s\n",
						b.getTopicCode(),
						b.getTopicName());
			}
			System.out.println("------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  기타2: 위치 코드
	 */
	private void locList() {
		try {
			List<Book> locList = BMService.locList();
			System.out.printf("\n%-3s| %s\n", "기호", "위치명");
			System.out.println("------------");
			for(Book b : locList) {
				System.out.printf("%-3s | %s\n",
						b.getLocCode(),
						b.getLocName());
			}
			System.out.println("------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 *  기타3: 상태 코드
	 */
	private void availList() {
		try {
			List<Book> availList = BMService.availList();
			System.out.printf("\n%-3s| %s\n", "기호", "상태명");
			System.out.println("------------");
			for(Book b : availList) {
				System.out.printf("%-3s | %s\n",
						b.getAvailCode(),
						b.getAvailName());
			}
			System.out.println("------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
}
