package prac.cy.user.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import prac.cy.user.model.service.MsgService;
import prac.cy.user.vo.MsgBox;
import prac.cy.user.vo.User;

public class MsgView {
	
	MsgService service = new MsgService();
	
	private Scanner sc = new Scanner(System.in);
	private String input = "-1";
	
	/* boxAllList
	 * boxList(Send, Recd)
	 * 
	 * 
	 */
	
	/** 3. 받은 메세지함
	 * @param myNo
	 * @return result
	 *    0  : 결과 없음
	 *    1  : 결과 있음
	 */
	public User msgMenu(User loginUser) {
		String myNo = loginUser.getUserNo();
		String myName = loginUser.getUserName();
		List<MsgBox> boxAllList = msgBoxAll(myNo);
		System.out.println();
		
		try {
			System.out.println("\n-----------");
			System.out.println("\n[받은 메세지]");
			System.out.println("\n-----------");
			
			List<MsgBox> boxList = service.msgBoxRecd(myNo);
			
			if(!(boxList.isEmpty())) {
				boxRecdPrint(boxList);
				System.out.println("a. 상세보기");
			}
			if(boxList.isEmpty()) System.out.println("\n메세지가 없습니다.\n");
			
			do {
				System.out.println("\n-----------");
				System.out.println("1. 메세지 작성 ");
				System.out.println("2. 전체 메세지");
				System.out.println("3. 받은 메세지"); // 기본값
				System.out.println("4. 보낸 메세지");
				System.out.println("5. 내 정보 ");
				System.out.println("0. 로그아웃 ");
				System.out.println("-----------");
				System.out.print("선택 > ");
				input = sc.nextLine();
				
				switch(input) {
				case "1": msgWrite  (myNo, myName); break;
//				case "2": msgBoxAll(myNo); break;
				case "3": continue;
				case "4": msgBoxSend(myNo); break;
//				case "5": msgMyInfo (myNo); break;
				case "a": msgDetailMenu(boxList); break;
				case "0": loginUser = logout(loginUser); break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
				}
			} while(!(input.equals("0")));
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 받은 메세지 조회 \n");
			e.printStackTrace();
		}
		return loginUser;
	}
	
	
//	public User msgMenu(User loginUser) {
//		String myNo = loginUser.getUserNo();
//		String myName = loginUser.getUserName();
//		
//		do {
//			msgBoxRecd(myNo);
//			
//			System.out.println("\n-----------");
//			System.out.println("1. 메세지 작성 ");
//			System.out.println("2. 전체 메세지");
//			System.out.println("3. 받은 메세지"); // 기본값
//			System.out.println("4. 보낸 메세지");
//			System.out.println("5. 내 정보 ");
//			System.out.println("0. 로그아웃 ");
//			System.out.println("-----------");
//			System.out.print("선택 > ");
//			input = sc.nextLine();
//			
//			switch(input) {
//			case "1": msgWrite  (myNo, myName); break;
////			case "2": msgBoxAll(myNo); break;
//			case "3": msgBoxRecd(myNo); break;
//			case "4": msgBoxSend(myNo); break;
////			case "5": msgMyInfo (myNo); break;
//			case "0": loginUser = logout(loginUser); break;
//			default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
//			}
//		} while(!(input.equals("0")));
//		return loginUser;
//	}
	
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

	/** A. 받은 메세지함
	 * @param myNo
	 * @return result
	 *    0  : 결과 없음
	 *    1  : 결과 있음
	 */
	private void msgBoxRecd(String myNo) {
		
		try {
			System.out.println("\n-----------");
			System.out.println("\n[받은 메세지]");
			System.out.println("\n-----------");
			
			List<MsgBox> boxList = service.msgBoxRecd(myNo);
			
			if(!(boxList.isEmpty())) {
				boxRecdPrint(boxList);
				System.out.println("a. 상세보기");
			}
			if(boxList.isEmpty()) System.out.println("\n메세지가 없습니다.\n");
			
			msgDetailMenu(boxList);
			
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 받은 메세지 조회 \n");
			e.printStackTrace();
		}
		
	}
	
	/** B. 보낸 메세지함
	 * @param myNo
	 */
	private void msgBoxSend(String myNo) {
		try {
			System.out.println("\n-----------");
			System.out.println("\n[보낸 메세지]");
			System.out.println("\n-----------");
			
			List<MsgBox> boxList = service.msgBoxSend(myNo);
			
			if(!(boxList.isEmpty())) boxSendPrint(boxList);
			if(boxList.isEmpty()) System.out.println("\n메세지가 없습니다.\n");
			
			msgDetailMenu(boxList);
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 보낸 메세지 조회 \n");
			e.printStackTrace();
		}
	}
	
