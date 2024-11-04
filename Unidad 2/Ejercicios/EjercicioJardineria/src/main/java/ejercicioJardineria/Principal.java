package ejercicioJardineria;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
					insertarEmpleado("Empleado1","Apellido1","Apellido2","1111","empleado1@gmail.com","TAL-ES",1,"Programador"); //Okay
					insertarEmpleado("Empleado1","Apellido1","Apellido2","1111","empleado1@gmail.com","TAL-ESSS",1,"Programador"); //Error Oficina
					insertarEmpleado("Empleado1","Apellido1","Apellido2","1111","empleado1@gmail.com","TAL-ES",100,"Programador"); //Error jefe
					break;
				case 2:
					visualizarPedidosCliente(2); //Cliente no existe
					visualizarPedidosCliente(6); //Cliente existe sin pedidos
					visualizarPedidosCliente(4); //Cliente existe con pedidos
					break;
				case 3:
					crearClientesSinPedido();
					break;
				case 4:
					clientesPorEmpleado();
					break;
				case 5:
					crearStockActualizado();
					break;
				case 6:
					ejercicioOficinas("BCN-ES"); //Existe
					ejercicioOficinas("BCNNNN"); //No existe
					ejercicioOficinas("NUEVA"); //Existe sin empleados
					break;
				case 7:
					verPedidosTodosClientes();
					break;
				case 8:
					tratarNuevosEmpleados();
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
		System.out.println("....................................");
		System.out.println("------------------------------------");
	}
	public static boolean comprobarEmpleado(int codigoEmpleado) {
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
	public static boolean comprobarOficina(String codigoOficina) {
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
		if(!comprobarEmpleado(codigoJefe)) { //Si no existe un empleado con el codigo del jefe
			insertar=false; //No insertaremos
			System.out.println("Empleado no insertado. El codigo de jefe no existe"); //Sacamos un mensaje
		}
		if(!comprobarOficina(codigoOficina)) { //Si no existe la oficina
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
	public static void visualizarPedidosCliente(int codigoCliente) {
		try {
			String sql1="select nombrecliente, lineadireccion1 from clientes where codigocliente=?"; //Obtenemos el cliente
			PreparedStatement sentencia=conexion.prepareStatement(sql1);
			sentencia.setInt(1, codigoCliente);
			ResultSet resul=sentencia.executeQuery();
			if (resul.next()) { //Si existe el cliente
				String sql2="select count(*) from pedidos where codigocliente=?";
				PreparedStatement sentencia2=conexion.prepareStatement(sql2); //Contamos sus pedidos
				sentencia2.setInt(1, codigoCliente);
				ResultSet resul2=sentencia2.executeQuery();
				resul2.next(); //Nos posicionamos, pues siempre devuelve una fila
				System.out.println("COD-CLIENTE: "+codigoCliente+"           NOMBRE: "+resul.getString(1));
				System.out.println("DIRECCIÓN1: "+resul.getString(2)+"       Número de pedidos: "+resul2.getInt(1));
				System.out.println("------------------------------------------------------------------------------------------------------ ");
				
				if(resul2.getInt(1)>0) listarPedidosCliente(codigoCliente); //Si tiene pedidos, los listamos
				
				resul2.close();
				sentencia2.close();	
			}
			else {
				System.out.println("------------------------------------------------------------------------------------------------------");
				System.out.println("Codigo de cliente no existe: "+codigoCliente);
				System.out.println("------------------------------------------------------------------------------------------------------");
			}
			resul.close();
			sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void listarPedidosCliente(int codigoCliente) {
		try {
			String sql="select codigopedido, fechapedido, estado from pedidos where codigocliente=? order by codigopedido";
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoCliente);
			ResultSet resul=sentencia.executeQuery();
			
			//Maximos
			float importeMax=0;
			int codigoPedidoMax=0, cantidadMaxima=0;
			Date fechaMax=null;
			String nombreMaximo="", codigoProductoMax="";
			
			while(resul.next()) { //Recorremos los pedidos
				int codigoPedido=resul.getInt(1);
				System.out.println("    COD-PEDIDO: "+codigoPedido+"   FECHA PEDIDO: "+resul.getDate(2)+"  ESTADO DEL PEDIDO: "+resul.getString(3));
				System.out.printf("        %-9s %-9s %-40s %-10s %-10s %-10s %n","NUM-LINEA","COD-PROD","NOMBRE PRODUCTO","CANTIDAD","PREC-UNID","IMPORTE");
				System.out.printf("        %9s %9s %40s %10s %10s %10s %n","---------","---------","----------------------------------------","----------","----------","----------");
				int sumaCantidad=0;
				float sumaPrecio=0, sumaImporte=0;
				String sql2="select numerolinea, codigoproducto,nombre,cantidad,preciounidad, preciounidad*cantidad from detallepedidos join productos using (codigoproducto) where codigopedido=? order by numerolinea";
				PreparedStatement sentencia2=conexion.prepareStatement(sql2);
				sentencia2.setInt(1, codigoPedido);
				ResultSet resul2=sentencia2.executeQuery();
				
				//Recorremos cada detalle del pedido
				while(resul2.next()) {
					System.out.printf("        %-9s %-9s %-40s %-10s %-10s %-10s %n",resul2.getInt(1),resul2.getString(2),resul2.getString(3),resul2.getInt(4),resul2.getFloat(5),resul2.getFloat(6));
					//Sumamos a los totales del pedido
					sumaCantidad+=resul2.getInt(4);
					sumaPrecio+=resul2.getFloat(5);
					sumaImporte+=resul2.getFloat(6);
					
					if(resul2.getInt(4)>cantidadMaxima) {
						cantidadMaxima=resul2.getInt(4);
						nombreMaximo=resul2.getString(3);
						codigoProductoMax=resul2.getString(2);
					}
				}
				System.out.printf("        %9s %9s %40s %10s %10s %10s %n","---------","---------","----------------------------------------","----------","----------","----------");
				System.out.printf("        %-60s %-10s %-10s %-10s %n","TOTALES POR PEDIDO",sumaCantidad,sumaPrecio,sumaImporte);
				System.out.println();
				
				if(sumaImporte>importeMax) {
					importeMax=sumaImporte;
					codigoPedidoMax=codigoPedido;
					fechaMax=resul.getDate(2);
				}
				resul2.close();
				sentencia2.close();
			}
			System.out.println("COD de PEDIDO y FECHA PEDIDO CON TOTAL IMPORTE MÁXIMO: "+codigoPedidoMax+",  "+fechaMax);
			System.out.println("COD PRODUCTO y NOMBRE PRODUCTO, del producto más comprado (producto con CANTIDAD Máxima): "+codigoProductoMax+"   "+nombreMaximo);
			resul.close();
			sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void crearClientesSinPedido() {
		//Crear tabla sin pedidos y con los clientes
		String crear="create table clientessinpedido as select * from clientes where codigocliente not in (select codigocliente from pedidos) order by codigocliente";
		//Añadir la PK a la tabla
		String alter="alter table clientessinpedido add constraint csp_pk primary key (codigocliente)";
		
		//Borrar clientes
		String borrar="delete from clientes where codigocliente not in (select codigocliente from pedidos)";
		try {
			//Crear tabla
			PreparedStatement sentencia=conexion.prepareStatement(crear);
			sentencia.executeUpdate();
			System.out.println("-----------------------------");
			System.out.println("Tabla clientessinpedido creada con los registros");
			//Añadir PK
			sentencia=conexion.prepareStatement(alter);
			sentencia.executeUpdate();
			System.out.println("Añadida la pk en clientessinpedido");
			//Borrar de clientes
			sentencia=conexion.prepareStatement(borrar);
			int registrosBorrados=sentencia.executeUpdate();
			System.out.println("Clientes sin pedido borrados de clientes: "+registrosBorrados);
			
			sentencia.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Tabla CLIENTESSINPEDIDO ya creda y clientes borrados de CLIENTES");
			
		}
		
	}
	private static void clientesPorEmpleado() {
		String crearColumna="alter table empleados add numclientes number(5)";
		String actualizar="update empleados emple set numclientes=(select count(*) from clientes where codigoempleadorepventas=emple.codigoempleado)";
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
	private static void crearStockActualizado() {
		String crearColumna="alter table productos add stockactualizado number(5)";
		
		String actualizar="update productos p set stockactualizado=cantidadenstock-(select coalesce(sum(cantidad),0) from detallepedidos where codigoproducto=p.codigoproducto)";
		
		String consulta="select codigoproducto, cantidadenstock, stockactualizado from productos where stockactualizado<5";
		
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
		try {
			//Listar productos a reponer
			sentencia=conexion.prepareStatement(consulta);
			ResultSet resul=sentencia.executeQuery();
			System.out.printf("%15s %15s %16s %n","COD PRODUCTO","CANTIDADENSTOCK","STOCKACTUALIZADO");
			System.out.printf("%15s %15s %16s %n","---------------","---------------","----------------");
			while(resul.next()) {
				System.out.printf("%15s %15s %16s %n",resul.getString(1),resul.getInt(2),resul.getInt(3));
			}
		}catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Atención error en la consulta: "+e.getMessage());
		}
	}
	private static void ejercicioOficinas(String codigoOficina) {
		String sql="{ ? = call veroficina(?,?,?,?,?)}";
		System.out.printf("%-12s %-30s %-50s %-50s %-50s %-10s %n","COD OFICINA","CIUDAD","PAIS","REGION","DIRECCION1","NUM EMPLES");
		System.out.printf("%-12s %-30s %-50s %-50s %-50s %-10s %n","------------","------------------------------","--------------------------------------------------","--------------------------------------------------","--------------------------------------------------","----------");
		try {
			CallableStatement llamada=conexion.prepareCall(sql);
			llamada.registerOutParameter(1, Types.INTEGER); //Contador
			llamada.setString(2, codigoOficina); //Oficina
			llamada.registerOutParameter(3,Types.VARCHAR);//ciudad
			llamada.registerOutParameter(4,Types.VARCHAR);//pais
			llamada.registerOutParameter(5,Types.VARCHAR);//region
			llamada.registerOutParameter(6,Types.VARCHAR);//direccion1
			//Ejecutar procedimiento
			llamada.executeUpdate();
			//Mostrar datos
			System.out.printf("%-12s %-30s %-50s %-50s %-50s %-10s %n",codigoOficina,llamada.getString(3),llamada.getString(4),llamada.getString(5),llamada.getString(6),llamada.getInt(1));
			
			llamada.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void verPedidosTodosClientes() {
		String sql="select codigocliente from clientes order by codigocliente";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			ResultSet resul=sentencia.executeQuery();
			while(resul.next()) {
				System.out.println("***");
				visualizarPedidosCliente(resul.getInt(1));
			}
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private static void tratarNuevosEmpleados() {
		String sql="select codigoempleado, nombre, apellido1, apellido2, extension, email, codigooficina, codigojefe, puesto from nuevosempleados";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			ResultSet resul=sentencia.executeQuery();
			while(resul.next()) {
				//Comprobar si existe
				String mensaje="";
				boolean error=false;
				int codigoEmpleado=resul.getInt(1);
				if(!comprobarEmpleado(codigoEmpleado)) {
					insertarEmple(resul);
				}
				if(comprobarEmpleado(codigoEmpleado)) {
					actualizarEmple(resul);
				}
			}
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void insertarEmple(ResultSet resul) {
		try {
			boolean insertar=true;
			String codigoOficina = resul.getString(7);
			int codigoJefe=resul.getInt(8);
			String mensaje="";
			if(!comprobarEmpleado(codigoJefe)) { //Si no existe un empleado con el codigo del jefe
				insertar=false; //No insertaremos
				mensaje+=("Empleado no insertado. El codigo de jefe no existe "+codigoJefe+"\n"); //Sacamos un mensaje
			}
			if(!comprobarOficina(codigoOficina)) { //Si no existe la oficina
				insertar=false; //No insertaremos
				mensaje+=("Empleado no insertado. El codigo de oficina no existe: "+codigoOficina+"\n");
			}

			if (insertar) {//Si todo es correcto, insertamos
				try {
					//Obtenemos el maximo codigo de empleado y sumamos 1
					String sql="insert into empleados (codigoempleado,nombre,apellido1,apellido2,extension,email,codigooficina,codigojefe,puesto) "
							+ "values (?,?,?,?,?,?,?,?,?)";
					PreparedStatement sentencia=conexion.prepareStatement(sql);
					sentencia=conexion.prepareStatement(sql);
					sentencia.setInt(1, resul.getInt(1));
					sentencia.setString(2,resul.getString(2));
					sentencia.setString(3, resul.getString(3));
					sentencia.setString(4, resul.getString(4));
					sentencia.setString(5, resul.getString(5));
					sentencia.setString(6, resul.getString(6));
					sentencia.setString(7, resul.getString(7));
					sentencia.setInt(8, resul.getInt(8));
					sentencia.setString(9, resul.getString(9));
					int linea=sentencia.executeUpdate();
					System.out.println("Empleado insertado: "+resul.getInt(1));
					sentencia.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else System.out.println("Error, no se inserta el empleado "+resul.getInt(1)+"\n"+mensaje);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void actualizarEmple(ResultSet resul) {
		try {
			boolean actualizar=true;
			String codigoOficina = resul.getString(7);
			int codigoJefe=resul.getInt(8);
			String mensaje="";
			if(!comprobarEmpleado(codigoJefe)) { //Si no existe un empleado con el codigo del jefe
				actualizar=false; //No insertaremos
				mensaje+=("Empleado no actualizado. El codigo de jefe no existe "+codigoJefe+"\n"); //Sacamos un mensaje
			}
			if(!comprobarOficina(codigoOficina)) { //Si no existe la oficina
				actualizar=false; //No insertaremos
				mensaje+=("Empleado no actualizado. El codigo de oficina no existe: "+codigoOficina+"\n");
			}

			if (actualizar) {
				String sql="select * from empleados where codigoempleado = ?";
				PreparedStatement sentenciaEmple=conexion.prepareStatement(sql);
				sentenciaEmple.setInt(1, resul.getInt(1));
				ResultSet resulEmple=sentenciaEmple.executeQuery();
				resulEmple.next();
				String cambios="";
				//Comprobar cambios
				if(!resul.getString(2).equals(resulEmple.getString(2))) cambios+="Se actualiza el nombre\n";
				if(!resul.getString(3).equals(resulEmple.getString(3))) cambios+="Se actualiza el apellido 1\n";
				if(!(resul.getString(4)==resulEmple.getString(4))) cambios+="Se actualiza el apellido 2\n";
				if(!resul.getString(5).equals(resulEmple.getString(5))) cambios+="Se actualiza la extension\n";
				if(!resul.getString(6).equals(resulEmple.getString(6))) cambios+="Se actualiza el email\n";
				if(!resul.getString(7).equals(resulEmple.getString(7))) cambios+="Se actualiza el codigo de oficina\n";
				if(resul.getInt(8)!=resulEmple.getInt(8)) cambios+="Se actualiza el codigo del jefe\n";
				if(!resul.getString(9).equals(resulEmple.getString(9))) cambios+="Se actualiza el puesto\n";
				
				if(cambios.equals("")) System.out.println("No se actualiza el empleado "+resul.getInt(1)+", no hay cambios");
				else {
					String sql2="update empleados set nombre=?,apellido1=?,apellido2=?,extension=?,email=?,codigooficina=?,codigojefe=?,puesto=? where codigoempleado=?";
					PreparedStatement sentenciaUpdate=conexion.prepareStatement(sql2);
					sentenciaUpdate.setInt(9, resul.getInt(1));
					sentenciaUpdate.setString(1, resul.getString(2));
					sentenciaUpdate.setString(2, resul.getString(3));
					sentenciaUpdate.setString(3, resul.getString(4));
					sentenciaUpdate.setString(4, resul.getString(5));
					sentenciaUpdate.setString(5, resul.getString(6));
					sentenciaUpdate.setString(6, resul.getString(7));
					sentenciaUpdate.setInt(7, resul.getInt(8));
					sentenciaUpdate.setString(8, resul.getString(9));
					int filas=sentenciaUpdate.executeUpdate();
					System.out.println("Empleado actualizado: "+resul.getInt(1)+", Cambios: "+cambios);
					sentenciaUpdate.close();
				}
				sentenciaEmple.close();
			}
			else {
				System.out.println("Error, no se actualiza el empleado "+resul.getInt(1)+"\n"+mensaje);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
