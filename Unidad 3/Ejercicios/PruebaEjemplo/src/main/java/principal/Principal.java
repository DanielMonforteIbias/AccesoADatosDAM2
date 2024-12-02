package principal;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import clases.*;

public class Principal {
	private static SessionFactory factori;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);

		factori = Conexion.getSession(); // SesionFactory

//		insertarDepartamento();
//		insertarEmpleado();
//		
//		cargarDepartGet(10);
//		cargarDepartGet(100);
//		cargarDepartGet(61);
//		
//		
//		actualizarDeparEmpleado(1111,10); //Empleado no existe
//		actualizarDeparEmpleado(4455,99); //Dept no existe
//		actualizarDeparEmpleado(4455,30); //Correcta
//		
//		insertarEmpleadoAlSetDepartamento(999,4455); //Dept no existe
//		insertarEmpleadoAlSetDepartamento(30,445555); //Empleado no existe
//		insertarEmpleadoAlSetDepartamento(20,4455); //Correcta
		
		consultasObjetos();
		
		consultaTotales();
		
		consultaConObjetos();
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
	
	private static void actualizarDeparEmpleado(int idEmpleado, int idDepartamento) {
		Session session = factori.openSession();
		Empleados emple = (Empleados) session.get(Empleados.class, idEmpleado);
		if (emple == null) System.out.println("El Empleado no existe. No se puede actualizar: "+idEmpleado);
		else {
			Departamentos dep = (Departamentos) session.get(Departamentos.class, idDepartamento);
			if (dep == null) System.out.println("El departamento no existe. No se puede actualizar: "+idDepartamento);
				else {
					Transaction tx = session.beginTransaction();
					emple.setDepartamentos(dep);
					System.out.println("Empleado " + idEmpleado + " actualizado al departamento " + idDepartamento);
					// en desuso session.update(emple);
					session.merge(emple);
					tx.commit();
					}
			}
		session.close();
	}
	private static void insertarEmpleadoAlSetDepartamento(int idDepartamento, int idEmpleado) {
		Session session = factori.openSession();

		Departamentos dep = (Departamentos) session.get(Departamentos.class, idDepartamento);
		if (dep == null) {
			System.out.println("El departamento no existe. No se puede insertar: "+idDepartamento);
		} else {
			// compruebo empleado
			Empleados emple = (Empleados) session.get(Empleados.class, BigInteger.valueOf(idEmpleado));
			if (emple == null) {
				System.out.println("El Empleado no existe. No se puede insertar: "+idEmpleado);
			} else {
				// lo añado al set
				Transaction tx = session.beginTransaction();
				dep.getEmpleadoses().add(emple);
				System.out.println("Empleado " + idEmpleado + " añadido al departamento " + idDepartamento);
				//en desuso session.update(dep);
				session.merge(dep);
				tx.commit();
			}
		}
		session.close();
		//Si todo está bien y no se actualiza en la base de datos, cambiar de true a false la propiedad inverse de Departamentos.hbm.xml
	}
	private static void consultasObjetos() {
		Session session = factori.openSession();
		String hql="from Empleados e, Departamentos d where  e.departamentos.deptNo=d.deptNo order by e.apellido";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		for (int i = 1; i < datos.size(); i++) {
			Object[] par = (Object[]) datos.get(i);
			Empleados em = (Empleados) par[0]; // objeto empleado el primero
			Departamentos de = (Departamentos) par[1]; // objeto departamento el segundo
			System.out.println(em.getApellido() + "*" + em.getSalario() + "*" +de.getDnombre() + "*" + de.getLoc());
		}
		session.close();
	}
	
	private static void consultaConObjetos() {
		Session session = factori.openSession();
		Query cons = session.createQuery("select d.deptNo, count(em.empNo), coalesce(avg(em.salario),0), d.dnombre from Departamentos d left join d.empleadoses em group by d.deptNo,d.dnombre order by d.deptNo",Object.class);
		System.out.printf("%n%10s %-15s %14s %-14s", "NUMERO DEP", "NOMBRE", "SALARIO MEDIO", "NUM EMPLES");
		System.out.printf("%n%10s %-15s %14s %-14s", "----------", "---------------","--------------", "--------------");
		List filas = cons.list();
		for (int i = 0; i < filas.size(); i++) {
			Object[] filaActual = (Object[]) filas.get(i); // Acceso a una fila
			System.out.printf("%n%10s %-15s %14.2f %-14s", filaActual[0], filaActual[3], filaActual[2], filaActual[1]);
		}
	}

	
	private static void consultaTotales() {
		Session session = factori.openSession();
		Query cons4 = session.createQuery("select new clases.Totales(d.deptNo, count(em.empNo),coalesce(avg(em.salario),0), d.dnombre ) from Departamentos d left join d.empleadoses em group by  d.deptNo,d.dnombre order by d.deptNo",Totales.class);	
		System.out.printf("%n%10s %-15s %14s %-14s", "NUMERO DEP", "NOMBRE","SALARIO MEDIO", "NUM EMPLES");
		System.out.printf("%n%10s %-15s %14s %-14s", "----------", "---------------","--------------", "--------------");
		List<Totales> filas4 = cons4.list();
		for (int i = 0; i < filas4.size(); i++) {
			Totales tot = (Totales) filas4.get(i);
			System.out.printf("%n%10s %-15s %14.2f %-14s",tot.getNumero(),tot.getNombre() ,tot.getMedia(),tot.getCuenta()); //Para que salgan solo 2 decimales poner en el printf .(numero de decimales)f
		}
		System.out.printf("%n%10s %-15s %14s %-14s", "----------", "---------------","--------------", "--------------");
		session.close();
	}

}