package task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PracNum2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		boolean cycle = true;
		System.out.print("Database name : ");
		String useDB = sc.next();
		while(cycle) {
			menuPrint();
			int menuSelected = sc.nextInt();
			switch(menuSelected) {
				case 1:
					System.out.println("----- LIST OF DATABASE -----");
					queryIn("show databases", statement, resultSet, connection, useDB);
					break;
				case 2:
					System.out.print("Database name: ");
					useDB = sc.next();
					queryDBChange("use " + useDB, statement, resultSet, connection);
					System.out.println("----- CONNECTING DB : " + useDB + " -----");
					System.out.println("");
					break;
				case 3:
//					queryDBChange("use " + useDB, statement, resultSet, connection);
					System.out.println("----- CONNECTING DB : " + useDB + " -----");
					System.out.println("");
					System.out.println("----- LIST OF TABLES -----");
					queryIn("show tables", statement, resultSet, connection, useDB);
					break;
				case 4:
//					queryIn("use " + useDB, statement, resultSet, connection);
					System.out.println("----- CONNECTING DB : " + useDB + "  -----");
					System.out.print("TABLE name: ");
					String tblName = sc.next();
					System.out.println("QUERY: SELECT * FROM " + tblName);
					queryIn("SELECT * FROM " + tblName, statement, resultSet, connection, useDB);
					break;
				default:
					cycle = false;
					System.out.println(" Illegal input");
					continue;
			}
		sc.close();
		}
		
	}
	public static void menuPrint() {
		System.out.println("######################################");
		System.out.println("############# MY DATABASE ############");
		System.out.println("######################################");
		System.out.println("[1] LIST OF DATABASE");
		System.out.println("[2] USE DATABASE");
		System.out.println("[3] LIST OF TABLES");
		System.out.println("[4] SELECT * FROM A TABLE");
		System.out.print("Input number : ");
	}
	public static void queryDBChange(String query, Statement statement, ResultSet resultSet
			, Connection connection) {
		String id = "root";
		String password = "qortjf90";
		String useDB = query.substring(4);
		String useDB_URL = "jdbc:mysql://localhost:3306/";
		String useDB_URL_Option = "?useUnicode=true&characterEncoding=utf8"
				+ "&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
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
				for(int i = 1; i <= columnSize; i++) {
					String str = resultSet.getString(i);
					System.out.println(" " + str);
				}
			}
			System.out.println("");
		} catch(NullPointerException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void queryIn(String query, Statement statement, ResultSet resultSet
			, Connection connection, String useDB) {
		String id = "root";
		String password = "qortjf90";
		String useDB_URL = "jdbc:mysql://localhost:3306/";
		String useDB_URL_Option = "?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
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
					System.out.print(" " + str);
				}
				System.out.println();
			}
			System.out.println();
		} catch(NullPointerException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
