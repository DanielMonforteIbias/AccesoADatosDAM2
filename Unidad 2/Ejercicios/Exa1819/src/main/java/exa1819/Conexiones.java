package exa1819;

import java.sql.*;
public class Conexiones {
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
