package pruebasSentencias;

import java.sql.*;

public class Principal {
	public static void main(String[] args) {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		System.out.println("PRUEBA VER EMPLEADOS MYSQL");
		//ERROR EN EMPLEADO, DIRECTOR, DEP, APELLIDO, OFICIO Y SALARIO
		System.out.println(insertarEmpleado(conexion,7369,"","",222,-1500,100,45));
		//ERROR EN EMPLEADO Y APELLIDO
		System.out.println(insertarEmpleado(conexion,7369,"","INFORMATICO",7499,1500,100,10));
		//ERROR EN DEP Y OFICIO
		System.out.println(insertarEmpleado(conexion,123,"EMPLE123","",7499,1500,100,45));
		//DATOS OK
		System.out.println(insertarEmpleado(conexion,122,"EMPLE123","INFORMATICO",7499,1500,100,10));
	}
	public static void mainVerEmpleados(String[] args) {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		System.out.println("PRUEBA VER EMPLEADOS MYSQL");
		verempleadosdep(conexion, 10);
		
		conexion=Conexiones.getDerby("basesdatos/derby/ejemplo.db");
		System.out.println("PRUEBA VER EMPLEADOS DERBY");
		verempleadosdep(conexion, 10);
	}
	public static void mainInsertarDepartamentos(String[] args) {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		System.out.println("PRUEBA INSERTAR DEP MYSQL");
		System.out.println(insertarDepartamento(conexion, 12,"DEP 12", "TOLEDO"));
		
		
		conexion=Conexiones.getSQLite("basesdatos/sqlite/ejemplo.db");
		System.out.println("PRUEBA INSERTAR DEP SQLITE");
		System.out.println(insertarDepartamento(conexion, 12,"DEP 12", "TOLEDO"));
		
		
		conexion=Conexiones.getOracle("ejemplo", "dam");
		System.out.println("PRUEBA INSERTAR EN ORACLE");
		System.out.println(insertarDepartamento(conexion, 12,"DEP 12", "TOLEDO"));
	}
	public static void mainCrearVista(String[] args) {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		System.out.println("PRUEBA CREAR VISTA MYSQL");
		String vista="CREATE OR REPLACE VIEW totales (dep, dnombre, nemp, media) AS SELECT d.dept_no, dnombre, COUNT(emp_no), AVG(salario) FROM departamentos d LEFT JOIN empleados e ON e.dept_no = d.dept_no GROUP BY d.dept_no, dnombre";
		crearVista(conexion,vista);
		
		
		
		conexion=Conexiones.getOracle("ejemplo", "dam");
		System.out.println("PRUEBA SUBIDA EN ORACLE");
		crearVista(conexion, vista);
	}
	
	public static void mainSubirSalario(String[] args) {
		//Subir el salario a los empleados de un departamento
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		System.out.println("PRUEBA SUBIDA EN MYSQL");
		subirSalario(conexion,100,10);
		
		conexion=Conexiones.getOracle("ejemplo", "dam");
		System.out.println("PRUEBA SUBIDA EN ORACLE");
		subirSalario(conexion, 100, 10);
		
		conexion=Conexiones.getSQLite("basesdatos/sqlite/ejemplo.db");
		System.out.println("PRUEBA SUBIDA EN SQLITE");
		subirSalario(conexion, 100, 10);
		
	}

