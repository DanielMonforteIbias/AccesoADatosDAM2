package dep;

import java.util.ArrayList;

import org.neodatis.odb.ODB;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class NeodatisDepartamentoImpl implements DepartamentoDAO {
	static ODB bd;

	public NeodatisDepartamentoImpl() {
		bd = NeodatisDAOFactory.crearConexion();
	}

	@Override
	public boolean InsertarDep(Departamento dep) {
		bd.store(dep);
		bd.commit();
		System.out.printf("Departamento: %d Insertado", dep.getDeptno());
		return true;
	}

	@Override
	public boolean EliminarDep(int deptno) {
		boolean valor = false;
		IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
		Objects<Departamento> objetos = bd.getObjects(query);
		try {
			Departamento depart = (Departamento) objetos.getFirst();
			bd.delete(depart);
			bd.commit();
			valor = true;
			System.out.printf("Departamento: %d eliminado %n", deptno);
		} catch (IndexOutOfBoundsException i) {
			System.out.printf("Departamento a eliminar: %d No existe %n", deptno);
		}
		return valor;
	}

	@Override
	public boolean ModificarDep(int deptno, Departamento dep) {
		boolean valor = false;
		IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
		Objects<Departamento> objetos = bd.getObjects(query);
		try {
			Departamento depart = (Departamento) objetos.getFirst();
			depart.setDnombre(dep.getDnombre());
			depart.setLoc(dep.getLoc());
			bd.store(depart); // actualiza el objeto
			valor = true;
			bd.commit();
		} catch (IndexOutOfBoundsException i) {
			System.out.printf("Departamento: %d No existe%n", deptno);
		}
		return valor;
	}

	@Override
	public Departamento ConsultarDep(int deptno) {
		IQuery query = new CriteriaQuery(Departamento.class, Where.equal("deptno", deptno));
		Objects<Departamento> objetos = bd.getObjects(query);
		Departamento dep = new Departamento();
		if (objetos != null) {
			try {
				dep = (Departamento) objetos.getFirst();
			} catch (IndexOutOfBoundsException i) {
				System.out.printf("Departamento: %d No existe%n", deptno);
				dep.setDnombre("no existe");
				dep.setDeptno(deptno);
				dep.setLoc("no existe");
			}
		}
		return dep;
	}

	@Override
	public ArrayList<Departamento> Obtenerdepartamentos() {
		ArrayList<Departamento> lista = new ArrayList<Departamento>();
		Objects<Departamento> objetos = bd.getObjects(Departamento.class);
		for (Departamento dep : objetos) {
			lista.add(dep);
		}
		return lista;

	}
}
