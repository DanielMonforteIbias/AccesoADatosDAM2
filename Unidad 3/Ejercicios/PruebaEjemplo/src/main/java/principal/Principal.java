package principal;

import java.math.BigInteger;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import clases.*;

public class Principal {
	private static SessionFactory factori;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);

		factori = Conexion.getSession(); // SesionFactory

		insertarDepartamento();
		insertarEmpleado();
		
		cargarDepartGet(10);
		cargarDepartGet(100);
		cargarDepartGet(61);
		factori.close();

	}

	private static void insertarDepartamento() {
		Session session = factori.openSession(); // creo una sesión de trabajo
		Transaction tx = session.beginTransaction();

		System.out.println("Inserto una fila en la tabla DEPARTAMENTOS.");

		try {
			Departamentos dep = new Departamentos();
			dep.setDeptNo(BigInteger.valueOf(61));
			dep.setDnombre("MARKETs");
			dep.setLoc("GUADALAJARA");

			session.persist(dep);
			tx.commit();
			System.out.println("Departamento insertado");
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			System.out.println(e.getMessage());
		} catch (org.hibernate.exception.GenericJDBCException e1) {
			System.out.println(e1.getMessage());
		}
		session.close();
	}

	public static void insertarEmpleado() {
		Session session = factori.openSession(); // creo una sesión de trabajo
		Transaction tx = session.beginTransaction();

		System.out.println("Inserto un EMPLEADO EN EL DEPARTAMENTO 10.");

		Float salario = new Float(1500);// inicializo el salario
		Float comision = new Float(10); // inicializo la comisión

		Empleados em = new Empleados(); // creo un objeto empleados
		em.setEmpNo(BigInteger.valueOf(4455)); // el número de empleado es 4455
		em.setApellido("PEPE");
		em.setOficio("VENDEDOR");
		em.setSalario(2000.0);
		em.setComision(100.0);

		Departamentos d = new Departamentos(); // creo un objeto Departamentos
		d.setDeptNo(BigInteger.valueOf(10)); // el número de dep es 10
		em.setDepartamentos(d);
		// fecha de alta
		java.util.Date hoy = new java.util.Date();
		java.sql.Date fecha = new java.sql.Date(hoy.getTime());
		em.setFechaAlt(fecha);

		try {
			session.persist(em);
			tx.commit();
			System.out.println("EMPLEADO INSERTADO EN EL DEPARTAMENTO "+d.getDeptNo());
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			System.out.println(e.getMessage());
			System.out.println("EMPLEADO NO INSERTADO");
		} catch (org.hibernate.exception.GenericJDBCException e1) {
			System.out.println(e1.getMessage());
			System.out.println("EMPLEADO NO INSERTADO");
		} catch (java.lang.IllegalStateException e2) {
			System.out.println(e2.getMessage());
			System.out.println("EMPLEADO NO INSERTADO");
		}
		session.close();

	}
	
	private static void cargarDepartGet(int nu) {
		Session session = factori.openSession();
		Departamentos dep = (Departamentos) session.get(Departamentos.class, nu);
		if (dep == null) {
			System.out.println("El departamento no existe "+nu);
		} else {
			System.out.println("Nombre Dep:" + dep.getDnombre());
			System.out.println("Localidad:" + dep.getLoc());
			
			Set<Empleados>listaemple=dep.getEmpleadoses();
			System.out.println("Numero de empleados: "+listaemple.size());
			for(Empleados e:listaemple) {
				System.out.println(e.getApellido()+" * "+e.getSalario());
			}
		}
		session.close();
	}
}