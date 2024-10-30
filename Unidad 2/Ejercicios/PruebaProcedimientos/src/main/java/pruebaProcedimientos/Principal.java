package pruebaProcedimientos;

import java.sql.*;

public class Principal {

	public static void main(String[] args) {
		try {
			//Connection conexion = Conexiones.getOracle("EJEMPLO", "dam");
			Connection conexion=Conexiones.getMySQL("ejemplo", "root", "1234");
			// recuperar parametro de main
			String dep = args[0]; // departamento

			String sql = "{ call datos_dep (?, ?, ?) } "; 

			// Preparar la llamada
			CallableStatement llamada = conexion.prepareCall(sql);

			llamada.setInt(1, Integer.parseInt(dep)); // param de entrada
			llamada.registerOutParameter(2, Types.VARCHAR);//nombre dep
			llamada.registerOutParameter(3, Types.VARCHAR);// localidad

			// Ejecutar el procedimiento
			llamada.executeUpdate();
			System.out.printf("Nombre Dep: %s, Localidad: %s %n", llamada.getString(2), llamada.getString(3));
			llamada.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void mainNombreDepOracle(String[] args) {
		try {
			Connection conexion = Conexiones.getOracle("EJEMPLO", "dam");
			// Connection conexion=Conexiones.getMySQL("ejemplo", "root", "1234");
			// recuperar parametro de main
			String dep = args[0]; // departamento

			// Construir orden de llamada
			String sql = "{ ? = call nombre_dep (?, ?) } "; // ORACLE

			// Preparar la llamada
			CallableStatement llamada = conexion.prepareCall(sql);

			// registrar parámetro de resultado
			llamada.registerOutParameter(1, Types.VARCHAR);// valor devuelto

			llamada.setInt(2, Integer.parseInt(dep)); // param de entrada

			// Registrar parámetro de salida
			llamada.registerOutParameter(3, Types.VARCHAR);// parámetro OUT

			// Ejecutar el procedimiento
			llamada.executeUpdate();
			System.out.printf("Nombre Dep: %s, Localidad: %s %n", llamada.getString(1), llamada.getString(3));
			llamada.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void mainSubidaSal(String[] args) {
		Connection conexion = Conexiones.getOracle("EJEMPLO", "dam");
		// Connection conexion=Conexiones.getMySQL("ejemplo", "root", "1234");
		subidaSal(args, conexion);

	}// fin de main

	public static void subidaSal(String args[], Connection conexion) {
		try {
			// recuperar parámetros de main
			String dep = args[0]; // departamento
			String subida = args[1]; // subida

			// construir orden de llamada
			String sql = "{ call subida_sal (?, ?) } ";

			// Preparar la llamada
			CallableStatement llamada = conexion.prepareCall(sql);

			// Dar valor a los argumentos
			llamada.setInt(1, Integer.parseInt(dep)); // primero
			llamada.setFloat(2, Float.parseFloat(subida)); // segundo
			// Ejecutar el procedimiento
			llamada.executeUpdate();
			System.out.println("Subida realizada....");

			llamada.close();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
