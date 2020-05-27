package k26_DBProject3rdExam;

import java.util.*;

public class K26_Menu {	// K26_Menu : 각 메뉴를 구현한 class
	private static final String k26_dbName = "dbproject_3rd_exam";			// 사용할 MySQL DB 이름
	private static final String k26_tblName = "item_tbl";					// 사용할 table 이름
	
	// MySQL Database 접속 및 쿼리 수행을 위한 클래스. 메서드 수행 결과를 저장.
	private static K26_DataBaseDAO k26_dbDAO = new K26_DataBaseDAO();		
	private static List<K26_Item> k26_itemList = new ArrayList<>();			// Query 수행 결과를 저장하는 Item 객체를 요소로 하는 List
	
	// Class 내부에서 공용으로 String manipulation을 담당할 StringBuilder instance
	private static StringBuilder k26_stringBuilder = new StringBuilder();
	private static Scanner k26_scanner = new Scanner(System.in);			// Class 내부에서 공용으로 사용할 Scanner instance
	private static Map<String, String> k26_itemFieldType = new HashMap<>();
	private static K26_Item k26_item = new K26_Item();	// Class 내부에서 공용으로 물품의 데이터를 갖는 K26_Item class의 instance
	
	
	// k26_initialMenu : 초기 메뉴를 구현하는 method
	public static void k26_initialMenu() {
		k26_dbDAO.k26_setDBName(k26_dbName);		// k26_dbDAO 객체의 DBName을 k26_dbName으로 set
		k26_dbDAO.k26_connectDB();					// k26_dbDAO 객체의 connection 객체 생성
		k26_dbDAO.k26_setDB();						// k26_dbName과 동일한 이름의 DB를 탐색하고, 없는 경우 DB 생성해 use
		k26_dbDAO.k26_setTableName(k26_tblName);	// k26_dbDAO 객체의 table name을 k26_tblName으로 set
		
		// k26_tblName과 동일한 이름의 table 생성(if not exists)
		k26_dbDAO.k26_createTable();
		
		List<String> k26_initMenuList = new ArrayList<>() {
			{	// 초기 메뉴를 구성하는 문자열을 List로 선언
				add("### 메뉴 ###");   
				add("1. 물품 목록 조회");
				add("2. 물품 비교하기");
				add("3. 물품 수정하기");
				add("4. 물폼 입력하기");
				add("5. 물품 삭제하기");
			}
		};
				
		k26_whileLoop:	// k26_initialMenu method의 while문을 k26_whileLoop로 labeling
			while(true) {
				// k26_initMenuList의 모든 요소를 forEach 문으로 출력
				k26_initMenuList.stream().forEach(System.out::println);
				System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
				// 메뉴 선택을 입력받아 switch - case문으로 각 메뉴 작동
				switch(k26_scanner.nextLine()) {
					case "1": {	// 1. 물품 목록 조회					// k26_whileLoop method의 argument로 k26_itemDataLoad method를
						k26_whileLoop(K26_Menu::k26_itemDataLoad);	// functional interface 형태로 넘겨받아 내부에서 호출
						break k26_whileLoop;						// method 종료 후 k26_initialMenu method의 k26_whileLoop escape
					}	// end case "1"
					case "2": {	// 2. 물품 비교하기					// k26_whileLoop method의 argument로 k26_itemCoompare method를
						k26_whileLoop(K26_Menu::k26_itemCompare);	// functional interface 형태로 넘겨받아 내부에서 호출
						break k26_whileLoop;						// method 종료 후 k26_initialMenu method의 k26_whileLoop escape
					}	// end case "2"
					case "3": { // 3. 물품 수정하기					// k26_whileLoop method의 argument로 k26_revisedItem method를
						k26_whileLoop(K26_Menu::k26_revisedItem);	// functional interface 형태로 넘겨받아 내부에서 호출
						break k26_whileLoop;						// method 종료 후 k26_initialMenu method의 k26_whileLoop escape
					}	// end case "3"
					case "4": { // 4. 물품 입력하기					// k26_whileLoop method의 argument로 k26_insertItem method를
						k26_whileLoop(K26_Menu::k26_insertItem);	// functional interface 형태로 넘겨받아 내부에서 호출
						break k26_whileLoop;						// method 종료 후 k26_initialMenu method의 k26_whileLoop escape
					}	// end case "4"								
					case "5": {	// 5. 물품 삭제하기					// k26_whileLoop method의 argument로 k26_insertItem method를
						k26_whileLoop(K26_Menu::k26_deleteItem);	// functional interface 형태로 넘겨받아 내부에서 호출
						break k26_whileLoop;						// method 종료 후 k26_initialMenu method의 k26_whileLoop escape
					}
					case "0": {  // 0. 초기 메뉴로 이동
						System.out.println("\n초기 메뉴로 돌아갑니다.");
						break k26_whileLoop;	// k26_initialMenu method의 k26_whileLoop escape
					}	// end case "0"
					default: {		// "1", "2", "3", "4", "5", "0" 외의 값을 입력하는 경우 예외 처리
						System.out.println("\n잘못 입력하셨습니다. 다시 입력해 주세요.");
						System.out.print("원하시는 메뉴를 선택하세요 (0은 초기메뉴) --> ");
						continue;	// k26_initialMenu Method의 k26_whileLoop continue 
					}	// end default 
				}	// end switch
			}	// end while
	}	// end k26_initialMenu
	
