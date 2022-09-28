package prac.cy.basic.view;

import java.util.List;
import java.util.Scanner;

import prac.cy.basic.model.service.BasicService;
import prac.cy.library.vo.Book;
import prac.cy.library.vo.Library;

public class BasicView {
	BasicService basicService = new BasicService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 *  A. 키워드로 통합 검색
	 */
	public int searchKeyword() {
		int result = 0;
		
		System.out.println("\n[통합 검색]\n");
		System.out.print("검색어 입력 : ");
		String keyword = sc.nextLine();
		
		System.out.println("\n검색중...\n");
		
		try {
			List<Book> bookList = basicService.searchKeyword(keyword);
			
			if(bookList.isEmpty()) {
				System.out.println("\n[알림] 검색 결과가 없습니다.\n");
			} else {
				print(bookList);
				result = 1;
				
			}
			
		} catch (Exception e) {
			System.out.println("\n[알림] 도서 검색 중 문제가 발생했습니다.\n");
			System.out.println("\n       문제가 지속될 경우 담당자에게 문의해주세요.\n");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 *  B. 도서 정보 상세 조회
	 */
	public void searchDetail() {
			System.out.print("청구기호 입력 : ");
			String callNo = sc.nextLine();
		
		try {
			List<Book> book = basicService.searchDetail(callNo);
			
			if(book.isEmpty()) {
				System.out.println("\n[알림] 검색 결과가 없습니다. \n");
			} else {
				printBookDetail(book);
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 상세 조회 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  C. 목록 조회 
	 */
	public void print(List<Book> bookList) {
		System.out.println();
		System.out.printf("%-7s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
				"청구기호","주제","제목","저자","출판사","위치","상태","반납예정일");
		System.out.println("----------------------------------------------------------------------------------------------");
		for(int i=0; i<bookList.size(); i++) {
			System.out.printf("%-8s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
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
	
	/** D. 상세 정보 출력
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
}
