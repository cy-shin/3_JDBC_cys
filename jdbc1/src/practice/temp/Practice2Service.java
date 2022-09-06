package practice.temp;

import java.util.Scanner;

public class Practice2Service {
	
	public Practice2Service() {};
	Scanner sc = new Scanner(System.in);
	
	public String selectDeptTitle(String input) {

		String sql = "SELECT EMP_NAME 이름, DEPT_TITLE 부서명, SALARY 급여"
				+ "FROM EMPLOYEE JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID) "
				+ "WHERE EMP_NAME = \'" + input + "\'";
		return sql;
	}
	
}
