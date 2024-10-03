package examen;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder= {"numAlumno","nombre","localidad","numAsig","notamedia","listaNotas"})
public class Alumno {
	private int numAlumno;
	private String nombre;
	private String localidad;
	private int numAsig;
	private float notamedia;
	private ArrayList<Nota>listaNotas;
	
	public Alumno(int numAlumno, String nombre, String localidad, int numAsig, float notamedia,
			ArrayList<Nota> listaNotas) {
		super();
		this.numAlumno = numAlumno;
		this.nombre = nombre;
		this.localidad = localidad;
		this.numAsig = numAsig;
		this.notamedia = notamedia;
		this.listaNotas = listaNotas;
	}
	public Alumno() {}
	public int getNumAlumno() {
		return numAlumno;
	}
	public void setNumAlumno(int numAlumno) {
		this.numAlumno = numAlumno;
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
	public int getNumAsig() {
		return numAsig;
	}
	public void setNumAsig(int numAsig) {
		this.numAsig = numAsig;
	}
	public float getNotamedia() {
		return notamedia;
	}
	public void setNotamedia(float notamedia) {
		this.notamedia = notamedia;
	}
	@XmlElementWrapper(name="ListaNotas")
	@XmlElement(name="notaasig")
	public ArrayList<Nota> getListaNotas() {
		return listaNotas;
	}
	public void setListaNotas(ArrayList<Nota> listaNotas) {
		this.listaNotas = listaNotas;
	}
}
