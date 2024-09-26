package actividad1_7;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="MISLIBRERIAS")
public class Librerias {
	private ArrayList<Libreria> librerias;

	public Librerias(ArrayList<Libreria> librerias) {
		super();
		this.librerias = librerias;
	}
	public Librerias() {}
	
	@XmlElement(name="Libreria")
	public ArrayList<Libreria> getLibrerias() {
		return librerias;
	}
	
	public void setLibrerias(ArrayList<Libreria> librerias) {
		this.librerias = librerias;
	}
	
}
