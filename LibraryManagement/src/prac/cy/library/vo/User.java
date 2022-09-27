package prac.cy.library.vo;

/**
 * @author user1
 *
 */
public class User {
		// TB_USER
		private int userNo;
		private String userId;
		private String userPw;
		private String userName;
		private String userPhone;
		private String userEmail;
		private String identityCode;
		private String identityName;
		private String statusCode;
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

		public String getUserPhone() {
			return userPhone;
		}


		public void setUserPhone(String userPhone) {
			this.userPhone = userPhone;
		}


		public String getUserEmail() {
			return userEmail;
		}


		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
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

		

		public String getStatusCode() {
			return statusCode;
		}


		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
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
