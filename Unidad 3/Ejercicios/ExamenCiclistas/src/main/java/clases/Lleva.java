package clases;
// Generated 10 dic 2024, 13:38:22 by Hibernate Tools 5.5.9.Final

/**
 * Lleva generated by hbm2java
 */
public class Lleva implements java.io.Serializable {

	private LlevaId id;
	private Ciclistas ciclistas;
	private Etapas etapas;
	private Camisetas camisetas;

	public Lleva() {
	}

	public Lleva(LlevaId id, Ciclistas ciclistas, Etapas etapas, Camisetas camisetas) {
		this.id = id;
		this.ciclistas = ciclistas;
		this.etapas = etapas;
		this.camisetas = camisetas;
	}

	public LlevaId getId() {
		return this.id;
	}

	public void setId(LlevaId id) {
		this.id = id;
	}

	public Ciclistas getCiclistas() {
		return this.ciclistas;
	}

	public void setCiclistas(Ciclistas ciclistas) {
		this.ciclistas = ciclistas;
	}

	public Etapas getEtapas() {
		return this.etapas;
	}

	public void setEtapas(Etapas etapas) {
		this.etapas = etapas;
	}

	public Camisetas getCamisetas() {
		return this.camisetas;
	}

	public void setCamisetas(Camisetas camisetas) {
		this.camisetas = camisetas;
	}

}
