package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.main.view.MainView;

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
				
				case 2: selectBoard(); break; // 게시글 상세 조회
				
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
			// -> DAO에서 new ArrayList<>(); 구문으로 인해 반환되는 조회 결과는 null이 될 수 없다.
			
			if(boardList.isEmpty()) {
				System.out.println("게시글이 존재하지 않습니다.");

			} else {
				System.out.printf("%-3s|%-25s|%-5s|%-5s|%-10s\n",
						"번호","제목", "작성자", "조회수", "작성일");
				System.out.println("-----------------------------------------------------------");
				for(Board b : boardList) {
					System.out.printf("% -4d|%-1s[%d]              |%-5s|%-5d |%-10s\n",
							b.getBoardNo(),
							b.getBoardTitle(),
							b.getCommentCount(),
							b.getMemberName(),
							b.getReadCount(),
							b.getCreateDate());
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시판 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	/**
	 *  게시글 상세 조회
	 */
	private void selectBoard() {
		System.out.println("\n[게시글 상세 조회]\n");
		
		try {
			System.out.print("게시글 번호 입력 : ");
			int boardNo = sc.nextInt();
			sc.nextLine();
			System.out.println();
			// 게시글 상세 조회 서비스 호출 후 결과 반환 받기
			Board board = bService.selectBoard(boardNo, MainView.loginMember.getMemberNo());
												// 게시글번호, 로그인한 회원의 회원번호
												//             -> 자신의 글 조회수 증가 X
			if(board != null) {
				System.out.printf("글번호: %d | %s"
						+ "\n------------------------------------------------\n"
						+ "%s               | 조회 %d"
						+ "\n------------------------------------------------\n"
						+ "%s"
						+ "\n------------------------------------------------\n"
						+ "%s",
						board.getBoardNo(),
						board.getBoardTitle(),
						board.getCreateDate(),
						board.getReadCount(),
						board.getMemberName(),
						board.getBoardContent());
				
				System.out.println(); // 줄 바꿈
				System.out.println(); // 줄 바꿈
				System.out.println(); // 줄 바꿈
				System.out.println(); // 줄 바꿈
				System.out.println("--------------------------------------------------------");
				
				 // 댓글 목록
	            if(!board.getCommentList().isEmpty()) {
	               for(Comment c : board.getCommentList()) {
	                  System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
	                        c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
	                  System.out.println("--------------------------------------------------------");
	               }
	            }
	            
	            // 댓글 등록, 수정, 삭제
	            // 수정/삭제 메뉴
	            if(board != null) {
	            	subBoardMenu(board);
	            }
	             
	             
	             
				
			} else {
				System.out.println("\n검색 결과가 없습니다.\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 상세 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}
	
	
	
	/** 게시글 상세 조회 시 출력되는 서브메뉴
	 * @param board(상세조회된 게시글 + 작성자 번호 + 댓글 목록)
	 */
	private void subBoardMenu(Board board) {
		
		try {
			System.out.println("1) 댓글 등록");
			System.out.println("2) 댓글 수정");
			System.out.println("3) 댓글 삭제");
			
			// 로그인한 회원과 게시글 작성자가 같은 경우에만 출력되는 메뉴
			if(board.getMemberNo() == MainView.loginMember.getMemberNo()) {
				System.out.println("4) 게시글 수정");
				System.out.println("5) 게시글 삭제");
			}
			
			System.out.println("0) 게시판 메뉴로 돌아가기");
			
			System.out.print("\n서브 메뉴 선택 : ");
			int input = sc.nextInt();
			sc.nextLine();
			
			int memberNo = MainView.loginMember.getMemberNo();
			
			switch(input) {
			case 1: insertComment(board.getBoardNo(), memberNo); break;
			case 2: updateComment(board.getCommentList(), memberNo); break;
			case 3: deleteComment(board.getCommentList(), memberNo); break;
			case 0: System.out.println("[게시판 메뉴로 이동합니다.]"); break;
			
			case 4: case 5: // 4 또는 5 입력 시 
				// 메뉴 4 또는 5를 입력 시, 로그인 회원과 게시글 작성자가 같은 경우에만 실행됨
				if(board.getMemberNo() == MainView.loginMember.getMemberNo()) {
					if(input == 4) { // 게시글 수정 호출
						updateBoard(board.getBoardNo());
					}
					
					if(input == 5) { // 게시글 삭제 호출
						input = deleteBoard(board.getBoardNo());
					}
					
					break;
				}
			// 만약 로그인 회원 != 게시글 작성자 이어서 case 4, case 5가 아예 실행되지 않으면
			// break문이 수행되지 않고 default문으로 넘어옴
			default : System.out.println("메뉴에 작성된 번호만 입력해주세요. ");
			}
			
			// 재귀 호출
			// 서브 메뉴 댓글 등록 or 댓글 수정 or 댓글 삭제 중 하나를 선택한 경우
			// 각각의 서비스 메서드 종료 후 다시 서브메뉴 메서드를 호출한다
			if(input > 0) {
				try {
		               board = bService.selectBoard(board.getBoardNo(), MainView.loginMember.getMemberNo());
		   
		               System.out.println(" --------------------------------------------------------");
		               System.out.printf("글번호 : %d | 제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
		               System.out.printf("작성자ID : %s | 작성일 : %s | 조회수 : %d\n", 
		                     board.getMemberName(), board.getCreateDate().toString(), board.getReadCount());
		               System.out.println(" --------------------------------------------------------");
		               System.out.println(board.getBoardContent());
		               System.out.println(" --------------------------------------------------------");
		   
		            
		               // 댓글 목록
		               if(!board.getCommentList().isEmpty()) {
		                  for(Comment c : board.getCommentList()) {
		                     System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
		                           c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
		                     System.out.println(" --------------------------------------------------------");
		                  }
		               }
		            }catch (Exception e) {
		               e.printStackTrace();
		            }
				subBoardMenu(board);
			}
			
			
		} catch (InputMismatchException e) {
			System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
			sc.nextLine();
		}
		
	}

	/** 댓글 등록
	 * @param boardNo
	 * @param memberNo
	 */
	private void insertComment(int boardNo, int memberNo) {
		
		try {
			
			// 내용 입력 메서드
				System.out.println("\n[댓글 등록]\n");
				String content = inputContent();
				
				if(content == null) {
					System.out.println("댓글 등록 취소");
				} else {
					
					// Comment 객체를 생성해서 댓글 내용, 게시글 번호, 댓글 작성자 번호를 저장했음
					Comment comment = new Comment();
					comment.setCommentContent(content);
					comment.setBoardNo(boardNo);
					comment.setMemberNo(memberNo);
					
					// Comment 객체를 BoardService의 insertComment 메서드로 전달하고, 그 결과를 받아옴
					// 이 서비스에서 실행할 SQL문은 insert 구문임
					// 즉 DML이므로 결과가 반영된 행의 개수로 반환됨 = int형이므로 int타입 변수인 result에 저장했음
					int result = cService.insertComment(comment);
					
					// 재귀호출?
					
					if(result > 0) {
						System.out.println("\n[댓글이 등록되었습니다.]\n");
					} else {
						System.out.println("[\n[댓글 등록 실패]\n");
					}
				}
		} catch (Exception e) {
			System.out.println("\n<<댓글 등록 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	/** 내용 입력
	 * @return content
	 */
	private String inputContent() {
		String content = ""; // 공백("")을 참조하고 있는 빈 문자열로, 아무것도 참조하지 않는다는 의미의 null과는 다르다는 것을 꼭 기억하자
							 //  공백도 NULL로 나옴
		
		String input = null; // 참조하는 객체가 없음 (db와 다르다는 것도 기억해줄것)
		
		System.out.println("입력 종료 시 ($exit) 입력");
		
		while(true) {
			input = sc.nextLine();
			
			if(input.equals("$exit")) {
				if(content=="") {
					System.out.println("내용이 입력되지 않았습니다.");
					System.out.println("댓글 입력을 취소할까요? (Y/N) ");
					char yesOrNo = sc.next().toUpperCase().charAt(0);
					if(yesOrNo=='Y') break; 
					System.out.print(">");
					content="";
					continue;
				}
				
				break;
			}
			else {
				// 입력된 내용을 content에 누적하되 엔터를 누를때마다 줄바꿈이 되도록 했음
				content += input + "\n";
			}
		}
		return content;
	}
	
	
	/** 댓글 수정
	 * @param commentList
	 * @param memberNo
	 */
	private void updateComment(List<Comment> commentList, int memberNo) {
		
		// 댓글 번호를 입력 받아서
		// 1) 해당 댓글이 commentList에 있는지 검사
		// 2) 있다면 해당 댓글이 로그인한 회원이 작성한 댓글인지 검사
		try {
			System.out.println("\n[댓글 수정]\n");
			System.out.print("수정할 댓글 번호 입력 : ");
			int commentNo = sc.nextInt();
			sc.nextLine();
			
			boolean flag = true;
			
			for(Comment c : commentList) {
				if(c.getCommentNo() == commentNo) { // 댓글 번호 일치
					flag = false;
					
					if(c.getMemberNo() == memberNo) { // 회원 번호 일치 검사
						// 수정할 댓글 내용 입력 받기
						String content = inputContent();
						
						// 댓글 수정 서비스 호출
						int result =cService.updateComment(commentNo, content);
						
						if(result > 0) {
							System.out.println("\n[댓글 수정 성공]\n");
						} else {
							System.out.println("\n[댓글 수정 실패...]\n");
						}
						
					} else {
						System.out.println("\n[자신의 댓글만 수정할 수 있습니다.]\n");
					}
					break;
					
				} 
			} // for end
			
			if(flag == true) {
				System.out.println("\n[번호가 일치하는 댓글이 없습니다. ]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<댓글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	/** 댓글 삭제
	 * @param commentList
	 * @param memberNo
	 */
	private void deleteComment(List<Comment> commentList, int memberNo) {
		
		// 댓글 번호를 입력 받아서
		// 1) 해당 댓글이 commentList에 있는지 검사
		// 2) 있다면 해당 댓글이 로그인한 회원이 작성한 댓글인지 검사
		try {
			System.out.println("\n[댓글 삭제]\n");
			System.out.print("삭제할 댓글 번호 입력 : ");
			int commentNo = sc.nextInt();
			sc.nextLine();
			
			boolean flag = true;
			
			for(Comment c : commentList) {
				if(c.getCommentNo() == commentNo) { // 댓글 번호 일치
					flag = false;
					
					if(c.getMemberNo() == memberNo) { // 회원 번호 일치 검사
						
						System.out.print("정말 삭제 하시겠습니까? (Y/N) : ");
						char ch = sc.next().toUpperCase().charAt(0);
						
						if(ch == 'Y') {
							// 댓글 삭제 서비스 호출
							int result =cService.deleteComment(commentNo);
							
							if(result > 0) {
								System.out.println("\n[댓글 삭제 성공]\n");
							} else {
								System.out.println("\n[댓글 삭제 실패...]\n");
							}
						} else {
							System.out.println("\n[취소 되었습니다.]\n");
						}
					} else {
						System.out.println("\n[자신의 댓글만 삭제할 수 있습니다.]\n");
					}
					break;
					
				} 
			} // for end
			
			if(flag == true) {
				System.out.println("\n[번호가 일치하는 댓글이 없습니다. ]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<댓글 삭제 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	/** 게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int boardNo) {
		try {
			System.out.println("\n[게시글 수정]\n");
			
			System.out.print("수정할 제목 : ");
			String boardTitle = sc.nextLine();

			System.out.print("수정할 내용 : ");
			String boardContent = inputContent();
			
			Board board = new Board();
			board.setBoardNo(boardNo);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			
			int result = bService.updateBoard(board);
			
			if(result > 0) {
				System.out.println("\n[게시글이 수정되었습니다.]\n");
			} else {
				System.out.println("\n[게시글 수정 실패!]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	/** 게시글 삭제
	 * @param boardNo
	 */
	private int deleteBoard(int boardNo) {
		int input = 5;
		
		try {
			System.out.println("\n[게시글 삭제]\n");
			
			System.out.print("정말 삭제 하시겠습니다? (Y/N) : ");
			char ch = sc.next().toLowerCase().charAt(0);
			
			if(ch == 'y') {
				int result = bService.deleteBoard(boardNo);
				
				if(result > 0) {
					System.out.println("\n[게시글이 삭제되었습니다.]\n");
					input = 0;
				} else {
					System.out.println("\n[게시글 삭제 실패!]\n");
				}
			} else {
				System.out.println("\n[삭제 취소]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<게시글 삭제 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
		return input;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
