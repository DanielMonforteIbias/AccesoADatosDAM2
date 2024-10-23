package operacionesEmple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperacionesEmple {
	public static boolean comprobarEmple(Connection conexion, int emp_no) {
		boolean existe=false;
		String sql="select * from empleados where emp_no = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, emp_no);
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
	public static boolean comprobarDirector(Connection conexion, int emp_no) {
		boolean esDirector=false;
		String sql="select * from empleados where dir = ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setInt(1, emp_no);
			ResultSet resul=sentencia.executeQuery();
			if(resul.next()) esDirector=true;
			else esDirector=false;
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return esDirector;
	}
	public static String borrarEmple(Connection conexion, int emp_no) {
		String mensaje="";
		if(comprobarEmple(conexion,emp_no)) { //Si el empleado existe
			if (comprobarDirector(conexion,emp_no)) mensaje="EMPLEADO NO BORRADO, ES DIRECTOR DE OTRO: "+emp_no; //Si es director, no borramos
			else { //Si no es director, borramos
				try {
					String sql="delete from empleados where emp_no=?";
					PreparedStatement sentencia=conexion.prepareStatement(sql);
					sentencia.setInt(1, emp_no);
					int linea=sentencia.executeUpdate();
					mensaje="EMPLEADO BORRADO: "+emp_no;
					sentencia.close();
				} catch (SQLException e) {
					mensaje=e.getMessage();
				}
			}
		}
		else mensaje="EMPLEADO NO BORRADO, NO EXISTE: "+emp_no;
		
		return mensaje;
	}
	public static String modificaremple(Connection conexion, int emp_no, String ape, String ofi, float sal, float comi, java.sql.Date fecha, int dept_no, int dir) {
		String mensaje="";
		if (comprobarEmple(conexion, emp_no)) { //Si existe, se puede modificar
			try {
				String sql="update empleados set apellido=?, oficio=?, dir=?, fecha_alt=? salario=?, comision=?, dept_no=? where emp_no=?";
				PreparedStatement sentencia=conexion.prepareStatement(sql);
				sentencia.setString(1, ape);
				sentencia.setString(2,ofi);
				sentencia.setInt(3, dir);
				sentencia.setDate(4, fecha);
				sentencia.setFloat(5, sal);
				sentencia.setFloat(6, comi);
				sentencia.setInt(7, dept_no);
				sentencia.setInt(8, emp_no);
				int linea=sentencia.executeUpdate();
				mensaje="EMPLEADO ACTUALIZADO: "+emp_no;
				sentencia.close();
			} catch (SQLException e) {
				mensaje=e.getMessage();
			}
		}
		else mensaje="EMPLEADO NO EXISTE, NO SE PUEDE MODIFICAR: "+emp_no;
		
		return mensaje;
	}

}
