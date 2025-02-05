package dep;

import java.sql.Date;

public class Empleado {
	private int emp_no;
	private String apellido;
	private String oficio;
	private int director;
	private Date fechaAlta;
	private float salario;
	private float comision;
	private int dept_no;
	public Empleado() {
		
	}
	public Empleado(int emp_no, String apellido, String oficio, int director, Date fechaAlta, float salario,
			float comision, int dept_no) {
		super();
		this.emp_no = emp_no;
		this.apellido = apellido;
		this.oficio = oficio;
		this.director = director;
		this.fechaAlta = fechaAlta;
		this.salario = salario;
		this.comision = comision;
		this.dept_no = dept_no;
	}
	public int getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(int emp_no) {
		this.emp_no = emp_no;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getOficio() {
		return oficio;
	}
	public void setOficio(String oficio) {
		this.oficio = oficio;
	}
	public int getDirector() {
		return director;
	}
	public void setDirector(int director) {
		this.director = director;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public float getSalario() {
		return salario;
	}
	public void setSalario(float salario) {
		this.salario = salario;
	}
	public float getComision() {
		return comision;
	}
	public void setComision(float comision) {
		this.comision = comision;
	}
	public int getDept_no() {
		return dept_no;
	}
	public void setDept_no(int dept_no) {
		this.dept_no = dept_no;
	}
	@Override
	public String toString() {
		return "Empleado [emp_no=" + emp_no + ", apellido=" + apellido + ", oficio=" + oficio + ", director=" + director
				+ ", fechaAlta=" + fechaAlta + ", salario=" + salario + ", comision=" + comision + ", dept_no="
				+ dept_no + "]";
	}
	
}
