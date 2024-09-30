package ejercicioDirectoProductos;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"unidadesVendidas","fecha","importe"})
public class Venta {
	private int unidadesVendidas;
	private String fecha;
	private double importe;
	public Venta(int unidadesVendidas, String fecha, double importe) {
		super();
		this.unidadesVendidas = unidadesVendidas;
		this.fecha = fecha;
		this.importe = importe;
	}
	public Venta() {}
	public int getUnidadesVendidas() {
		return unidadesVendidas;
	}
	public void setUnidadesVendidas(int unidadesVendidas) {
		this.unidadesVendidas = unidadesVendidas;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
}
