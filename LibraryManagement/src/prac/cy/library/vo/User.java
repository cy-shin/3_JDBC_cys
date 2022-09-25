package prac.cy.library.vo;

public class User {
		// TB_USER
		private int userNo;
		private String userId;
		private String userPw;
		private String userName;
		private String identityCode;
		private String identityName;
		private String statusName;
		private int identityLimit;
		private int lentNum;
		private int availNum;
		
		// 기본 생성자
		public User() {}

		
		// GETTER & SETTER
		public int getUserNo() {
			return userNo;
		}

		public void setUserNo(int userNo) {
			this.userNo = userNo;
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


		public String getStatusName() {
			return statusName;
		}


		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}


		public int getIdentityLimit() {
			return identityLimit;
		}


		public void setIdentityLimit(int identityLimit) {
			this.identityLimit = identityLimit;
		}


		public int getLentNum() {
			return lentNum;
		}


		public void setLentNum(int lentNum) {
			this.lentNum = lentNum;
		}


		public int getAvailNum() {
			return availNum;
		}


		public void setAvailNum(int availNum) {
			this.availNum = availNum;
		}

		
		
}
