package prac.cy.main.view;

import java.util.List;
import java.util.Scanner;

import prac.cy.lib.vo.LibVO;
import prac.cy.main.model.service.MainService;

public class MainView {
	MainService service = new MainService();
	
	// 스캐너
	private Scanner sc = new Scanner(System.in);
	
	// 입력값
	private int input = -1;
	
	// 로그인유저 정보
	public static LibVO loginUser = null;
	
	// 관리자계정 여부
	public static boolean userFlag = false;
	
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
					case 1: keywordSearch(); break;
					case 2: login(); break;
					case 3: break;
					case 0: System.out.println("서비스를 종료합니다."); break;
					default : System.out.println("잘못된 선택입니다. ");	
					}
				}
				if(loginUser != null && userFlag == true) {
					System.out.println("관리자 계정으로 접속중입니다.");
					System.out.print("종료하려면 0 입력 : ");
					input = sc.nextInt();
					if(input == 0) input = -1; break;
				}
			} catch (Exception e) {
				System.out.println("메뉴 목록에 있는 숫자만 입력해주세요. ");
			}
			
		} while(input != 0);
	}
	
	
	/**
	 *  1. 도서 검색 서비스
	 */
	private void keywordSearch() {
		System.out.println("\n[통합 검색]\n");
		System.out.print("검색어 입력 : ");
		String keyword = sc.nextLine();
		
		System.out.println("\n검색중...\n");
		
		try {
			List<LibVO> bookList = service.keywordSearch(keyword);
			
			if(bookList.isEmpty()) {
				System.out.println("검색 결과가 없습니다.");
			} else {
				select(bookList);
				
			}
			
		} catch (Exception e) {
			System.out.println("\n[알림] 도서 검색 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
		
	}
	
	/**
	 *  2. 로그인
	 */
	private void login() {
		System.out.println("\n[로그인]\n");
		
		System.out.print("아이디 : ");
		String memberId = sc.next();
		
		System.out.print("비밀번호 : ");
		String memberPw = sc.next();
		
		try {
		loginUser = service.login(memberId, memberPw);
		
		if(loginUser != null) {
			if(loginUser.getIdentityName().equals("관리자")) {
				userFlag = true;
			}
			System.out.printf("\n%s님, 안녕하세요\n", loginUser.getMemberName());
		} else {
			System.out.println("[알림] 아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		} catch (Exception e) {
			System.out.println("\n[알림] 로그인 중 오류가 발생했습니다.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  A. 목록 조회 
	 */
	private void select(List<LibVO> bookList) {
		System.out.println();
		for(int i=0; i<bookList.size(); i++) {
			System.out.printf("%-7s|%-6s|%-12s|%-10s|%-10s|%-6s|%-6s|%-10s\n",
					"청구기호","주제","제목","저자","출판사","위치","상태","반납예정일");
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
