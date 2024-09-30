package ejercicioDirectoProductos;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="productos")
public class Productos2 {
	private ArrayList<Producto2>productos;

	public Productos2(ArrayList<Producto2> productos) {
		super();
		this.productos = productos;
	}
	public Productos2() {}
	
	@XmlElement(name="producto")
	public ArrayList<Producto2> getProductos() {
		return productos;
	}
	public void setProductos(ArrayList<Producto2> productos) {
		this.productos = productos;
	}
}