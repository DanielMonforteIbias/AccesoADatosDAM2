package danielmonforteibias;

public class Pasaje {
	private int idPasaje;
	private Pasajero pasajero;
	private String identificador;
	private int numAsiento;
	private String clase;
	private int pvp;
	public Pasaje() {
		
	}
	public Pasaje(int idPasaje, Pasajero pasajero, String identificador, int numAsiento, String clase, int pvp) {
		super();
		this.idPasaje = idPasaje;
		this.pasajero = pasajero;
		this.identificador = identificador;
		this.numAsiento = numAsiento;
		this.clase = clase;
		this.pvp = pvp;
	}
	public int getIdPasaje() {
		return idPasaje;
	}
	public void setIdPasaje(int idPasaje) {
		this.idPasaje = idPasaje;
	}
	public Pasajero getPasajero() {
		return pasajero;
	}
	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public int getNumAsiento() {
		return numAsiento;
	}
	public void setNumAsiento(int numAsiento) {
		this.numAsiento = numAsiento;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public int getPvp() {
		return pvp;
	}
	public void setPvp(int pvp) {
		this.pvp = pvp;
	}
}