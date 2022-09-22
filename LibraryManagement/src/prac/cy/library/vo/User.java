package prac.cy.library.vo;

public class User {
		// TB_USER
		private int userNO;
		private String userId;
		private String userPw;
		private String userName;
		private String identityCode;
		private String identityName;
		private int lentNum;
		
		// 기본 생성자
		public User() {}

		
		// GETTER & SETTER
		public int getUserNO() {
			return userNO;
		}

		public void setUserNO(int userNO) {
			this.userNO = userNO;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserPw() {
			return userPw;
		}

		public void setUserPw(String userPw) {
			this.userPw = userPw;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
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
