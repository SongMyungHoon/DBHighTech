package musicPlayer;

import java.sql.*;
import java.util.*;

public class K26_TestMain {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
//		MusicDAO.k26_setDB_NAME("project_db_3rd");
//		String query = "create database if not exists project_db_3rd";
//		MusicDAO.executeStatement(query);
//		String query = sb.append("create table if not exists item(")
//				.append("No int primary key auto_increment")
//				.append(", Name varchar(100)")
//				.append(", Weight_g int")
//				.append(", ScreenSize_inch int")
//				.append(", DiskCapacity_GB int")
//				.append(", etc varchar(100)")
//				.append(", Price_만원 int)").toString();
//		MusicDAO.executeStatement(query);
//		String query = "drop table item";
//		MusicDAO.executeStatement(query);
//		String query = "explain item";
//		MusicDAO.selectStatement(query);
		
//		Scanner sc = new Scanner(System.in);
//		boolean cycle = true;
//		while(cycle) {
//			menuPrint();
//			int menuSelected = sc.nextInt();
//			switch(menuSelected) {
//				case 1:
//					System.out.println("----- LIST OF DATABASE -----");
//					MusicDAO.selectStatement("show databases");
//					break;
//				case 2:
//					System.out.print("Database name: ");
//					MusicDAO.setDB_NAME(sc.next());
//					MusicDAO.selectStatement("use " + MusicDAO.getDB_NAME());
//					System.out.println("----- CONNECTING DB : " + MusicDAO.getDB_NAME() + " -----");
//					System.out.println("");
//					break;
//				case 3:
//					queryDBChange("use " + useDB, statement, resultSet, connection);
//					System.out.println("----- CONNECTING DB : " + MusicDAO.getDB_NAME() + " -----");
//					System.out.println("");
//					System.out.println("----- LIST OF TABLES -----");
//					MusicDAO.selectStatement("show tables");
//					break;
//				case 4:
//					queryIn("use " + useDB, statement, resultSet, connection);
//					System.out.println("----- CONNECTING DB : " + MusicDAO.getDB_NAME() + "  -----");
//					System.out.print("TABLE name: ");
//					String tblName = sc.next();
//					System.out.println("QUERY: SELECT * FROM " + tblName);
//					MusicDAO.selectStatement("SELECT * FROM " + tblName);
//					break;
//				case 0:
//					System.out.println(" Exit ");
//					cycle = false;
//				default:
//					System.out.println(" Illegal input");
//					continue;
//			}
//		}
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
}
