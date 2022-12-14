package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Comment;
import oracle.net.aso.l;

public class CommentDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public CommentDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-query.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/** 댓글 목록 조회 DAO
	 * @param conn
	 * @param boardNo
	 * @return
	 */
	public List<Comment> selectCommentList(Connection conn, int boardNo) throws Exception {
		List<Comment> commentList = new ArrayList<>();
		
		try {
			String sql = prop.getProperty("selectCommentList");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int commentNo = rs.getInt("COMMENT_NO");
				String commentComment = rs.getString("COMMENT_CONTENT");
				int memberNo = rs.getInt("MEMBER_NO");
				String memberName = rs.getString("MEMBER_NM");
				String createDate = rs.getString("CREATE_DT");
				
				Comment comment = new Comment();
				
				comment.setCommentNo(commentNo);
				comment.setCommentContent(commentComment);
				comment.setMemberNo(memberNo);
				comment.setMemberName(memberName);
				comment.setCreateDate(createDate);
				
				commentList.add(comment);
			}
					
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return commentList;
	}

	/** 댓글 등록 서비스
	 * @param comment
	 * @return result
	 * @throws Exception
	 */
	public int insertComment(Connection conn, Comment comment) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("insertComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, comment.getCommentContent());
			pstmt.setInt(2, comment.getMemberNo());
			pstmt.setInt(3, comment.getBoardNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		return result;
	}


	/** 댓글 수정
	 * @param conn
	 * @param commentNo
	 * @param content
	 * @return
	 */
	public int updateComment(Connection conn, int commentNo, String content) throws Exception {
		int result;
		
		try {
			String sql = prop.getProperty("updateComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, content);
			pstmt.setInt(2, commentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/** 댓글 삭제
	 * @param conn
	 * @param commentNo
	 * @return
	 */
	public int updateComment(Connection conn, int commentNo) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("deleteComment");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, commentNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
}
