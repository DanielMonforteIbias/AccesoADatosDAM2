package crearDepartamentosXml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"id","nombre","localidad","numEmples","mediaSalario"})
public class Departamento {
	private int id;
	private String nombre;
	private String localidad;
	private int numEmples;
	private float mediaSalario;
	public Departamento(int id, String nombre, String localidad, int numEmples, float mediaSalario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localidad = localidad;
		this.numEmples = numEmples;
		this.mediaSalario = mediaSalario;
	}
	public Departamento() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	@XmlElement(name="numemple")
	public int getNumEmples() {
		return numEmples;
	}
	public void setNumEmples(int numEmples) {
		this.numEmples = numEmples;
	}
	@XmlElement(name="salario")
	public float getMediaSalario() {
		return mediaSalario;
	}
	public void setMediaSalario(float mediaSalario) {
		this.mediaSalario = mediaSalario;
	}
	
}
