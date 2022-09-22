package prac.cy.library.vo;

public class Library {
	

	// TB_LENT_RECORD
	private int bookNo;
	private int userNo;
	private String lentDate;
	private String dueDate;
	
	// TB_MEMBER
	private String userName;
	
	// TB_BOOKS
	private String bookName;
	
	// 기본 생성자
	public Library() {}
	
	
	// GETTER & SETTER
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getBookNo() {
		return bookNo;
	}


	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	public String getLentDate() {
		return lentDate;
	}


	public void setLentDate(String lentDate) {
		this.lentDate = lentDate;
	}


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
