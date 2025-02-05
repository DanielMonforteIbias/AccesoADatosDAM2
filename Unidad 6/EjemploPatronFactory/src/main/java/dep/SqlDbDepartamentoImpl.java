package dep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlDbDepartamentoImpl implements DepartamentoDAO {
    Connection conexion;

    public SqlDbDepartamentoImpl() {
        conexion = SqlDbDAOFactory.crearConexion();
    }

    @Override
    public boolean InsertarDep(Departamento dep) {
        boolean valor = false;
        String sql = "INSERT INTO departamentos VALUES(?, ?, ?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, dep.getDeptno());
            sentencia.setString(2, dep.getDnombre());
            sentencia.setString(3, dep.getLoc());
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d insertado%n",
                                 dep.getDeptno());
            }
            sentencia.close();

        } catch (SQLException e) { MensajeExcepcion(e); }

        return valor;
    }

    @Override
    public boolean EliminarDep(int deptno) {
        boolean valor = false;
        String sql = "DELETE FROM departamentos WHERE dept_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
              valor = true;
              System.out.printf("Departamento %d eliminado%n",deptno);
            }
            sentencia.close();
        } catch (SQLException e) { MensajeExcepcion(e); }

        return valor;
    }

    @Override
    public boolean ModificarDep(int num, Departamento dep) {
        boolean valor = false;
        String sql = "UPDATE departamentos SET dnombre= ?, loc = ? WHERE dept_no = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(3, num);
            sentencia.setString(1, dep.getDnombre());
            sentencia.setString(2, dep.getLoc());
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                valor = true;
                System.out.printf("Departamento %d modificado%n", num);
            }
            sentencia.close();
        } catch (SQLException e) { MensajeExcepcion(e); }

        return valor;
    }

    @Override
    public Departamento ConsultarDep(int deptno) {
        String sql = "SELECT dept_no, dnombre, loc FROM departamentos WHERE dept_no = ?";
        PreparedStatement sentencia;
        Departamento dep = new Departamento();        
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, deptno);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                dep.setDeptno(rs.getInt("dept_no"));
                dep.setDnombre(rs.getString("dnombre"));
                dep.setLoc(rs.getString("loc"));
            }
            else
                System.out.printf("Departamento: %d No existe%n", 
                                       deptno);
            
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) { MensajeExcepcion(e); }
        return dep;
    }

    private void MensajeExcepcion(SQLException e) {
       System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
       System.out.printf("Mensaje   : %s %n", e.getMessage());
       System.out.printf("SQL estado: %s %n", e.getSQLState());
       System.out.printf("Cód error : %s %n", e.getErrorCode());
    }

	@Override
	public ArrayList<Departamento> Obtenerdepartamentos() {
		ArrayList<Departamento> lista= new ArrayList<Departamento>();
		String sql = "select * from departamentos";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);			
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				Departamento dd = new Departamento();
				dd.setDeptno(filas.getInt(1));
				dd.setDnombre(filas.getString(2));
				dd.setLoc(filas.getString(3));
				lista.add(dd);		
			}
			
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		
		return lista;
	}
}
