package danielmonforteibias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VueloImpl implements VueloDAO{
	private Connection conexion;

    public VueloImpl() {
    	try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "VUELOS", "vuelos"); 
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
    }

	@Override
	public String insertarVuelo(Vuelo v) {
		String mensaje="";
        String sql = "INSERT INTO vuelo VALUES(?,?,?,?,?,?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, v.getIdentificador());
            sentencia.setString(2, v.getAeropuertoOrigen().getCodAeropuerto());
            sentencia.setString(3, v.getAeropuertoDestino().getCodAeropuerto());
            sentencia.setString(4,v.getTipoVuelo());
            sentencia.setDate(5, v.getFechaVuelo());
            sentencia.setInt(6, v.getDescuento());
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                mensaje="Vuelo "+v.getIdentificador()+" insertado";
                System.out.printf(mensaje);
            }
            else mensaje="Vuelo "+v.getIdentificador()+" NO insertado";
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace();; }

        return mensaje;
	}

	@Override
	public String eliminarVuelo(String identificador) {
		String mensaje="";
        String sql = "DELETE FROM vuelo WHERE identificador = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, identificador);
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
              mensaje="Vuelo "+identificador+" eliminado";
              System.out.printf(mensaje);
            }
            else mensaje="Vuelo "+identificador+" NO eliminado";
            sentencia.close();
        } catch (SQLException e) { e.printStackTrace();; }

        return mensaje;
	}

	@Override
	public String modificarVuelo(String identificador, Vuelo v) {
		String mensaje="";
        String sql = "UPDATE vuelo SET aeropuertoorigen=?, aeropuertodestino=?, tipovuelo=?,  fechavuelo=?, descuento=? WHERE identificador=? ";
        PreparedStatement sentencia;
        try {
        	sentencia = conexion.prepareStatement(sql);
            sentencia.setString(6, identificador);
            sentencia.setString(1, v.getAeropuertoOrigen().getCodAeropuerto());
            sentencia.setString(2, v.getAeropuertoDestino().getCodAeropuerto());
            sentencia.setString(3,v.getTipoVuelo());
            sentencia.setDate(4, v.getFechaVuelo());
            sentencia.setInt(5, v.getDescuento());
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
                mensaje="Vuelo "+v.getIdentificador()+" modificado";
                System.out.printf(mensaje);
            }
            else mensaje="Vuelo "+v.getIdentificador()+" NO modificado";
            sentencia.close();
        } catch (SQLException e) { e.printStackTrace();; }

        return mensaje;
	}

	@Override
	public Vuelo consultarVuelo(String identificador) {
		String sql = "SELECT * FROM vuelo WHERE identificador = ?";
        PreparedStatement sentencia;
        Vuelo v=new Vuelo();     
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, identificador);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                v.setIdentificador(rs.getString(1));
                v.setAeropuertoOrigen(obtenerAeropuerto(rs.getString(2)));
                v.setAeropuertoDestino(obtenerAeropuerto(rs.getString(2)));
                v.setTipoVuelo(rs.getString(4));
                v.setFechaVuelo(rs.getDate(5));
                v.setDescuento(rs.getInt(6));
            }
            else
                System.out.printf("Vuelo "+identificador+" no existe");
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) { e.printStackTrace();; }
        return v;
	}

	@Override
	public ArrayList<Vuelo> obtenerVuelos() {
		ArrayList<Vuelo> lista= new ArrayList<Vuelo>();
		String sql = "select * from vuelo";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);			
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				Vuelo v=new Vuelo();
				v.setIdentificador(filas.getString(1));
				v.setAeropuertoOrigen(obtenerAeropuerto(filas.getString(2)));
                v.setAeropuertoDestino(obtenerAeropuerto(filas.getString(3)));
                v.setTipoVuelo(filas.getString(4));
                v.setFechaVuelo(filas.getDate(5));
                v.setDescuento(filas.getInt(6));
				lista.add(v);		
			}
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		return lista;
	}
	@Override
	public ArrayList<String> obtenerIdentificadoresVuelos() {
		ArrayList<String> lista= new ArrayList<String>();
		String sql = "select * from vuelo";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);			
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				lista.add(filas.getString(1));		
			}
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		return lista;
	}
	
	public Aeropuerto obtenerAeropuerto(String codAeropuerto) {
		String sql = "SELECT * FROM aeropuerto WHERE codaeropuerto = ?";
        PreparedStatement sentencia;
        Aeropuerto a=new Aeropuerto();   
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setString(1, codAeropuerto);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                a.setCodAeropuerto(rs.getString(1));
                a.setNombre(rs.getString(2));
                a.setCiudad(rs.getString(3));
                a.setPais(rs.getString(4));
                a.setTasa(rs.getInt(5));
            }
            else
                System.out.printf("Aeropuerto "+codAeropuerto+" no existe");
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) { e.printStackTrace();; }
        return a;
	}

	@Override
	public ArrayList<Pasaje> obtenerPasajesVuelo(String identificador) {
		ArrayList<Pasaje> lista= new ArrayList<Pasaje>();
		String sql = "select * from pasaje where identificador=?";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);		
			sentencia.setString(1, identificador);
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				lista.add(new Pasaje(filas.getInt(1),getPasajero(filas.getInt(2)),filas.getString(3),filas.getInt(4),filas.getString(5),filas.getInt(6)));	
			}
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		return lista;
	}
	@Override
	public Pasajero getPasajero(int pasajeroCod) {
		String sql = "SELECT * FROM pasajero WHERE pasajerocod = ?";
        PreparedStatement sentencia;
        Pasajero p=new Pasajero();   
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, pasajeroCod);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
                p.setPasajeroCod(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setTelefono(rs.getString(3));
                p.setDireccion(rs.getString(4));
                p.setPais(rs.getString(5));
            }
            else
                System.out.printf("Pasajero "+pasajeroCod+" no existe");
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) { e.printStackTrace();; }
        return p;
	}

	@Override
	public ArrayList<Pasaje> getPasajes() {
		ArrayList<Pasaje> lista= new ArrayList<Pasaje>();
		String sql = "select * from pasaje";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);			
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				Pasaje p=new Pasaje();
				p.setIdPasaje(filas.getInt(1));
				p.setPasajero(getPasajero(filas.getInt(2)));
				p.setIdentificador(filas.getString(3));
				p.setNumAsiento(filas.getInt(4));
				p.setClase(filas.getString(5));
				p.setPvp(filas.getInt(6));
				lista.add(p);
			}
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public ArrayList<Pasajero> getPasajeros() {
		ArrayList<Pasajero> lista= new ArrayList<Pasajero>();
		String sql = "select * from pasajero";
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);			
			ResultSet  filas = sentencia.executeQuery(); 
			while (filas.next()) {
				Pasajero p=new Pasajero();
				p.setPasajeroCod(filas.getInt(1));
				p.setNombre(filas.getString(2));
				p.setTelefono(filas.getString(3));
				p.setDireccion(filas.getString(4));
				p.setPais(filas.getString(5));
				lista.add(p);
			}
		} catch (SQLException e) {
			System.out.println("Código de error: " + e.getErrorCode() + "\nMensaje de error: " + e.getMessage());
		}
		return lista;
	}

	@Override
	public String borrarPasaje(int idPasaje) {
		String mensaje="";
		String sql = "DELETE FROM pasaje WHERE idpasaje = ? ";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idPasaje);
            int filas = sentencia.executeUpdate();
            if (filas > 0) {
              mensaje="Pasaje "+idPasaje+" eliminado";
              System.out.printf(mensaje);
            }
            else mensaje="Pasaje "+idPasaje+" no eliminado";
            sentencia.close();
        } catch (SQLException e) { e.printStackTrace();; }

        return mensaje;
	}

	@Override
	public Pasaje getPasaje(int idPasaje) {
		String sql = "SELECT * FROM pasaje WHERE idpasaje = ?";
        PreparedStatement sentencia;
        Pasaje p=new Pasaje();   
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, idPasaje);
            ResultSet rs = sentencia.executeQuery();          
            if (rs.next()) {
				p.setIdPasaje(rs.getInt(1));
				p.setPasajero(getPasajero(rs.getInt(2)));
				p.setIdentificador(rs.getString(3));
				p.setNumAsiento(rs.getInt(4));
				p.setClase(rs.getString(5));
				p.setPvp(rs.getInt(6));
            }
            else
                System.out.printf("Pasaje "+idPasaje+" no existe");
            rs.close();// liberar recursos
            sentencia.close();
         
        } catch (SQLException e) { e.printStackTrace();; }
        return p;
	}

	@Override
	public String insertarPasaje(Pasaje p) {
		String mensaje="";
        String sql = "INSERT INTO pasaje VALUES(?,?,?,?,?,?)";
        PreparedStatement sentencia;
        try {
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, p.getIdPasaje());
            sentencia.setInt(2, p.getPasajero().getPasajeroCod());
            sentencia.setString(3, p.getIdentificador());
            sentencia.setInt(4,p.getNumAsiento());
            sentencia.setString(5, p.getClase());
            sentencia.setInt(6, p.getPvp());
            if(!comprobarPasajeExiste(p.getPasajero().getPasajeroCod(), p.getIdentificador())) {
            	int filas = sentencia.executeUpdate();
                if (filas > 0) {
                    mensaje="Pasaje "+p.getIdPasaje()+" insertado";
                    System.out.printf(mensaje);
                }
                else mensaje="Pasaje "+p.getIdPasaje()+" NO insertado";
            }
            else mensaje="Ya existe un pasaje para el pasajero "+p.getPasajero().getPasajeroCod()+" con el vuelo "+p.getIdentificador()+", no se inserta";
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace();; }

        return mensaje;
	}
	
	public boolean comprobarPasajeExiste(int pasajeroCod, String identificador) {
		String sql = "SELECT COUNT(*) FROM pasaje WHERE pasajerocod = ? AND identificador = ?";
	    try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
	        sentencia.setInt(1, pasajeroCod);
	        sentencia.setString(2, identificador);
	        ResultSet rs = sentencia.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
