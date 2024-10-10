package pruebaMaven;

import java.sql.*;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		try {
			Connection conexion=null;
			do {
				menu();
				int opcion=s.nextInt();
				switch(opcion) {
					case 1:
						Class.forName("com.mysql.cj.jdbc.Driver");
						conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ejemplo", "root", "1234");
						consultardepartamentos(conexion);
						conexion.close();  // Cerrar conexión
						break;
					case 2:
						Class.forName("org.apache.derby.jdbc.EmbeddedDriver");		
						conexion =  DriverManager.getConnection("jdbc:derby:basesdatos/derby/ejemplo.db");
						consultardepartamentos(conexion);
						break;
					case 3:
						Class.forName("org.hsqldb.jdbcDriver" );
						conexion = DriverManager.getConnection("jdbc:hsqldb:file:basesdatos/hsqldb/ejemplo/ejemplo");  
						consultardepartamentos(conexion);
						break;
					case 4:
						Class.forName("org.sqlite.JDBC");
						conexion = DriverManager.getConnection("jdbc:sqlite:basesdatos/sqlite/ejemplo.db"); 
						consultardepartamentos(conexion);
						break;
					case 5:
						Class.forName("com.mysql.jdbc.Driver");
						conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "ejemplo", "dam"); 
						consultardepartamentos(conexion);
						break;
					case 6:
						salir=true;
						break;
					default:
						System.out.println("Opcion incorrecta");
						break;
				}
			}while(!salir);
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	private static void menu() {
		System.out.println("");
		System.out.println("Selecciona una opcion: ");
		System.out.println("1. Prueba mysql");
		System.out.println("2. Prueba Derby");
		System.out.println("3. Prueba HSQLDB");
		System.out.println("4. Prueba SQLite");
		System.out.println("5. Prueba Oracle");
		System.out.println("6. Salir");
		System.out.println("");
	}
	private static void consultardepartamentos(Connection conexion) throws SQLException {
		  Statement sentencia = conexion.createStatement();
	      String sql = "SELECT * FROM departamentos";
		  ResultSet resul = sentencia.executeQuery(sql);


		  //Recorremos el resultado para visualizar cada fila
		  //Se hace un bucle mientras haya registros y se van mostrando
		  
	     while (resul.next()) {
		    System.out.printf("%d, %s, %s %n",resul.getInt(1), resul.getString(2), resul.getString(3));
	     }
	     resul.close();     // Cerrar ResultSet
		 sentencia.close(); // Cerrar Statement
	}
}