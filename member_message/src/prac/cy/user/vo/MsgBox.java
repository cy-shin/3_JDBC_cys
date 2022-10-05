package prac.cy.user.vo;

public class MsgBox {
	private String msgNo;
	private String title;
	private String content;
	private String userName;
	private String msgDate;
	private String sDelFl;
	private String readFl;
	private String rDelFl;
	private String boxType;
	
	public MsgBox() {}
	
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public String getsDelFl() {
		return sDelFl;
	}
	public void setsDelFl(String sDelFl) {
		this.sDelFl = sDelFl;
	}
	public String getReadFl() {
		return readFl;
	}
	public void setReadFl(String readFl) {
		this.readFl = readFl;
	}
	public String getrDelFl() {
		return rDelFl;
	}
	public void setrDelFl(String rDelFl) {
		this.rDelFl = rDelFl;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	
	
}
