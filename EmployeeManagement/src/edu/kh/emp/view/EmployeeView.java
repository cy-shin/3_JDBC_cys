package edu.kh.emp.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.emp.model.dao.EmployeeDAO;
import edu.kh.emp.model.vo.Employee;

// View : 화면용 클래스로 입력(Scanner), 출력(Print 메서드) 작업을 담당함
public class EmployeeView {
	private Scanner sc = new Scanner(System.in);
	
	// DAO 객체를 생성!
	private EmployeeDAO dao = new EmployeeDAO();
	
	// 메인 메뉴
	public void displayMenu() {
		
		int input = 0;
		
		do {
			try {
				System.out.println("---------------------------------------------------------");
				System.out.println("----- 사원 관리 프로그램 -----");
				System.out.println("1. 새로운 사원 정보 추가");
				System.out.println("2. 전체 사원 정보 조회");
				System.out.println("3. 사번이 일치하는 사원 정보 조회");
				System.out.println("4. 사번이 일치하는 사원 정보 수정");
				System.out.println("5. 사번이 일치하는 사원 정보 삭제");
				System.out.println("6. 입력 받은 부서와 일치하는 모든 사원 정보 조회");
				System.out.println("7. 입력 받은 급여 이상을 받는 모든 사원 정보 조회");
				System.out.println("8. 부서별 급여 합 전체 조회");
				
				System.out.println("9. 주민등록번호가 일치하는 사원 정보 조회");
				
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 >> ");
				input = sc.nextInt();
				
				System.out.println();
				
				switch(input) {
				case 1: insertEmployee(); break;
				case 2: selectAll(); break;
				case 3: selectEmpId(); break;
				case 4: updateEmployee(); break;
				case 5: deleteEmployee(); break;
				case 6: break;
				case 7: break;
				case 8: break;

				case 9: selectEmpNo(); break;
				
				case 0: System.out.println("프로그램을 종료합니다..."); break;
				default: System.out.println("메뉴에 존재하는 번호만 입력하세요!");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("정수만 입력해주세요.");
				input = -1; // 반복문 첫 번째 바퀴에서 잘못 입력하여 프로그램이 종료되는 상황을 방지
				sc.nextLine(); // 입력버퍼에 남아있는 잘못 입력된 문자열을 제거하여 무한반복을 방지함
			}
			
		} while (input!=0);
		
	}
	
	
	/** 1. 새로운 사원 정보 추가
	 * 
	 */
	public void insertEmployee() {
		System.out.println("[사원 정보 추가]");
		
		
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();

		System.out.print("사원 이름 : ");
		String empName = sc.next();
		
		System.out.print("주민등록번호 : ");
		String empNo = sc.next();

		System.out.print("이메일 : ");
		String empEmail = sc.next();
		
		System.out.print("전화번호(-제외) : ");
		String phone = sc.next();
		
		System.out.print("부서코드(D1 ~ D9) : ");
		String deptCode = sc.next();
		
		System.out.print("직급코드(J1 ~ J7) : ");
		String jobCode = sc.next();
		
		System.out.print("급여등급(S1 ~ S6) : ");
		String salLevel = sc.next();
		
		System.out.print("급여 : ");
		int salalry = sc.nextInt();
		
		System.out.print("보너스 : ");
		double bonus = sc.nextDouble();
		
		System.out.print("사수번호 : ");
		int managerId = sc.nextInt();
		
		// 입력받은 값을 Employee 객체에 담아서 DAO로 전달
		
		Employee emp = new Employee(empId, empName, empNo, empEmail, phone,
				salalry, deptCode, jobCode, salLevel, bonus, managerId);
		
		int result = dao.insertEmployee(emp);
		// INSERT, UPDATE, DELETE 와 같은 DML구문은
		// 수행 후 테이블에 반영된 행의 개수를 반환함
		// 예) 1 row -> 1줄이 반영됨 0 row -> 반영된 행이 없음
		// 조건이 잘못된 경우, 반영된 행이 없으므로 0을 반환하게 됨
		
		if(result > 0) { // DML 구문 성공 시
			System.out.println("사원 정보 추가 성공");
			
		} else { // DML 구문 실패 시
			System.out.println("사원 정보 추가 실패...");
		}
	}
	
	/**
	 *  2. 전체 사원 정보 조회
	 */
	public void selectAll() {
		System.out.println("[전체 사원 정보 조회]");
		
		// DB에서 전체 사원의 정보를 조회하여 List<Employee> 형태로 반환하는 dao.selectAll() 메서드를 호출함
		List<Employee> empList = dao.selectAll();
		
		printAll(empList);
	}
	
	
	/**
	 *  3. 사번이 일치하는 사원 정보 조회
	 */
	public void selectEmpId() {
		System.out.println("[사번이 일치하는 사원 정보 조회]");
		
		// 사번 입력 받기
		int empId = inputEmpId();
		
		// 입력 받은 사번을 DAO의 selectEmpId() 메서드로 전달하여
		// 조회된 사원 정보를 반환받음
		Employee emp = dao.selectEmpId(empId); // 사원 한 명의 정보이므로 List가 아니라 VO를 이용해 결과를 받아옴
		
		printOne(emp);
	}
	
	/**
	 *  4. 사번이 일치하는 사원 정보 수정(이메일, 전화번호, 급여)
	 */
	public void updateEmployee() {
		System.out.println("[4. 사번이 일치하는 사원 정보 수정]");
		
		// 사번 입력 받기
		int empId = inputEmpId();
		
		System.out.print("이메일 : ");
		String email = sc.next();

		System.out.print("전화번호(-제외) : ");
		String phone = sc.next();
		
		System.out.print("급여 : ");
		int salary = sc.nextInt();
		
		// 기본생성자로 객체 생성 후 setter를 이용해서 초기화
		Employee emp = new Employee();
		emp.setEmpId(empId);
		emp.setEmail(email);
		emp.setPhone(phone);
		emp.setSalary(salary);
		
		int result = dao.updateEmployee(emp); // UPDATE(DML) -> 행의 개수를 반환함(int형)
		
		if(result>0) {
			System.out.println("사원 정보가 수정되었습니다.");
		} else {
			System.out.println("사번이 일치하는 사원이 존재하지 않습니다.");
		}
	}
	
	/**
	 *  5. 사번이 일치하는 사원 정보 삭제
	 */
	public void deleteEmployee() {
		System.out.println("[5. 사번이 일치하는 사원 정보 삭제]");
		int empId = inputEmpId();
		
		System.out.println("정말 삭제 하시겠습니까?(Y/N)");
		char input = sc.next().toUpperCase().charAt(0); // 대소문자 구별 없이 입력받을수있음~~
		if(input == 'Y') {
			int result = dao.deleteEmployee(empId);
			if(result == 1) System.out.println("삭제되었습니다.");
			else            System.out.println("사번을 확인해주세요.");
		} else {
			System.out.println("작업이 취소되었습니다.");
		}
		
	}
	
	/**
	 *  9. 주민등록번호가 일치하는 사원 정보 조회
	 */
	public void selectEmpNo() {
		System.out.println("[주민등록번호가 일치하는 사원 정보 조회]");
		System.out.print("주민등록번호 입력 : ");
		String empNo = sc.next();
		
		Employee emp = dao.selectEmpNo(empNo);
		
		printOne(emp);
	}
	
	/** (공용)전달받은 사원 List 모두 출력하기
	 *  공유중인 메서드 : 2. 전체 사원 정보 조회
	 *  				  
	 *  @param empList 
	 */
	public void printAll(List<Employee> empList) {
		if(empList.isEmpty()) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번  |   이름  |   주민 등록 번호   |         이메일         |   전화 번호   |    부서    |  직책  |  급여" );
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			for(Employee emp : empList) { 
				System.out.printf(" %2d  | %4s | %s | %20s | %-11s | %7s | %4s | %d\n",
						emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
						emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			}
		}
	}
	
	/** (공용)사번을 입력받아서 반환하는 메서드
	 * 공유중인 메서드 : 3. 사번이 일치하는 사원 정보 조회
	 * @return
	 */
	public int inputEmpId() {
		System.out.print("사번 입력 : ");
		int empId = sc.nextInt();
		sc.nextLine(); // 버퍼에 남아있는 개행문자를 제거함
		
		return empId;
	}
	
	/** (공용) 사원 1명의 정보만 출력
	 * 공유중인 메서드 : 3. 사번이 일치하는 사원 정보 조회
	 * 					 9. 주민등록번호가 일치하는 사원 정보 조회
	 * @param emp
	 */
	public void printOne(Employee emp) {
		if(emp == null) {
			System.out.println("조회된 사원 정보가 없습니다.");
			
		} else {
			System.out.println("사번  |   이름  |   주민 등록 번호   |         이메일         |   전화 번호   |    부서    |  직책  |  급여" );
			System.out.println("------------------------------------------------------------------------------------------------");
			System.out.printf(" %2d  | %4s | %s | %20s | %-11s | %7s | %4s | %d\n",
						emp.getEmpId(), emp.getEmpName(), emp.getEmpNo(), emp.getEmail(), 
						emp.getPhone(), emp.getDepartmentTitle(), emp.getJobName(), emp.getSalary());
			
		}
	}
	
}
