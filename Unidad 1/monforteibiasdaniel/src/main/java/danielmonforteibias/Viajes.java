package danielmonforteibias;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ListaViajes")
public class Viajes {
	private ArrayList<Viaje>listaViajes;

	public Viajes(ArrayList<Viaje> listaViajes) {
		super();
		this.listaViajes = listaViajes;
	}
	
	public Viajes() {}
	
	@XmlElement(name="Viaje")
	public ArrayList<Viaje> getListaViajes() {
		return listaViajes;
	}
	public void setListaViajes(ArrayList<Viaje> listaViajes) {
		this.listaViajes = listaViajes;
	}
	
}