	/** k26_whileLoop method
	 *  k26_initialMenu method 내부에서 호출되어 각 메뉴를 작동시키는 method들을  
	 *  사전에 Single Abstract Method interface로 정의된 interface type의
	 *  parameter인 k26_function으로 받아 while문 안에서 반복적으로 호출하는 형태로 설계
	 * @param K26_IntReturnFunctionOperator interface type k26_function
	 * @return void
	 */
	public static void k26_whileLoop(K26_IntReturnFunctionOperator k26_function) {
		k26_whileLoop:	// while loop labeling : k26_whileLoop
			while(true) {
				/* SAM interface인 K26_IntReturnFunctionOperator의 k26_call method로
				 * k26_fuction 호출하고 return이 (int) -1인 경우 k26_whileLoop escape */
				switch (k26_function.k26_call()) {
					case -1: {
						System.out.println("\n상위 메뉴로 이동합니다.\n");
						break k26_whileLoop;	// k26_whileLoop escape
					}
					default:{		// k26_function.k26_call()의 return != -1인 경우
						System.out.println("\n잘못 입력하셨습니다. 다시 입력해 주세요.");
						continue;	// k26_whileLoop continue
					}
				}	// end switch
			}	// end while
	}	// k26_whileLoop method end
	
	/** k26_itemListPrint() method
	 * 	K26_Menu class의 static List인 k26_itemList의 데이터를 
	 *  console 출력하는 method
	 *  @return void
	 */
	public static void k26_itemListPrint() {
		System.out.println("");
		System.out.println(String.format("%s. %s, %s, %s, %s, %s, %s", "No", "이름", "무게 (g)",
					"화면 (inch)", "디스크용량 (GB)", "비고", "가격 (만원)"));
		k26_itemList.stream().forEach(System.out::println);
	}	// k26_itemListPrint method end
	
