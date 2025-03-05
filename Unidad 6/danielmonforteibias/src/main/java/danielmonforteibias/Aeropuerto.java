package danielmonforteibias;

public class Aeropuerto {
	private String codAeropuerto;
	private String nombre;
	private String ciudad;
	private String pais;
	private int tasa;
	public Aeropuerto() {
		
	}
	public Aeropuerto(String codAeropuerto, String nombre, String ciudad, String pais, int tasa) {
		super();
		this.codAeropuerto = codAeropuerto;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.pais = pais;
		this.tasa = tasa;
	}
	public String getCodAeropuerto() {
		return codAeropuerto;
	}
	public void setCodAeropuerto(String codAeropuerto) {
		this.codAeropuerto = codAeropuerto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public int getTasa() {
		return tasa;
	}
	public void setTasa(int tasa) {
		this.tasa = tasa;
	}
	
}
