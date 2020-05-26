package k26_DBProject3rdExam;

public class K26_Main {
	public static void main(String[] args) throws NumberFormatException, NoSuchFieldException, SecurityException {
		boolean execute = true;
		while(execute) {
			K26_Menu.k26_initialMenu();
		}
	}
}
/*  menu.k26_itemListPrint();
 *	K26_Item item1 = menu.itemList.get(0);
 *	Field[] className = item1.getClass().getFields();
 *	System.out.println(className.length);
 *	for(int i=0; i < className.length; i++) {
 *		System.out.println(className[i].getType());
 *	}
 *	System.out.println(className);
 *	List<K26_Item> itemList = new ArrayList<>();
 *	Scanner sc = new Scanner(System.in);
 *	StringBuilder sb = new StringBuilder();
 *	String k26_dbName = "dbproject_3rd_exam";
 *	String k26_tblName = "item_tbl";
 *	K26_DataBaseDAO k26_dbDAO = new K26_DataBaseDAO();
 *	k26_dbDAO.k26_setDBName(k26_dbName);
 *	k26_dbDAO.k26_connectDB();
 *	k26_dbDAO.k26_setDB();
 *	String createTableQuery 
 *			= sb.append("create table if not exists item_tbl(")
 *			.append("no int primary key auto_increment")
 *			.append(", name varchar(100)")
 *			.append(", weight int")
 *			.append(", display_size decimal(3, 1)")
 *			.append(", disk_volume int")
 *			.append(", etc varchar(100)")
 *			.append(", price int)").toString();
 *	sb.setLength(0);
 *	k26_dbDAO.k26_executeQuery(createTableQuery);
 *	String explainQuery = "explain item_tbl";
 *	k26_dbDAO.k26_dataPrintQuery(explainQuery);
 *	String insertQuery 
 *	= sb.append("insert into item_tbl ")
 *	    .append("(name, weight, display_size, disk_volume, etc, price) ")
 *	    .append("values ")
 *   	.append("('LG Gram 14', 980, 14.4, 512, ' ', 140)").toString();
 *	sb.setLength(0);
 *	k26_dbDAO.k26_executeQuery(insertQuery);
 *	String selectQuery = "select * from " + k26_tblName;
 *	itemList = k26_dbDAO.k26_itemDataLoadQuery(selectQuery);
 *	itemList.stream().forEach(System.out::println);
 *	System.out.print("수정할 물품의 No를 입력하세요 : ");
 *	String itemNo = sc.nextLine();
 *	System.out.print("수정할 항목을 입력하세요 : ");
 *	String revisedColumn = sc.nextLine();
 *	System.out.print("수정할 값을 입력하세요 : ");
 *	String setVal = sc.nextLine();
 *	String updateQuery = sb.append("update ").append(k26_tblName)
 *			.append(" set ")
 *			.append(revisedColumn).append(" = '").append(setVal).append("'")
 *			.append(" where no = ").append(itemNo).toString();
 *	k26_dbDAO.k26_executeQuery(updateQuery);
 *	String select2Query = "select * from " + k26_tblName;
 *	itemList = k26_dbDAO.k26_itemDataLoadQuery(select2Query);
 *	itemList.stream().forEach(System.out::println);
 */
/*
 * 	public static void k26_firstMenu() {
 *		String[] k26_menuLineArray = {"### 메뉴 ###", "1. 물품 비교하기", "2. 물품 수정하기", "3. 물품 입력하기", "원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> "};
 *		List<String> k26_menuList = Arrays.asList(k26_menuLineArray);
 *		
 *		List<Integer> k26_menuStrlength = new ArrayList<Integer>();
 *		int k26_menuLineMaxLength = 0;
 *		for(int i = 0; i < k26_menuList.size() - 1 ; i++) {
 *			k26_menuLineMaxLength = Math.max(k26_menuLineArray[i].length(), k26_menuLineArray[i+1].length());
 *			k26_menuStrlength.add(k26_menuLineArray[i].length());
 *		}
 *		k26_menuStrlength.add(k26_menuLineArray[k26_menuList.size()-1].length());
 *		StringBuilder sb = new StringBuilder();
 *		int spaceStrLine = 0;
 *		String space = "";
 *		for(int i = 0; i < k26_menuLineArray.length; i++) {
 *			spaceStrLine = (int) Math.ceil((k26_menuLineMaxLength - k26_menuLineArray[i].length()) / 2);
 *			for(int j = 0; j < spaceStrLine; j++) {
 *				space = sb.append(" ").toString();
 *			}
 *			sb.setLength(0);
 *			if(i != (k26_menuLineArray.length - 1)) {
 *				k26_menuList.set(i, sb.append(space).append(space).append(k26_menuLineArray[i]).toString());
 *			}
 *			sb.setLength(0);
 *		}
 *		k26_menuList.stream().forEach(i -> System.out.println(i));
 *	}
 */