	/** C. 전체 메세지함
	 * @param myNo
	 */
	private List<MsgBox> msgBoxAll(String myNo) {
		List<MsgBox> boxAllList = new ArrayList<>();
		
		try {
			boxAllList = service.msgBoxAll(myNo);
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 보낸 메세지 조회 \n");
			e.printStackTrace();
		}
		return boxAllList;
	}
	
	/** 0. 로그아웃
	 * @param loginUser
	 */
	private User logout(User loginUser) {
		loginUser = null;
		System.out.println("\n[알림] 로그아웃되었습니다.");
		return loginUser;
	}
	
	/** 메세지 리스트 하위 메뉴
	 * @param boxList
	 */
	private void msgDetailMenu(List<MsgBox> boxList) {
		if(!(boxList.isEmpty())) {
			System.out.print("선택 > ");
			int input = sc.nextInt();
			sc.nextLine();
			switch(input) {
			case 1: 
				System.out.print("선택 > ");
				int idx = sc.nextInt();
				sc.nextLine();
				msgDetail(idx, boxList);
				
				System.out.println("--------------");
				System.out.println("1. 전달하기");
				System.out.println("0. 뒤로가기");
				System.out.println("--------------");
				System.out.print("선택 > ");
			case 0: break;
			default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
			}
		}
	}
	
	/**
	 *  메세지 상세보기
	 */
	public void msgDetail(int idx, List<MsgBox> boxList) {
		// ---------------
		// 제목
		// ---------------
		// 보낸사람 | 날짜
		// ---------------
		// 내용
		//
		// ---------------
		idx--;
		String msgNo = boxList.get(idx).getMsgNo();
		String title = boxList.get(idx).getTitle();
		String userName = boxList.get(idx).getUserName();
		String msgDate = boxList.get(idx).getMsgDate();
		
		String content = "";
		
		try {
			content = service.msgDetail(msgNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("------------------------------------------------------");
		System.out.printf(" %s\n", title);
		System.out.println("------------------------------------------------------");
		System.out.printf(" 보낸사람 : %-15s | %s\n", userName, msgDate);
		System.out.println("------------------------------------------------------");
		for(int i=0; i<content.length(); i++) {
			char letter = content.charAt(i);
			System.out.print(letter);
			if(i!=0 && i%30==0) {
				System.out.println(); // 30자마다 줄바꿈
			}
		}
		System.out.println("\n------------------------------------------------------");
	}
	
	/** 기본 메세지 리스트 출력
	 * @param boxList
	 */
	private void boxListPrint(List<MsgBox> boxList) {
		int idx = 1;
		System.out.printf("번호|보낸사람|제목|날짜\n");
		System.out.println("--------------------------------------------");
		for(MsgBox b : boxList) {
			System.out.printf("%d|%s|%s|%s\n",
					idx++,
					b.getUserName(),
					b.getTitle(),
					b.getMsgDate()
					);
		}
		System.out.println("--------------------------------------------");
	}
	
	/** 전체 메세지 목록 출력
	 * @param boxList
	 */
	private void boxAllListPrint(List<MsgBox> boxAllList) {
		int idx = 1;
		System.out.printf("번호|보낸사람|제목|날짜\n");
		System.out.println("--------------------------------------------");
		for(MsgBox b : boxAllList) {
			String temp = b.getBoxType();
			String type = "";
			switch(temp) {
			case "S": type = "보낸메세지";
			case "R": type = "받은메세지";
			}
			System.out.printf("%d|%s|%s|%s|%s\n",
					idx++,
					b.getUserName(),
					b.getTitle(),
					b.getMsgDate(),
					type
					);
		}
		System.out.println("--------------------------------------------");
	}
	
	
	/** 받은 메세지 목록 출력
	 * @param boxList
	 */
	private void boxRecdPrint(List<MsgBox> boxList) {
		int idx = 1;
		System.out.printf("번호|보낸사람|제목|날짜|확인\n");
		System.out.println("--------------------------------------------");
		for(MsgBox b : boxList) {
			System.out.printf("%d|%s|%s|%s|%s\n",
					idx++,
					b.getUserName(),
					b.getTitle(),
					b.getMsgDate(),
					b.getReadFl()
					);
		}
		System.out.println("--------------------------------------------");
	}
	
	/** 보낸 메세지 목록 출력
	 * @param boxList
	 */
	private void boxSendPrint(List<MsgBox> boxList) {
		int idx = 1;
		System.out.printf("번호|보낸사람|제목|날짜\n");
		System.out.println("--------------------------------------------");
		for(MsgBox b : boxList) {
			System.out.printf("%d|%s|%s|%s\n",
					idx++,
					b.getUserName(),
					b.getTitle(),
					b.getMsgDate()
					);
		}
		System.out.println("--------------------------------------------");
	}
	

	
}
