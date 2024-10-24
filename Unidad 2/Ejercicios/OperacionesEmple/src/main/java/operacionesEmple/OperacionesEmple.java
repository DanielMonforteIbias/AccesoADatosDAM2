package operacionesEmple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperacionesEmple {
	public boolean comprobarEmple(Connection conexion, int emp_no) {
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
	public boolean comprobarDirector(Connection conexion, int emp_no) {
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
	public String borrarEmple(Connection conexion, int emp_no) {
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
	public String modificaremple(Connection conexion, int emp_no, String ape, String ofi, float sal, float comi, java.sql.Date fecha, int dept_no, int dir) {
		String mensaje="";
		if (comprobarEmple(conexion, emp_no)) { //Si existe, se puede modificar
			try {
				String sql="update empleados set apellido=?, oficio=?, dir=?, fecha_alt=?, salario=?, comision=?, dept_no=? where emp_no=?";
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
	public String insertarEmple(Connection conexion, int emp_no, String ape, String ofi, float sal, float comi, java.sql.Date fecha, int dept_no, int dir) {
		String mensaje="";
		if (!comprobarEmple(conexion, emp_no)) { //Si no existe, se puede insertar
			try {
				String sql="insert into empleados (emp_no,apellido,oficio,dir,fecha_alt,salario,comision,dept_no) values "
						+ "(?,?,?,?,?,?,?,?)";
				PreparedStatement sentencia=conexion.prepareStatement(sql);
				sentencia.setInt(1, emp_no);
				sentencia.setString(2,ape);
				sentencia.setString(3, ofi);
				sentencia.setInt(4, dir);
				sentencia.setDate(5, fecha);
				sentencia.setFloat(6, sal);
				sentencia.setFloat(7, comi);
				sentencia.setInt(8, dept_no);
				int linea=sentencia.executeUpdate();
				mensaje="EMPLEADO INSERTADO: "+emp_no;
				sentencia.close();
			} catch (SQLException e) {
				mensaje=e.getMessage();
			}
		}
		else mensaje="EMPLEADO YA EXISTE, NO SE PUEDE INSERTAR: "+emp_no;
		
		return mensaje;
	}
	public void verEmpleados(Connection conexion) {
		try {
			String sql="select emp_no, apellido, oficio,dir,fecha_alt,salario,comision,dept_no from empleados";
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			ResultSet resul=sentencia.executeQuery();
			System.out.printf("%10s %15s %15s %10s %10s %10s %10s %10s %n","EMP_NO","APELLIDO","OFICIO","DIRECTOR","FECHA ALTA","SALARIO","COMISION","DEPARTAMENTO");
			System.out.printf("%10s %15s %15s %10s %10s %10s %10s %10s %n","----------","---------------","---------------","----------","----------","----------","----------","----------");
			while(resul.next()) {
				System.out.printf("%10s %15s %15s %10s %10s %10s %10s %10s %n",resul.getInt(1),resul.getString(2),resul.getString(3),resul.getInt(4),resul.getDate(5),resul.getFloat(6),resul.getFloat(7),resul.getInt(8  ));
			}
			System.out.printf("%10s %15s %15s %10s %10s %10s %10s %10s %n %n","----------","---------------","---------------","----------","----------","----------","----------","----------");
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void verUnEmpleado(Connection conexion, int emp_no) {
		if(comprobarEmple(conexion, emp_no)) {
			try {
				String sql="select emp_no, apellido, oficio,dir,fecha_alt,salario,comision,dept_no from empleados where emp_no=?";
				PreparedStatement sentencia=conexion.prepareStatement(sql);
				sentencia.setInt(1, emp_no);
				ResultSet resul=sentencia.executeQuery();
				resul.next();
				System.out.println("DATOS DEL EMPLEADO: "+emp_no);
				System.out.println("Apellido: "+resul.getString(2));
				System.out.println("Oficio: "+resul.getString(3));
				System.out.println("Director: "+resul.getInt(4));
				System.out.println("Fecha alta: "+resul.getDate(5));
				System.out.println("Salario: "+resul.getFloat(6));
				System.out.println("Comision: "+resul.getFloat(7));
				System.out.println("Departamento: "+resul.getInt(8));
				resul.close();
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("Empleado "+emp_no+" no existe");
	}
}
