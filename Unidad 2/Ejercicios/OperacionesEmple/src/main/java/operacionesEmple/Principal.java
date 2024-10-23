package operacionesEmple;

import java.sql.Connection;
import java.sql.SQLException;

public class Principal {
	public static void main(String[] args) throws SQLException {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		//Connection conexion=Conexiones.getOracle("EJEMPLO", "dam");
		//Connection conexion=Conexiones.getSQLite("basesdatos/sqlite/ejemplo.db");
		if(conexion!=null) {
			int emp_no=126;
			if (OperacionesEmple.comprobarEmple(conexion,emp_no)) System.out.println("Existe el empleado "+emp_no);
			else System.out.println("No existe el empleado "+emp_no);
			
			System.out.println(OperacionesEmple.borrarEmple(conexion, emp_no));
			System.out.println(OperacionesEmple.borrarEmple(conexion, 1000));
			System.out.println(OperacionesEmple.borrarEmple(conexion, 7499));
		}
		else {
			System.out.println("ERROR EN LA CONEXION");
		}
		conexion.close();
	}
}
