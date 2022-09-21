package prac.cy.admin.view;

import java.util.List;
import java.util.Scanner;

import prac.cy.admin.model.service.AdminService;
import prac.cy.lib.vo.LibVO;
import prac.cy.main.view.MainView;

public class AdminView {
	AdminService adminService = new AdminService();
	private int input = -1;
	private Scanner sc = new Scanner(System.in);
			
	public void adminMenu() {
		
		do {
			try {
				System.out.println("\n***** 관리자 메뉴 *****\n");
				System.out.println("----------------------------");
				System.out.println("1. 도서검색");
				System.out.println("2. 도서전체조회");
				System.out.println("3. 회원조회");
				System.out.println("4. 도서대출반납");
				System.out.println("5. 도서신규등록");
				System.out.println("6. 도서상태변경");
				System.out.println("0. 로그아웃");
				System.out.print("\n메뉴 선택 > ");
				input = sc.nextInt();
				sc.nextLine();

				switch(input) {
				case 1: keywordSearch(); break;
				case 2: searchAll(); break;
				case 3: break;
				case 4: break;
				case 5: break;
				case 6: break;
				case 0: 
					System.out.println("\n[알림] 로그아웃 되었습니다.\n"); break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");	 
				}
			} catch (Exception e) {
				System.out.println("\n[알림] 메뉴 목록에 있는 숫자만 입력해주세요. \n");
				sc.nextLine();
			}
		} while (input != 0);
	}
	
	/**
	 *  1. 도서 검색 서비스
	 */
	public void keywordSearch() {
		System.out.println("\n[통합 검색]\n");
		System.out.print("검색어 입력 : ");
		String keyword = sc.nextLine();
		
		System.out.println("\n\n검색중...\n");
		
		try {
			List<LibVO> bookList = adminService.keywordSearch(keyword);
			
			if(bookList.isEmpty()) {
				System.out.println("\n[알림] 검색 결과가 없습니다.\n");
			} else {
				print(bookList);
				
			}
			
		} catch (Exception e) {
			System.out.println("\n[알림] 도서 검색 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
		
	}
	
	/**
	 *  2. 도서 전체 조회 서비스
	 */
	public void searchAll() {
		System.out.println("\n[도서 전체 조회]\n");
		
		System.out.println("\n검색중...\n");
		try {
			List<LibVO> bookList = adminService.searchAll();
			
			if(bookList.isEmpty()) {
				System.out.println("[알림] 검색 결과가 없습니다.");
			} else {
				print(bookList);
			
			}
			
		} catch(Exception e) {
			System.out.println("\n[알림] 도서를 조회하는 과정에서 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  A. 목록 조회 
	 */
	public void print(List<LibVO> bookList) {
		System.out.println();
		System.out.printf("%-7s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
				"청구기호","주제","제목","저자","출판사","위치","상태","반납예정일");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<bookList.size(); i++) {
			System.out.printf("%-8s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
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
