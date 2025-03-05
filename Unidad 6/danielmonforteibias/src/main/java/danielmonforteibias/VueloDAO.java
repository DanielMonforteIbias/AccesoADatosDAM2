package danielmonforteibias;

import java.util.ArrayList;

public interface VueloDAO {
	public String insertarVuelo(Vuelo v);
    public String eliminarVuelo(String identificador); 
    public String modificarVuelo(String identificador,  Vuelo v);
    public Vuelo consultarVuelo(String identificador);
    public ArrayList<Vuelo> obtenerVuelos();
    public ArrayList<String>obtenerIdentificadoresVuelos();
    public ArrayList<Pasaje>obtenerPasajesVuelo(String identificador);
    public ArrayList<Pasaje>getPasajes();
    public ArrayList<Pasajero>getPasajeros();
    public Pasajero getPasajero(int pasajeroCod);
    public String insertarPasaje(Pasaje p);
    public String borrarPasaje(int idPasaje);
    public Pasaje getPasaje(int idPasaje);
    
}
