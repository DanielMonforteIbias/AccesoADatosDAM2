package cursosAlumnosVer2;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cursosalumnos")
public class CursosAlumnos {
	private ArrayList<Curso>cursos;

	public CursosAlumnos(ArrayList<Curso> cursos) {
		super();
		this.cursos = cursos;
	}
	public CursosAlumnos() {}
	@XmlElement(name="curso")
	public ArrayList<Curso> getCursos() {
		return cursos;
	}
	public void setCursos(ArrayList<Curso> cursos) {
		this.cursos = cursos;
	}
	
}
