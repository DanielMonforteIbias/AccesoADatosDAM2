package clases;
// Generated 12 dic 2024, 18:36:54 by Hibernate Tools 5.4.33.Final

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Asignaturas generated by hbm2java
 */
public class Asignaturas implements java.io.Serializable {

	private BigInteger codasig;
	private String nombreasig;
	private BigInteger precioasig;
	private Character tipoasig;
	private BigInteger creditos;
	private Set cursoasigs = new HashSet(0);
	private Set matriculases = new HashSet(0);

	public Asignaturas() {
	}

	public Asignaturas(BigInteger codasig) {
		this.codasig = codasig;
	}

	public Asignaturas(BigInteger codasig, String nombreasig, BigInteger precioasig, Character tipoasig,
			BigInteger creditos, Set cursoasigs, Set matriculases) {
		this.codasig = codasig;
		this.nombreasig = nombreasig;
		this.precioasig = precioasig;
		this.tipoasig = tipoasig;
		this.creditos = creditos;
		this.cursoasigs = cursoasigs;
		this.matriculases = matriculases;
	}

	public BigInteger getCodasig() {
		return this.codasig;
	}

	public void setCodasig(BigInteger codasig) {
		this.codasig = codasig;
	}

	public String getNombreasig() {
		return this.nombreasig;
	}

	public void setNombreasig(String nombreasig) {
		this.nombreasig = nombreasig;
	}

	public BigInteger getPrecioasig() {
		return this.precioasig;
	}

	public void setPrecioasig(BigInteger precioasig) {
		this.precioasig = precioasig;
	}

	public Character getTipoasig() {
		return this.tipoasig;
	}

	public void setTipoasig(Character tipoasig) {
		this.tipoasig = tipoasig;
	}

	public BigInteger getCreditos() {
		return this.creditos;
	}

	public void setCreditos(BigInteger creditos) {
		this.creditos = creditos;
	}

	public Set getCursoasigs() {
		return this.cursoasigs;
	}

	public void setCursoasigs(Set cursoasigs) {
		this.cursoasigs = cursoasigs;
	}

	public Set getMatriculases() {
		return this.matriculases;
	}

	public void setMatriculases(Set matriculases) {
		this.matriculases = matriculases;
	}

}
