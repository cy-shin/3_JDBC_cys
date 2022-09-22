package prac.cy.admin.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import prac.cy.admin.model.service.AdminService;
import prac.cy.admin.model.service.BookManageService;
import prac.cy.basic.view.BasicView;
import prac.cy.library.vo.Book;

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
				case 4: break;
				case 5: break;
				case 6: break;
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
			List<Book> overDueList = BMService.searchOverdue();
			
			if(overDueList.isEmpty()) {
				System.out.println("[알림] 연체중인 도서가 없습니다.");
			} else {
				System.out.printf("%-5s|%-7s|%-12s|%-5s|%-10s|%-10s\n",
						"번호", "청구기호", "제목", "이용자 번호", "이용자 이름", "반납예정일");
				System.out.println("----------------------------------------------------------------------------------------------");
				for(int i=0; i<overDueList.size(); i++) {
					System.out.printf("%-5d|%-8s|%-12s|%-5s|%-10s|%-10s\n",
							overDueList.get(i).getBookNo(),
							overDueList.get(i).getCallNo(),
							overDueList.get(i).getBookName(),
							overDueList.get(i).getUserNo(),
							overDueList.get(i).getUserName(),
							overDueList.get(i).getDueDate());
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n[알림] 도서를 조회하는 과정에서 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 *  A. 목록 조회용 
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
