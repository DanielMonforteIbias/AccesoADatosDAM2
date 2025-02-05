package dep;

import java.io.Serializable;

//Objeto DAO
public class Departamento implements Serializable {
	private int deptno;
	private String dnombre;
	private String loc;

	public Departamento() {
	}

	public Departamento(int deptno, String dnombre, String loc) {
		this.deptno = deptno;
		this.dnombre = dnombre;
		this.loc = loc;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getDnombre() {
		return dnombre;
	}

	public void setDnombre(String dnombre) {
		this.dnombre = dnombre;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@Override
	public String toString() {
		return "Departamento [deptno=" + deptno + ", dnombre=" + dnombre + ", loc=" + loc + "]";
	}

}
