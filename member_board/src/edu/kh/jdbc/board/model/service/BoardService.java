package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;

public class BoardService {
	
	// BoardDAO 객체를 생성
	private BoardDAO bDao = new BoardDAO();
	
	// CommentDAO 객체 생성 -> 상세 조회 시 댓글 목록 조회 용도로 사용함
	private CommentDAO cDao = new CommentDAO();

	/** 게시글 목록 조회 서비스
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard() throws Exception {
		Connection conn = getConnection();
		
		List<Board> boardList = bDao.selectAllBoard(conn);
		
		close(conn);
				
		return boardList;
	}

	/**	게시글 상세 조회 서비스
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	public Board selectBoard(int boardNo, int memberNo) throws Exception {
		Connection conn = getConnection();
		
		// 1. 게시글 상세 조회 DAO 호출
		Board board = bDao.selectBoard(conn, boardNo);
		// -> 조회 결과가 없으면 null
		// -> 조회 결과가 있으면 null 아님 ~
		
		if(board != null) { // 게시글이 존재 한다면
			// 2. 댓글 목록 조회 DAO 호출
			List<Comment> commentList = cDao.selectCommentList(conn, boardNo);
			
			// 조회된 댓글 목록을 board에 저장
			board.setCommentList(commentList);
			
			// 3. 조회수를 증가
			//    단, 로그인한 회원과 게시글 작성자가 다를 경우에만 증가시킴
			if(memberNo != board.getMemberNo()) {
				int result = bDao.increaseReadCount(conn, boardNo);
				
				// 트랜잭션 제어
				if(result > 0) {
					commit(conn);
					// 미리 조회된 board의 조회 수를
					// 증가된 DB의 조회 수와 동기화 작업이 필요함
					board.setReadcount(board.getReadCount() + 1);
					// DB에서 가져와도 상관 없음
				}
				else {
					rollback(conn);
				}
				
			} else {
				
			}
		}
		
		close(conn);
				
		return board;
		
	}
	
	/** 게시글 등록
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public int insertBoard(Board board) throws Exception {
		Connection conn = getConnection();
		
		// 게시글 번호 생성
		int boardNo = bDao.nextBoardNo(conn);
		
		board.setBoardNo(boardNo);

		int result = bDao.insertBoard(conn, board);
		
		
		if(result > 0) {
			commit(conn);
			// 동시에 여러 사람이 게시글을 등록했을 떄,
			// 현재 게시글 번호를 얻어오기 위해 시퀀스의 CURRVAL 구문을 사용하면 충돌 가능성이 있음
			// -> 게시글 작성 전, 미리 게시글 번호를 생성하는 방식으로 작성해보자
			result = boardNo;
			// INSERT 성공 시 생성된 게시글 번호(boardNo)를 결과로 반환함
		}
		else rollback(conn);
	
		
		close(conn);
				
		return result;
	}

	/** 게시글 수정
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public int updateBoard(Board board) throws Exception {
		Connection conn = getConnection();
		
		int result = bDao.updateBoard(conn, board);
				
		if(result > 0) {
			commit(conn);
			board.setReadcount(board.getReadCount() + 1);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
				
		return result;
	}
	
	/** 게시글 삭제
	 * @param boardNo
	 * @return
	 * @Exception 
	 */
	public int deleteBoard(int boardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = bDao.deleteBoard(conn, boardNo);
				
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
				
		return result;
	}

	/** 게시글 검색
	 * @param condition
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public List<Board> searchBoard(int condition, String query) throws Exception {
		Connection conn = getConnection();
		
		List<Board> boardList = bDao.searchBoard(conn, condition, query);
		
		close(conn);
				
		return boardList;
	}


	
	
}
