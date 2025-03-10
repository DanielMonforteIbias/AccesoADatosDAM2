package clases;
// Generated 10 dic 2024, 13:38:22 by Hibernate Tools 5.5.9.Final

import java.math.BigInteger;

/**
 * LlevaId generated by hbm2java
 */
public class LlevaId implements java.io.Serializable {

	private BigInteger numetapa;
	private BigInteger codigocamiseta;

	public LlevaId() {
	}

	public LlevaId(BigInteger numetapa, BigInteger codigocamiseta) {
		this.numetapa = numetapa;
		this.codigocamiseta = codigocamiseta;
	}

	public BigInteger getNumetapa() {
		return this.numetapa;
	}

	public void setNumetapa(BigInteger numetapa) {
		this.numetapa = numetapa;
	}

	public BigInteger getCodigocamiseta() {
		return this.codigocamiseta;
	}

	public void setCodigocamiseta(BigInteger codigocamiseta) {
		this.codigocamiseta = codigocamiseta;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LlevaId))
			return false;
		LlevaId castOther = (LlevaId) other;

		return ((this.getNumetapa() == castOther.getNumetapa()) || (this.getNumetapa() != null
				&& castOther.getNumetapa() != null && this.getNumetapa().equals(castOther.getNumetapa())))
				&& ((this.getCodigocamiseta() == castOther.getCodigocamiseta())
						|| (this.getCodigocamiseta() != null && castOther.getCodigocamiseta() != null
								&& this.getCodigocamiseta().equals(castOther.getCodigocamiseta())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getNumetapa() == null ? 0 : this.getNumetapa().hashCode());
		result = 37 * result + (getCodigocamiseta() == null ? 0 : this.getCodigocamiseta().hashCode());
		return result;
	}

}
