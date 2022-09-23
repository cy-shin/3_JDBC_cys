package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.admin.model.service.AdminService;
import prac.cy.admin.model.service.BookManageService;
import prac.cy.basic.view.BasicView;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.Library;

public class BookManageView {
	private int input = -1;
	private Scanner sc = new Scanner(System.in);
	
	BasicView basicView = new BasicView();
	BookManageService BMService = new BookManageService();
	
	AdminService adminService = new AdminService(); 
	
	public void BookManageMenu(String myName) {
		do {
			input = -1;
			try {
				System.out.println("----------------------------");
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
	
	/**
	 *  1. 도서 검색 서비스 for admin
	 *  
	 */
	
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
				print(bookList);
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
			List<Library> overDueList = BMService.searchOverdue();
			
			if(overDueList.isEmpty()) {
				System.out.println("[알림] 연체중인 도서가 없습니다.");
			} else {
				System.out.printf("%-5s|%-7s|%-12s|%-8s|%-8s|%-10s|%-8s|%-10s\n",
						"번호", "청구기호", "제목", "이용자 번호", "이용자 이름", "대출일", "반납예정일", "반납일");
				System.out.println("----------------------------------------------------------------------------------------------");
				for(int i=0; i<overDueList.size(); i++) {
					System.out.printf("%-6d|%-8s|%-12s|%-10s|%-10s|%-10s|%-10s|%-10s\n",
							overDueList.get(i).getBookNo(),
							overDueList.get(i).getCallNo(),
							overDueList.get(i).getBookName(),
							overDueList.get(i).getUserNo(),
							overDueList.get(i).getUserName(),
							overDueList.get(i).getLentDate(),
							overDueList.get(i).getDueDate(),
							overDueList.get(i).getReturnDate());
				}
			}
			
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
			// 3. 대출중이 아니었던 책인 경우, 책의 대출가능여부를 확인해서 대출불가시 대출불가 안내함
			// 4. 대출중이 아니고, 대출도 가능한 책인 경우 회원 아이디를 입력받음
			// 5. 회원 정보를 불러옴(아이디, 이름, 신분, 대출권수, 대출목록)
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
				print(book);
				// 2. 만약 대출중이었던 책인 경우, 반납처리함
				if(book.get(0).getAvail())
			}
			
			
		} catch (Exception e) {
			System.out.println("\n[알림] 대출 / 반납 처리 중 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
		
	}
	

	/**
	 *  A. 도서 목록 조회용 
	 */
	private void print(List<Book> bookList) {
		System.out.println();
		System.out.printf("%-5s|%-7s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
				"번호","청구기호","주제","제목","저자","출판사","위치","상태","반납예정일");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<bookList.size(); i++) {
			System.out.printf("%-5d|%-8s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
					bookList.get(i).getBookNo(),
					bookList.get(i).getCallNo(),
					bookList.get(i).getTopic(),
					bookList.get(i).getBookName(),
					bookList.get(i).getAuthor(),
					bookList.get(i).getPublisher(),
					bookList.get(i).getLoc(),
					bookList.get(i).getAvail(),
					bookList.get(i).getDueDate());
		}
		System.out.println();
	}
	
}
