package clases;
// Generated 13 nov 2024, 18:03:12 by Hibernate Tools 6.5.1.Final

import java.math.BigInteger;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Compras generated by hbm2java
 */
public class Compras implements java.io.Serializable {

	private BigInteger numcompra;
	private Clientes clientes;
	private Date fecha;
	private BigInteger descuento;
	private Set detcomprases = new HashSet(0);

	public Compras() {
	}

	public Compras(BigInteger numcompra, Clientes clientes) {
		this.numcompra = numcompra;
		this.clientes = clientes;
	}

	public Compras(BigInteger numcompra, Clientes clientes, Date fecha, BigInteger descuento, Set detcomprases) {
		this.numcompra = numcompra;
		this.clientes = clientes;
		this.fecha = fecha;
		this.descuento = descuento;
		this.detcomprases = detcomprases;
	}

	public BigInteger getNumcompra() {
		return this.numcompra;
	}

	public void setNumcompra(BigInteger numcompra) {
		this.numcompra = numcompra;
	}

	public Clientes getClientes() {
		return this.clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigInteger getDescuento() {
		return this.descuento;
	}

	public void setDescuento(BigInteger descuento) {
		this.descuento = descuento;
	}

	public Set getDetcomprases() {
		return this.detcomprases;
	}

	public void setDetcomprases(Set detcomprases) {
		this.detcomprases = detcomprases;
	}

}
