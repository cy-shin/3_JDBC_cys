package edu.kh.jdbc.model.vo;

public class TestVO {
	private int testNo;
	private String testTitle;
	private String testContent;
	
	// 기본생성자
	public TestVO() {}
	
	// 매개변수생성자
	public TestVO(int testNo, String testTitle, String testContent) {
		super();
		this.testNo = testNo;
		this.testTitle = testTitle;
		this.testContent = testContent;
	}
	
	// Getter & Setter
	public int getTestNo() {
		return testNo;
	}
	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}
	public String getTestTitle() {
		return testTitle;
	}
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
	public String getTestContent() {
		return testContent;
	}
	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}
	
	// toString() 오버라이딩 객체가 가진 정보를 한 줄로 출력하고 싶을 떄~
	@Override
	public String toString() {
		return "TestVO [testNo=" + testNo + ", testTitle=" + testTitle + ", testContent=" + testContent + "]";
	}
	
	
}
