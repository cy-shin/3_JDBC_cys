package practice;

import java.util.Scanner;

public class Practice2Service {
	
	public Practice2Service() {};
	Scanner sc = new Scanner(System.in);
	
	public String selectDeptTitle(String input) {

		String sql = "SELECT EMP_ID, EMP_NAME, DEPT_TITLE "
				+ "FROM EMPLOYEE JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID) "
				+ "WHERE EMP_NAME = \'" + input + "\'";
		return sql;
	}
	
}
