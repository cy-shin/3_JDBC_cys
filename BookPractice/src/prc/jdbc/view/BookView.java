package prc.jdbc.view;

import java.util.List;
import java.util.Scanner;

import prc.jdbc.model.service.BookService;
import prc.jdbc.model.vo.Book;

public class BookView {
	private Scanner sc = new Scanner(System.in);
	BookService service = new BookService();
	
	public void displayMenu() {
		int input = 0;
		
		do {
			System.out.println("도서 관리 서비스");
			System.out.println("-------------------");
			System.out.println("1. 도서 검색");
			System.out.println("0. 종료");
			
			System.out.print("작업 선택 > ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			case 1: unifiedSearch(); break;
			case 2: break;
			case 0: System.out.println("종료합니다."); break;
			default: System.out.println("잘못된 선택입니다. ");
			}
		} while(input!=0);
	}

	/** 1. 통합검색
	 * 
	 */
	private void unifiedSearch() {
		System.out.print("검색할 키워드 : ");
		String keyword = sc.nextLine();
		
		try {
			List<Book> bookList = service.unifiedSearch(keyword);
			
			
			if(bookList.isEmpty()) {
				System.out.println("검색 결과가 없습니다.");
			} else {
				selectAll(bookList);
				
				
			}
			
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		}
		
	};
	
	/** A. 전체 출력 메서드
	 * 
	 */
	private void selectAll(List<Book> bookList) {
		System.out.println();
		for(int i=0; i<bookList.size(); i++) {
			System.out.printf("%5s|%12s|%12s|%10s|%10s|%6s|%6s|%10s\n",
					"NO","청구기호","제목","저자","출판사","위치","상태","반납일");
			System.out.printf("%5d|%12s|%12s|%10s|%10s|%6s|%6s|%10s\n",
					bookList.get(i).getBookNo(),
					bookList.get(i).getCallNo(),
					bookList.get(i).getBookName(),
					bookList.get(i).getAuthor(),
					bookList.get(i).getPublisher(),
					bookList.get(i).getLocation(),
					bookList.get(i).getAvailability(),
					bookList.get(i).getDueDate());
		}
		System.out.println();
	}
}
