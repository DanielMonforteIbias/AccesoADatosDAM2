package ventasArticulos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"numventa","unidades","nombreCliente","fecha"})
public class Venta {
	private int numventa;
	private int unidades;
	private String nombreCliente;
	private String fecha;
	public Venta(int numventa, int unidades, String nombreCliente, String fecha) {
		super();
		this.numventa = numventa;
		this.unidades = unidades;
		this.nombreCliente = nombreCliente;
		this.fecha = fecha;
	}
	public Venta() {}
	public int getNumventa() {
		return numventa;
	}
	public void setNumventa(int numventa) {
		this.numventa = numventa;
	}
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	@XmlElement(name="nombrecliente")
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}
