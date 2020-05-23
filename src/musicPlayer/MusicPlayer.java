package musicPlayer;

import java.sql.*;
import java.util.*;

public class MusicPlayer {
	public static void main(String[] args) {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		while(true) {
			System.out.println("------ MENU ------");
			System.out.println("1. 재생 목록에 음악 추가");
			System.out.println("2. 재생 목록");
			System.out.println("3. 음악 차트");
			System.out.print("입력: ");
			int menuSelected = sc.nextInt();
			switch(menuSelected) {
				case 1:
					musicPlay(sc, sb, statement, resultSet, connection);
					break;
				case 2:
					playList(sc, sb,statement, resultSet, connection);
					break;
				case 3:
					musicRank(sc, sb,statement, resultSet, connection);
					break;
				default: 
					System.out.println("메뉴를 잘못 입력하셨습니다.");
					System.out.println("다시 입력해 주세요.");
					break;
			}
		}
	}
	public static void musicPlay(Scanner sc, StringBuilder sb, Statement statement
			, ResultSet resultSet, Connection connection) {
		String tblName = "music_list";
		String useDB = "music_player";
		sb.append("id, ").append("곡이름, ").append("가수 ");
		String columns = sb.toString();
		sb.setLength(0);
		sb.append("select ").append(columns).append("from ").append(tblName);
		String query = sb.toString();
		sb.setLength(0);
		System.out.println(query);
		String mod = query.split(" ")[0];
		System.out.println("---- 곡 목록 ----");
		System.out.println("|No.|곡 명|가수명|");
		System.out.println("---------------");
		queryIn(query, statement, resultSet, connection, useDB, mod);
		while(true) {			
			System.out.println("재생하고 싶은 노래의 번호를 입력하세요.");
			System.out.println("이전 메뉴로 돌아가시려면 숫자 0을 입력하세요.");
			System.out.print("입력: ");
			int menuSelected = sc.nextInt();
			if(menuSelected == 0) {
				System.out.println("Back");
				return;
			} else if(menuSelected <= 20 && menuSelected >= 1) {
				String updateQuery = "update music_list set ����� = ����� + 1 where id in(" + Integer.toString(menuSelected);
				mod = updateQuery.split(" ")[0];
				System.out.println(query);
				queryIn(updateQuery, statement, resultSet, connection, useDB, mod);
				String selectQuery = "select id, 곡이름, 가수 from music_list where id in(" + Integer.toString(menuSelected)+")";
				mod = selectQuery.split(" ")[0];
				queryIn(selectQuery + ";", statement, resultSet, connection, useDB, mod);
			} else {
				System.out.println("메뉴를 잘못 입력하셨습니다.");
				System.out.println("다시 입력해 주세요.");
			}
		}
	}
	public static void playList(Scanner sc, StringBuilder sb, Statement statement
			, ResultSet resultSet, Connection connection) {

		
	}
	public static void musicRank(Scanner sc, StringBuilder sb, Statement statement
			, ResultSet resultSet, Connection connection) {
		
	}
	public static void queryIn(String query, Statement statement, ResultSet resultSet
			, Connection connection, String useDB, String mod) {
		String id = "root";
		String password = "qortjf90";
		String useDB_URL = "jdbc:mysql://localhost:3306/";
		String useDB_URL_Option = "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
		String dbQuery = query;
		int columnSize = 0;
		try {
			connection = DriverManager.getConnection(useDB_URL + useDB + useDB_URL_Option, id, password);
			statement = connection.createStatement();
			if (statement.execute(dbQuery)) {
				resultSet = statement.getResultSet();
				ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
				columnSize = rsmd.getColumnCount();
			}
			while (resultSet != null && resultSet.next()) {
				
				if(mod.equals("select")||mod.equals("SELECT")) {
					for(int i = 1; i <= columnSize; i++) {
						String str = resultSet.getString(i);
						System.out.print("|" + str);
						if(i == columnSize) {
							System.out.print("|");
						}
					}
					System.out.println();
				}
			}
			System.out.println();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
//	public static void printSelect(int columnSize, ResultSet resultSet) throws SQLException {
//		for(int i = 1; i < columnSize; i++) {
//			String str = resultSet.getString(i);
//			System.out.print("|" + str);
//			if(i == (columnSize - 1)) {
//				System.out.print("|");
//			}
//		}
//		System.out.println();
//	}
//	public static void printSelect(int columnSize, ResultSet resultSet) throws SQLException {
//		for(int i = 1; i < columnSize; i++) {
//			String str = resultSet.getString(i);
//			System.out.print("|" + str);
//			if(i == (columnSize - 1)) {
//				System.out.print("|");
//			}
//		}
//		System.out.println();
//	}
}
