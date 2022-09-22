package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;

public class BoardView {
	
private Scanner sc = new Scanner(System.in);
	
	private BoardService bService = new BoardService();
	
	private CommentService cService = new CommentService();
	
	/**
	 *  게시판 기능 메뉴
	 */
	public void boardMenu() {
		
		int input = -1;
		
		do {
			try {
				System.out.println("\n***** 게시판 기능 *****\n");
				System.out.println("1.게시판 목록 조회");
				System.out.println("2.게시판 목록 조회(+ 댓글 기능)");
				System.out.println("3.게시판 작성");
				System.out.println("4.게시판 검색");
				System.out.println("0.로그인 메뉴로 이동");
				
				System.out.print("\n메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine();

				System.out.println();
				
				switch(input) {
				case 1: selectAllBoard(); break; // 게시글 목록 조회
				case 2: break;
				case 3: break;
				case 4: break;
				case 0: System.out.println("[로그인 메뉴로 이동합니다.]"); break;
				default : System.out.println("메뉴에 작성된 번호만 입력해주세요. ");
				}
				
				System.out.println();
				
			} catch (InputMismatchException e) {
				System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
				sc.nextLine();
				
			}
		} while(input != 0);
	}
	
	/**
	 *  게시글 목록 조회
	 */
	private void selectAllBoard() {
		System.out.println("\n[게시글 목록 조회]\n");
		try {
			List<Board> boardList = bService.selectAllBoard();
			
			if(boardList == null) {
				System.out.println("[게시글이 없습니다.]");
			} else {
				System.out.printf("%s|%s|%s|%s|%s|%s\n",
						"번호","제목", "작성자", "조회수", "작성일", "댓글수");
				for(Board board : boardList) {
					System.out.printf("%d|%s|%s|%d|%s|%d\n",
							board.getBoardNo(),
							board.getBoardTitle(),
							board.getMemberName(),
							board.getReadcount(),
							board.getCreateDate(),
							board.getCommentCount());
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("\n<<게시판 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
}
