package musicPlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class K26_MusicDAO {
	private static String k26_DB_NAME = "project_db_3rd";
//	private static String DB_NAME = "java_practice";
	private static final String k26_ID = "root";
	private static final String k26_PASSWORD = "qortjf90";
	
	private static Connection k26_connection;
	private static Scanner k26_scanner = new Scanner(System.in);
	
	
	public static String k26_getDB_NAME() {
		return k26_DB_NAME;
	}

	public static void k26_setDB_NAME(String dB_NAME) {
		k26_DB_NAME = dB_NAME;
	}

	public static Connection k26_connectDB () {
		PreparedStatement k26_preStatement = null;
		ResultSet k26_resultSet = null;
		StringBuilder k26_sb = new StringBuilder();
		String k26_useDB_SERVER = "localhost:3306";
		String k26_useDB_URL_Option = k26_sb.append("?useUnicode=true&characterEncoding=utf8")
					.append("&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true")
					.append("&useSSL=false").toString();
//		String dbSql = "SELECT * FROM Information_schema.SCHEMATA WHERE SCHEMA_NAME = ?";

		k26_sb.setLength(0);
		String k26_DB_URL = k26_sb.append("jdbc:mysql://").append(k26_useDB_SERVER)
				.append("/").append(k26_useDB_URL_Option).toString();
		k26_sb.setLength(0);
		// driver 존재 여부 확인
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException k26_Error) {
		    System.out.println("ERROR] JDBC Driver load: \n" + k26_Error.getMessage());
		    k26_Error.printStackTrace();
		}
		
		// Open a connection
		try {
			System.out.println("---- Connecting DB ----");
			k26_connection = DriverManager.getConnection(k26_DB_URL, k26_ID, k26_PASSWORD);
//			preStatement = connection.prepareStatement(dbSql);
//			preStatement.setString(1,  DB_NAME);
//			resultSet = preStatement.executeQuery();
//			if(connection == null) {
//				System.out.println("Database not found");
//				System.out.println("Create new database");
//				System.out.print("database name : ");
//				DB_NAME = sc.next();
//				
//				String query = sb.append("create database ").append(DB_NAME).toString();
//				dDLQuery(query);
//				connection.setCatalog(DB_NAME);
//			}
			// 데이터베이스를 변환 (use DB_NAME)
			k26_connection.setCatalog(k26_DB_NAME);
		} catch (SQLException k26_Error) {
			System.out.println("Error] Connection fail: \n" + k26_Error.getMessage());
			k26_Error.printStackTrace();
		}
		System.out.println("---- DB connection complete ----");
		// connection을 살려서 반환
		return k26_connection;
	}
	// Data Definition Language: create, alter, drop, truncate
//	public static void dDLQuery(String query) {
//		PreparedStatement preStatement = null;
//		Connection connection = connectDB();
//		try {
//			preStatement = connection.prepareStatement(query);
//			System.out.println("Query: " + query);
//			/**PreparedStatement.executeUpdate()
//		     * Executes the SQL statement in this <code>PreparedStatement</code> object,
//		     * which must be an SQL Data Manipulation Language (DML) statement, 
//		     * such as <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>;
//			 * or an SQL statement that returns nothing, such as a DDL statement.
//		     * @return either (1) the row count for SQL Data Manipulation Language (DML) 
//		     * 		   statements or (2) 0 for SQL statements that return nothing
//		     */
//			preStatement.executeUpdate();
//			System.out.println("----   Query O.K.   ----");
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		} finally {
//			try {
//				if(preStatement != null) {
//					preStatement.close();
//				}	
//			} catch(SQLException e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//			}	// end finally try
//		}	// end try
//	}	// end stmtQuery method
	
	public static List<List<String>> k26_selectStatement (String query) {
		List<List<String>> k26_result = new ArrayList<>();
		Statement k26_statement = null;
		ResultSet k26_resultSet = null;
		ResultSetMetaData k26_rsmd = null;
		Connection k26_connection = k26_connectDB();
		try {
			k26_statement = k26_connection.createStatement();
			System.out.println("Query: " + query);
			if((k26_resultSet = k26_statement.executeQuery(query)) != null) {
				System.out.println("----   Query O.K.   ----");
				k26_rsmd = (ResultSetMetaData) k26_resultSet.getMetaData();
			}
			while (k26_resultSet != null && k26_resultSet.next()) {
				List<String> k26_tempList = new ArrayList<>();
				for(int i = 1; i <= k26_rsmd.getColumnCount(); i++) {
					String k26_str = k26_resultSet.getString(i);
					System.out.print(" " + k26_str);
					k26_tempList.add(k26_str);
				}
				System.out.println();
				k26_result.add(k26_tempList);
			}	// end while
		} catch (SQLException k26_Error) {
			System.out.println(k26_Error.getMessage());
			k26_Error.printStackTrace();
		} finally {
			try {
				if(k26_statement != null) {
					k26_statement.close();
				}
			} catch(SQLException k26_Error) {
				System.out.println(k26_Error.getMessage());
				k26_Error.printStackTrace();
			}	// end finally try 
		}	// end try
		return k26_result;
	}
	// Data Manipulation Language: select, insert, update, delete
	public static void k26_executeStatement(String k26_query) {
		Connection k26_connection = k26_connectDB();
		Statement k26_statement = null;
		try {
			System.out.println("Query: " + k26_query);
			k26_statement = k26_connection.createStatement();
			if(k26_statement.executeUpdate(k26_query) >= 0) {
				System.out.println("----   Query O.K.   ----");
			} else {
				System.out.println("Error] Execute fail");
			}
		} catch (SQLException k26_Error) {
			System.out.println(k26_Error.getMessage());
			k26_Error.printStackTrace();
		} finally {
			try {
				if(k26_statement != null) {
					k26_statement.close();
				}	
			} catch(SQLException k26_Error) {
				System.out.println(k26_Error.getMessage());
				k26_Error.printStackTrace();
			}	// end finally try 
		}	// end try
	}
}
