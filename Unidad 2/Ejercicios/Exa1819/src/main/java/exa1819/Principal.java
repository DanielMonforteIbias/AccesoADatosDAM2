package exa1819;

import java.sql.*;
public class Principal {
	static Connection conexion=Conexiones.getOracle("EXA1819", "dam");
	public static void main(String[] args) {
		visualizarDepartamentosEmpleados(1);
	}
	private static void visualizarDepartamentosEmpleados(int codigoEmpresa) {
		try {
			String sql1="select codempre,nombre,direccion from empresas where codempre=?";
			PreparedStatement sentencia=conexion.prepareStatement(sql1);
			sentencia.setInt(1, codigoEmpresa);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) { //Si existe la empresa
				String sql2="select count(*) from departamentos where codempre=?";
				PreparedStatement sentencia2=conexion.prepareStatement(sql2); //Contamos sus pedidos
				sentencia2.setInt(1, codigoEmpresa);
				ResultSet resul2=sentencia2.executeQuery();
				resul2.next(); //Nos posicionamos, pues siempre devuelve una fila
				System.out.println("COD-EMPRESA: "+resul.getInt(1)+"    NOMBRE: "+resul.getString(2));
				System.out.println("DIRECCION: "+resul.getString(3)+"     NUMERO DE DEPARTAMENTOS: "+resul2.getInt(1));
				if(resul2.getInt(1)>0) listarDepartamentosEmpresa(codigoEmpresa);
			}
			else System.out.println("Codigo de empresa no existe: "+codigoEmpresa);
			sentencia.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void listarDepartamentosEmpresa(int codigoEmpresa) {
		try {
			String sql="select coddepart, nombre, localidad, codjefedepartamento from departamentos where codempre=? order by coddepart";
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, codigoEmpresa);
			ResultSet resul=sentencia.executeQuery();
			
			
			while(resul.next()) { //Recorremos los departamentos
				int codigoDepartamento=resul.getInt(1);
				System.out.println("    COD-DEPARTAMENTO: "+codigoDepartamento+"   NOMBRE: "+resul.getString(2)+"  LOCALIDAD: "+resul.getString(3));
				System.out.printf("        %-12s %-35s %-30s %-35s %n","COD-EMPLEADO","NOMBRE","OFICIO","NOMBRE ENCARGADO");
				System.out.printf("        %12s %35s %30s %35s %n","------------","-----------------------------------","------------------------------","-----------------------------------");
				String sql2="select codemple,nombre,codoficio,codencargado from empleados where coddepart=?";
				String sqlOficio="select nombre from oficios where codoficio=?";
				String sqlEncargado="select nombre from empleados where codemple=?";
				String sqlJefeDepartamento="select nombre from empleados where codemple=?";
				int contadorEmples=0;
				PreparedStatement sentencia2=conexion.prepareStatement(sql2);
				sentencia2.setInt(1, codigoDepartamento);
				ResultSet resul2=sentencia2.executeQuery();
				sentencia2=conexion.prepareStatement(sqlJefeDepartamento);
				sentencia2.setInt(1, resul.getInt(4));
				ResultSet resulJefe=sentencia2.executeQuery();
				resulJefe.next();
				while(resul2.next()) {
					sentencia2=conexion.prepareStatement(sqlOficio);
					sentencia2.setInt(1, resul2.getInt(3));
					ResultSet resulOficio=sentencia2.executeQuery();
					String oficio="";
					if(resulOficio.next())oficio=resulOficio.getString(1);
					sentencia2=conexion.prepareStatement(sqlEncargado);
					sentencia2.setInt(1, resul2.getInt(4));
					ResultSet resulEncargado=sentencia2.executeQuery();
					String encargado="";
					if(resulEncargado.next())encargado=resulEncargado.getString(1);
					System.out.printf("        %-12s %-35s %-30s %-35s %n",resul2.getInt(1),resul2.getString(2),oficio,encargado);
					contadorEmples++;
					resulOficio.close();
					resulEncargado.close();
				}
				System.out.printf("        %12s %35s %30s %35s %n","------------","-----------------------------------","------------------------------","-----------------------------------");
				System.out.println("        Numero de empleados por departamento: "+contadorEmples);
				System.out.println("        Nombre del jefe de departamento: "+resulJefe.getString(1));
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
}
