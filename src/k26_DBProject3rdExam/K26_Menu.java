package k26_DBProject3rdExam;

import java.util.*;
import java.lang.reflect.Field;

public class K26_Menu {
	private static final String k26_dbName = "dbproject_3rd_exam";
	private static final String k26_tblName = "item_tbl";
	private static K26_DataBaseDAO k26_dbDAO = new K26_DataBaseDAO();
	private static List<K26_Item> itemList = new ArrayList<>();
	private static StringBuilder sb = new StringBuilder();
	private static Scanner sc = new Scanner(System.in);
	private static K26_VoidFunctionOperator function;
	
	public static void k26_initialMenu() {
		k26_dbDAO.k26_setDBName(k26_dbName);
		k26_dbDAO.k26_connectDB();
		k26_dbDAO.k26_setDB();
		String[] k26_initMenuLineArray = {
				"### 메뉴 ###", "1. 물품 목록 조회"
				, "2. 물품 비교하기", "3. 물품 수정하기"
				, "4. 물폼 입력하기", "5. 물품 삭제하기"
				};
		boolean k26_menuExecute = true;
		List<String> k26_initMenuList = Arrays.asList(k26_initMenuLineArray);
		while(k26_menuExecute) {
			k26_initMenuList.stream().forEach(System.out::println);
			System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
			switch(sc.next()) {
				case "1": {	// 1. 물품 목록 조회
					function = K26_Menu::k26_itemListPrint;
					k26_whileLoop(function);
					break;
				}	// end case "1"
				case "2": {	// 2. 물품 비교하기
					function = K26_Menu::k26_itemCompare;
					k26_whileLoop(function);
					break;
				}	// end case "2"
				case "3": { // 3. 물품 수정하기
					function = K26_Menu::k26_revisedItem;
					k26_whileLoop(function);
					break;
				}	// end case "3"
				case "4": { // 4. 물품 입력하기
					function = K26_Menu::k26_insertItem;
					k26_whileLoop(function);
					break;
				}	// end case "4"
				case "5": {	// 5. 물품 삭제하기
					function = K26_Menu::k26_deleteItem;
					k26_whileLoop(function);
					break;
				}
				case "0": {  // 0. 초기 메뉴로 이동
					System.out.println("\n초기 메뉴로 돌아갑니다.\n");
					k26_menuExecute = false;
					break;
				}	// end case "0"
				default: {
					System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
					System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
					continue;
				}	// end default
			}	// end switch
		}	// end while
	}	// end k26_initialMenu
	
	public static void k26_whileLoop(K26_VoidFunctionOperator function) {
		boolean k26_menuExecute = true;
		while(k26_menuExecute) {
			function.call();
			System.out.print("다시 반복하시려면 1, 상위 메뉴로 가시려면 -1을 입력하시오 : ");
			String menuKey = sc.next(); 
			switch (menuKey) {
				case "1" : {
					continue;
				}
				case "-1": {
					System.out.println("상위 메뉴로 이동합니다.\n");
					k26_menuExecute = false;
					break;
				}
				default:{
					throw new IllegalArgumentException("Unexpected value: " + menuKey);
				}	// end default
			}	// end switch
		}	// end while
	}
	public static void k26_itemListPrint() {
		System.out.println(String.format("|%s|%s|%s|%s|%s|%s|%s| ", "No", "이름", "무게 (g)",
				"화면 (inch)", "디스크용량 (GB)", "비고", "가격 (만원)"));
		itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName);
		itemList.stream().forEach(System.out::println);
	}
	
	public static void k26_itemCompare() {
		k26_itemListPrint();
	}
	
	public static void k26_revisedItem() {
		k26_itemListPrint();
		System.out.print("수정할 물품의 No를 입력하세요 : ");
		String itemNo = sc.nextLine();
		System.out.print("수정할 항목을 입력하세요 : ");
		String revisedColumn = sc.nextLine();
		System.out.print("수정할 값을 입력하세요 : ");
		String setVal = sc.nextLine();
//		itemList.get(Integer.parseInt(itemNo)).getClass().getName();
		String query = sb.append("update ").append(k26_tblName)
				.append(" set ")
				.append(revisedColumn).append(" = '").append(setVal).append("'")
				.append(" where no = ").append(itemNo).toString();
		sb.setLength(0);
		k26_dbDAO.k26_executeQuery(query);
		k26_itemListPrint();
	}
	
	public static void k26_insertItem() {
		System.out.println("새로운 물품을 등록합니다.");
		sb.append("insert into item_tbl ")
		   .append("(name, weight, display_size, disk_volume, etc, price) ")
		   .append("values ").append("('");
		System.out.print("물품 이름을 입력 후 엔터를 눌러주세요 : ");
		sc.nextLine();
		String input = sc.nextLine();
		sb.append(input).append("', ");
		System.out.print("물품 무게를 g 단위로 입력 후 엔터를 눌러주세요 : ");
		input = sc.nextLine();
		sb.append(input).append("', ");
		System.out.print("물품의 화면 크기를 inch 단위로 입력 후 엔터를 눌러주세요 : ");
		sb.append(sc.nextLine()).append(", ");
		System.out.print("물품의 디스크 용량을 GB 단위로 입력 후 엔터를 눌러주세요 : ");
		sb.append(sc.nextLine()).append(", ");
		System.out.print("물품의 비고 내용을 입력 후 엔터를 눌러주세요 : ");
		sb.append(sc.nextLine()).append(", ");
		System.out.print("물품의 가격을 만원 단위로 입력 후 엔터를 눌러주세요 : ");
		sb.append(sc.nextLine().isEmpty()? " ":sc.).append(")");
		String insertQuery = sb.toString();
		sb.setLength(0);
		k26_dbDAO.k26_executeQuery(insertQuery);
		k26_itemListPrint();
	}
	
	public static void k26_deleteItem() {
		k26_itemListPrint();
		System.out.print("수정할 물품의 No를 입력하세요 : ");
		String itemNo = sc.nextLine();
		String query = sb.append("delete from ").append(k26_tblName)
				.append(" where no = ").append(itemNo).toString();
		sb.setLength(0);
		k26_dbDAO.k26_executeQuery(query);
		k26_itemListPrint();
	}
}
