package prac.cy.user.view;

import java.util.Scanner;

public class UserView {
	private Scanner sc = new Scanner(System.in);
	
	public void userMenu() {
		System.out.println("로그인 완료");
		System.out.println("종료하려면 0");
		int input = sc.nextInt();
		sc.nextLine();
		
	}
}
