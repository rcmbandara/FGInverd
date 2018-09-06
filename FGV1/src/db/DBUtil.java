package db;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String P_CONN_STRING = "";
	private static String M_CONN_STRING = "";

	public static Connection getConnection(DBType dbType) throws SQLException {

		int lineNo;
		
		String x =System.getProperty("os.name");
		
		
		try {
			FileReader fr = new FileReader("C:\\Program Files\\MySQL\\MySQL Server 5.7\\DBUtil.txt");
			//FileReader fr = new FileReader("/home/fg-admin/DBUtil/DBUtil.txt");
			
			BufferedReader br = new BufferedReader(fr);
			for (lineNo = 1; lineNo < 10; lineNo++) {
				if (lineNo == 1) {
					M_CONN_STRING = br.readLine();
				} else if (lineNo == 2) {
					USERNAME = br.readLine();
				} else if (lineNo == 3) {
					PASSWORD = br.readLine();
				} else if (lineNo == 4) {
					P_CONN_STRING = br.readLine();
				} else
					br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (dbType) {
		case MYSQL:
			return DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
		case POSTGRESQL:
			return DriverManager.getConnection(P_CONN_STRING, USERNAME, PASSWORD);
		default:
			return null;
		}
	}

	public static void processException(SQLException e) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}
}
