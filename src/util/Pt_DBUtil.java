package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pt_DBUtil {
	
	//Oracle DB연결을 util로 만들었음
	public static Connection getConnection() {
		Connection conn= null;
		String url= "jdbc:oracle:thin:@192.168.0.57:1521:XE";
		String userid="pt", userpass="pt";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,userid,userpass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
}
	
	public static void dbDisconnect(Connection conn, Statement st, ResultSet rs) {
		
			try {
				if(rs!= null) rs.close();
				if(st!= null) st.close();
				if(conn!= null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
