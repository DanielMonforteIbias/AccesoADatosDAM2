package operacionesEmple;

import java.sql.Connection;
import java.sql.SQLException;

public class Principal {
	public static void main(String[] args) throws SQLException {
		OperacionesEmple o=new OperacionesEmple();
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		//Connection conexion=Conexiones.getOracle("EJEMPLO", "dam");
		//Connection conexion=Conexiones.getSQLite("basesdatos/sqlite/ejemplo.db");
		if(conexion!=null) {
			int emp_no=126;
			if (o.comprobarEmple(conexion,emp_no)) System.out.println("Existe el empleado "+emp_no);
			else System.out.println("No existe el empleado "+emp_no);
			
			//BORRAR
			System.out.println(o.borrarEmple(conexion, emp_no));
			System.out.println(o.borrarEmple(conexion, 1000));
			System.out.println(o.borrarEmple(conexion, 7499));
			
			//MODIFICAR
			java.util.Date utilDate=new java.util.Date();
			java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
			System.out.println(o.modificaremple(conexion, 124, "NuevoNombre", "PROGRAMADOR", 1600, 0, sqlDate, 20,7369)); //Okay
			System.out.println(o.modificaremple(conexion, 127, "NuevoNombre", "PROGRAMADOR", 1600, 0, sqlDate, 20,7369)); //Error, no existe
			System.out.println(o.modificaremple(conexion, 125, "NuevoNombre", "PROGRAMADOR", 1600, 0, sqlDate, 80,7369)); //Error, departamento no existe
		
			//INSERTAR
			System.out.println(o.insertarEmple(conexion, 1234, "Nuevo1234", "PROGRAMADOR", 1300, 0, sqlDate, 20, 7369)); //Okay
			System.out.println(o.insertarEmple(conexion, 1235, "Nuevo1235", "PROGRAMADOR", 1300, 0, sqlDate, 80, 7369)); //Error departamento
			System.out.println(o.insertarEmple(conexion, 1236, "Nuevo12355555555555555", "PROGRAMADOR", 1300, 0, sqlDate, 20, 7369)); //Error campo muy largo
		
			//VER DATOS
			o.verEmpleados(conexion);
			
			//VER DATOS DE UN EMPLEADO
			o.verUnEmpleado(conexion, 124); //Existe
			o.verUnEmpleado(conexion, 1244); //No existe
		}
		else {
			System.out.println("ERROR EN LA CONEXION");
		}
		conexion.close();
	}
	
}
