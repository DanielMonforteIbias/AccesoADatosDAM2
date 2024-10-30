package pruebaProcedimientos;

import java.sql.*;

public class UsarFuncionCreada {
	public static void main(String[] args) {
		Connection conexion = Conexiones.getOracle("EJEMPLO", "dam");
		String sql="select dept_no,dnombre,loc from departamentos order by dept_no";
		
		String fun="{? = call FACTIVIDAD12 ( ?, ? ) }";//Devuelve media FACTIVIDAD12
		try {
			Statement sentencia = conexion.createStatement();
			ResultSet resul=sentencia.executeQuery(sql);
			//CABECERAS
			System.out.printf("%7s %15s %15s %12s %20s %n","DEPT-NO","NOMBRE","LOCALIDAD","MEDIASALARIO","CONTADOREMPLES");
			System.out.printf("%7s %15s %15s %12s %20s %n","-------","---------------","---------------","------------","--------------------");
			CallableStatement llamada=null;
			float totalMedia=0;
			int totalContador=0;
			while(resul.next()) { //Para cada departamento
				llamada=conexion.prepareCall(fun);
				llamada.registerOutParameter(1, Types.FLOAT);
				llamada.setInt(2, resul.getInt(1));
				llamada.registerOutParameter(3, Types.INTEGER);
				
				llamada.executeUpdate();
				System.out.printf("%7s %15s %15s %12.2f %20s %n",resul.getInt(1),resul.getString(2),resul.getString(3),llamada.getFloat(1),llamada.getInt(3));
				totalMedia+=llamada.getFloat(1);
				totalContador+=llamada.getInt(3);
			}
			System.out.printf("%7s %15s %15s %12s %20s %n","-------","---------------","---------------","------------","--------------------");
			System.out.printf("%-39s %12.2f %20s %n","TOTALES",totalMedia,totalContador);
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