	private static void subirSalario(Connection conexion, int subida, int departamento) {
		String sql="UPDATE empleados SET salario= salario+"+subida+" WHERE dept_no="+departamento;
		System.out.println(sql);
		try {
			Statement sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(sql);  
			System.out.printf("Empleados modificados: %d %n", filas);
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void crearVista(Connection conexion, String vista) {
		System.out.println("Vista a crear: "+vista);
		try {
			Statement sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(vista);  
			System.out.println("Vista creada");
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String insertarDepartamento(Connection conexion, int dept, String nom, String loc) {
		String mensaje = "";
		try {
			String sql = "INSERT INTO departamentos VALUES("
	                  + dept + ", '" + nom + "', '" + loc + "')";
			Statement sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(sql);
			mensaje = "Registro Insertado. Filas afectadas: " + filas;
			sentencia.close();
			conexion.close();
		} catch (SQLException e) {
		 mensaje = "Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage();
		}
		return mensaje;
	}
	
	public static String insertarEmpleado(Connection conexion, int emp_no, String apellido, String oficio, int director, float salario, float comision, int dept_no) {
		String mensaje="";
		boolean error=false;
		String sql="select count(*) from empleados where emp_no="+emp_no;
		try {
			//COMPROBACIONES
			//Comprobar si el empleado existe
			Statement sentencia = conexion.createStatement();
			ResultSet resul=sentencia.executeQuery(sql);
			resul.next(); //Nos posicionamos en la fila que devuelve
			int cuenta=resul.getInt(1);
			if (cuenta==1) { //Si el contador es 1, el empleado existe
				mensaje+="EL NUM DE EMPLEADO "+emp_no+" YA EXISTE, ERROR AL INSERTAR.\n";
				error=true;
			}
			//Comprobar si el director existe
			sql="select count(*) from empleados where emp_no="+director;
			sentencia = conexion.createStatement();
			resul=sentencia.executeQuery(sql);
			resul.next(); //Nos posicionamos en la fila que devuelve
			cuenta=resul.getInt(1);
			if (cuenta==0) { //Si el contador es 1, el director existe
				mensaje+="EL DIRECTOR "+director+" NO EXISTE EN EMPLEADOS, ERROR AL INSERTAR.\n";
				error=true;
			}
			//Comprobar si el departamento existe
			sql="select count(*) from departamentos where dept_no="+dept_no;
			sentencia = conexion.createStatement();
			resul=sentencia.executeQuery(sql);
			resul.next(); //Nos posicionamos en la fila que devuelve
			cuenta=resul.getInt(1);
			if (cuenta==0) { //Si el contador es 0, el departamento no existe
				mensaje+="EL NUM DE DEPARTAMENTO "+dept_no+" NO EXISTE EN LA TABLA DEPARTAMENTOS.\n";
				error=true;
			}
			//Otras comprobaciones
			if (salario<0) {
				mensaje+="EL SALARIO ES NEGATIVO, ERROR NO PUEDE SER NEGATIVO.\n";
				error=true;
			}
			if (apellido.equals("")||apellido==null) {
				mensaje+="EL APELLIDO NO PUEDE SER NULO.\n";
				error=true;
			}
			if (oficio.equals("")||oficio==null) {
				mensaje+="EL OFICIO NO PUEDE SER NULO.\n";
				error=true;
			}
			//FIN COMPROBACIONES
			if (!error) { //Si no hay error, se inserta
				 mensaje+=insertarEmple(conexion,emp_no,apellido,oficio,director,salario,comision,dept_no);
			}
			else mensaje+="REGISTRO NO INSERTADO"; //Si hay error, no se inserta
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mensaje;
		
		
	}
	public static String insertarEmple(Connection conexion, int emp_no, String apellido, String oficio, int director, float salario, float comision, int dept_no) {
		String mensaje="";
		java.util.Date utilDate=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
		System.out.println(utilDate);
		System.out.println(sqlDate);
		return mensaje;
	}
	public static void verempleadosdep(Connection conexion, int dept) {
		try {
			String sql = "select emp_no, apellido, oficio, salario from empleados where dept_no = " + dept;
			Statement sentencia = conexion.createStatement();
			ResultSet resul = sentencia.executeQuery(sql);
			System.out.println("Datos de los empleados del dep : " + dept);
			while (resul.next()) {
				System.out.println("Emp-no:" +  resul.getInt(1) +". Apellido: " +  resul.getString(2) +". Oficio: " +  resul.getString(3) +". Salario: " +  resul.getFloat(4) );				
			}
			resul.close();
			sentencia.close();

		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
	}
}