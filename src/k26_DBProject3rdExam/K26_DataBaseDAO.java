package k26_DBProject3rdExam;

import java.sql.*;
import java.util.*;

public class K26_DataBaseDAO {
	private static final String k26_ID = "root";
	private static final String k26_PASSWORD = "qortjf90";
	
	private static Connection k26_connection = null;
	private static String k26_DBName = "";
	
	public String k26_getDB_NAME() {
		return k26_DBName;
	}

	public void k26_setDBName(String k26_inputDBName) {
		k26_DBName = k26_inputDBName;
	}
	
	public Connection k26_connectDB () {
		StringBuilder k26_stringBuilder = new StringBuilder();
		String k26_useDB_SERVER = "localhost:3306";
		String k26_useDB_URL_Option = k26_stringBuilder.append("?useUnicode=true&characterEncoding=utf8")
					.append("&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true")
					.append("&useSSL=false").toString();

		k26_stringBuilder.setLength(0);
		String k26_DB_URL = k26_stringBuilder.append("jdbc:mysql://").append(k26_useDB_SERVER)
				.append("/").append(k26_useDB_URL_Option).toString();
		k26_stringBuilder.setLength(0);
		// driver 존재 여부 확인
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException k26_Error) {
		    System.out.println("ERROR] JDBC Driver load: \n" + k26_Error.getMessage());
		    k26_Error.printStackTrace();
		}
		
		// Open a connection
		try {
			k26_connection = DriverManager.getConnection(k26_DB_URL, k26_ID, k26_PASSWORD);
		} catch (SQLException k26_Error) {
			System.out.println("Error] Connection fail: \n" + k26_Error.getMessage());
			k26_Error.printStackTrace();
		}
		// connection을 살려서 반환
		return k26_connection;
	}
	
	// 데이터베이스가 있는지 확인하고, 없으면 데이터베이스 생성 후 데이터베이스 전환 
	public void k26_setDB() {
		PreparedStatement k26_preStatement = null;
		ResultSet k26_resultSet = null;
		try {
			String k26_DBSql = "SELECT * FROM Information_schema.SCHEMATA WHERE SCHEMA_NAME = ?";
			k26_preStatement = k26_connection.prepareStatement(k26_DBSql);
			k26_preStatement.setString(1, k26_DBName);
			k26_resultSet = k26_preStatement.executeQuery();
			//데이터베이스가 없다면 데이터베이스 생성
			if(!k26_resultSet.next()){
				Statement k26_statement = k26_connection.createStatement();
				System.out.println("database not found");
				System.out.println("create new database " + k26_DBName);
				String k26_dbSql = "create database if not exists " + k26_DBName;
				
				/* statement.execute(sql)
				 * return true if the first result is a ResultSet object 
			     * return false if it is an update count or there are no results 
			     * create database db_name query는 row의 count를 반환하므로 
			     * return true인 경우 DB 생성 실패를 의미한다. */ 
				if(k26_statement.execute(k26_dbSql)) {
					System.out.println("데이터베이스 생성 실패");
				}
				k26_statement.close();
			} 
			//데이터베이스를 변환 (use database)
			k26_connection.setCatalog(k26_DBName);
		} catch (Exception k26_Error) {
			System.out.println("CreateOrChangeDatabase error : " + k26_Error);
		} finally {
			try{
				if(k26_resultSet != null) {
					k26_resultSet.close();
				}
				if(k26_preStatement != null) {
					k26_preStatement.close();
				}
				if(k26_connection != null) {
					k26_preStatement.close();
				}
			} catch (Exception k26_Error) {
				System.out.println("Close error : " + k26_Error);
				k26_Error.printStackTrace();
			}
		}
	}
	
	public void k26_executeQuery(String k26_query) {
		Statement k26_statement = null;
		try {
			k26_statement = k26_connection.createStatement();
			if(k26_statement != null && k26_statement.executeUpdate(k26_query) >= 0) {
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
	
	public List<K26_Item> k26_itemDataLoadQuery (String k26_query) {
		List<K26_Item> k26_result = new ArrayList<>();
		Statement k26_statement = null;
		ResultSet k26_resultSet = null;
		try {
			k26_statement = k26_connection.createStatement();
			if((k26_resultSet = k26_statement.executeQuery(k26_query)) != null) {
			}
			while (k26_resultSet != null && k26_resultSet.next()) {
				K26_Item k26_item = new K26_Item(
						  k26_resultSet.getInt("no")
						, k26_resultSet.getString("name")
						, k26_resultSet.getInt("weight")
						, k26_resultSet.getDouble("display_size")
						, k26_resultSet.getInt("disk_volume")
						, k26_resultSet.getString("etc")
						, k26_resultSet.getInt("price"));
				k26_result.add(k26_item);
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
	
	public List<List<String>> k26_dataPrintQuery (String k26_query) {
		List<List<String>> k26_result = new ArrayList<>();
		Statement k26_statement = null;
		ResultSet k26_resultSet = null;
		ResultSetMetaData k26_rsmd = null;
		try {
			k26_statement = k26_connection.createStatement();
			System.out.println("Query: " + k26_query);
			if((k26_resultSet = k26_statement.executeQuery(k26_query)) != null) {
				System.out.println("----   Query O.K.   ----");
				k26_rsmd = (ResultSetMetaData) k26_resultSet.getMetaData();
			}
			while (k26_resultSet != null && k26_resultSet.next()) {
				List<String> tempList = new ArrayList<>();
				for(int i = 1; i <= k26_rsmd.getColumnCount(); i++) {
					String k26_tempStr = k26_resultSet.getString(i);
					System.out.print(" " + k26_tempStr);
					tempList.add(k26_resultSet.getString(i));
				}
				System.out.println();
				k26_result.add(tempList);
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
}
