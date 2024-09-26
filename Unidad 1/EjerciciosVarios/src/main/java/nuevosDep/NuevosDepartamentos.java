package nuevosDep;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Nuevosdepartamentos")
public class NuevosDepartamentos {
	private ArrayList<Departamento>departamentos;

	public NuevosDepartamentos(ArrayList<Departamento> departamentos) {
		super();
		this.departamentos = departamentos;
	}
	public NuevosDepartamentos() {}
	
	@XmlElement(name="departamento")
	public ArrayList<Departamento> getDepartamentos() {
		return departamentos;
	}
	public void setDepartamentos(ArrayList<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
}