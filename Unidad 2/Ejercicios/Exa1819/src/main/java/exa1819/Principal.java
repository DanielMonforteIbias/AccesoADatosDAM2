package exa1819;

import java.sql.*;
import java.util.Scanner;

public class Principal {
	static Connection conexion = Conexiones.getOracle("EXA1819", "dam");

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		boolean salir = false;
		do {
			menu();
			int opcion = s.nextInt();
			switch (opcion) {
				case 1:
					visualizarDepartamentosEmpleados(1);
					break;
				case 2:
					crearEmpresasSinDepar();
					break;
				case 3:
					crearNumEmpleEnOficios();
					break;
				case 4:
					insertarEmpleado("EmpleadoNuevo", "DireccionNueva", "PoblacionNueva", 13,11);
					break;
				case 5:
					datosOficios();
					break;
				case 6:
					actualizarDepartamento(42, "nuevo", "nuevooo", "Nuevooo", 101, 1);
				case 0:
					salir = true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
				}
		} while (!salir);
	}

	private static void menu() {
		System.out.println("1 - Visualizar departamentos y empleados de una empresa");
		System.out.println("2 - Crear empresassindepar");
		System.out.println("3 - Almacenar empleados por oficio");
		System.out.println("4 - Insertar empleado");
		System.out.println("5 - MostrarDatosOficios");
		System.out.println("6 - Actualizar datos departamento");
		System.out.println("7 - Actualizar empresas");
		System.out.println("0 - Salir");
	}

	private static void visualizarDepartamentosEmpleados(int codigoEmpresa) {
		try {
			String sql1 = "select codempre,nombre,direccion from empresas where codempre=?";
			PreparedStatement sentencia = conexion.prepareStatement(sql1);
			sentencia.setInt(1, codigoEmpresa);
			ResultSet resul = sentencia.executeQuery();
			if (resul.next()) { // Si existe la empresa
				String sql2 = "select count(*) from departamentos where codempre=?";
				PreparedStatement sentencia2 = conexion.prepareStatement(sql2); // Contamos sus pedidos
				sentencia2.setInt(1, codigoEmpresa);
				ResultSet resul2 = sentencia2.executeQuery();
				resul2.next(); // Nos posicionamos, pues siempre devuelve una fila
				System.out.println("COD-EMPRESA: " + resul.getInt(1) + "    NOMBRE: " + resul.getString(2));
				System.out.println(
						"DIRECCION: " + resul.getString(3) + "     NUMERO DE DEPARTAMENTOS: " + resul2.getInt(1));
				if (resul2.getInt(1) > 0)
					listarDepartamentosEmpresa(codigoEmpresa);
			} else
				System.out.println("Codigo de empresa no existe: " + codigoEmpresa);
			sentencia.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void listarDepartamentosEmpresa(int codigoEmpresa) {
		try {
			String sql = "select coddepart, nombre, localidad, codjefedepartamento from departamentos where codempre=? order by coddepart";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoEmpresa);
			ResultSet resul = sentencia.executeQuery();

			while (resul.next()) { // Recorremos los departamentos
				int codigoDepartamento = resul.getInt(1);
				System.out.println("    COD-DEPARTAMENTO: " + codigoDepartamento + "   NOMBRE: " + resul.getString(2)
						+ "  LOCALIDAD: " + resul.getString(3));
				System.out.printf("        %-12s %-35s %-30s %-35s %n", "COD-EMPLEADO", "NOMBRE", "OFICIO",
						"NOMBRE ENCARGADO");
				System.out.printf("        %12s %35s %30s %35s %n", "------------",
						"-----------------------------------", "------------------------------",
						"-----------------------------------");
				String sql2 = "select codemple,nombre,codoficio,codencargado from empleados where coddepart=?";
				String sqlOficio = "select nombre from oficios where codoficio=?";
				String sqlEncargado = "select nombre from empleados where codemple=?";
				String sqlJefeDepartamento = "select nombre from empleados where codemple=?";
				int contadorEmples = 0;
				PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
				sentencia2.setInt(1, codigoDepartamento);
				ResultSet resul2 = sentencia2.executeQuery();
				sentencia2 = conexion.prepareStatement(sqlJefeDepartamento);
				sentencia2.setInt(1, resul.getInt(4));
				ResultSet resulJefe = sentencia2.executeQuery();
				resulJefe.next();
				while (resul2.next()) {
					sentencia2 = conexion.prepareStatement(sqlOficio);
					sentencia2.setInt(1, resul2.getInt(3));
					ResultSet resulOficio = sentencia2.executeQuery();
					String oficio = "";
					if (resulOficio.next())
						oficio = resulOficio.getString(1);
					sentencia2 = conexion.prepareStatement(sqlEncargado);
					sentencia2.setInt(1, resul2.getInt(4));
					ResultSet resulEncargado = sentencia2.executeQuery();
					String encargado = "";
					if (resulEncargado.next())
						encargado = resulEncargado.getString(1);
					System.out.printf("        %-12s %-35s %-30s %-35s %n", resul2.getInt(1), resul2.getString(2),
							oficio, encargado);
					contadorEmples++;
					resulOficio.close();
					resulEncargado.close();
				}
				System.out.printf("        %12s %35s %30s %35s %n", "------------",
						"-----------------------------------", "------------------------------",
						"-----------------------------------");
				System.out.println("        Numero de empleados por departamento: " + contadorEmples);
				System.out.println("        Nombre del jefe de departamento: " + resulJefe.getString(1));
				System.out.println();

				resul2.close();
				sentencia2.close();
			}
			resul.close();
			sentencia.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void crearEmpresasSinDepar() {
		// Crear tabla empresassindepar
		String crear = "create table empresassindepar as select * from empresas where codempre not in (select codempre from departamentos) order by codempre";
		// Añadir la PK a la tabla
		String alter = "alter table empresassindepar add constraint emp_pk primary key (codempre)";

		// Borrar empresas
		String borrar = "delete from empresas where codempre not in (select codempre from departamentos)";
		try {
			// Crear tabla
			PreparedStatement sentencia = conexion.prepareStatement(crear);
			sentencia.executeUpdate();
			System.out.println("-----------------------------");
			System.out.println("Tabla empresassindepar creada con los registros");
			// Añadir PK
			sentencia = conexion.prepareStatement(alter);
			sentencia.executeUpdate();
			System.out.println("Añadida la pk en empresassindepar");
			// Borrar de clientes
			sentencia = conexion.prepareStatement(borrar);
			int registrosBorrados = sentencia.executeUpdate();
			System.out.println("Empresas sin departamentos borradas de empresas: " + registrosBorrados);

			sentencia.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Tabla empresassindepar ya creda y empresas borradas de EMPRESAS");

		}
	}
	
	private static void crearNumEmpleEnOficios() {
		String crearColumna="alter table oficios add numemple number(5)";
		String actualizar="update oficios o set numemple=(select count(*) from empleados where codoficio=o.codoficio)";
		
		PreparedStatement sentencia;
		try {
			//Crear columna
			sentencia=conexion.prepareStatement(crearColumna);
			sentencia.executeUpdate();
			System.out.println("----------------------------------");
			System.out.println("Columna creada");
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Atención error, comprueba si existe la columna: "+e.getMessage());
		}
		try {
			//Actualizar columna
			sentencia=conexion.prepareStatement(actualizar);
			int numeroRegistros=sentencia.executeUpdate();
			System.out.println("Columna actualizada. Registros: "+numeroRegistros);
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Atención error en la actualización: "+e.getMessage());
		}
	}
	
	private static boolean comprobarOficio(int codigoOficio) {
		boolean existe=false;
		String sql="select * from oficios where codoficio = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoOficio);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) existe=true;
			else existe=false;
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
	public static boolean comprobarDepartamento(int codigoDepartamento) {
		boolean existe=false;
		String sql="select * from departamentos where coddepart = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoDepartamento);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) existe=true;
			else existe=false;
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
	public static boolean comprobarEmpleado(int codigoEmpleado) {
		boolean existe=false;
		String sql="select * from empleados where codemple = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoEmpleado);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) existe=true;
			else existe=false;
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
	public static boolean comprobarEmpresa(int codigoEmpresa) {
		boolean existe=false;
		String sql="select * from empresas where codempre = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoEmpresa);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) existe=true;
			else existe=false;
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
	
	private static void insertarEmpleado(String nombre, String direccion, String poblacion, int codigoOficio, int codigoDepartamento) {
		boolean insertar=true;
		if(!comprobarOficio(codigoOficio)) { //Si no existe un oficio con ese codigo
			insertar=false; //No insertaremos
			System.out.println("Empleado no insertado. El codigo de oficio no existe"); //Sacamos un mensaje
		}
		if(!comprobarDepartamento(codigoDepartamento)) { //Si no existe el departamento
			insertar=false; //No insertaremos
			System.out.println("Empleado no insertado. El codigo de departamento no existe");
		}

		if (insertar) { //Si todo es correcto, insertamos
			try {
				//Obtenemos el maximo codigo de empleado y sumamos 1
				String sql="select max(codemple) from empleados";
				PreparedStatement sentencia=conexion.prepareStatement(sql);
				ResultSet resul=sentencia.executeQuery();
				resul.next();
				int codigoEmpleado=resul.getInt(1)+1;
				java.util.Date utilDate=new java.util.Date();
				java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
				sql="insert into empleados (codemple,nombre,direccion,poblacion,fechaalta,codencargado,coddepart,codoficio) values "
						+ "(?,?,?,?,?,?,?,?)";
				String sql2="select codjefedepartamento from departamentos where coddepart=?";
				PreparedStatement sentencia2=conexion.prepareStatement(sql2);
				sentencia2.setInt(1, codigoDepartamento);
				ResultSet resul2=sentencia2.executeQuery();
				resul2.next();
				sentencia=conexion.prepareStatement(sql);
				sentencia.setInt(1, codigoEmpleado);
				sentencia.setString(2,nombre);
				sentencia.setString(3, direccion);
				sentencia.setString(4, poblacion);
				sentencia.setDate(5, sqlDate);
				sentencia.setInt(6,resul2.getInt(1));
				sentencia.setInt(7, codigoDepartamento);
				sentencia.setInt(8, codigoOficio);
				int linea=sentencia.executeUpdate();
				sentencia.close();
				System.out.println("Empleado insertado: "+codigoEmpleado);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private static void datosOficios() {
		try {
			String sql1 = "select codoficio, nombre, salariomes, preciotrienio from oficios";
			PreparedStatement sentencia = conexion.prepareStatement(sql1);
			ResultSet resul = sentencia.executeQuery();
			System.out.println("Datos de oficios");
			System.out.printf("%-6s %-30s %-11s %-16s %-14s %-13s %n", "Codigo", "Nombre", "Salario mes","Numero empleados","Precio trienio","Total salario");
			System.out.printf("%-6s %-30s %-11s %-16s %-14s %-13s %n", "------", "------------------------------", "-----------","----------","----------","----------");
			String sql2="";
			ResultSet resulNumEmpleados = null;
			while (resul.next()) { // Mientras existan oficios
				sql2="select count(*) from empleados where codoficio=?";
				sentencia=conexion.prepareStatement(sql2);
				sentencia.setInt(1, resul.getInt(1));
				resulNumEmpleados=sentencia.executeQuery();
				resulNumEmpleados.next();
				int numEmpleados=resulNumEmpleados.getInt(1);
				System.out.printf("%-6s %-30s %-11s %-16s %-14s %-13s %n", resul.getInt(1),resul.getString(2),resul.getFloat(3),numEmpleados,resul.getFloat(4),numEmpleados*resul.getFloat(3));
			}
			resulNumEmpleados.close();
			resul.close();
			sentencia.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void actualizarDepartamento(int codigo, String nombre, String direccion, String localidad, int codigoJefe, int codigoEmpresa) {
		try {
			String mensaje="";
			boolean actualizar=true;
			if(!comprobarDepartamento(codigo)) { 
				actualizar=false; //No insertaremos
				mensaje+=("Departamento no actualizado. El codigo no existe "+codigo+"\n"); //Sacamos un mensaje
			}
			if(!comprobarEmpleado(codigoJefe)) { //Si no existe la oficina
				actualizar=false; //No insertaremos
				mensaje+=("Departamento no actualizado. El codigo de jefe no existe: "+codigoJefe+"\n");
			}
			if(!comprobarEmpresa(codigoEmpresa)) { //Si no existe la oficina
				actualizar=false; //No insertaremos
				mensaje+=("Departamento no actualizado. El codigo de empresa no existe: "+codigoEmpresa+"\n");
			}

			if (actualizar) {
				String sql="select * from departamentos where coddepart = ?";
				PreparedStatement sentenciaDepart=conexion.prepareStatement(sql);
				sentenciaDepart.setInt(1, codigo);
				ResultSet resul=sentenciaDepart.executeQuery();
				resul.next();
				String cambios="";
				//Comprobar cambios
				if(!nombre.equals(resul.getString(2))) cambios+="Se actualiza el nombre\n";
				if(!direccion.equals(resul.getString(3))) cambios+="Se actualiza la direccion\n";
				if(!localidad.equals(resul.getString(4))) cambios+="Se actualiza la localidad\n";
				if(codigoJefe!=resul.getInt(5)) cambios+="Se actualiza el codigo del jefe\n";
				if(codigoEmpresa!=resul.getInt(6)) cambios+="Se actualiza el codigo de la empresa\n";
				
				if(cambios.equals("")) System.out.println("No se actualiza el empleado "+resul.getInt(1)+", no hay cambios");
				else {
					String sql2="update departamentos set nombre=?,direccion=?,localidad=?,codjefedepartamento=?, codempre=? where coddepart=?";
					PreparedStatement sentenciaUpdate=conexion.prepareStatement(sql2);
					sentenciaUpdate.setInt(6, codigo);
					sentenciaUpdate.setString(1, nombre);
					sentenciaUpdate.setString(2, direccion);
					sentenciaUpdate.setString(3, localidad);
					sentenciaUpdate.setInt(4, codigoJefe);
					sentenciaUpdate.setInt(5, codigoEmpresa);
					int filas=sentenciaUpdate.executeUpdate();
					System.out.println("Departamento actualizado: "+codigo+", Cambios: "+cambios);
					sentenciaUpdate.close();
				}
				sentenciaDepart.close();
			}
			else {
				System.out.println("Error, no se actualiza el departamento "+codigo+"\n"+mensaje);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
