package prac.cy.library.vo;


// Book 객체
public class Book {
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
	
	// 기본 생성자
	public Book() {}
	
	// 매개변수 생성자1
	public Book(String callNo, String topic, String bookName, String author, String publisher, String loc, String avail, String dueDate) {
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
	
	// 매개변수 생성자2
	public Book(int bookNo, String callNo, String topic, String bookName, String author, String publisher, String loc, String avail, String dueDate) {
		super();
		this.bookNo = bookNo;
		this.callNo = callNo;
		this.topic = topic;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.loc = loc;
		this.avail = avail;
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
	
	
	
}
