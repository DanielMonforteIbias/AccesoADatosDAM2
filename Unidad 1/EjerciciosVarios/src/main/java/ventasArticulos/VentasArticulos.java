package ventasArticulos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ventasarticulos")
public class VentasArticulos {
	private Articulo articulo;
	private ArrayList<Venta>ventas;
	public VentasArticulos(Articulo articulo, ArrayList<Venta> ventas) {
		super();
		this.articulo = articulo;
		this.ventas = ventas;
	}
	
	public VentasArticulos() {}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	@XmlElementWrapper(name="ventas")
	@XmlElement(name="venta")
	public ArrayList<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(ArrayList<Venta> ventas) {
		this.ventas = ventas;
	}
	
}
