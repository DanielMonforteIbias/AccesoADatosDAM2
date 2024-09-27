package crearDepartamentosXml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Departamentos")
public class Departamentos {
	private ArrayList<Departamento>departamentos;

	public Departamentos(ArrayList<Departamento> departamentos) {
		super();
		this.departamentos = departamentos;
	}
	public Departamentos() {}
	@XmlElement(name="dep")
	public ArrayList<Departamento> getDepartamentos() {
		return departamentos;
	}
	public void setDepartamentos(ArrayList<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
}
