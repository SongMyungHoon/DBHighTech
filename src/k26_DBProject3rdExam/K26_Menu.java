package k26_DBProject3rdExam;

import java.util.*;

public class K26_Menu {
	private static final String k26_dbName = "dbproject_3rd_exam";
	private static final String k26_tblName = "item_tbl";
	private static K26_DataBaseDAO k26_dbDAO = new K26_DataBaseDAO();
	private static List<K26_Item> k26_itemList = new ArrayList<>();
	private static StringBuilder k26_stringBuilder = new StringBuilder();
	private static Scanner k26_scanner = new Scanner(System.in);
	private static Map<String, String> k26_itemFieldType = new HashMap<>();
	private static K26_Item k26_item = new K26_Item();
	
	public static void k26_initialMenu() {
		k26_dbDAO.k26_setDBName(k26_dbName);
		k26_dbDAO.k26_connectDB();
		k26_dbDAO.k26_setDB();
		String[] k26_initMenuLineArray = {
				"### 메뉴 ###", "1. 물품 목록 조회"
				, "2. 물품 비교하기", "3. 물품 수정하기"
				, "4. 물폼 입력하기", "5. 물품 삭제하기"
				};
		List<String> k26_initMenuList = Arrays.asList(k26_initMenuLineArray);
		k26_whileLoop:
		while(true) {
			k26_initMenuList.stream().forEach(System.out::println);
			System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
			switch(k26_scanner.nextLine()) {
				case "1": {	// 1. 물품 목록 조회
					k26_whileLoop(K26_Menu::k26_itemDataLoad);
					break k26_whileLoop;
				}	// end case "1"
				case "2": {	// 2. 물품 비교하기
					k26_whileLoop(K26_Menu::k26_itemCompare);
					break k26_whileLoop;
				}	// end case "2"
				case "3": { // 3. 물품 수정하기
					k26_whileLoop(K26_Menu::k26_revisedItem);
					break k26_whileLoop;
				}	// end case "3"
				case "4": { // 4. 물품 입력하기
					k26_whileLoop(K26_Menu::k26_insertItem);
					break k26_whileLoop;
				}	// end case "4"
				case "5": {	// 5. 물품 삭제하기
					k26_whileLoop(K26_Menu::k26_deleteItem);
					break k26_whileLoop;
				}
				case "0": {  // 0. 초기 메뉴로 이동
					System.out.println("\n초기 메뉴로 돌아갑니다.");
					break k26_whileLoop;
				}	// end case "0"
				default: {
					System.out.println("\n잘못 입력하셨습니다. 다시 입력해 주세요.");
					System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
					continue;
				}	// end default
			}	// end switch
		}	// end while
	}	// end k26_initialMenu
	
	public static void k26_whileLoop(K26_IntReturnFunctionOperator k26_function) {
		k26_whileLoop:
		while(true) {
			switch (k26_function.k26_call()) {
				case -1: {
					System.out.println("\n상위 메뉴로 이동합니다.\n");
					break k26_whileLoop;
				}
				default:{
					System.out.println("\n잘못 입력하셨습니다. 다시 입력해 주세요.");
					continue;
				}	// end default
			}	// end switch
		}	// end while
	}	// k26_whileLoop end
	
	public static void k26_itemListPrint() {
		System.out.println("");
		System.out.println(String.format("%s. %s, %s, %s, %s, %s, %s", "No", "이름", "무게 (g)",
					"화면 (inch)", "디스크용량 (GB)", "비고", "가격 (만원)"));
		k26_itemList.stream().forEach(System.out::println);
	}
	
	public static int k26_itemDataLoad() {
		k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by no");
		k26_itemListPrint();
		k26_whileLoop:
		while(true) {
			System.out.print("\n특정 항목 기준으로 오름차순 정렬하시려면 1,"
					+ " 내림차순 정렬하시려면 2, 상위메뉴로 되돌아가시려면 -1 을 입력하세요 : ");
			String k26_selectMenu = k26_scanner.nextLine();
			// 특정 항목 기준으로 정렬하는 경우
			switch(k26_selectMenu) {
				case "1": {
					if(k26_orderByPrint("asc") == 1) {
						// 번호 순으로 정렬 초기화하여 itemList에 저장
						k26_itemListInitialize();
						break;
					} else {
						k26_itemListInitialize();
						continue;
					}
				}
				case "2": {
					if(k26_orderByPrint("desc") == -1) {
						// 번호 순으로 정렬 초기화하여 itemList에 저장
						k26_itemListInitialize();
						break;
					} else {
						k26_itemListInitialize();
						continue;
					}
				}
				case "-1": {
					break k26_whileLoop;
				}
				default : { 
					System.out.println("\n잘못 입력하셨습니다.");
					continue;
				}	// outer switch - case end
			}	// if end
		}	// while end
		return -1;
	}	// k26_itemDataLoad method end
	
