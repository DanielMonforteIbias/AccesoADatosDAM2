package exa1920;

import java.sql.*;
public class Principal {
	static Connection conexion=Conexiones.getOracle("EXA1920", "dam");
	public static void main(String[] args) throws SQLException {
		crearEstadisticaCiudades();
		conexion.close();
	}
	private static void crearEstadisticaCiudades() {
		String sqlCrearTabla="Create table estadisticaciudades ( ciudad varchar(20) primary key, nombrepais varchar(25), numviajesdestino number(5), numviajesprocedencia number(5))";
		String sqlRellenarTabla="insert into estadisticaciudades select ciudad, nombre, 0, 0 from ciudades join paises using(codpais) order by ciudad";
		String sqlActualizarContadores="update estadisticaciudades e set numviajesdestino=(select count(*) from viajes where ciudaddestino=e.ciudad), numviajesprocedencia=(select count(*) from viajes where ciudadorigen=e.ciudad)";
	
		try {
			//Crear tabla
			PreparedStatement sentencia=conexion.prepareStatement(sqlCrearTabla);
			sentencia.executeUpdate();
			System.out.println("Tabla creada");
			//Rellenar tabla con ciudades
			sentencia=conexion.prepareStatement(sqlRellenarTabla);
			int lineas=sentencia.executeUpdate();
			System.out.println(lineas+" registros insertados");
			
			//Actualizar contadores
			sentencia=conexion.prepareStatement(sqlActualizarContadores);
			lineas=sentencia.executeUpdate();
			System.out.println("Actualizadas "+lineas+" tablas");
			}
		catch (SQLException e) {
			System.out.println("Error. Comprueba si la tabla ya existe");
		}
	}
	private static void crearEstadisticaCiudades2() {
		String sqlCrearTabla="Create table estadisticaciudades ( ciudad varchar(20) primary key, nombrepais varchar(25), numviajedestino number(5), numviajesprocedencia number(5))";
		String sql2="select ciudad, nombre, 0, 0 from ciudades join paises using(codpais) order by ciudad";
	
		try {
			//Crear tabla
			PreparedStatement sentencia=conexion.prepareStatement(sqlCrearTabla);
			sentencia.executeUpdate();
			System.out.println("Tabla creada");
			//Recorrer ciudades e insertar
			sentencia=conexion.prepareStatement(sql2);
			ResultSet res=sentencia.executeQuery();
			String destino="select count(*) from viajes where ciudaddestino = ?";
			String procedencia="select count(*) from viajes where ciudadorigen = ?";
			String insert="insert into estadisticaciudades values (?,?,?,?)";
			while(res.next()) {
				PreparedStatement sent2=conexion.prepareStatement(destino);
				sent2.setString(1,res.getString(1));
				ResultSet resDestino=sent2.executeQuery();
				resDestino.next();
				int contadorDestino=resDestino.getInt(1);
				sent2=conexion.prepareStatement(procedencia);
				sent2.setString(1, res.getString(1));
				ResultSet resProcedencia=sent2.executeQuery();
				resProcedencia.next();
				int contadorProcedencia=resProcedencia.getInt(1);
				
				sent2=conexion.prepareStatement(insert);
				sent2.setString(1, res.getString(1));
				sent2.setString(2, res.getString(2));
				sent2.setInt(3, contadorDestino);
				sent2.setInt(4, contadorProcedencia);
				
				sent2.executeUpdate();
				System.out.println("Insertada ciudad: "+res.getString(1));
			}
			}
		catch (SQLException e) {
			System.out.println("Error. Comprueba si la tabla ya existe");
		}
	}
}
