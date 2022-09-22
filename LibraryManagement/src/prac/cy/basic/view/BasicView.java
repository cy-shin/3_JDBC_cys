package prac.cy.basic.view;

import java.util.List;
import java.util.Scanner;

import prac.cy.basic.model.service.BasicService;
import prac.cy.library.vo.Library;

public class BasicView {
	BasicService basicService = new BasicService();
	private Scanner sc = new Scanner(System.in);
	
	/**
	 *  A. 키워드로 통합 검색
	 */
	public void keywordSearch() {
		System.out.println("\n[통합 검색]\n");
		System.out.print("검색어 입력 : ");
		String keyword = sc.nextLine();
		
		System.out.println("\n검색중...\n");
		
		try {
			List<Library> bookList = basicService.keywordSearch(keyword);
			
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
	 *  B. 목록 조회 
	 */
	public void print(List<Library> bookList) {
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
