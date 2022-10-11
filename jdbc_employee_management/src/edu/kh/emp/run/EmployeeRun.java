package edu.kh.emp.run;

import edu.kh.emp.view.EmployeeView;

// Run : 실행용 클래스로 메인 메서드가 존재함!
public class EmployeeRun {
	public static void main(String[] args) {
		
//		EmployeeView view = new EmployeeView();
//		view.displayMenu();
		
		new EmployeeView().displayMenu();
	}
}
