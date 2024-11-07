package monforteibiasdaniel;

import java.sql.*;
public class Conexiones {

	public static Connection getMySQL(String bd,String usuario, String password) {
		Connection conexion=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+bd, usuario,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR SQLException: "+e.getMessage());
		}
		return conexion;
	}
	public static Connection getOracle(String usuario, String password) {
		Connection conexion=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", usuario,password); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("ERROR SQLException: "+e.getMessage());
		}
		return conexion;
	}
}
