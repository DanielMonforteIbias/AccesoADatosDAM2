package danielmonforteibias;

public class Pasajero {
	private int pasajeroCod;
	private String nombre;
	private String telefono;
	private String direccion;
	private String pais;
	public Pasajero() {
		
	}
	public Pasajero(int pasajeroCod, String nombre, String telefono, String direccion, String pais) {
		super();
		this.pasajeroCod = pasajeroCod;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.pais = pais;
	}
	public int getPasajeroCod() {
		return pasajeroCod;
	}
	public void setPasajeroCod(int pasajeroCod) {
		this.pasajeroCod = pasajeroCod;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
}
