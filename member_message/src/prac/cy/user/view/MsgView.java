package prac.cy.user.view;

import java.util.Scanner;

import prac.cy.user.model.service.MsgService;

public class MsgView {
	
	MsgService service = new MsgService();
	
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
		String userName ="";
		String title;
		String text ="";
		String content ="";
		String userNo ="";
		String msgNo ="";
		
		boolean loopMainFL = false;
		boolean userCheckFL = false; // 수신자 확인
		boolean loopFillFL = false; // 내용 입력
		boolean loopSendFL = false; // 전송 확인
		
		System.out.println("\n-----------");
		System.out.println("\n[메세지 작성]");
		System.out.println("\n-----------");
		
		
		LoopMain : while(!loopMainFL) { // 전체 반복문
			char agree;
			
			LoopCheck : while(!userCheckFL) { // 이름 확인 반복문
				System.out.println("받는사람 이름 입력(작성 취소 시 $cancel 입력)");
				System.out.print("> ");
				userName = sc.nextLine();
				if(userName.equals(myName)) {
					System.out.println("\n[알림] 자기 자신에게는 메세지를 보낼 수 없습니다. ");
					continue LoopCheck; // LoopCheck continue
				}
				if(userName.equals("$cancel")) {
					System.out.println("\n[알림] 메세지 작성이 취소되었습니다.");
					break LoopMain;
				}
				try {
					userNo = service.msgUserCheck(userName);
					if(userNo.equals("")) {
						System.out.println("\n[알림] 존재하지 않는 사용자입니다.\n");
						continue LoopCheck;  // LoopCheck continue
					} 
					userCheckFL = true; // 이름이 정상적으로 입력된 경우 loopCheck 종료
				} catch (Exception e) {
					System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
					System.out.println("\n[위치] 메세지 수신 회원번호 확인 과정 \n");
					e.printStackTrace();
				}
			} // LoopCheck end
			
			System.out.println("\n-----------");
			System.out.println("제목 입력(50자 이내)");
			System.out.print("> ");
			title = sc.nextLine();
			System.out.println("\n-----------");
			System.out.println("내용 입력");
			System.out.println("줄바꿈은 $, 종료는 $exit 입력");
			System.out.print("> ");
			
			
			LoopFill : while(!loopFillFL) { // 내용 입력 반복문
				do {
					text = sc.nextLine();
					if(!(text.equals("$exit"))) {
						content += text;
					}
				} while(!(text.equals("$exit")));
				
				while(true) {
					if(content.equals("") && text.equals("$exit")) {
						System.out.println("내용이 입력되지 않았습니다. 메세지 전송을 취소할까요? (Y/N) : ");
						agree = sc.nextLine().toUpperCase().charAt(0);
						if(agree=='Y') {
							break LoopMain; // 전체 반복문 종료
						}
						if(agree=='N') {
							continue LoopFill; // 내용 입력 반복문 재시작
						}
						if(agree!='Y' && agree!='N') {
							System.out.println("\n-----------");
							System.out.println("\n[알림] Y 또는 N으로 입력해주세요.");
							continue;
						}
					}
					break;
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

				System.out.println("\n---------------------");

				LoopSend : while(!loopSendFL) { // 전송
					try {
						System.out.print("\n전송(Y/N) : ");
						agree = sc.nextLine().toUpperCase().charAt(0);
						if(agree=='Y') {
							while(true) { // 난수 생성
								int tempNo = (int)(Math.random() * 9000000) + 1000000;
								String ran = tempNo + "";
								try {
									int noCheck;
									noCheck = service.makeMsgNo(ran);
									if(noCheck > 0)	{
										msgNo = ran;
										break;
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							int result = 0;

							result = service.msgWrite(msgNo, myNo, userNo, title, content);

							if(result >= 3) {
								System.out.println("\n[알림] 메세지가 전송되었습니다.\n");
							} else {
								System.out.println("\n[알림] 메세지 전송에 실패하였습니다.\n");
							}
						}
						if(agree=='N') {
							System.out.println("\n메세지 전송이 취소되었습니다. ");
						}
						if(agree=='Y' || agree =='N') {
							loopSendFL = true;
							loopMainFL = true;
							loopFillFL = true;
						}
						if(agree!='Y' && agree!='N') {
							System.out.println("\n-----------");
							System.out.println("\n[알림] Y 또는 N으로 입력해주세요.");
							continue LoopSend;
						}
					} catch (Exception e) {
						System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
						System.out.println("\n[위치] 메세지 전송 과정 \n");
						e.printStackTrace();
					} 
				} // 전송 end

			} // loopFL end
		}
	}

	private void msgBox(String myNo, String myName) {
		System.out.println("준비중");
	}
	
}
