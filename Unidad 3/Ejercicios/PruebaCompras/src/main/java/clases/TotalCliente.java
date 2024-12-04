package clases;

import java.math.BigInteger;

public class TotalCliente {
	private BigInteger codCliente;
	private String nombre;
	private Long contador;
	private Double suma;
	
	public TotalCliente() {}
	public TotalCliente(BigInteger codCliente, String nombre, Long contador, Double suma) {
		super();
		this.codCliente=codCliente;
		this.nombre = nombre;
		this.contador = contador;
		this.suma = suma;
	}
	
	public BigInteger getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(BigInteger codCliente) {
		this.codCliente = codCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getContador() {
		return contador;
	}
	public void setContador(Long contador) {
		this.contador = contador;
	}
	public Double getSuma() {
		return suma;
	}
	public void setSuma(Double suma) {
		this.suma = suma;
	}
}