package prac.cy.user.view;

import java.util.Scanner;

public class MsgView {
	private Scanner sc = new Scanner(System.in);
	
	public void msgMenu(int select, String myNo, String myName) {
		switch(select) {
		case 1: msgWrite(myNo, myName); break;
		case 2: msgBox(myNo, myName); break;
		}
	}
	
	/** 1. 메세지 작성
	 * @param myNo
	 * @param myName
	 */
	private void msgWrite(String myNo, String myName) {
		String userName;
		String title;
		String content ="";
		String temp ="";
		
		System.out.println("\n-----------");
		System.out.println("\n[메세지 작성]");
		System.out.println("\n-----------");
		System.out.print("받는사람 이름 : ");
		userName = sc.nextLine();
		System.out.println("\n-----------");
		System.out.print("제목 입력(50자 이내) : ");
		title = sc.nextLine();
		System.out.println("\n-----------");
		System.out.println("내용 입력");
		System.out.println("줄바꿈은 $, 종료는 $exit 입력");
		System.out.print("> ");
		
		boolean loopFL = true;
		
		loopFill : while(loopFL) {
			do {
				temp = sc.nextLine();
				if(!(temp.equals("$exit"))) {
					content += temp;
				}
			} while(!(temp.equals("$exit")));
			
			System.out.println("\n-----------");
			if(content.equals("") && temp.equals("$exit")) {
				System.out.println("내용이 입력되지 않았습니다. 메세지 전송을 취소할까요? (Y/N) : ");
				char agree = sc.nextLine().toUpperCase().charAt(0);
				if(agree=='Y') loopFL = false;
				if(agree=='N') {
					continue loopFill;
				}
				if(agree!='Y' && agree!='N') {
					System.out.println("\n-----------");
					System.out.println("\n[알림] Y 또는 N으로 입력해주세요.");
				}
			}
			
			System.out.println("\n---------------------");
			System.out.println("[내용 확인]");
			System.out.println("\n---------------------");
			System.out.printf("받는 사람 : %s", userName);
			System.out.println("\n---------------------");
			System.out.printf("제목 : %s", title);
			System.out.println("\n---------------------");
			for(int i=0; i<content.length(); i++) {
				if(i!=0 && i%30==0) System.out.println(" |");
				if(content.charAt(i)==('$')) {
					System.out.println();
				} else {
					System.out.print(content.charAt(i));
				}
			}
			while(true) {
				System.out.println("전송(Y/N) : ");
				char agree = sc.nextLine().toUpperCase().charAt(0);
				if(agree!='Y') {
					// service 메서드 호출
					
				}
				if(agree!='F') {
					System.out.println("\n메세지 전송이 취소되었습니다. ");
				}
				if(agree!='Y' && agree!='N') {
					System.out.println("\n-----------");
					System.out.println("\n[알림] Y 또는 N으로 입력해주세요.");
					continue;
				}
				loopFL = false;
				break;
				
			}
		}
	}

	private void msgBox(String myNo, String myName) {
		System.out.println("준비중");
	}
	
}
