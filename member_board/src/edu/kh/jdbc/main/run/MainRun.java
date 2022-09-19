package edu.kh.jdbc.main.run;

import edu.kh.jdbc.main.view.MainView;

// 프로그램 전체를 실행하는 실행용 클래스
public class MainRun {
	public static void main(String[] args) {
		new MainView().mainMenu();
		
		// System.exit(0);
		// Java Virtual Machine을 강제로 종료, 매개변수는 정상적으로 종료시킬 경우 0, 이 외에는 오류를 의미함
		// 내부적으로 존재(컴파일러가 자동 추가함)
		
	}
}
