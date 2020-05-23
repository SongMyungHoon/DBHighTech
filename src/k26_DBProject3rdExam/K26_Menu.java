package k26_DBProject3rdExam;

import java.util.*;

public class K26_Menu {
	static Scanner sc = new Scanner(System.in);
	static final String k26_dbName = "dbproject_3rd_exam";
	static final String k26_tblName = "item_tbl";
	static K26_DataBaseDAO k26_dbDAO = new K26_DataBaseDAO();
	List<K26_Item> itemList = new ArrayList<>();
	
	public void k26_initialMenu() {
		String[] k26_initMenuLineArray = {
				"### 메뉴 ###", "1. 물품 목록 조회", "2. 물품 비교하기", "3. 물품 수정하기", "4. 물폼 입력하기"};
		boolean k26_menuExecute = true;
		List<String> k26_initMenuList = Arrays.asList(k26_initMenuLineArray);
		k26_initMenuList.stream().forEach(System.out::println);
		System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
		while(k26_menuExecute) {
			switch(sc.next()) {
				case "1":
					k26_itemListPrint();
				case "2":
					
				case "3":
				
				case "4":
					
				case "0":
					System.out.println("\n초기 메뉴로 돌아갑니다.\n");
					k26_menuExecute = false;
					break;
				default:
					System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
					System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
					continue;
			}
		}
	}
	
	public void k26_itemListPrint() {
		k26_dbDAO.k26_setDBName(k26_dbName);
		k26_dbDAO.k26_connectDB();
		String selectQuery = "select * from " + k26_tblName;
		itemList = k26_dbDAO.k26_itemDataLoadQuery(selectQuery);
		itemList.stream().forEach(System.out::println);
	}
	
	public void k26_itemCompare() {
		String selectQuery = "select * from " + k26_tblName;
		itemList = k26_dbDAO.k26_itemDataLoadQuery(selectQuery);
		itemList.stream().forEach(System.out::println);
	}
	
	public void k26_revisedItem() {
		StringBuilder sb = new StringBuilder();
		String selectQuery = "select * from " + k26_tblName;
		itemList = k26_dbDAO.k26_itemDataLoadQuery(selectQuery);
		itemList.stream().forEach(System.out::println);
		System.out.print("수정할 물품의 No를 입력하세요 : ");
		String itemNo = sc.nextLine();
		System.out.print("수정할 항목을 입력하세요 : ");
		String revisedColumn = sc.nextLine();
		System.out.print("수정할 값을 입력하세요 : ");
		String setVal = sc.nextLine();
		itemList.get(Integer.parseInt(itemNo)).getClass().getName();
		String updateQuery = sb.append("update ").append(k26_tblName)
				.append(" set ")
				.append(revisedColumn).toString();
		if(itemList.get(Integer.parseInt(itemNo)).  instanceof String) {
				sb.append(" = '").append(setVal).append("'")
				.append(" where no = ").append(itemNo).toString();
		} else if(setVal instanceof Integer) {
			
		}
		sb.setLength(0);
		k26_dbDAO.k26_executeQuery(updateQuery);
		String select2Query = "select * from " + k26_tblName;
		itemList = k26_dbDAO.k26_itemDataLoadQuery(select2Query);
		itemList.stream().forEach(System.out::println);
	}
	
	public void k26_insertItem() {
		
	}
}
