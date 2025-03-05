package danielmonforteibias;

import java.util.ArrayList;


public class Main {
	public static void main(String[] args) {
        VueloImpl depDAO = new VueloImpl();

        ArrayList<Vuelo>vuelos=depDAO.obtenerVuelos();
        for(Vuelo v:vuelos) {
        	//System.out.println(v.getIdentificador());
        }
        ArrayList<String>codigos=depDAO.obtenerIdentificadoresVuelos();
        for(String s:codigos) {
        	//System.out.println(s);
        }
        ArrayList<Pasaje>pasajes=depDAO.obtenerPasajesVuelo("BRU-1234");
        for(Pasaje p:pasajes) {
        	System.out.println(p.getIdPasaje());
        }
        System.out.println("Fin");
        Pasaje p=new Pasaje();
	}
}