	public static int k26_orderByPrint(String direction) {
		System.out.println("\n정렬할 항목을 대소문자를 구분하여 정확히 입력해 주세요");
		System.out.println("선택 가능 항목: name, weight, displaySize, diskVolume, price");
		// 정렬할 column 값 입력 받아 orderBy에 저장
		String orderBy = k26_scanner.nextLine();
		switch(orderBy) {
			case "name": {
				k26_itemList.clear();
				// "name" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by " + orderBy + " " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "name" end
			case "weight": {
				k26_itemList.clear();
				// "weight" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by " + orderBy + " " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "weight" end
			case "displaySize": {
				k26_itemList.clear();
				// "display_size" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by display_size " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "displaySize" end
			case "diskVolume": {
				k26_itemList.clear();
				// "display_size" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by disc_volume " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "diskVolume" end
			case "price": {
				k26_itemList.clear();
				// "price" column 기준 오름차순 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by " + orderBy + " " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "price" end
			default : {	// 예외 처리
				System.out.println("\n잘못된 입력입니다.\n");
				// 잘못된 입력인 경우 다시 반복
				return -1;
			}	// default end
		}	// switch - case end
		return 1;
	}
	
	public static int k26_itemCompare() {
		String k26_itemANum = "";
		String k26_itemBNum = "";
		boolean k26_criteria = false;
		k26_outerWhileLoop:
			while(true) {
				k26_itemListInitialize();
				k26_itemListPrint();
				System.out.println("(상위 메뉴로 가시려면 -1을 입력하시오)");
				System.out.print("비교할 item A의 번호를 선택하시오 --> ");
				k26_itemANum = k26_scanner.nextLine();
				switch(k26_itemANum) {
					case "-1": {
						break k26_outerWhileLoop;
					}
					default: {
						k26_forLoop1:
							for(K26_Item k26_item : k26_itemList) {
								if(Integer.parseInt(k26_itemANum) == k26_item.getNo()) {
									k26_criteria = true;
									break k26_forLoop1;
								}
							}	// k26_forLoop1 end
							if(!k26_criteria) {	// k26_criteria가 false라면,
								System.out.println("입력이 잘못됐습니다.");
								continue k26_outerWhileLoop;
							}
							k26_criteria = false;	// k26_criteria 재사용을 위한 초기화
						System.out.print("비교할 item B의 번호를 선택하시오 --> ");
						k26_itemBNum = k26_scanner.nextLine();	
						switch(k26_itemBNum) {
							case "-1":{
								break k26_outerWhileLoop;
							}
							default: {
								k26_forLoop2:
									for(K26_Item k26_item : k26_itemList) {
										if(Integer.parseInt(k26_itemBNum) == k26_item.getNo()) {
											k26_criteria = true;
											break k26_forLoop2;
										}
									}	// k26_forLoop2 end
									if(!k26_criteria) { // k26_criteria가 false라면,
										System.out.println("입력이 잘못됐습니다.");
										continue k26_outerWhileLoop;
									}
							}	//	item B default end
						}	// item B switch end
					}	// item A default end
				}	// item A switch end

				k26_stringBuilder.append("select * from ").append(k26_tblName)
				.append(" where no = ").append(k26_itemANum).append(" or no = ").append(k26_itemBNum);
				k26_itemList.clear();	// 기존 itemList에 저장된 데이터를 삭제
				// DB에서 item A, B에 대한 레코드 두 개를 받아 itemList에 add
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery(k26_stringBuilder.toString());
				k26_stringBuilder.setLength(0);	// StringBuilder instance initializing
				System.out.println("\n### 물품 비교 결과 ###");
				k26_itemList.stream().forEach(System.out::println);	// itemList의 요소인 item A, B 데이터를 출력
				K26_Item k26_itemA = k26_itemList.get(0);
				K26_Item k26_itemB = k26_itemList.get(1);
				if(k26_itemA.getWeight() > k26_itemB.getWeight()) {
					System.out.println(String.format("*무게 : %d > %d", k26_itemA.getNo(), k26_itemA.getNo()));
				} else if(k26_itemA.getWeight() < k26_itemB.getWeight()) {
					System.out.println(String.format("*무게 : %d < %d", k26_itemA.getNo(), k26_itemA.getNo()));
				} else {
					System.out.println(String.format("*무게 : %d = %d", k26_itemA.getNo(), k26_itemA.getNo()));
				}
				k26_innerWhileLoop:	// 반복 작업 요청 입력 시 잘못된 값 입력하는 경우
					while(true) {	// 반복 작업 요청 입력을 다시 하기 위한 while문
						System.out.println("더 비교하시겠습니까? (1:예, 2:아니오-상위메뉴로 이동)");
						switch(k26_scanner.nextLine()) {
							case "1": {
								continue k26_outerWhileLoop;	// 비교 작업 전체 반복
							}	// case "1" end
							case "2": {
								break k26_outerWhileLoop;	// k26_outerWhileLoop end로 빠져 나가기
							}	// case "2" end
							default: {	// 1, 2 외 다른 값 입력 시 재입력
								System.out.println("\n잘못된 입력입니다.\n");
								continue k26_innerWhileLoop;	// k26_innerWhileLoop 반복
							}	// default end
						}	// switch - case end
					}	// k26_innerWhileLoop end
			}	// k26_outerWhileLoop end
		return -1;
	}

	
	public static int k26_revisedItem() {
		k26_itemListInitialize();
		k26_itemListPrint();
		System.out.print("\n수정할 물품의 No를 입력하세요 : ");
		String itemNo = k26_scanner.nextLine();
		System.out.println("수정할 수 있는 항목");
		System.out.println("no, name, weight, displaySize, diskVolume, etc, price");
		System.out.println("잘못 입력하는 경우 수정 메뉴 처음으로 되돌아갑니다.");
		System.out.println("수정할 항목을 입력하세요 : ");
		String revisedColumn = k26_scanner.nextLine();
		System.out.print("수정할 값을 입력하세요 : ");
		String setVal = k26_scanner.nextLine();
		k26_stringBuilder.append("update ").append(k26_tblName).append(" set ").append(revisedColumn).append(" = ");
		k26_itemFieldType = k26_item.k26_getFieldType();
		if(k26_itemFieldType.get(revisedColumn)!= null && k26_itemFieldType.get(revisedColumn).equals("String")) {
			// "update item_tbl set revisedColumn = 'setVal' where no = itemNo"
			k26_stringBuilder.append("'").append(setVal).append("'");
		} else if(k26_itemFieldType.get(revisedColumn)!= null) {
			// "update item_tbl set revisedColumn = setVal where no = itemNo"
			k26_stringBuilder.append(setVal);
		} else if(k26_itemFieldType.get(revisedColumn) == null) {
			System.out.println("\n잘못된 입력입니다.");
			return -1;
		}
		k26_stringBuilder.append(" where no = ").append(itemNo);
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		k26_stringBuilder.setLength(0);
		k26_itemListInitialize();
		k26_itemListPrint();
		return -1;
	}
	
	public static int k26_insertItem() {
		System.out.println("\n새로운 물품을 등록합니다.");
		k26_stringBuilder.append("insert into item_tbl ")
		   .append("(name, weight, display_size, disk_volume, etc, price) ")
		   .append("values ").append("('");
		System.out.print("물품 이름을 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine()).append("', ");
		System.out.print("물품 무게를 g 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine()).append(", ");
		System.out.print("물품의 화면 크기를 inch 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine()).append(", ");
		System.out.print("물품의 디스크 용량을 GB 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine()).append(", '");
		System.out.print("물품의 비고 내용을 입력 후 엔터를 눌러주세요 : ");
		String k26_input = k26_scanner.nextLine();
		k26_stringBuilder.append(k26_input.isEmpty()? " ":k26_input).append("', ");
		System.out.print("물품의 가격을 만원 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine()).append(")");
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		k26_stringBuilder.setLength(0);
		k26_itemListInitialize();
		k26_itemListPrint();
		return -1;
	}
	
	public static int k26_deleteItem() {
		k26_itemListInitialize();
		k26_itemListPrint();
		System.out.print("\n삭제할 물품의 번호를 입력하세요. 상위 메뉴로 되돌아가려면 0을 입력하세요.: ");
		String itemNo = k26_scanner.nextLine();

		if(Integer.parseInt(itemNo) <= k26_itemList.size() && Integer.parseInt(itemNo) > 0) {
			k26_stringBuilder.append("delete from ").append(k26_tblName).append(" where no = ").append(itemNo);
			k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
			k26_stringBuilder.setLength(0);
			k26_stringBuilder.append("update ").append(k26_tblName).append(" set no = no - 1 where no > ").append(itemNo);
			k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
			k26_stringBuilder.setLength(0);
			k26_stringBuilder.append("alter table ").append(k26_tblName).append(" auto_increment = 1");
			k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
			k26_stringBuilder.setLength(0);
			k26_itemListInitialize();
			k26_itemListPrint();
			return -1;
		} else if(Integer.parseInt(itemNo) == 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public static void k26_itemListInitialize() {
		k26_itemList.clear();
		k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by no");
	}
}