package pruebasSentencias;

import java.sql.*;

public class Principal {
	public static void main(String[] args) throws SQLException {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		//Connection conexion=Conexiones.getOracle("EJEMPLO", "dam");
		//Connection conexion=Conexiones.getSQLite("basesdatos/sqlite/ejemplo.db");
		if(conexion!=null) {
			visualizarEmpleadosDepartamento(conexion, 10); //Existe
			System.out.println();
			visualizarEmpleadosDepartamento(conexion, 99); //No existe
			System.out.println();
			visualizarEmpleadosDepartamento(conexion, 40); //Existe sin empleados
		}
		else {
			System.out.println("ERROR EN LA CONEXION");
		}
		conexion.close();
	}
	
	public static void mainPruebasInsertar(String[] args) throws SQLException {
		Connection conexion=Conexiones.getMySQL("ejemplo", "root","1234");
		//Connection conexion=Conexiones.getOracle("EJEMPLO", "dam");
		//Connection conexion=Conexiones.getSQLite("basesdatos/sqlite/ejemplo.db");
		if(conexion!=null) {
			System.out.println("PRUEBA VER EMPLEADOS MYSQL");
			//ERROR EN EMPLEADO, DIRECTOR, DEP, APELLIDO, OFICIO Y SALARIO
			System.out.println(insertarEmpleado(conexion,7369,"","",222,-1500,100,45));
			//ERROR EN EMPLEADO Y APELLIDO
			System.out.println(insertarEmpleado(conexion,7369,"","INFORMATICO",7499,1500,100,10));
			//ERROR EN DEP Y OFICIO
			System.out.println(insertarEmpleado(conexion,123,"EMPLE123","",7499,1500,100,45));
			//DATOS OK
			System.out.println(insertarEmpleado(conexion,122,"EMPLE123","INFORMATICO",7499,1500,100,10));
			System.out.println(insertarEmpleado(conexion,124,"EMPLE124","INFORMATICO",7499,1800,100,10));
			System.out.println(insertarEmpleado(conexion,125,"EMPLE125","INFORMATICO",7499,1600,100,10));
			System.out.println(insertarEmpleado(conexion,126,"EMPLE126","INFORMATICO",7499,1800,100,10));
		}
		else {
			System.out.println("ERROR EN LA CONEXION");
		}
		conexion.close();
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
	public static String insertarEmple(Connection conexion, int emp_no, String apellido, String oficio, int director, float salario, float comision, int dept_no) { //ACTIVIDAD 2_10
		String mensaje="";
		java.util.Date utilDate=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
		System.out.println(utilDate);
		System.out.println(sqlDate);
		
		String insert="INSERT INTO EMPLEADOS VALUES ("+emp_no+",'"+apellido+"','"+oficio+"',"+director+",'"+sqlDate+"',"+salario+","+comision+","+dept_no+")";
		System.out.println(insert);
		String sql= "INSERT INTO EMPLEADOS VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			//Statement sentencia = conexion.createStatement();
			//int filas=sentencia.executeUpdate(insert);
			
			PreparedStatement sentencia =  conexion.prepareStatement(sql);
			sentencia.setInt(1, emp_no); // num empleado
			sentencia.setString(2, apellido); //Apellido
			sentencia.setString(3, oficio); //Oficio
			sentencia.setInt(4, director); //Director
			sentencia.setDate(5, sqlDate); //Fecha
			sentencia.setFloat(6, salario); //Salario
			sentencia.setFloat(7, comision); //Comision
			sentencia.setInt(8, dept_no); //Departamento

			int filas = sentencia.executeUpdate();  // filas afectadas

			mensaje="Empleado insertado: "+emp_no;
		} catch (SQLException e) {
			e.printStackTrace();
			mensaje="Error al insertar: "+e.getMessage();
		}	
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
	public static void visualizarEmpleadosDepartamento(Connection conexion, int dept_no) { //Actividad 2_11
		//Comprobar si el dep existe
		String sql="SELECT dnombre FROM DEPARTAMENTOS WHERE dept_no = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, dept_no);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) {
				String nombre=resul.getString(1);
				System.out.println("EMPLEADOS DEL DEPARTAMENTO: "+nombre);
				sql="select apellido, salario, oficio from empleados  where dept_no = ?";
				sentencia=conexion.prepareStatement(sql);
				sentencia.setInt(1, dept_no);
				resul=sentencia.executeQuery();
				if(resul.next()) {
					System.out.printf("%15s %10s %15s %n","APELLIDO","SALARIO","OFICIO");
					System.out.printf("%15s %10s %15s %n","---------------","----------","---------------");
					//Recorrer el ResultSet
					//CONTADOR SIN SELECT: int cuen=0;
					//SUMA DE MEDIA SIN SELECT: float suma=0;
					do{ //Se hace do while porque si se hace while nos saltaríamos un registro, pues ya hemos hecho un resul.next antes para comprobar si habia datos
						System.out.printf("%15s %10s %15s %n",resul.getString(1),resul.getFloat(2),resul.getString(3));
						//cuen++;
						//suma+=resul.getFloat(2);
					}while(resul.next());
					System.out.printf("%15s %10s %15s %n","---------------","----------","---------------");
					
					//Totales con select
					sql="select avg(salario), count(emp_no) from empleados  where dept_no = ?";
					sentencia=conexion.prepareStatement(sql);
					sentencia.setInt(1, dept_no);
					resul=sentencia.executeQuery();
					//Aquí ya sabemos que va a devolver datos asi que no hace falta comprobarlo
					resul.next(); //Como devuelve solo una fila nos posicionamos
					float media=resul.getFloat(1);
					int cuenta=resul.getInt(2);
					System.out.println("SALARIO MEDIO: "+media);
					System.out.println("NUM EMPLEADOS: "+cuenta);
					
					//Totales con contadores
					//System.out.println("SALARIO MEDIO: "+suma/cuen);
					//System.out.println("NUM EMPLEADOS: "+cuen);
				}
				else System.out.println("DEPARTAMENTO SIN EMPLEADOS");
			}
			else System.out.println("DEPARTAMENTO NO EXISTE: "+dept_no);
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}
}