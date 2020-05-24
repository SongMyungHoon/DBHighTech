package k26_DBProject3rdExam;

import java.util.*;

public class K26_Menu {
	private static final String k26_dbName = "dbproject_3rd_exam";
	private static final String k26_tblName = "item_tbl";
	private static K26_DataBaseDAO k26_dbDAO = new K26_DataBaseDAO();
	private static List<K26_Item> k26_itemList = new ArrayList<>();
	private static StringBuilder k26_stringBuilder = new StringBuilder();
	private static Scanner k26_scanner = new Scanner(System.in);
	private static Map<String, String> k26_ItemFieldType = new HashMap<>();
	
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
			switch(k26_scanner.nextLine().trim()) {
				case "1": {	// 1. 물품 목록 조회
					k26_whileLoop(K26_Menu::k26_itemDataLoad);
					break;
				}	// end case "1"
				case "2": {	// 2. 물품 비교하기
					k26_whileLoop(K26_Menu::k26_itemCompare);
					break;
				}	// end case "2"
				case "3": { // 3. 물품 수정하기
					k26_whileLoop(K26_Menu::k26_revisedItem);
					break;
				}	// end case "3"
				case "4": { // 4. 물품 입력하기
					k26_whileLoop(K26_Menu::k26_insertItem);
					break;
				}	// end case "4"
				case "5": {	// 5. 물품 삭제하기
					k26_whileLoop(K26_Menu::k26_deleteItem);
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
	
	public static void k26_whileLoop(K26_VoidFunctionOperator k26_function) {
		boolean k26_menuExecute = true;
		while(k26_menuExecute) {
			if(k26_function.k26_call() == -1) {
				
			}
			
			System.out.print("상위 메뉴로 가시려면 -1을 입력하세요 : ");
			String menuKey = k26_scanner.nextLine().trim(); 
			switch (menuKey) {
				case "-1": {
					System.out.println("상위 메뉴로 이동합니다.\n");
					k26_menuExecute = false;
					break;
				}
				default:{
					System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
					continue;
				}	// end default
			}	// end switch
		}	// end while
	}	// k26_whileLoop end
	
	public static void k26_itemListPrint() {
		if(!k26_itemList.isEmpty()) {
			System.out.println(String.format("|%s|%s|%s|%s|%s|%s|%s| ", "No", "이름", "무게 (g)",
					"화면 (inch)", "디스크용량 (GB)", "비고", "가격 (만원)"));
			k26_itemList.stream().forEach(System.out::println);
		} else {
			System.out.println("현재 Database에 저장된 물품 데이터가 없습니다.");
		}
	}
	
	public static int k26_itemDataLoad() {
		k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by no");
		k26_itemListPrint();
		boolean k26_repeat = true;
		while(k26_repeat) {
			System.out.print("특정 항목 기준으로 정렬하시려면 1, 상위메뉴로 되돌아가시려면 -1 을 입력하세요 : ");
			String k26_selectMenu = k26_scanner.nextLine().trim();
			// 특정 항목 기준으로 정렬하는 경우
			if(k26_selectMenu == "1") {
				System.out.println("정렬할 항목을 대소문자를 구분하여 정확히 입력해 주세요");
				System.out.println("선택 가능 항목: name, weight, displaySize, diskVolume, price");
				// 정렬할 column 값 입력 받아 orderBy에 저장
				String orderBy = k26_scanner.nextLine().trim();
				switch(orderBy) {
					case "name": {
						// "name" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
						k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by " + orderBy);
						k26_itemListPrint();	// 정렬된 결과를 출력
						break;
					}	// case "name" end
					case "weight": {
						// "weight" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
						k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by " + orderBy);
						k26_itemListPrint();	// 정렬된 결과를 출력
						break;
					}	// case "weight" end
					case "displaySize": {
						// "display_size" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
						k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by display_size");
						k26_itemListPrint();	// 정렬된 결과를 출력
						break;
					}	// case "displaySize" end
					case "diskVolume": {
						// "display_size" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
						k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by disc_volume");
						k26_itemListPrint();	// 정렬된 결과를 출력
						break;
					}	// case "diskVolume" end
					case "price": {
						// "price" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
						k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by " + orderBy);
						k26_itemListPrint();	// 정렬된 결과를 출력
						break;
					}	// case "price" end
					default : {	// 예외 처리
						System.out.println("잘못된 입력입니다.");
						// 잘못된 입력인 경우 다시 반복
						continue;
					}	// default end
				}	// switch - case end
				// while 반복 종료 설정
				k26_repeat = false;
				// 번호 순으로 정렬 초기화하여 itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by no");
			} else if(k26_selectMenu == "-1") {
				k26_repeat = false;
			} else {
				System.out.println("잘못 입력하셨습니다.");
				continue;
			}	// if end
		}	// while end
		return -1;
	}	// k26_itemDataLoad method end
	
	public static void k26_itemCompare() {
		k26_itemListPrint();
	}
	
	public static void k26_revisedItem() {
		k26_itemListPrint();
		System.out.print("수정할 물품의 No를 입력하세요 : ");
		String itemNo = k26_scanner.nextLine().trim();
		System.out.println("수정할 수 있는 항목");
		System.out.println("name, weight, displaySize, diskVolume, etc, price");
		System.out.println("잘못 입력하는 경우 수정 메뉴 처음으로 되돌아갑니다.");
		System.out.println("수정할 항목을 입력하세요 : ");
		String revisedColumn = k26_scanner.nextLine().trim();
		System.out.print("수정할 값을 입력하세요 : ");
		String setVal = k26_scanner.nextLine().trim();
		k26_stringBuilder.append("update ").append(k26_tblName).append(" set ").append(revisedColumn).append(" = ");
		k26_ItemFieldType = k26_itemList.get(0).k26_getFieldList();
		if(k26_ItemFieldType.get(revisedColumn).equals("String")) {
			// "update item_tbl set revisedColumn = 'setVal' where no = itemNo"
			k26_stringBuilder.append("'").append(setVal).append("'");
		} else {
			// "update item_tbl set revisedColumn = setVal where no = itemNo"
			k26_stringBuilder.append(setVal);
		}
		k26_stringBuilder.append(" where no = ").append(itemNo);
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		k26_stringBuilder.setLength(0);
		k26_itemListPrint();
	}
	
	public static void k26_insertItem() {
		System.out.println("새로운 물품을 등록합니다.");
		k26_stringBuilder.append("insert into item_tbl ")
		   .append("(name, weight, display_size, disk_volume, etc, price) ")
		   .append("values ").append("('");
		System.out.print("물품 이름을 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine().trim()).append("', ");
		System.out.print("물품 무게를 g 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine().trim()).append(", ");
		System.out.print("물품의 화면 크기를 inch 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine().trim()).append(", ");
		System.out.print("물품의 디스크 용량을 GB 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine().trim()).append(", '");
		System.out.print("물품의 비고 내용을 입력 후 엔터를 눌러주세요 : ");
		String k26_input = k26_scanner.nextLine().trim();
		k26_stringBuilder.append(k26_input.isEmpty()? " ":k26_input).append("', ");
		System.out.print("물품의 가격을 만원 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine().trim()).append(")");
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		k26_stringBuilder.setLength(0);
		k26_itemListPrint();
	}
	
	public static void k26_deleteItem() {
		k26_itemListPrint();
		System.out.print("삭제할 물품의 번호를 입력하세요 : ");
		String itemNo = k26_scanner.nextLine().trim();
		k26_stringBuilder.append("delete from ").append(k26_tblName).append(" where no = ").append(itemNo);
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		k26_stringBuilder.setLength(0);
		k26_itemListPrint();
	}
}
