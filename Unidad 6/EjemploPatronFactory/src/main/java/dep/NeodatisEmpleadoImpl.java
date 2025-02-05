package dep;

import java.util.ArrayList;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class NeodatisEmpleadoImpl implements EmpleadoDAO{
	static ODB bd;

	public NeodatisEmpleadoImpl() {
		bd = NeodatisDAOFactory.crearConexion();
	}
	@Override
	public boolean InsertarEmp(Empleado emp) {
		bd.store(emp);
		bd.commit();
		System.out.printf("Empleado: %d Insertado", emp.getEmp_no());
		return true;
	}

	@Override
	public boolean EliminarEmp(int empno) {
		boolean valor = false;
		IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", empno));
		Objects<Empleado> objetos = bd.getObjects(query);
		try {
			Empleado emple = (Empleado) objetos.getFirst();
			bd.delete(emple);
			bd.commit();
			valor = true;
			System.out.printf("Empleado: %d eliminado %n", empno);
		} catch (IndexOutOfBoundsException i) {
			System.out.printf("Empleado a eliminar: %d No existe %n", empno);
		}
		return valor;
	}

	@Override
	public boolean ModificarEmp(int empno, Empleado emp) {
		boolean valor = false;
		IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", empno));
		Objects<Empleado> objetos = bd.getObjects(query);
		try {
			Empleado emple = (Empleado) objetos.getFirst();
			emple.setApellido(emp.getApellido());
			emple.setOficio(emp.getOficio());
			emple.setDirector(emp.getDirector());
			emple.setFechaAlta(emp.getFechaAlta());
			emple.setSalario(emp.getSalario());
			emple.setComision(emp.getComision());
			emple.setDept_no(emp.getDept_no());
			bd.store(emple); // actualiza el objeto
			valor = true;
			bd.commit();
		} catch (IndexOutOfBoundsException i) {
			System.out.printf("Empleado: %d No existe%n", empno);
		}
		return valor;
	}

	@Override
	public Empleado ConsultarEmp(int empno) {
		IQuery query = new CriteriaQuery(Empleado.class, Where.equal("emp_no", empno));
		Objects<Empleado> objetos = bd.getObjects(query);
		Empleado emple=new Empleado();
		if (objetos != null) {
			try {
				emple = (Empleado) objetos.getFirst();
			} catch (IndexOutOfBoundsException i) {
				System.out.printf("Empleado: %d No existe%n", empno);
				emple.setApellido("no existe");
				emple.setOficio("no existe");
				emple.setEmp_no(empno);
			}
		}
		return emple;
	}
	@Override
	public ArrayList<Empleado> Obtenerempleados() {
		ArrayList<Empleado> lista = new ArrayList<Empleado>();
		Objects<Empleado> objetos = bd.getObjects(Empleado.class);
		for (Empleado emp : objetos) {
			lista.add(emp);
		}
		return lista;
	}
}