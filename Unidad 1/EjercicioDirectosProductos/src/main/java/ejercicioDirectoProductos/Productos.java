package ejercicioDirectoProductos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="productos")
public class Productos {
	private ArrayList<Producto>productos;

	public Productos(ArrayList<Producto> productos) {
		super();
		this.productos = productos;
	}
	public Productos() {}
	
	@XmlElement(name="producto")
	public ArrayList<Producto> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}
	
}
