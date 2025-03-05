package danielmonforteibias;

import java.sql.Date;
public class Vuelo {
	private String identificador;
	private Aeropuerto aeropuertoOrigen;
	private Aeropuerto aeropuertoDestino;
	private String tipoVuelo;
	private Date fechaVuelo;
	private int descuento;
	
	public Vuelo() {
		super();
	}
	public Vuelo(String identificador, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, String tipoVuelo,
			Date fechaVuelo, int descuento) {
		super();
		this.identificador = identificador;
		this.aeropuertoOrigen = aeropuertoOrigen;
		this.aeropuertoDestino = aeropuertoDestino;
		this.tipoVuelo = tipoVuelo;
		this.fechaVuelo = fechaVuelo;
		this.descuento = descuento;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Aeropuerto getAeropuertoOrigen() {
		return aeropuertoOrigen;
	}
	public void setAeropuertoOrigen(Aeropuerto aeropuertoOrigen) {
		this.aeropuertoOrigen = aeropuertoOrigen;
	}
	public Aeropuerto getAeropuertoDestino() {
		return aeropuertoDestino;
	}
	public void setAeropuertoDestino(Aeropuerto aeropuertoDestino) {
		this.aeropuertoDestino = aeropuertoDestino;
	}
	public String getTipoVuelo() {
		return tipoVuelo;
	}
	public void setTipoVuelo(String tipoVuelo) {
		this.tipoVuelo = tipoVuelo;
	}
	public Date getFechaVuelo() {
		return fechaVuelo;
	}
	public void setFechaVuelo(Date fechaVuelo) {
		this.fechaVuelo = fechaVuelo;
	}
	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
}