package monforteibiasdaniel;

import java.sql.*;
import java.util.Scanner;

public class Principal {
	static Connection conexion=Conexiones.getOracle("examen", "examen");
	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		do {
			menu();
			int opcion=s.nextInt();
			switch(opcion) {
				case 1:
					insertarEstudiante("Pedro Martinez Rodriguez", "C/Los Sauces 5. Talavera. ESP", "925666770"); //Inserción correcta la primera vez
					//insertarEstudiante("MARÍA JIMÉNEZ SULIVAN", "C/Los Sauces 5. Talavera. ESP", "925666770"); //No se inserta, nombre ya existe
					//insertarEstudiante("", "   ", " "); //No se inserta, datos vacios o solo espacios
					break;
				case 2:
					listarProyecto(3); //Existe, tiene entidades y estudiantes
					//listarProyecto(5); //Existe sin entidades ni estudiantes
					//listarProyecto(50); //No existe
					break;
				case 3:
					actualizarProyectosNuevasColumnas();
					break;
				case 4:
					salir=true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		System.out.println("FIN");
		s.close();
	}
	
	public static void menu() {
		System.out.println();
		System.out.println("OPERACIONES CON PROYECTOS. Realizado por Daniel Monforte Ibias");
		System.out.println("1. EJERCICIO 1: Insertar estudiantes");
		System.out.println("2. EJERCICIO 2: Listar proyecto");
		System.out.println("3. EJERCICIO 3: Añadir columnas y actualizar con datos nuevos");
		System.out.println("4. Salir");
		System.out.println();
	}
	
	public static boolean comprobarEstudiante(String nombre) {
		boolean existe=false;
		String sql="select * from estudiantes where nombre = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setString(1, nombre);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) existe=true; //Si hay resultado es porque hay un estudiante con ese nombre
			else existe=false; //Si no hay resultado, no hay ningun estudiante con ese nombre
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
	public static void insertarEstudiante(String nombre, String direccion, String telefono) {
		boolean insertar=true;
		if(comprobarEstudiante(nombre)) { //Si ya existe un estudiante con ese nombre
			insertar=false; //No insertaremos
			System.out.println("Ya existe un estudiante con ese nombre, no se ha podido insertar"); //Sacamos un mensaje
		}
		//Fecha de alta, que es la del dia actual
		java.util.Date utilDate=new java.util.Date();
		java.sql.Date fechaAlta=new java.sql.Date(utilDate.getTime());
		//Comprobamos campos vacios uno por uno. Usamos trim para eliminar espacios en blanco, por si solo tiene espacios
		if(nombre.trim().equals("")) { //Si el nombre esta vacio
			insertar=false; //No insertaremos
			System.out.println("El nombre no puede estar vacio, compruebe datos");
		}
		if(direccion.trim().equals("")) { //Si la direccion esta vacia
			insertar=false; //No insertaremos
			System.out.println("La direccion no puede estar vacio, compruebe datos");
		}
		if(telefono.trim().equals("")) { //Si el telefono esta vacio
			insertar=false; //No insertaremos
			System.out.println("El telefono no puede estar vacio, compruebe datos");
		}
		if (insertar) { //Si todo es correcto, insertamos
			try {
				String sql="select max(codestudiante) from estudiantes"; //Obtenemos el maximo codigo de estudiante
				PreparedStatement sentencia=conexion.prepareStatement(sql);
				ResultSet resul=sentencia.executeQuery();
				resul.next();
				int codigoEstudiante=resul.getInt(1)+1;	 //Aumentamos en 1 el codigo maximo de estudiante
				sql="insert into estudiantes (codestudiante,nombre,direccion,tlf,fechaalta) values (?,?,?,?,?)";
				sentencia=conexion.prepareStatement(sql);
				sentencia.setInt(1, codigoEstudiante);
				sentencia.setString(2,nombre);
				sentencia.setString(3, direccion);
				sentencia.setString(4, telefono);
				sentencia.setDate(5, fechaAlta);
				sentencia.executeUpdate(); //Insertamos el alumno
				sentencia.close();
				System.out.println("Estudiante insertado correctamente con el codigo "+codigoEstudiante);
			} catch (SQLException e) {
				System.out.println("Error: "+e.getMessage());
			}
		}
	}
	
	
	public static void listarProyecto(int codigoProyecto) {
		try {
			String sql1="select * from proyectos where codigoproyecto=?";
			PreparedStatement sentencia=conexion.prepareStatement(sql1);
			sentencia.setInt(1, codigoProyecto);
			ResultSet resul=sentencia.executeQuery();
			if (resul.next()) { //Si existe el proyecto
				//INFORMACION DEL PROYECTO
				System.out.println("COD-PROYECTO: "+codigoProyecto+"     NOMBRE: "+resul.getString(2));
				System.out.println("FECHA INICIO: "+resul.getDate(3)+", FECHA FIN: "+resul.getDate(4));
				System.out.println("PRESUPUESTO: "+resul.getFloat(5)+", EXTRAAPORTACION: "+resul.getFloat(6));
				System.out.println("------------------------------------------------------------------------------------------------------ ");
				//Obtenemos entidades
				String sqlEntidades="select codentidad,descripcion,importeaportacion,fechaaportacion from entidades join patrocina using(codentidad) where codigoproyecto=?";
				sentencia=conexion.prepareStatement(sqlEntidades);
				sentencia.setInt(1, codigoProyecto);
				ResultSet resulEntidades=sentencia.executeQuery();
				if(resulEntidades.next()) {//Si existen entidades
					//Cabeceras de entidades
					System.out.println("LISTA DE ENTIDADES QUE PATROCINA EL PROYECTO");
					System.out.printf("%-7s %-30s %-20s %-20s %n","Código","Descripción","Importe aportación","Fecha aportación");
					System.out.printf("%-7s %-30s %-20s %-20s %n","=======","==============================","====================","====================");
					//Totales
					double totalAportaciones=0, presupuestoTotal=0;
					do { //Recorremos el resultset. Hacemos un do while porque ya nos hemos posicionado al hacer la comprobacion
						System.out.printf("%-7s %-30s %20s %20s %n",resulEntidades.getInt(1),resulEntidades.getString(2),resulEntidades.getFloat(3),resulEntidades.getDate(4));
						totalAportaciones+=resulEntidades.getFloat(3);
					}while(resulEntidades.next());
					presupuestoTotal=resul.getFloat(5)+totalAportaciones;
					System.out.printf("%-38s %-20s %-20s %n","======================================","====================","====================");
					System.out.printf("%-38s %20s %-20s %n","TOTAL APORTACIONES",totalAportaciones,"");
					System.out.printf("%-38s %20s %-20s %n","PRESUPUESTO TOTAL",presupuestoTotal,"");
				}
				else { //Si no existen entidades
					System.out.println("\tNINGUNA ENTIDAD PATROCINA ESTE PROYECTO.");
					System.out.println("------------------------------------------------------------------------------------------------------ ");
				}
				
				//Obtenemos estudiantes
				String sqlEstudiantes="select codestudiante,nombre,direccion,codparticipacion,tipoparticipacion,numaportaciones from estudiantes join participa using (codestudiante) where codigoproyecto=?";
				sentencia=conexion.prepareStatement(sqlEstudiantes);
				sentencia.setInt(1, codigoProyecto);
				ResultSet resulEstudiantes=sentencia.executeQuery();
				if(resulEstudiantes.next()) {//Si existen estudiantes
					//Cabeceras de entidades
					System.out.println("LISTA DE ESTUDIANTES QUE PARTICIPAN EN EL PROYECTO");
					System.out.printf("%-4s %-30s %-40s %-5s %20s %6s %8s %n","Cod","Nombre","Direccion","CodPar","Tipo aportacion","NumApt","TotAport");
					System.out.printf("%-4s %-30s %-40s %-5s %20s %6s %8s %n","====","==============================","========================================","=====","=====================","======","========");
					//Totales
					int totalNumApt=0,totalTotAport=0;
					do { //Recorremos el resultset. Hacemos un do while porque ya nos hemos posicionado al hacer la comprobacion
						System.out.printf("%-4s %-30s %-40s %5s %20s %6s %8s %n",resulEstudiantes.getInt(1),resulEstudiantes.getString(2),resulEstudiantes.getString(3),resulEstudiantes.getInt(4),resulEstudiantes.getString(5),resulEstudiantes.getInt(6),resulEstudiantes.getInt(6)*resul.getFloat(6));
						totalNumApt+=resulEstudiantes.getInt(6);
						totalTotAport+=resulEstudiantes.getInt(6)*resul.getFloat(6);
					}while(resulEstudiantes.next());
					
					System.out.printf("%-4s %-30s %-40s %-5s %20s %6s %8s %n","====","==============================","========================================","=====","====================","======","========");
					System.out.printf("%-103s %6s %8s %n","TOTALES",totalNumApt,totalTotAport);
				}
				else { //Si no existen estudiantes
					System.out.println("\tNINGUNA ESTUDIANTE PERTENECE A ESTE PROYECTO.");
					System.out.println("------------------------------------------------------------------------------------------------------ ");
				}
				resulEntidades.close();
				resulEstudiantes.close();
			}
			else { //Si no existe el proyecto
				System.out.println("------------------------------------------------------------------------------------------------------");
				System.out.println("Codigo de proyecto no existe: "+codigoProyecto);
				System.out.println("------------------------------------------------------------------------------------------------------");
			}
			resul.close();
			sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void actualizarProyectosNuevasColumnas() {
		PreparedStatement sentencia;
		//CREAR COLUMNAS (un try catch para cada una por si no estuviesen todas creadas)
		try {//Crear columna numempre
			
			String crearColumnaNumEmpre="alter table proyectos add NUMEMPRE number(5)";
			sentencia=conexion.prepareStatement(crearColumnaNumEmpre);
			sentencia.executeUpdate();
			System.out.println("----------------------------------");
			System.out.println("Columna NUMEMPRE creada");
		}catch (SQLException e) {
			System.out.println("Atención error, comprueba si existe la columna NUMEMPRE: "+e.getMessage());
		}
		try {//Crear columna importeemp
			String crearColumnaImporteEmp="alter table proyectos add IMPORTEEMP float";
			sentencia=conexion.prepareStatement(crearColumnaImporteEmp);
			sentencia.executeUpdate();
			System.out.println("----------------------------------");
			System.out.println("Columna creada");
		}catch (SQLException e) {
			System.out.println("Atención error, comprueba si existe la columna IMPORTEEMP: "+e.getMessage());
		}
		try {//Crear columna numalum
			String crearColumnaNumAlum="alter table proyectos add NUMALUM number(5)";
			sentencia=conexion.prepareStatement(crearColumnaNumAlum);
			sentencia.executeUpdate();
			System.out.println("----------------------------------");
			System.out.println("Columna NUMALUM creada");
		}catch (SQLException e) {
			System.out.println("Atención error, comprueba si existe la columna NUMALUM: "+e.getMessage());
		}
		try {//Crear columna gastoalum
			String crearColumnaGastoAlum="alter table proyectos add GASTOALUM float";
			sentencia=conexion.prepareStatement(crearColumnaGastoAlum);
			sentencia.executeUpdate();
			System.out.println("----------------------------------");
			System.out.println("Columna GASTOALUM creada");
		}catch (SQLException e) {
			System.out.println("Atención error, comprueba si existe la columna GASTOALUM: "+e.getMessage());
		}
		try {//Crear columna gastorecur
			String crearColumnaGastoRecur="alter table proyectos add GASTORECUR float";
			sentencia=conexion.prepareStatement(crearColumnaGastoRecur);
			sentencia.executeUpdate();
			System.out.println("----------------------------------");
			System.out.println("Columna GASTORECUR creada");
		}catch (SQLException e) {
			System.out.println("Atención error, comprueba si existe la columna GASTORECUR: "+e.getMessage());
		}
		//ACTUALIZAR COLUMNAS
		try {
			//COLUMNA 1
			String actualizar="update proyectos p set numempre=(select count(*) from entidades where codentidad in (select codentidad from patrocina where codigoproyecto=p.codigoproyecto))";
			sentencia=conexion.prepareStatement(actualizar);
			int numeroRegistros=sentencia.executeUpdate();
			System.out.println("Columna NUMEMPRE actualizada. Registros: "+numeroRegistros);
			
			//COLUMNA 2
			actualizar="update proyectos p set importeemp=(select sum(importeaportacion) from patrocina where codigoproyecto=p.codigoproyecto)";
			sentencia=conexion.prepareStatement(actualizar);
			numeroRegistros=sentencia.executeUpdate();
			System.out.println("Columna IMPORTEEMP actualizada. Registros: "+numeroRegistros);
			
			//COLUMNA 3
			actualizar="update proyectos p set numalum=(select count(*) from estudiantes where codestudiante in (select codestudiante from participa where codigoproyecto=p.codigoproyecto))";
			sentencia=conexion.prepareStatement(actualizar);
			numeroRegistros=sentencia.executeUpdate();
			System.out.println("Columna NUMALUM actualizada. Registros: "+numeroRegistros);
			
			//COLUMNA 4
			actualizar="update proyectos p set gastoalum=(select sum(numaportaciones) from participa where codigoproyecto=p.codigoproyecto)*(select extraaportacion from proyectos where codigoproyecto=p.codigoproyecto)";
			sentencia=conexion.prepareStatement(actualizar);
			numeroRegistros=sentencia.executeUpdate();
			System.out.println("Columna GASTOALUM actualizada. Registros: "+numeroRegistros);
			
			//COLUMNA 5
			actualizar="update proyectos p set gastorecur=(select sum(sum(cantidad*pvp)) from usa join recursos using(codrecurso) where codigoproyecto=p.codigoproyecto group by codrecurso)"; //Agrupamos por codrecurso para obtener las sumas de las cantidades por el precio de cada producto, y luego sumamos esos valores en la suma total
			sentencia=conexion.prepareStatement(actualizar);
			numeroRegistros=sentencia.executeUpdate();
			System.out.println("Columna GASTORECUR actualizada. Registros: "+numeroRegistros);
		}catch (SQLException e) {
			System.out.println("Atención error en la actualización de las columnas: "+e.getMessage());
		}
		
		System.out.println();
		listarProyectos();
	}
	
	public static void listarProyectos() {
		try {
			String sql1 = "select * from proyectos";
			PreparedStatement sentencia = conexion.prepareStatement(sql1);
			ResultSet resul = sentencia.executeQuery();
			System.out.printf("%-5s %-45s %10s %10s %10s %10s %10s %10s %10s %10s %10s %n", "COD", "NOMBRE", "FECHAINI","FECHAFIN","PRESUPUEST","EXTRAAPORT","NUMEMPRE","IMPORTEEMP","NUMALUM","GASTOALUM","GASTORECUR");
			System.out.printf("%-5s %-45s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %n", "-----", "---------------------------------------------", "----------","----------","----------","----------","----------","----------","----------","----------","----------");
			while (resul.next()) { // Mientras existan proyectos
				System.out.printf("%5s %-45s %-10s %-10s %10s %10s %10s %10s %10s %10s %10s %n", resul.getInt(1),resul.getString(2),resul.getDate(3),resul.getDate(4),resul.getFloat(5),resul.getFloat(6),resul.getInt(7),resul.getFloat(8),resul.getInt(9),resul.getFloat(10),resul.getFloat(11));
			}
			System.out.printf("%-5s %-45s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %n", "-----", "---------------------------------------------", "----------","----------","----------","----------","----------","----------","----------","----------","----------");
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}