package pruebaProcedimientos;

import java.sql.*;

public class CrearFuncionOracle {
	public static void main(String[] args) {
		Connection conexion = Conexiones.getOracle("EJEMPLO", "dam");
		Statement sentencia;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("CREATE OR REPLACE FUNCTION FACTIVIDAD12 (d NUMBER, num out number) \n");
			sql.append("RETURN number AS \n");
			sql.append("media number;\n");
			sql.append(" C NUMBER; \n");
			sql.append("BEGIN \n");
			sql.append(" --existe el dep\n");
			sql.append(" SELECT COUNT(*) INTO C FROM DEPARTAMENTOS WHERE DEPT_NO=d;\n");
			sql.append(" IF C = 0 THEN \n");
			sql.append(" media :=-1;\n");
			sql.append(" num:=0;\n");
			sql.append(" ELSE \n");
			sql.append(" SELECT nvl(AVG(SALARIO),0), count(emp_no)\n");
			sql.append("   INTO media, num\n");
			sql.append("    FROM empleados WHERE dept_no=d;\n");
			sql.append(" END IF;\n");
			sql.append(" RETURN media;\n");
			sql.append("END;");

			System.out.println(sql);
			sentencia = conexion.createStatement();
			int filas = sentencia.executeUpdate(sql.toString());
			System.out.printf("Resultado  de la ejecución: %d %n", filas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
