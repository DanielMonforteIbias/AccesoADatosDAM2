package dep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlDbEmpleadoImpl implements EmpleadoDAO{
	Connection conexion;

    public SqlDbEmpleadoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }
	@Override
	public boolean InsertarEmp(Empleado emp) {
		boolean valor = false;
        String sql = "INSERT INTO empleados VALUES(?, ?, ?,?,?,?,?,?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, emp.getEmp_no());
            sentencia.setString(2, emp.getApellido());
            sentencia.setString(3, emp.getOficio());
            sentencia.setInt(4,emp.getDirector());
            sentencia.setDate(5, emp.getFechaAlta());
            sentencia.setFloat(6, emp.getSalario());
            sentencia.setFloat(7, emp.getComision());
            sentencia.setInt(8,emp.getDept_no());
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d insertado%n",
                                 emp.getEmp_no());
            }
            sentencia.close();

        } catch (SQLException e) { MensajeExcepcion(e); }

        return valor;
	}

	@Override
	public boolean EliminarEmp(int empno) {
		boolean valor = false;
        String sql = "DELETE FROM empleados WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, empno);
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
              valor = true;
              System.out.printf("Empleado %d eliminado%n",empno);
            }
            sentencia.close();
        } catch (SQLException e) { MensajeExcepcion(e); }

        return valor;
	}

	@Override
	public boolean ModificarEmp(int empno, Empleado emp) {
		boolean valor = false;
        String sql = "UPDATE empleados SET apellido= ?, oficio = ?,dir=?,fecha_alt=?,salario=?,comision=?,dept_no=? WHERE emp_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, emp.getApellido());
            sentencia.setString(2, emp.getOficio());
            sentencia.setInt(3, emp.getDirector());
            sentencia.setDate(4, emp.getFechaAlta());
            sentencia.setFloat(5, emp.getSalario());
            sentencia.setFloat(6, emp.getComision());
            sentencia.setInt(7, emp.getDept_no());
            sentencia.setInt(8, emp.getEmp_no());
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.printf("Empleado %d modificado%n", empno);
            }
            sentencia.close();
        } catch (SQLException e) { MensajeExcepcion(e); }

        return valor;
	}

	@Override
	public Empleado ConsultarEmp(int empno) {
		String sql = "SELECT * FROM empleados WHERE emp_no = ?";
        PreparedStatement sentencia;
        Empleado emp=new Empleado();      
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, empno);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                emp.setEmp_no(rs.getInt("emp_no"));
                emp.setApellido(rs.getString("apellido"));
                emp.setOficio(rs.getString("oficio"));
                emp.setDirector(rs.getInt("dir"));
                emp.setFechaAlta(rs.getDate("fecha_alt"));
                emp.setSalario(rs.getFloat("salario"));
                emp.setComision(rs.getFloat("comision"));
                emp.setDept_no(rs.getInt("dept_no"));
            }
            else
                System.out.printf("Empleado: %d No existe%n", empno);
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) { MensajeExcepcion(e); }
        return emp;
	}
	
	private void MensajeExcepcion(SQLException e) {
	       System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
	       System.out.printf("Mensaje   : %s %n", e.getMessage());
	       System.out.printf("SQL estado: %s %n", e.getSQLState());
	       System.out.printf("Cód error : %s %n", e.getErrorCode());
	    }
	@Override
	public ArrayList<Empleado> Obtenerempleados() {
		ArrayList<Empleado> lista= new ArrayList<Empleado>();
		String sql = "select * from empleados";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);			
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				Empleado emp=new Empleado();
				emp.setEmp_no(filas.getInt(1));
				emp.setApellido(filas.getString(2));
				emp.setOficio(filas.getString(3));
				emp.setDirector(filas.getInt(4));
				emp.setFechaAlta(filas.getDate(5));
				emp.setSalario(filas.getFloat(6));
				emp.setComision(filas.getFloat(7));
				emp.setDept_no(filas.getInt(8));
				lista.add(emp);		
			}
			
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		
		return lista;
	}
}
