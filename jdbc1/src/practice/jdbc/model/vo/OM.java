package practice.jdbc.model.vo;

public class OM {
	
	private String bookName;
	private String author;
	private String publisher;
	
	// 기본생성자
	public OM () {}
	
	// 매개변수 생성자
	public OM(String bookName, String author, String publisher) {
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
	}

	// getter setter
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
	
	
}