	/** k26_itemDataLoad() method
	 *  1. 물품 목록 조회 메뉴를 구현하는 method로 no 기준 정렬 출력,
	 *  특정 column 기준 오름차순, 내림차순 정렬 작업을 수행
	 *  @return -1
	 */
	public static int k26_itemDataLoad() {
		// K26_Menu class의 static List인 k26_itemList에 DB에서 받아온 물품 data를 저장
		k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by no");
		// static List에 저장된 data를 console 출력
		k26_itemListPrint();
		k26_whileLoop:	// while문을 k26_whileLoop로 Labeling
		while(true) {
			System.out.print("\n특정 항목 기준으로 오름차순 정렬하시려면 1,"
					+ " 내림차순 정렬하시려면 2, 상위메뉴로 되돌아가시려면 -1 을 입력하세요 : ");
			String k26_selectMenu = k26_scanner.nextLine();
			// 특정 항목 기준으로 정렬하는 경우
			switch(k26_selectMenu) {
				case "1": {		// order by asc case
					k26_orderByPrint("asc");	// k26_orderByPrint method 실행 후
					k26_itemListInitialize();	// itemList.clear() 하고 번호 순으로 재정렬 
					break;						// break로 switch - case문 escape -> k26_whileLoop 반복
				}
				case "2": { 	// order by desc case
					k26_orderByPrint("desc"); 	// k26_orderByPrint method 실행해 desc 방향으로 정렬
					k26_itemListInitialize();	// itemList.clear() 하고 번호 순으로 재정렬
					break;						// break로 switch - case문 escape -> k26_whileLoop 반복
				}
				case "-1": {	// 상위 메뉴로 되돌아가는 case, k26_whileLoop escape -> k26_whileLoop method로 이동
					break k26_whileLoop;	// k26_whileLoop method에서 k26_initialMenu method로 이동
				}
				default : { 	// 잘못 입력 하는 경우, 재입력 받음
					System.out.println("\n잘못 입력하셨습니다.");
					continue;	// k26_whileLoop continue
				}
			}	// switch - case end
		}	// while end
		/* k26_itemDataLoad method 호출 결과, k26_whileLoop method에서 -1 return받아
		 * k26_whileLoop method의 while문 escape하여 k26_initialMenu method로 되돌아감 */
		return -1;	 
	}	// k26_itemDataLoad method end
	
