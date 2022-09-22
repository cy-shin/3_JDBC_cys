package prac.cy.library.vo;

public class Library {
	
	// TB_BOOKS
	private int bookNo;
	private String callNo;
	private String topicCode;
	private String topic;
	private String bookName;
	private String author;
	private String publisher;
	private String locCode;
	private String loc;
	private String availCode;
	private String avail;
	private String dueDate;
	
	// TB_MEMBER
	private int userNO;
	private String userId;
	private String userPw;
	private String userName;
	private String identityCode;
	private String identityName;
	private int lentNum;
	

	

	// admin flag
	private boolean adminFl;
	
	// 기본 생성자
	public Library() {}
	
	// 매개변수 생성자
	public Library(String callNo, String topic, String bookName, String author, String publisher, String loc,
			String avail, String dueDate) {
		super();
		this.callNo = callNo;
		this.topic = topic;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.loc = loc;
		this.avail = avail;
		this.dueDate = dueDate;
	}
	
	// GETTER & SETTER
	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getCallNo() {
		return callNo;
	}

	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}

	public String getTopicCode() {
		return topicCode;
	}

	public void setTopicCode(String topicCode) {
		this.topicCode = topicCode;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getLocCode() {
		return locCode;
	}

	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getAvailCode() {
		return availCode;
	}

	public void setAvailCode(String availCode) {
		this.availCode = availCode;
	}

	public String getAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public int getuserNO() {
		return userNO;
	}

	public void setuserNO(int userNO) {
		this.userNO = userNO;
	}

	public String getuserId() {
		return userId;
	}

	public void setuserId(String userId) {
		this.userId = userId;
	}

	public String getuserPw() {
		return userPw;
	}

	public void setuserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getuserName() {
		return userName;
	}

	public void setuserName(String userName) {
		this.userName = userName;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}
	
	public String getIdentityName() {
		return identityName;
	}
	
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public int getLentNum() {
		return lentNum;
	}

	public void setLentNum(int lentNum) {
		this.lentNum = lentNum;
	}
}
