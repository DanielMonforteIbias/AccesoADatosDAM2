package ejercicioJardineria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Principal {
	private static Connection conexion=Conexiones.getOracle("JARDINERIA","dam");
	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		do {
			menu();
			int opcion=s.nextInt();
			switch(opcion) {
				case 1:
					insertarEmpleado("Empleado1","Apellido1","Apellido2","1111","empleado1@gmail.com","TAL-ES",1,"Programador");
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				case 0:
					salir=true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("FIN");
	}
	public static void menu() {
		System.out.println("------------------------------------");
		System.out.println("....................................");
		System.out.println("1 - Insertar empleado");
		System.out.println("2 - Visualizar pedidos de un cliente");
		System.out.println("3 - Crear clientes sin pedido");
		System.out.println("4 - Actualizar clientes por empleado");
		System.out.println("5 - Crear stock actualizado");
		System.out.println("6 - Oficinas con funcion almacenada");
		System.out.println("7 - Ver los pedidos de todos los clientes");
		System.out.println("8 - Tratar nuevos empleados");
		System.out.println("0 - Salir");
	}
	public static boolean comprobarEmpleado(Connection conexion, int codigoEmpleado) {
		boolean existe=false;
		String sql="select * from empleados where codigoempleado = ?";
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
	public static boolean comprobarOficina(Connection conexion, String codigoOficina) {
		boolean existe=false;
		String sql="select * from oficinas where codigooficina = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setString(1, codigoOficina);
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
	public static void insertarEmpleado(String nombre, String apellido1, String apellido2, String extension, String email, String codigoOficina, int codigoJefe, String puesto) {
		boolean insertar=true;
		if(!comprobarEmpleado(conexion, codigoJefe)) { //Si no existe un empleado con el codigo del jefe
			insertar=false; //No insertaremos
			System.out.println("Empleado no insertado. El codigo de jefe no existe"); //Sacamos un mensaje
		}
		if(comprobarOficina(conexion,codigoOficina)) { //Si existe la oficina
			insertar=false; //No insertaremos
			System.out.println("Empleado no insertado. El codigo de oficina no existe");
		}

		if (insertar) { //Si todo es correcto, insertamos
			try {
				//Obtenemos el maximo codigo de empleado y sumamos 1
				String sql="select max(codigoempleado) from empleados";
				PreparedStatement sentencia=conexion.prepareStatement(sql);
				ResultSet resul=sentencia.executeQuery();
				resul.next();
				int codigoEmpleado=resul.getInt(1)+1;
				
				sql="insert into empleados (codigoempleado,nombre,apellido1,apellido2,extension,email,codigooficina,codigojefe,puesto) values "
						+ "(?,?,?,?,?,?,?,?,?)";
				sentencia=conexion.prepareStatement(sql);
				sentencia.setInt(1, codigoEmpleado);
				sentencia.setString(2,nombre);
				sentencia.setString(3, apellido1);
				sentencia.setString(4, apellido2);
				sentencia.setString(5, extension);
				sentencia.setString(6, email);
				sentencia.setString(7, codigoOficina);
				sentencia.setInt(8, codigoJefe);
				sentencia.setString(9, puesto);
				int linea=sentencia.executeUpdate();
				sentencia.close();
				System.out.println("Empleado insertado: "+codigoEmpleado);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