	/**k26_orderByPrint(String direction) method
	 * 1. 물품 목록 출력 메뉴를 구현하는 k26_itemDataLoad() method에서 호출되어
	 * 	   특정 column 기준으로 오름차순, 내림차순 정렬을 구현하는 mathod
	 * @param direction(asc or desc)
	 * @return 잘못된 입력의 경우 -1, 정상 작동의 경우 1 return
	 */
	public static int k26_orderByPrint(String direction) {
		System.out.println("\n정렬할 항목을 대소문자를 구분하여 정확히 입력해 주세요");
		System.out.println("선택 가능 항목: name, weight, displaySize, diskVolume, price");
		// 정렬할 column 값 입력 받아 switch - case 작동
		switch(k26_scanner.nextLine()) {
			case "name": {
				k26_itemList.clear();	// itemList에 select문 결과를 add하기 전에 itemList 초기화
				// "name" column 기준 direction 방향(asc, desc) 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by name " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "name" end
			case "weight": {
				k26_itemList.clear();	// itemList에 select문 결과를 add하기 전에 itemList 초기화
				// "weight" column 기준 direction 방향(asc, desc) 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by weight " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "weight" end
			case "displaySize": {
				k26_itemList.clear(); 	// itemList에 select문 결과를 add하기 전에 itemList 초기화
				// "display_size" column 기준 direction 방향(asc, desc) 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by display_size " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "displaySize" end
			case "diskVolume": {
				k26_itemList.clear();
				// "display_size" column 기준 direction 방향(asc, desc) 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by disc_volume " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "diskVolume" end
			case "price": {
				k26_itemList.clear();
				// "price" column 기준 direction 방향(asc, desc) 정렬 request 후 결과를 k26_itemList에 저장
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by price " + direction);
				k26_itemListPrint();	// 정렬된 결과를 출력
				break;
			}	// case "price" end
			default : {	// 예외 처리
				System.out.println("\n잘못된 입력입니다.\n");
				/* 잘못된 입력인 경우 k26_orderByPrint return -1
				 * k26_itemDataLoad method의 while에서 반복 */
				return -1;
			}	// default end
		}	// switch - case end
		return 1;	// k26_itemDataLoad method에서 1을 return 받고 while Loop escape
	}	// k26_orderByPrint method end
	
	/** k26_itemCompare() method
	  * 두 개의 item의 no를 입력 받아 column 별로 비교하여
	  * 점수를 계산하고 높은 점수를 가지는 item을 추천해주는 method
	  * @return int -1 (k26_itemCompare method의 작동 결과,
	  * 				k26_whileLoop method에서 -1을 return 받고 while문 escape하여
	  * 				k26_initialMenu method로 되돌아간다.
	  */
	public static int k26_itemCompare() {
		String k26_itemANum = "";	// 첫번째로 입력받을 item의 No
		String k26_itemBNum = "";	// 두번째로 입력받을 item의 No
		boolean k26_criteria = false;
		// k26_itemCompare() method의 outer While문을 k26_outerWhileLoop로 Labeling
		k26_outerWhileLoop:	
			while(true) {
				/* DB에 등록된 모든 item의 정보를 no로 정렬하여 K26_Menu Class의 
				 * static List에 item data를 저장 */
				k26_itemListInitialize();
				// static List에 저장된 item data를 출력
				k26_itemListPrint();
				System.out.println("(상위 메뉴로 가시려면 -1을 입력하시오)");
				System.out.print("비교할 item A의 번호를 선택하시오 --> ");
				k26_itemANum = k26_scanner.nextLine();
				switch(k26_itemANum) {
					case "-1": {					// item A의 no를 -1을 입력하는 경우
						break k26_outerWhileLoop;	// k26_outerWhileLoop escape하여
					}								// 상위 메뉴로 이동
					default: {						// k26_itemANum != -1 외의 경우
						k26_forLoop1: 				// for문을 k26_forLoop1으로 Labeling
							// k26_itemList에 있는 k26_Item instance의 no를 입력받은 itemANum과 비교
							for(K26_Item k26_item : k26_itemList) {	// 동일한 no를 가진 경우
								if(Integer.parseInt(k26_itemANum) == k26_item.getNo()) {
									k26_criteria = true;	// k26_criteria = true 설정 후
									break k26_forLoop1;		// k26_forLoop1 escape
								}	// if end
							}	// k26_forLoop1 end
							if(!k26_criteria) {	// k26_criteria가 false라면,
								// 입력한 k26_itemANum과 동일한 no을 갖는 item이 없음.
								System.out.println("입력이 잘못됐습니다.");
								continue k26_outerWhileLoop;	// k26_outerWhileLoop continue
							}
							k26_criteria = false;	// k26_criteria 재사용을 위한 초기화
						System.out.print("비교할 item B의 번호를 선택하시오 --> ");
						k26_itemBNum = k26_scanner.nextLine();	// 첫번째 item과 비교할 item no를 입력받음
						switch(k26_itemBNum) {
							case "-1":{						// item B의 no를 -1을 입력하는 경우
								break k26_outerWhileLoop;	// k26_outerWhileLoop escape하여
							}								// 상위 메뉴로 이동
							default: {						// k26_itemBNum != -1 외의 경우
								k26_forLoop2:				// for문을 k26_forLoop2으로 Labeling
									// k26_itemList에 있는 k26_Item instance의 no를 입력받은 itemBNum과 비교
									for(K26_Item k26_item : k26_itemList) {	// 동일한 no를 가진 경우
										if(Integer.parseInt(k26_itemBNum) == k26_item.getNo()) {
											k26_criteria = true;	// k26_criteria = true 설정 후
											break k26_forLoop2;		// k26_forLoop2 escape
										}	// if end
									}	// k26_forLoop2 end
									if(!k26_criteria) { // k26_criteria가 false라면,
										// 입력한 k26_itemBNum과 동일한 no을 갖는 item이 없음.
										System.out.println("입력이 잘못됐습니다.");
										continue k26_outerWhileLoop;	// k26_outerWhileLoop continue
									}
							}	//	item B default end
						}	// item B switch end
					}	// item A default end
				}	// item A switch end
				
				// DB에서 실행할 select query 작성
				k26_stringBuilder.append("select * from ").append(k26_tblName)
				.append(" where no = ").append(k26_itemANum).append(" or no = ").append(k26_itemBNum);
				k26_itemList.clear();	// 기존 itemList에 저장된 데이터를 삭제
				// DB에서 item A, B에 대한 레코드 두 개를 받아 itemList에 add
				k26_itemList = k26_dbDAO.k26_itemDataLoadQuery(k26_stringBuilder.toString());
				k26_stringBuilder.setLength(0);	// StringBuilder instance initializing
				System.out.println("\n### 물품 비교 결과 ###");
				k26_itemList.stream().forEach(System.out::println);	// itemList의 요소인 item A, B 데이터를 출력
				// k26_itemA = select query 실행 결과 받아온 itemList data의 첫번째 element
				K26_Item k26_itemA = k26_itemList.get(0);
				// k26_itemB = select query 실행 결과 받아온 itemList data의 두번째 element
				K26_Item k26_itemB = k26_itemList.get(1);
				// k26_scoreListA : item A의 조건 별 점수를 저장하기 위한 List 객체
				List<K26_Score> k26_scoreListA = new ArrayList<>();
				// k26_scoreListB : item B의 조건 별 점수를 저장하기 위한 List 객체
				List<K26_Score> k26_scoreListB = new ArrayList<>();
				// 무게가 가벼우면 점수가 더 높다
				if(k26_itemA.getWeight() > k26_itemB.getWeight()) {
					// item A의 weigth와 item B의 weight의 비교를 console 출력
					System.out.println(String.format("*무게 : %d < %d", k26_itemA.getNo(), k26_itemB.getNo()));
					// scoreListA에는 해당 조건과 점수 0점을 add
					k26_scoreListA.add(new K26_Score("", 0));
					/* item B의 weigth가 더 가벼우므로, 해당되는 조건과 점수 20점을
					 * K26_Score 객체에 저장해 B의 scoreList에 add */
					k26_scoreListB.add(new K26_Score("조건1(20)", 20));
				} else if(k26_itemA.getWeight() < k26_itemB.getWeight()) {
					// item A의 weigth와 item B의 weight의 비교를 console 출력
					System.out.println(String.format("*무게 : %d > %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 weigth가 더 가벼우므로, 해당되는 조건과 점수 20점을
				     * K26_Score 객체에 저장해 A의 scoreList에 add */
					k26_scoreListA.add(new K26_Score("조건1(20)", 20));
					// scoreListB에는 해당 조건과 점수 0점을 add
					k26_scoreListB.add(new K26_Score("", 0));
				} else {
					// item A의 weigth와 item B의 weight의 비교를 console 출력
					System.out.println(String.format("*무게 : %d = %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 weight == item B의 weight 이므로
			         * 해당 조건에서 모두 점수 0점으로 기록해 각 scoreList에 add */
					k26_scoreListA.add(new K26_Score("", 0));
					k26_scoreListB.add(new K26_Score("", 0));
				}
				// 화면이 크면 점수가 더 높다
				if(k26_itemA.getDisplaySize() > k26_itemB.getDisplaySize()) {
					// item A의 weigth와 item B의 weight의 비교를 console 출력
					System.out.println(String.format("*화면 : %d > %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 화면크기가 더 크므로, 해당되는 조건과 점수 20점을 
					 * K26_Score 객체에 저장해 A의 scoreList에 add */
					k26_scoreListA.add(new K26_Score("조건2(20)", 20));
					// scoreListB에는 해당 조건과 점수 0점을 add
					k26_scoreListB.add(new K26_Score("", 0));
				} else if(k26_itemA.getDisplaySize() < k26_itemB.getDisplaySize()) {
					// item A의 weigth와 item B의 weight의 비교를 console 출력
					System.out.println(String.format("*화면 : %d < %d", k26_itemA.getNo(), k26_itemB.getNo()));
					// A scoreList에는 해당 조건과 점수 0점을 add
					k26_scoreListA.add(new K26_Score("", 0));
					/* item B의 화면크기가 더 크므로, 해당되는 조건과 점수 20점을 
					 * K26_Score 객체에 저장해 B의 scoreList에 add */
					k26_scoreListB.add(new K26_Score("조건2(20)", 20));
				} else {
					// item A의 weigth와 item B의 weight의 비교를 console 출력
					System.out.println(String.format("*화면 : %d = %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 화면크기 == item B의 화면크기 이므로
		             * 해당 조건에서 모두 점수 0점으로 기록해 각 scoreList에 add */
					k26_scoreListA.add(new K26_Score("", 0));
					k26_scoreListB.add(new K26_Score("", 0));
				}
				// 디스크용량이 크면 점수가 더 높다
				if(k26_itemA.getDiskVolume() > k26_itemB.getDiskVolume()) {
					// item A의 디스크 용량과 item B의 디스크 용량의 비교를 console 출력
					System.out.println(String.format("*디스크 용량 : %d > %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 디스크 용량이 더 크므로, 해당되는 조건과 점수 20점을 
					 * K26_Score 객체에 저장해 A의 scoreList에 add */
					k26_scoreListA.add(new K26_Score("조건3(20)", 20));
					// scoreListB에는 해당 조건과 점수 0점을 add
					k26_scoreListB.add(new K26_Score("", 0));
				} else if(k26_itemA.getWeight() < k26_itemB.getWeight()) {
					// item A의 디스크 용량과 item B의 디스크 용량의 비교를 console 출력
					System.out.println(String.format("*디스크 용량 : %d < %d", k26_itemA.getNo(), k26_itemB.getNo()));
					// A scoreList에는 해당 조건과 점수 0점을 add
					k26_scoreListA.add(new K26_Score("", 0));
					/* item B의 디스크용량이 더 크므로, 해당되는 조건과 점수 20점을 
					 * K26_Score 객체에 저장해 B의 scoreList에 add */
					k26_scoreListB.add(new K26_Score("조건3(20)", 20));
				} else {
					// item A의 디스크 용량과 item B의 디스크 용량의 비교를 console 출력
					System.out.println(String.format("*디스크 용량 : %d = %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 디스크 용량 == item B의 디스크 용량 이므로
		             * 해당 조건에서 모두 점수 0점으로 기록해 각 scoreList에 add */
					k26_scoreListA.add(new K26_Score("", 0));
					k26_scoreListB.add(new K26_Score("", 0));
				}
				// 가격이 싸면 점수가 더 높다
				if(k26_itemA.getPrice() > k26_itemB.getPrice()) {
					// item A의 가격과 item B의 가격의 비교를 console 출력
					System.out.println(String.format("*가격 : %d < %d", k26_itemA.getNo(), k26_itemB.getNo()));
					// A scoreList에는 해당 조건과 점수 0점을 add
					k26_scoreListA.add(new K26_Score("", 0));
					/* item B의 가격이 더 낮으므로, 해당되는 조건과 점수 20점을 
					 * K26_Score 객체에 저장해 B의 scoreList에 add */
					k26_scoreListB.add(new K26_Score("가격(40)", 40));
				} else if(k26_itemA.getPrice() < k26_itemB.getPrice()) {
					// item A의 가격과 item B의 가격의 비교를 console 출력
					System.out.println(String.format("*가격 : %d > %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 가격이 더 낮으므로, 해당되는 조건과 점수 20점을 
					 * K26_Score 객체에 저장해 A의 scoreList에 add */
					k26_scoreListA.add(new K26_Score("가격(40)", 40));
					// B scoreList에는 해당 조건과 점수 0점을 add
					k26_scoreListB.add(new K26_Score("", 0));
				} else {
					// item A의 가격과 item B의 가격의 비교를 console 출력
					System.out.println(String.format("*가격 : %d = %d", k26_itemA.getNo(), k26_itemB.getNo()));
					/* item A의 가격 == item B의 가격 이므로
		             * 해당 조건에서 모두 점수 0점으로 기록해 각 scoreList에 add */
					k26_scoreListA.add(new K26_Score("", 0));
					k26_scoreListB.add(new K26_Score("", 0));
				}
				System.out.println("추천 : 조건1 (20점), 조건2 (20점), 조건3 (20점), 가격 (40점)");
				// k26_scoring method로 앞에서 저장한 k26_scoreList의 조건에서의 점수를 계산
				int scoreA = k26_scoring(k26_itemA, k26_scoreListA);
				int scoreB = k26_scoring(k26_itemB, k26_scoreListB);
				
				// 더 높은 점수를 획득한 item을 최종 추천
				if(scoreA > scoreB) {
					System.out.println(String.format("- 최종 추천 : %s", k26_itemA.getName()));
				} else if(scoreA < scoreB) {
					System.out.println(String.format("- 최종 추천 : %s", k26_itemB.getName()));
				} else {	// 동점의 경우, 동점임을 알리는 문구를 console 출력
					System.out.println("- 최종 추천 : 동점입니다.");
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
		// k26_itemCompare method의 결과 -1을 return해 
		return -1;	// k26_whileLoop method에서 while문 escape
	}	// k26_temCompare method end
	
	/** k26_scoring method
	  * K26_Item의 instance와 scoreList를 parameter로 전달받아
	  * 각 조건에서의 점수를 console로 출력하고 total score를 계산해서 return
	  * @param k26_item
	  * @param k26_score
	  * @return (int) totalScore
	  */
	public static int k26_scoring(K26_Item k26_item, List<K26_Score> k26_score) {
		int k26_totalScore = 0;
		int k26_count = 0;
		k26_stringBuilder.append(String.format("- %s : ", k26_item.getName()));
		
		for(int i = 0; i< k26_score.size(); i++) {
			if(!k26_score.get(i).isScoreZero()) {
				if(k26_count == 0) {
					k26_stringBuilder.append(k26_score.get(i).getCondition());
				} else {
					k26_stringBuilder.append(" + ").append(k26_score.get(i).getCondition());
				}	// inner if end
				k26_count++;
			}	// if end 
			k26_totalScore += k26_score.get(i).getScore();
		}	// for문 end
		if(k26_totalScore != 0) {
			k26_stringBuilder.append(String.format(" = %d점", k26_totalScore));
		} else {
			k26_stringBuilder.append(String.format("%d점", k26_totalScore));
		}
		System.out.println(k26_stringBuilder.toString());
		k26_stringBuilder.setLength(0);
		return k26_totalScore;
	}	// k26_scoring method end
	
	/** k26_revisedItem method
	  *  DB에 저장된 Item data를 수정하는 update query를 수행하는 method
	  * @return (int) -1 -> (k26_itemCompare method의 작동 결과,
	  * 					 k26_whileLoop method에서 -1을 return 받고 while문 escape하여
	  * 					 k26_initialMenu method로 되돌아간다.
	  */
	public static int k26_revisedItem() {
		/* DB에 등록된 모든 item의 정보를 no로 정렬하여 K26_Menu Class의 
		 * static List에 item data를 저장 */
		k26_itemListInitialize();
		k26_itemListPrint();		// static List에 저장된 item data를 출력

		System.out.print("\n수정할 물품의 No를 입력하세요 : ");
		String k26_itemNo = k26_scanner.nextLine();
		
		System.out.println("수정할 수 있는 항목");
		System.out.println("no, name, weight, displaySize, diskVolume, etc, price");
		System.out.println("잘못 입력하는 경우 수정 메뉴 처음으로 되돌아갑니다.");
		System.out.println("수정할 항목을 입력하세요 : ");
		String k26_revisedColumn = k26_scanner.nextLine();
		
		System.out.print("수정할 값을 입력하세요 : ");
		String k26_setVal = k26_scanner.nextLine();
		
		// update query 작성
		k26_stringBuilder.append("update ").append(k26_tblName).append(" set ").append(k26_revisedColumn).append(" = ");
		
		// k26_getFieldType method를 사용해 item의 Field의 data type을 String으로 return받아 저장 
		k26_itemFieldType = k26_item.k26_getFieldType();
		// k26_revisedColumn != null && "String"인 경우
		if(k26_itemFieldType.get(k26_revisedColumn)!= null && k26_itemFieldType.get(k26_revisedColumn).equals("String")) {
			// "update item_tbl set revisedColumn = 'setVal' where no = itemNo"
			k26_stringBuilder.append("'").append(k26_setVal).append("'");
		// k26_revisedColumn != null && "String"이 아닌 경우
		} else if(k26_itemFieldType.get(k26_revisedColumn)!= null) {
			// "update item_tbl set revisedColumn = setVal where no = itemNo"
			k26_stringBuilder.append(k26_setVal);
		// k26_revisedColumn == null 인 경우
		} else if(k26_itemFieldType.get(k26_revisedColumn) == null) {
			System.out.println("\n잘못된 입력입니다.");
			/* k26_whileLoop method에서 -1을 return 받고 while문 escape하여
			 * k26_initialMenu method로 되돌아간다.*/
			return -1;
		}
		k26_stringBuilder.append(" where no = ").append(k26_itemNo);
		
		// 작성된 query를 실행
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		// 공용 StringBuilder 사용 후 초기화
		k26_stringBuilder.setLength(0);
		/* DB에 등록된 모든 item의 정보를 no로 정렬하여 K26_Menu Class의 
		 * static List에 item data를 저장 */
		k26_itemListInitialize();
		k26_itemListPrint();	// static List에 저장된 item data를 출력
		/* k26_whileLoop method에서 -1을 return 받고 while문 escape하여
		 * k26_initialMenu method로 되돌아간다.*/
		return -1;
	}	// k26_revisedItem method end
	
	/** k26_insertItem method
	  * DB에 새로운 Item record를 입력하는 insert query를 수행하는 method
	  * @return (int) -1 -> (k26_itemCompare method의 작동 결과,
	  * 					 k26_whileLoop method에서 -1을 return 받고 while문 escape하여
	  * 					 k26_initialMenu method로 되돌아간다.
	  */
	public static int k26_insertItem() {
		System.out.println("\n새로운 물품을 등록합니다.");
		// 실행할 insert query 작성
		k26_stringBuilder.append("insert into ").append(k26_tblName).append(" ")
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
		// input 입력 없이 엔터만 입력한 경우, 공백을 엔터로 간주하고 아닌 경우 input을 query에 포함
		k26_stringBuilder.append(k26_input.isEmpty()? " ":k26_input).append("', ");
		
		System.out.print("물품의 가격을 만원 단위로 입력 후 엔터를 눌러주세요 : ");
		k26_stringBuilder.append(k26_scanner.nextLine()).append(")");
		
		// 작성된 query를 실행
		k26_dbDAO.k26_executeQuery(k26_stringBuilder.toString());
		
		// 공용 StringBuilder 사용 후 초기화
		k26_stringBuilder.setLength(0);
		
		/* DB에 등록된 모든 item의 정보를 no로 정렬하여 K26_Menu Class의 
		 * static List에 item data를 저장 */
		k26_itemListInitialize();
		
		k26_itemListPrint();	// static List에 저장된 item data를 출력
		
		/* k26_whileLoop method에서 -1을 return 받고 while문 escape하여
		 * k26_initialMenu method로 되돌아간다.*/
		return -1;
	}	// k26_insertItem method end
	
	/** k26_deleteItem method
	  * DB에 저장된 Item record를 삭제하는 delete query를 수행하는 method
	  * @return (int) -1 -> (k26_itemCompare method의 작동 결과,
	  * 					 k26_whileLoop method에서 -1을 return 받고 while문 escape하여
	  * 					 k26_initialMenu method로 되돌아간다.
	  */
	public static int k26_deleteItem() {
		
		/* DB에 등록된 모든 item의 정보를 no로 정렬하여 K26_Menu Class의 
		 * static List에 item data를 저장 */
		k26_itemListInitialize();
		
		k26_itemListPrint();	// static List에 저장된 item data를 출력
		
		System.out.print("\n삭제할 물품의 번호를 입력하세요. 상위 메뉴로 되돌아가려면 0을 입력하세요.: ");
		String itemNo = k26_scanner.nextLine();
		
		/* 입력 받은 itemNo가 DB에 등록된 모든 item의 record를 담고 있는 itemList의 size 이하
		 * and itemNo는 0보다 커야 한다.*/
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
		// itemNo가 0인 경우
		} else if(Integer.parseInt(itemNo) == 0) {
			/* k26_whileLoop method에서 -1을 return 받고 while문 escape하여
			 * k26_initialMenu method로 되돌아간다.*/
			return -1;
		// itemNo를 잘못 입력 한 경우
		} else {
			/* k26_whileLoop method에서 0을 return 받고 
			 * while문에서 continue하여 k26_whileLoop method 재반복*/
			return 0;
		}
	}	// k26_deleteItem method end
	
	/** k26_itemListInitialize method
	  * K26_Menu Class 내 static List인 itemList를 clear하고 
	  * DB에서 no로 order by asc 하여 select query를 실행한 결과를
	  * 다시 static List인 itemList에 저장하는, 초기화 작업을 수행하는 method
	  * @return void
	  */
	public static void k26_itemListInitialize() {
		k26_itemList.clear();
		k26_itemList = k26_dbDAO.k26_itemDataLoadQuery("select * from " + k26_tblName + " order by no");
	}	// k26_itemListInitialize method end
}	// K26_Menu class end