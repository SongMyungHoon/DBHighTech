package task;

import java.sql.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String id = "root";
		String password = "qortjf90";
		String useDB = "practice";
		String useDB_URL = "jdbc:mysql://localhost:3306/";
		String useDB_URL_Option = "?useUnicode=true&characterEncoding=utf8"
				+ "&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true"
				+ "&useSSL=false";
		while(true) {
			System.out.print("Query> ");
			String query = sc.nextLine();
			System.out.println("Query : " + query);
			System.out.println();
			if(query.contains("use")) {
				useDB = query.substring(4);
			}
			String dbQuery = query;
			int columnSize = 0;
			try {
				connection = DriverManager.getConnection(useDB_URL + useDB + useDB_URL_Option, id, password);
				statement = connection.createStatement();
				if (statement.execute(dbQuery) ) {
					resultSet = statement.getResultSet();
					ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
					columnSize = rsmd.getColumnCount();
				}
				while (resultSet != null && resultSet.next()) {
					for(int i = 1; i <= columnSize; i++) {
						String str = resultSet.getString(i);
						System.out.println(" " + str);
					}
				}
			} catch(NullPointerException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		sc.close();
		}
	}
}


//public static void query(String query) {
//	Statement statement = null;
//	ResultSet resultSet = null;
//	Connection connection = null;
//	String id = "root";
//	String password = "qortjf90";
//	String useDB = "";
//	if(query.contains("use")) {
//		useDB = query.substring(4);
//	}
//	System.out.println(useDB);
//	System.out.println(query);
//	String useDB_URL = "jdbc:mysql://localhost:3306/";
//	String useDB_URL_Option = "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
//	String dbQuery = query;
//	int columnSize = 0;
//	try {
//		connection = DriverManager.getConnection(useDB_URL + useDB + useDB_URL_Option, id, password);
//		statement = connection.createStatement();
//		
//		if (statement.execute(dbQuery) ) {
//			System.out.println("Query OK");
//			resultSet = statement.getResultSet();
//			ResultSetMetaData rsmd = (ResultSetMetaData) resultSet.getMetaData();
//			columnSize = rsmd.getColumnCount();
//		}
//		while (resultSet != null && resultSet.next()) {
//			for(int i = 1; i <= columnSize; i++) {
//				String str = resultSet.getString(i);
//				System.out.println(" " + str);
//			}
//		}
//	} catch(NullPointerException e) {
//		e.printStackTrace();
//	} catch(Exception e) {
//		e.printStackTrace();
//	}
//}