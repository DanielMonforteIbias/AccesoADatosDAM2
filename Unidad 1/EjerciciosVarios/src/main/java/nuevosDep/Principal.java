package nuevosDep;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Principal {

	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(NuevosDepartamentos.class);
			Unmarshaller unmars = context.createUnmarshaller();
	        NuevosDepartamentos nd=(NuevosDepartamentos) unmars.unmarshal(new File("NuevosDep.xml"));
		    ArrayList<Departamento>departamentos=nd.getDepartamentos();
		    for(Departamento dep:departamentos) {
		    	int deptno=dep.getDeptno();
		    	String nombre=dep.getDnombre();
		    	String localidad=dep.getLocalidad();
		    	System.out.println("Número dep: "+deptno+" Nombre: "+nombre+" Localidad: "+localidad);
		    }
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}