package edu.kh.jdbc1;

import java.util.Scanner;

public class Example3Service {
	
	public Example3Service() {};
	Scanner sc = new Scanner(System.in);
	
	public void selectDeptTitle(int input) {

		String sql = "SELECT EMP_ID, EMP_NAME, DEPT_TITLE FROM EMPLOYEE JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID) WHERE DEPT_TITLE = \'" + input + "\'";
	}
	
}
