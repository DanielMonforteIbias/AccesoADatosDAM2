package clases;

public class ComprasPorCliente {
	private int codCliente;
	private String nombre;
	private int numCompras;
	private double totalCompras;
	public ComprasPorCliente() {}
	public ComprasPorCliente(int codCliente, String nombre, int numCompras, double totalCompras) {
		super();
		this.codCliente = codCliente;
		this.nombre = nombre;
		this.numCompras = numCompras;
		this.totalCompras = totalCompras;
	}
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumCompras() {
		return numCompras;
	}
	public void setNumCompras(int numCompras) {
		this.numCompras = numCompras;
	}
	public double getTotalCompras() {
		return totalCompras;
	}
	public void setTotalCompras(double totalCompras) {
		this.totalCompras = totalCompras;
	}
	
}
