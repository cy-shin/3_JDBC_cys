package prac.cy.user.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import prac.cy.user.model.service.MsgService;
import prac.cy.user.model.service.UserService;
import prac.cy.user.vo.MsgBox;
import prac.cy.user.vo.User;

public class MsgView {
	
	MsgService service = new MsgService();
	UserView userView = new UserView();
	
	private Scanner sc = new Scanner(System.in);
	private List<MsgBox> boxList = new ArrayList<>();
	public MsgView() {}
	
	public User msgMenu(int select, User loginUser) {
		String myNo = loginUser.getUserNo();
		String myName = loginUser.getUserName();
		String myId = loginUser.getUserId();
		
		do {
			switch(select) {
//			case 0: 
//				if(!(boxList.isEmpty())) {
//					msgDetailMenu(myNo, myName, boxList);
//				}
//				break;
			case 1: 
				msgWrite(myNo, myName, "", "");
				break;
			case 2: 
				boxList = boxAll(myNo);
				boxPrinter( select, boxList );
				break;
			case 3: 
				boxList = boxRecd(myNo);
				boxPrinter( select, boxList );
				break;
			case 4: 
				boxList = boxSend(myNo);
				boxPrinter( select, boxList );
				break;
			case 5:
				boxList = boxBin(myNo);
//				boxPrinter( select, boxList );
				break;
			case 8: myInfo(myId, myName); break;
			default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
			}
			
			System.out.println("\n-----------");
			System.out.println("1. 메세지 작성 ");
			System.out.println("2. 전체 메세지");
			System.out.println("3. 받은 메세지"); // 기본값
			System.out.println("4. 보낸 메세지");
			System.out.println("5. 휴지통");
			System.out.println("8. 내 정보 ");
			System.out.println("9. 로그아웃 ");
			System.out.println("-----------");
			System.out.print("선택 > ");
			int temp = sc.nextInt();
			sc.nextLine();
			
			if(temp==0 && select!=5) { // 휴지통이 아닌 경우
				if(!(boxList.isEmpty())) {
					msgDetailMenu(myNo, myName, boxList);
				}
				break;
			}
			if(temp!=0) { // 상세 내용 조회가 아닌 경우
				select = temp;
			}
			
			if(select==9) {
				loginUser = logout(loginUser);
			}
			
		} while(select!=9);
		
		return loginUser;
	}
	
	
	/** 1. 메세지 작성
	 * @param myNo
	 * @param myName
	 */
	private void msgWrite(String myNo, String myName, String userName, String content) {
		String title;
		String text ="";
		String userNo ="";
		String msgNo ="";
		
		boolean loopMainFL = false;
		boolean userCheckFL = false; // 수신자 확인
		boolean loopFillFL = false; // 내용 입력
		boolean loopSendFL = false; // 전송 확인
		
		System.out.println("\n-----------");
		System.out.println("\n[메세지 작성]");
		System.out.println("\n-----------");
		
		if(!(userName.equals(""))) { // 답장하는 경우
			try {
				userNo = service.msgUserCheck(userName);
			} catch (Exception e) {
				System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
				System.out.println("\n[위치] 메세지 수신 회원번호 확인 과정 \n");
				e.printStackTrace();
			}
			userCheckFL = true;
		}
		
		if(!(content.equals(""))) { // 전달하는 경우
			loopFillFL = true;
		}
		
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
	
	
	/** 8. 내 정보
	 * @param myNo
	 */
	private void myInfo(String myId, String myName) {
		
		System.out.printf("이름 : %s\n", myName);
		System.out.printf("아이디 : %s\n", myId);
	}
	
	/** 9. 로그아웃
	 * @param loginUser
	 */
	private User logout(User loginUser) {
		loginUser = null;
		System.out.println("\n[알림] 로그아웃되었습니다.");
		return loginUser;
	}

	/** a to c : 박스 프린터
	 * @param select
	 * @param boxList
	 */
	private void boxPrinter(int select, List<MsgBox> boxList) {
		if(boxList.isEmpty()) System.out.println("\n메세지가 없습니다.\n");

		if(!(boxList.isEmpty())) {
			switch(select) {
			case 2: boxAllPrint(boxList); break;
			case 3: boxRecdPrint(boxList); break;
			case 4: boxSendPrint(boxList); break;
			}
			if(!(boxList.isEmpty())) {
				System.out.print("0. 상세보기");
			}
		}
		
		
	}
	/** A. 전체 메세지함
	 * @param myNo
	 */
	private List<MsgBox> boxAll(String myNo) {
		List<MsgBox> boxAllList = new ArrayList<>();
		
		try {
			System.out.println("\n-----------");
			System.out.println("\n[전체 메세지]");
			System.out.println("\n-----------");
			
			boxAllList = service.msgBoxAll(myNo);
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 보낸 메세지 조회 \n");
			e.printStackTrace();
		}
		return boxAllList;
	}
	
	/** a. 전체 메세지 목록 출력
	 * @param boxList
	 */
	private void boxAllPrint(List<MsgBox> boxAllList) {
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
	
	/** B. 받은 메세지함
	 * @param myNo
	 * @return result
	 *    0  : 결과 없음
	 *    1  : 결과 있음
	 */
	private List<MsgBox> boxRecd(String myNo) {
		List<MsgBox> boxList = null;
		
		try {
			System.out.println("\n-----------");
			System.out.println("\n[받은 메세지]");
			System.out.println("\n-----------");
			
			boxList = service.msgBoxRecd(myNo);
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 받은 메세지 조회 \n");
			e.printStackTrace();
		}
		
		return boxList;
	}
	
	/** b. 받은 메세지 목록 출력
	 * @param boxList
	 */
	private void boxRecdPrint(List<MsgBox> boxList) {
		int idx = 1;
		System.out.printf("번호|보낸사람|제목|날짜|확인\n");
		System.out.println("--------------------------------------------");
		for(MsgBox b : boxList) {
			String readFl = "";
			
			if(b.getReadFl().equals("Y")) {
				readFl = "읽음";
			}
			if(b.getReadFl().equals("F")) {
				readFl = "읽지않음";
			}
			System.out.printf("%d|%s|%s|%s|%s\n",
					idx++,
					b.getUserName(),
					b.getTitle(),
					b.getMsgDate(),
					readFl
					);
		}
		System.out.println("--------------------------------------------");
	}
	
	/** C. 보낸 메세지함
	 * @param myNo
	 */
	private List<MsgBox> boxSend(String myNo) {
		List<MsgBox> boxList = null;
		
		try {
			System.out.println("\n-----------");
			System.out.println("\n[보낸 메세지]");
			System.out.println("\n-----------");
			
			boxList = service.msgBoxSend(myNo);
			
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 보낸 메세지 조회 \n");
			e.printStackTrace();
		}
		return boxList;
	}
	
	/** c. 보낸 메세지 목록 출력
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
	
	/** B. 휴지통
	 * @param myNo
	 * @return result
	 *    0  : 결과 없음
	 *    1  : 결과 있음
	 */
	private List<MsgBox> boxBin(String myNo) {
		List<MsgBox> boxList = null;
		String boxType = "";
		String msgNo = "";
		
		try {
			System.out.println("\n-----------");
			System.out.println("\n[휴지통]");
			System.out.println("\n-----------");
			
			boxList = service.boxBin(myNo);
			boxAllPrint(boxList);
			if(!(boxList.isEmpty())) {
				System.out.println("1. 영구삭제");
				System.out.println("2. 복원하기");
				System.out.println("9. 돌아가기");
				int input = sc.nextInt();
				if(input==1 || input==2) {
					System.out.print("번호 선택 > ");
					int idx = sc.nextInt();
					sc.nextLine();
					
					idx--;
					
					boxType = boxList.get(idx).getBoxType();
					msgNo = boxList.get(idx).getMsgNo();
				}
				switch(input) {
				case 1: 
					msgDel(boxType, msgNo);
					boxList = new ArrayList<>();
					break;
				case 2: 
					binToBox(boxType, msgNo);
					boxList = new ArrayList<>();
					break;
				case 9: break;
				}
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 휴지통 조회 \n");
			e.printStackTrace();
		}
		
		return boxList;
	}
	
	/** Sub : 메세지함 하위 메뉴
	 * @param boxList
	 */
	private void msgDetailMenu(String myNo, String myName, List<MsgBox> boxList) {
		if(!(boxList.isEmpty())) {
				System.out.print("메세지 선택 > ");
				int idx = sc.nextInt();
				sc.nextLine();
				msgDetail(idx, boxList);
				
				System.out.println("--------------");
				System.out.println("1. 삭제하기");
				System.out.println("2. 전달하기");
				System.out.println("3. 답장하기");
				System.out.println("9. 뒤로가기");
				System.out.println("--------------");
				System.out.print("선택 > ");
				int input = sc.nextInt();
				sc.nextLine();
				
				idx--;
				switch(input) {
				case 1: 
					msgPutInBin(boxList.get(idx).getBoxType(), boxList.get(idx).getMsgNo());
					break;
				case 2:
					String content = boxList.get(idx).getContent();
					msgWrite(myNo, myName, "", content); break;
				case 3: 
					String userName = boxList.get(idx).getUserName();
					msgWrite(myNo, myName, userName, ""); break;
				case 9: break;
				default : System.out.println("\n[알림] 잘못된 선택입니다.\n");
				}
		}
	}
	
	/**
	 *  Sub : 메세지 상세보기
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
			content = service.msgDetail(msgNo); // content 불러오기
			
			if(boxList.get(idx).getBoxType().equals("R")) { // 읽었다고 표시하기
				service.msgReaded(msgNo);
			}
			
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
	
	/**
	 *  Sub : 메세지를 휴지통으로(임시삭제)
	 */
	public void msgPutInBin(String boxType, String msgNo) {
		int result = 0;
		try {
			if(boxType.equals("S")) {
				result = service.sendToBin(msgNo);
			}
			if(boxType.equals("R")) {
				result = service.recdToBin(msgNo);
			}
			
			if(result > 0) {
				System.out.println("\n[알림]메세지가 휴지통으로 이동되었습니다.");
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 메세지 휴지통으로 이동 \n");
			e.printStackTrace();
		}
	}
	
	/** 메세지 완전 삭제
	 * @param boxType
	 * @param msgNo
	 */
	public void msgDel(String boxType, String msgNo) {
		int result = 0;
		try {
			if(boxType.equals("S")) {
				result = service.sendDel(msgNo);
			}
			if(boxType.equals("R")) {
				result = service.recdDel(msgNo);
			}
			
			if(result > 0) {
				System.out.println("\n[알림]메세지가 삭제되었습니다.");
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 메세지 삭제 과정 \n");
			e.printStackTrace();
		}
	}
	
	/** 메세지 복원
	 * @param boxType
	 * @param msgNo
	 */
	public void binToBox(String boxType, String msgNo) {
		int result = 0;
		try {
			if(boxType.equals("S")) {
				result = service.binToSend(msgNo);
			}
			if(boxType.equals("R")) {
				result = service.binToRecd(msgNo);
			}
			
			if(result > 0) {
				System.out.println("\n[알림]메세지가 복원되었습니다.");
			}
		} catch (Exception e) {
			System.out.println("\n[알림] 일시적인 오류가 발생했습니다.\n");
			System.out.println("\n[위치] 메세지 삭제 과정 \n");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	

	
}
