package clases;
// Generated 9 dic 2024, 16:32:03 by Hibernate Tools 5.5.9.Final

/**
 * TLineaEstacionId generated by hbm2java
 */
public class TLineaEstacionId implements java.io.Serializable {

	private int codLinea;
	private int codEstacion;

	public TLineaEstacionId() {
	}

	public TLineaEstacionId(int codLinea, int codEstacion) {
		this.codLinea = codLinea;
		this.codEstacion = codEstacion;
	}

	public int getCodLinea() {
		return this.codLinea;
	}

	public void setCodLinea(int codLinea) {
		this.codLinea = codLinea;
	}

	public int getCodEstacion() {
		return this.codEstacion;
	}

	public void setCodEstacion(int codEstacion) {
		this.codEstacion = codEstacion;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TLineaEstacionId))
			return false;
		TLineaEstacionId castOther = (TLineaEstacionId) other;

		return (this.getCodLinea() == castOther.getCodLinea()) && (this.getCodEstacion() == castOther.getCodEstacion());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCodLinea();
		result = 37 * result + this.getCodEstacion();
		return result;
	}

}
