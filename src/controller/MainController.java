package controller;

import java.util.List;
import java.util.Scanner;

import model.MemberDto;
import service.MemberService;
import view.MemberView;

public class MainController {

	static Scanner sc = new Scanner(System.in);
	static MemberService service = new MemberService();
	static MemberView view = new MemberView();

	public static void main(String[] args) {

		boolean isStop = false;

		while (!isStop) {
			menuDisplay();
			String choice = sc.next();
			sc.nextLine();
			switch (choice) {
			case "1" -> showAllMembers();
			case "2" -> showNameMembers();
			case "3" -> insertMembers();
			case "4" -> updateMembers();
			case "5" -> deleteNameMembers();
			case "6" -> proceedClass();
			case "9" -> {
				System.out.println("프그그램 종료");
				isStop = true;
			}
			default -> System.out.println("잘못된 입력입니다. 다시 선택하세요");
			}
		}

	}

	private static void proceedClass() {
		// TODO Auto-generated method stub
		System.out.println("수업을 진행한 회원의 이름을 입력하세요");
		String name = sc.next();
		System.out.println("수업을 진행한  회원의 전화번호를 입력하세요(xxx-xxxx-xxx)");
		String phone = sc.next();
		sc.nextLine();
		
		boolean result=service.decreaseSession(name,phone);
		
		if (result) {
			System.out.println("pt횟수 차감이 성공적을 진행되었습니다.");
		} else {
			System.out.println("pt횟수 차감에 실패했습니다. 회원정보를 올바르게 입력해주세요");
		}

	}

	private static void updateMembers() {
		// TODO Auto-generated method stub
		System.out.println("변경할 회원의 이름을 입력하세요");
		String name = sc.next();
		System.out.println("변경할 회원의 전화번호를 입력하세요(xxx-xxxx-xxx)");
		String phone = sc.next();
		sc.nextLine();

		List<MemberDto> nameMemberws = service.findMembers(name);
		if (nameMemberws.isEmpty()) {
			System.out.println("해당 이름을 가진 회원이 없습니다");
		} else {
			System.out.println("변경할 항목을 선택하세요:");
			System.out.println("1.이름  2.성별  3.나이  4.생년월일  5.전화번호  6.주소");

			String choice = sc.nextLine();
			boolean result = false;

			switch (choice) {
			case "1" -> {
				System.out.println("새로운 이름을 입력하세요");
				String newVal = sc.nextLine();
				result = service.updateMember("member_name", newVal, name, phone);

				if (result) {
					System.out.println("정보 변경이 성공적으로 완료되었습니다.");
				} else {
					System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
				}
			}
			case "2" -> {
				System.out.println("새로운 성별을 입력하세요(F/M)");
				String newVal = sc.nextLine();
				result = service.updateMember("member_gender", newVal, name, phone);

				if (result) {
					System.out.println("정보 변경이 성공적으로 완료되었습니다.");
				} else {
					System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
				}
			}
			case "3" -> {
				System.out.println("새로운 나이을 입력하세요");
				int newVal = sc.nextInt();
				result = service.updateMember("member_age", String.valueOf(newVal), name, phone);

				if (result) {
					System.out.println("정보 변경이 성공적으로 완료되었습니다.");
				} else {
					System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
				}
			}
			case "4" -> {
				System.out.println("새로운 생년월일을 입력하세요(yyyy-mm-dd)");
				String newVal = sc.nextLine();
				result = service.updateMember("member_birthday", newVal, name, phone);

				if (result) {
					System.out.println("정보 변경이 성공적으로 완료되었습니다.");
				} else {
					System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
				}
			}
			case "5" -> {
				System.out.println("새로운 전화번호를 입력하세요(xxxx-xxx-xxxx)");
				String newVal = sc.nextLine();
				result = service.updateMember("member_phone", newVal, name, phone);

				if (result) {
					System.out.println("정보 변경이 성공적으로 완료되었습니다.");
				} else {
					System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
				}
			}
			case "6" -> {
				System.out.println("새로운 주소를 입력하세요");
				String newVal = sc.nextLine();
				result = service.updateMember("member_address", newVal, name, phone);

				if (result) {
					System.out.println("정보 변경이 성공적으로 완료되었습니다.");
				} else {
					System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
				}
			}
			default -> {
				System.out.println("변경하고자 하는 정보를 올바르게 입력해주세요.");
			}

			}
		}

	}

	private static void insertMembers() {
		// TODO Auto-generated method stub
		System.out.println("이름을 입력하세요");
		String name = sc.next();
		System.out.println("성별을 입력하세요(F/M)");
		String gender = sc.next();
		System.out.println("나이를 입력하세요");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.println("생년월일을 입력하세요(yyyy-mm-dd)");
		String birthday = sc.nextLine();
		System.out.println("전화번호를 입력하세요(xxx-xxxx-xxx)");
		String phone = sc.nextLine();
		System.out.println("주소를 입력하세요");
		String address = sc.nextLine();

		System.out.println("등록할 pt패키지 번호를 입력하세요 \n" + "01번(김근육 10회 패키지), 02번(김근육 20회 패키지), 03번(김근육 50회 패키지) -> 벌크업전문\n"
				+ "04번(박재활 10회 패키지), 05번(박재활 20회 패키지), 06번(박재활 50회 패키지) -> 재활트레이닝 전문\n"
				+ "07번(김대회 10회 패키지), 08번(김대회 20회 패키지), 09번(김대회 50회 패키지) -> 선수레슨 전문\n"
				+ "10번(최지방 10회 패키지), 11번(최지방 20회 패키지), 12번(최지방 50회 패키지) -> 다이어트 전문\n ");
		int packageId = sc.nextInt();
		sc.nextLine();

		System.out.println("선택한 패키지의 횟수를 입력하세요");
		int totalSession = sc.nextInt();
		sc.nextLine();

		boolean result = service.InsertMember(name, gender, age, birthday, phone, address, packageId, totalSession);

		if (result) {
			System.out.println("회원 등록이 성공적으로 완료되었습니다.");
		} else {
			System.out.println("등록에 실패하였습니다. 입력값을 제대로 다시 입력해주세요.");
		}
	}

	private static void deleteNameMembers() {
		// TODO Auto-generated method stub
		System.out.println("삭제할 회원의 이름을 입력하세요");
		String name = sc.next();
		System.out.println("삭제할 회원의 전화번호를 입력하세요");
		String phone = sc.next();

		boolean result = service.delete(name, phone);

		if (result) {
			System.out.println("성공적으로 삭제가 완료되었습니다");
		} else {
			System.out.println("삭제가 실패했습니다. 회원의 이름과 전화번호를 다시 확인해주세요");
		}
	}

	private static void showNameMembers() {
		// TODO Auto-generated method stub

		System.out.println("조회할 회원의 이름을 입력하세요");
		String name = sc.next();

		List<MemberDto> nameMemberws = service.findMembers(name);
		if (nameMemberws.isEmpty()) {
			System.out.println("해당 이름을 가진 회원이 없습니다");
		} else {
			view.displayAllMembers(nameMemberws);
		}
	}

	private static void showAllMembers() {
		// TODO Auto-generated method stub
		List<MemberDto> members = service.getAllMembers();
		view.displayAllMembers(members);
	}

	private static void menuDisplay() {
		// TODO Auto-generated method stub
		System.out.println(
				"=================================================================================================================");
		System.out.println("1. 전체회원 조회       2. 이름으로 회원조회       3. 회원추가       4. 회원정보 변경      5. 삭제  6. 수업진행   9. 종료");
		System.out.println(
				"=================================================================================================================");

	}
}
