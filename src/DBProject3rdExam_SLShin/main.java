package DBProject3rdExam_SLShin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		welcome();
	}

	public static void welcome() {
		Scanner sc = new Scanner(System.in);

		System.out.println("");
		System.out.println("=========  W E L C O M E  =========");
		System.out.println("|          Laptop  World          |");
		System.out.println("+=================================+");
		System.out.println("|   [1] 메뉴 보기      [0] 종료하기   |");
		System.out.println("+=================================+");
		System.out.println(" 입력 >>>");
		while (true) {
			int m = sc.nextInt();
			if (m == 1) {
				menu();
				break;
			} else if (m == 0) {
				end();
				break;
			} else {
				System.out.println("잘 못 누르셨습니다. 다시 눌러주세요.");
				continue;
			}
		}
	}

	public static void end() {
		System.out.println("+=================================+");
		System.out.println("|            GOOD BYE~!|          |");
		System.out.println("|          See you again~         |");
		System.out.println("+=================================+");
	}

	public static void menu() {
		Scanner sc = new Scanner(System.in);

		System.out.println(" _________________________________");
		System.out.println("|              M E N U            |");
		System.out.println("+=================================+");
		System.out.println("|        [1]   제품 비교하기          |");
		System.out.println("|        [2]   제품 수정하기          |");
		System.out.println("|        [3]   제품 입력하기          |");
		System.out.println("|        [4]   제품 삭제하기          |");
		System.out.println("+=================================+");
		System.out.println("|        [0]   돌아가기                |");
		System.out.println("+=================================+");
		menuSelect();
	}

	public static void menuSelect() {
		Scanner sc = new Scanner(System.in);

		System.out.println(" 입력 >>>");

		int m = sc.nextInt();

		switch (m) {
		case 1:
			compare();
			System.out.println("");
			break;
		case 2:
			alter();
			break;
		case 3:
			insert();
			break;
		case 4:
			delete();
			break;
		case 0:
			welcome(); // 이전으로
			break;
		default:
			System.out.println(">>> 에러입니다. 다시 누르세요");
			menuSelect(); // 다시 선택
		}
	}

	public static void compare() {
		Scanner sc = new Scanner(System.in);

		System.out.println(">>> 제품을 비교합니다.");
		System.out.println(" ___________________________________________________________________________");
		System.out.println("|                              Prooduct List                                |");
		query("select * from product");
		System.out.println("|  [1]  제품 비교     [0]  이전으로   |");
		System.out.println("+=================================+");
		System.out.println(" 입력 >>>");

		int m = sc.nextInt();

		if (m == 0) {
			System.out.println(">>> 다시 메뉴를 보여드릴게요");
			menu();
		} else if (m == 1) {
			compare2();
		} else {
			System.out.println(">>> 에러입니다. 다시 누르세요");
			compare();
		}
	}

	public static void compare2() {
		Scanner sc = new Scanner(System.in);

		List<String> arr = new ArrayList<>();
		String com = "select * from product where No = "; // 쿼리문 선언
		String lis = null;

		System.out.println("+=================================+");
		System.out.println("|     비교 할 제품 2개를 고르세요     |");
		System.out.println("+=================================+");
		System.out.println(" 입력 >>>");

		int compare; // 변수 선언
		for (int i = 0; i < 2; i++) { // 입력받은 비교할 제품 2개 no를 배열에 넣기 0번 1번
			compare = sc.nextInt();
			if (compare <= 0 || compare > 10) {
				System.out.println("1번 ~ 10번까지만 선택하세요");
				compare2();
			} else {
				arr.add(Integer.toString(compare));
			}
		}

		for (int j = 0; j < arr.size(); j++) { // 사이즈 0,1 2회 반복
			if (j == arr.size() - 1) { // 마지막순서면 리스트의 값만, 쿼리문 com에 더하고,
				com += arr.get(j);
			} else { // 마지막이 아니면 쿼리문 com에 리스트 값 + or no 더하기
				com += arr.get(j) + " or No = ";
			}
		}

		for (String lists : arr) {
			lis = lists;
			System.out.println(">>>>>>>>> " + lis + "번 제품, 비교 리스트에 포함");
		}

		while (true) {
			System.out.println(" ___________________________________________________________________________");
			System.out.println("|                                비교 List                                   |");

			query(com); // 비교하고 싶은 메뉴들 보기

			System.out.println("|  [1]  제품 추천     [0]  이전으로   |");
			System.out.println("+=================================+");
			System.out.println(" 입력 >>>");

			int m = sc.nextInt();

			if (m == 0) {
				System.out.println("");
				System.out.println(">>> 다시, 우리 제품 리스트 보여드릴게요");
				compare();
				break;
			} else if (m == 1) {
				System.out.println("");
				System.out.println(">>> 다음 제품을 추천합니다");
				recommend(arr.get(0), arr.get(1));
				break;
			} else {
				System.out.println("");
				System.out.println(">>> 에러입니다. 다시, 우리 제품 리스트 보여드릴게요");
				continue;
			}
		}
	}

	public static void recommend(String str1, String str2) {
		Scanner sc = new Scanner(System.in);
		System.out.println(">>> " + str1 + "번 제품, " + str2 + "번 제품");
		String ppr = "update product set Point = Point + 20 where No = "; // 쿼리문

		int com1 = Integer.parseInt(str1); // 추천할 제품 1
		int com2 = Integer.parseInt(str2); // 추천할 제품 2

		query2(ppr + com1 + " or No = " + com2 + " order by Weight limit 1"); // 무게비교
		query2(ppr + com1 + " or No = " + com2 + " order by Display desc limit 1"); // 화면비교
		query2(ppr + com1 + " or No = " + com2 + " order by Capacity desc limit 1"); // 용량비교
		query2("update product set Point = Point + 40 where No = " + com1 + " or No = " + com2
				+ " order by Weight limit 1"); // 가격 비교

		System.out.println(" ___________________________________________________________________________");
		System.out.println("|                             Recommend Product                             |");
		System.out.println("|                          최고의 가성비 노트북은????                         |");
		System.out.println("|===========================================================================|");
		query3("select * from product order by Point desc limit 1;"); // 포인트 높은 제품 출력
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("|                    우리 Laptop World 의 자체 평가 기준입니다.                |");
		System.out.println("| [1] 가벼움 20점     [2] 큰 화면 20점      [3] 대용량 20점     [4] 가격 저렴 40점    |");
		System.out.println("+===========================================================================+");
		query2("update product set Point = 0"); // 다시 포인트 0으로
		System.out.println("|  >>> 더 비교하시겠습니까?         |");
		System.out.println("|  [1]  제품 비교     [0]  이전메뉴   |");
		System.out.println("+=================================+");
		System.out.println("|  [2]  종료 하기                         |");
		System.out.println("+=================================+");
		System.out.println(" 입력 >>>");

		int m = sc.nextInt();

		while (true) {

			if (m == 0) {
				System.out.println("");
				menu();
				break;
			} else if (m == 1) {
				System.out.println("");
				compare();
				break;
			} else if (m == 2) {
				end();
				break;
			} else {
				System.out.println("");
				System.out.println(">>> 에러입니다. 다시, 우리 제품 리스트 보여드릴게요");
				System.out.println("");
				compare();
				break;
			}
		}

	}

	public static void alter() {
		Scanner sc = new Scanner(System.in);

		System.out.println(">>> 제품을 수정합니다.");
		System.out.println(">>> 현재 제품 리스트입니다.");
		query("select * from product"); // 쿼리문

		System.out.println(">>> 수정 할 No 선택하세요>>>");
		int no = sc.nextInt();
		System.out.println(">>> 수정 할 Name 입력>>>");
		String name = sc.next();
		System.out.println(">>> 수정 할 Weight 입력>>>");
		int wei = sc.nextInt();
		System.out.println(">>> 수정 할 Display 입력>>>");
		int dis = sc.nextInt();
		System.out.println(">>> 수정 할 Capacity 입력>>>");
		int cap = sc.nextInt();
		System.out.println(">>> 수정 할 Price 입력>>>");
		int pri = sc.nextInt();

		query2("update product set Name = \'" + name + "'" + ", Weight = " + wei + ", Display = " + dis
				+ ", Capacity = " + cap + ", Price = " + pri + " where No = " + no);

		System.out.println("");
		System.out.println(">>> 수정된 제품 리스트입니다.");
		query("select * from product");
		System.out.println("|  [1]  다시 수정     [0]  이전으로   |");
		System.out.println("+=================================+");
		System.out.println("|  [2]  종료 하기                         |");
		System.out.println("+=================================+");
		alter2();

	}

	public static void alter2() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" 입력 >>>");

		int m = sc.nextInt();

		if (m == 0) {
			menu();
		} else if (m == 1) {
			System.out.println(">>> 다시 제품을 수정합니다.");
			alter();
		} else if (m == 2) {
			end();
		} else {
			System.out.println(">>> 에러입니다. 다시 누르세요");
			System.out.println("");
			alter2();
		}
	}

	public static void insert() {
		Scanner sc = new Scanner(System.in);

		System.out.println(">>> 제품을 입력합니다.");
		System.out.println(">>> 현재 제품 리스트입니다.");
		query("select * from product");

		System.out.println(">>> 추가 할 Name 입력>>>");
		String name = sc.next();
		System.out.println(">>> 추가 할 Weight 입력>>>");
		int wei = sc.nextInt();
		System.out.println(">>> 추가 할 Display 입력>>>");
		int dis = sc.nextInt();
		System.out.println(">>> 추가 할 Capacity 입력>>>");
		int cap = sc.nextInt();
		System.out.println(">>> 추가 할 Price 입력>>>");
		int pri = sc.nextInt();

		query2("insert into product(Name, Weight, Display, Capacity, etc, Price, point) " + "values('" + name + "', "
				+ wei + ", " + dis + ", " + cap + " ," + "'NA', " + pri + ", " + "0)");

		System.out.println("");
		System.out.println(">>> 수정된 제품 리스트입니다.");
		query("select * from product");
		System.out.println("|  [1]  추가 하기     [0]  이전으로   |");
		System.out.println("+=================================+");
		System.out.println("|  [2]  종료 하기                         |");
		System.out.println("+=================================+");
		insert2();

	}

	public static void insert2() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" 입력 >>>");

		int m = sc.nextInt();

		if (m == 0) {
			menu();
		} else if (m == 1) {
			System.out.println(">>> 제품을 더 추가합니다.");
			insert();
		} else if (m == 2) {
			end();
		} else {
			System.out.println(">>> 에러입니다. 다시 누르세요");
			System.out.println("");
			insert2();
		}
	}

	public static void delete() {
		Scanner sc = new Scanner(System.in);

		System.out.println(">>> 제품을 삭제합니다.");
		System.out.println(">>> 현재 제품 리스트입니다.");
		query("select * from product");

		System.out.println(">>> 삭제 할 No 입력>>>");
		int no = sc.nextInt();

		query2("delete from product where No = " + no);
		System.out.println("");
		System.out.println(">>> 수정된 제품 리스트입니다.");
		query("select * from product");
		System.out.println("|  [1]  삭제 하기     [0]  이전으로   |");
		System.out.println("+=================================+");
		System.out.println("|  [2]  종료 하기                         |");
		System.out.println("+=================================+");
		delete2();
	}

	public static void delete2() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" 입력 >>>");

		int m = sc.nextInt();

		if (m == 0) {
			menu();
		} else if (m == 1) {
			System.out.println(">>> 제품을 더 삭제합니다.");
			delete();
		} else if (m == 2) {
			end();
		} else {
			System.out.println(">>> 에러입니다. 다시 누르세요");
			System.out.println("");
			delete2();
		}
	}

	// 쿼리 메소드
	public static void query(String str) {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String id = "root";
		String password = "895623";

		System.out.println("+===========================================================================+");
		System.out.printf("| %3s |    %s   |  %4s  |  %4s  |  %4s  |   %s   |   %s   |\n", "No", "Name", "Weight",
				"Display", "Capacity", "etc", "Price");
		System.out.println("+===========================================================================+");

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_project?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&useSSL=false",
					id, password);
			statement = connection.createStatement();

			if (statement.execute(str)) {
				resultSet = statement.getResultSet();
			}

			while (resultSet != null && resultSet.next()) {
				String str1 = resultSet.getString(1);
				String str2 = resultSet.getString(2);
				String str3 = resultSet.getString(3);
				String str4 = resultSet.getString(4);
				String str5 = resultSet.getString(5);
				String str6 = resultSet.getString(6);
				String str7 = resultSet.getString(7);
				String str8 = resultSet.getString(8); // point

				System.out.printf("| %3s | %-9s |  %5sg  |  %3sinch  |   %4sgb   |  %4s   |  %3s만원   |\n", str1, str2,
						str3, str4, str5, str6, str7);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	// 쿼리 메소드2 - 수정하는 메소드
	public static void query2(String str) {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String id = "root";
		String password = "895623";

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_project?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&useSSL=false",
					id, password);
			statement = connection.createStatement();

			if (statement.execute(str)) {
				resultSet = statement.getResultSet();
			}

			while (resultSet != null && resultSet.next()) {
				String str1 = resultSet.getString(1); // no
				String str2 = resultSet.getString(2); // name
				String str3 = resultSet.getString(3); // weight
				String str4 = resultSet.getString(4); // display
				String str5 = resultSet.getString(5); // capacity
				String str6 = resultSet.getString(6); // etc
				String str7 = resultSet.getString(7); // price
				String str8 = resultSet.getString(8); // point

			}

		} catch (NullPointerException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 쿼리메소드3
	public static void query3(String str) {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String id = "root";
		String password = "895623";

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_project?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&useSSL=false",
					id, password);
			statement = connection.createStatement();

			if (statement.execute(str)) {
				resultSet = statement.getResultSet();
			}

			while (resultSet != null && resultSet.next()) {
				String str1 = resultSet.getString(1);
				String str2 = resultSet.getString(2);
				String str3 = resultSet.getString(3);
				String str4 = resultSet.getString(4);
				String str5 = resultSet.getString(5);
				String str6 = resultSet.getString(6);
				String str7 = resultSet.getString(7);
				String str8 = resultSet.getString(8); // point

				System.out.printf("| %3s | %-9s |  %5sg  |  %3sinch  |   %4sgb   |  %4s   |  %3s만원   |\n", str1, str2,
						str3, str4, str5, str6, str7);
				System.out.println("|===========================================================================|");
				System.out.println("|                                                                           |");
				System.out.printf("|        <<<<<<<<<<<<<<<   추천!!!   %3s 점입니다!   >>>>>>>>>>>>>>>         |\n", str8);
				System.out.println("|                                                                           |");
				System.out.println("|===========================================================================|");
				System.out.println("|___________________________________________________________________________|");

			}

		} catch (NullPointerException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
