package prac.cy.library.vo;


// Book 객체
public class Book {
	private int bookNo;
	private String callNo;
	private String topicCode;
	private String topicName;
	private String bookName;
	private String author;
	private String publisher;
	private String locCode;
	private String locName;
	private String availCode;
	private String availName;
	private String dueDate;
	
	// 기본 생성자
	public Book() {}
	
	// 매개변수 생성자1
	public Book(String callNo, String topicName, String bookName, String author, String publisher, String locName, String availName, String dueDate) {
		super();
		this.callNo = callNo;
		this.topicName = topicName;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.locName = locName;
		this.availName = availName;
		this.dueDate = dueDate;
	}
	
	// 매개변수 생성자2
	public Book(int bookNo, String callNo, String topic, String bookName, String author, String publisher, String loc, String avail, String dueDate) {
		super();
		this.bookNo = bookNo;
		this.callNo = callNo;
		this.topicName = topic;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.locName = loc;
		this.availName = avail;
		this.dueDate = dueDate;
	}

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

	public String getTopicName() {
		return topicName;
	}

	public void setTopic(String topicName) {
		this.topicName = topicName;
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

	public String getLocName() {
		return locName;
	}

	public void setLoc(String locName) {
		this.locName = locName;
	}

	public String getAvailCode() {
		return availCode;
	}

	public void setAvailCode(String availCode) {
		this.availCode = availCode;
	}

	public String getAvailName() {
		return availName;
	}

	public void setAvail(String availName) {
		this.availName = availName;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public void setAvailName(String availName) {
		this.availName = availName;
	}

	
	
}
