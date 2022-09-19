package prc.jdbc.model.vo;

// VALUE OBJECT 데이터를 보관?
public class Book {
	
	// 멤버변수 선언
	private int bookNo;
	private String callNo;
	private String bookName;
	private String author;
	private String publisher;
	private String location;
	private String availability;
	private String dueDate;
	
	// 기본 생성자
	public Book() {}
	
	// 매개변수 생성자 (callNo, bookName, author, publisher, location) callNo, bookName, author, publisher, location만 입력받음
	public Book(String callNo, String bookName, String author, String publisher, String location) {
		super();
		this.callNo = callNo;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.location = location;
	}
	
	
	// 매개변수 생성자 ALL
	public Book(int bookNo, String callNo, String bookName, String author, String publisher, String location,
			String availability, String dueDate) {
		super();
		this.bookNo = bookNo;
		this.callNo = callNo;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.location = location;
		this.availability = availability;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	
	@Override
	public String toString() {
		return "BookVo [bookNo=" + bookNo + ", callNo=" + callNo + ", bookName=" + bookName + ", author=" + author
				+ ", publisher=" + publisher + ", location=" + location + ", availability=" + availability
				+ ", dueDate=" + dueDate + "]";
	}
	
	
}
