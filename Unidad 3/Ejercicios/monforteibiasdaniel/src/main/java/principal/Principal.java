package principal;

import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import clases.*;

public class Principal {
	private static SessionFactory factory;
	//Nota: Se ha creado el usuario como CURSOS, en mayuscula, pues en minuscula no funciona. La clave es cursos
	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		
		factory=Conexion.getSession();
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		do {
			menu();
			int opcion=s.nextInt();
			switch(opcion) {
				case 1:
					listarAsignaturasPorCurso();
					break;
				case 2:
					calcularEstadisticas();
					break;
				case 3:
					añadirAlumnosNuevos();
					break;
				case 0:
					salir=true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		
		factory.close();
	}
	private static void menu() {
		System.out.println("Ejercicio realizado por Daniel Monforte");
		System.out.println("1 - Obtener asignaturas por curso");
		System.out.println("2 - Obtener estadisticas");
		System.out.println("3 - Añadir alumnos de NUEVOSALUMNOS a ALUMNOS");
		System.out.println("0 - Salir");
	}
	
	private static void listarAsignaturasPorCurso() {
		Session session=factory.openSession();
		Query<Cursos>q=session.createQuery("from Cursos",Cursos.class); //Obtenemos todos los cursos
		List<Cursos> listaCursos=q.getResultList();
		if(listaCursos.size()>0) { //Por si no existiesen cursos en la base de datos
			for(Cursos c:listaCursos) {
				System.out.println("CODCURSO: "+c.getCodcurso()+"    DENOMINACION: "+c.getDenominacion());
				System.out.println("PRECIO: "+c.getPrecio()+"     NIVEL: "+c.getNivel());
				System.out.println("Numero de alumnos: "+c.getAlumnoses().size()+"     Numero de asignaturas: "+c.getCursoasigs().size());
				System.out.println();
				if(c.getCursoasigs().size()>0) { //Si el curso tiene asignaturas
					System.out.printf("%10s %20s %15s %10s %10s %10s %10s %n","CODASIG","NOMBREASIG","PRECIOASIG","TIPOASIG","%INCREMENTO","NUM_ALUMNOS","TOTALASIG");
					System.out.printf("%10s %20s %15s %10s %10s %10s %10s %n","----------","--------------------","---------------","----------","----------","----------","----------");
					String hqlAsignaturas="from Asignaturas a join a.cursoasigs c where c.cursos.codcurso=:codcurso"; //Obtenemos las asignaturas del curso
					Query<Asignaturas>q2=session.createQuery(hqlAsignaturas,Asignaturas.class);
					q2.setParameter("codcurso", c.getCodcurso());
					List<Asignaturas>asignaturas=q2.list();
					int totalAlumnos=0;
					double totalAsigs=0;
					for(Asignaturas a:asignaturas) {
						String hqlNumAsignaturas="select count(m.alumnos) from Matriculas m join m.alumnos a join a.cursos c where c.codcurso=:codcurso and m.asignaturas.codasig=:codasig";
						Query<Long>q3=session.createQuery(hqlNumAsignaturas,Long.class);
						q3.setParameter("codcurso",c.getCodcurso());
						q3.setParameter("codasig", a.getCodasig());
						int numAsignaturas=(q3.uniqueResult()).intValue(); //Obtenemos el numero de asignaturas
						int porcentajeIncremento=calcularIncremento(a.getTipoasig()); //Obtenemos el porcentaje de incremento
						double incremento=(a.getPrecioasig().intValue()*((double)porcentajeIncremento/100)); //Calculamos el incremento segun el precio y el porcentaje
						double totalAsig=numAsignaturas*(a.getPrecioasig().intValue()+incremento); //Calculamos el total multiplicando el precio mas el incremento por el numero de alumnos
						System.out.printf("%10s %20s %15s %10s %10s %10s %10s %n",a.getCodasig(),a.getNombreasig(),a.getPrecioasig(),a.getTipoasig(),incremento,numAsignaturas,totalAsig);
						totalAlumnos+=numAsignaturas;
						totalAsigs+=totalAsig;
					}
					System.out.println("-------------------------------------------------------------------------------------------------");
					System.out.printf("%10s %20s %15s %10s %10s %10s %10s %n","TOTALES:","","","","",totalAlumnos,totalAsigs);
				}
				else System.out.println("CURSO SIN ASIGNATURAS");
			}
		}
		else System.out.println("No existen cursos");
	}
	private static int calcularIncremento(char tipo) { //Metodo que devuelve el porcentaje de incremento segun el tipo recibido
		int incremento=0;
		switch(tipo) {
		case 'A':
			incremento=5;
			break;
		case 'B':
			incremento=6;
			break;
		case 'C':
			incremento=8;
			break;
		case 'D':
			incremento=10;
			break;
		default: //Por si existiese otro tipo que no sea de los contemplados anteriormente
			incremento=0;
			break;
		}
		return incremento;
	}
	
	private static void calcularEstadisticas() {
		Session session=factory.openSession();
		System.out.println("ESTADISTICA 1");
		String hqlEstadistica1="select a.codasig, a.nombreasig, count(m.alumnos) from Asignaturas a join a.matriculases m group by a.codasig, a.nombreasig having count(m.alumnos)=(select max(count(m2.alumnos)) from Asignaturas a2 join a2.matriculases m2 group by a2)";
		Query<Object>q1=session.createQuery(hqlEstadistica1,Object.class);
		List<Object>lista=q1.list();
		String codMax="", nombreMax="", numMax="";
		for(int i=0;i<lista.size();i++) {
			Object[]filaActual=(Object[]) lista.get(i);
			codMax+=filaActual[0]+"   ";
			nombreMax+=filaActual[1]+"   ";
			numMax=filaActual[2]+"";
		}
		System.out.println("Codigo asignatura/s con mas alumnos: "+codMax);
		System.out.println("Nombre asignatura/s con mas alumnos: "+nombreMax);
		System.out.println("Numero alumnos asignatura/s con mas alumnos: "+numMax);
		System.out.println();
		System.out.println("ESTADISTICA 2");
		String hqlEstadistica2="select a.codalum, a.nombre, avg(m.notaasig) from Alumnos a join a.matriculases m group by a.codalum, a.nombre having avg(m.notaasig)=(select max(avg(m2.notaasig)) from Matriculas m2 group by m2.alumnos)";
		Query<Object>q2=session.createQuery(hqlEstadistica2,Object.class);
		List<Object>lista2=q2.list();
		String codMax2="", nombreMax2="", notaMax="";
		for(int i=0;i<lista2.size();i++) {
			Object[]filaActual=(Object[]) lista2.get(i);
			codMax2+=filaActual[0]+"   ";
			nombreMax2+=filaActual[1]+"   ";
			notaMax=filaActual[2]+"";
		}
		System.out.println("Codigo alumno/s con mas nota media: "+codMax2);
		System.out.println("Nombre alumno/s con mas nota media: "+nombreMax2);
		System.out.println("Nota media maxima: "+notaMax);
		session.close();
	}
	
	private static void añadirAlumnosNuevos() {
		Session session =factory.openSession();
		Transaction tx=session.beginTransaction();
		String hql="from Nuevosalumnos n order by codalum";
		Query<Nuevosalumnos>q=session.createQuery(hql,Nuevosalumnos.class);
		List<Nuevosalumnos>lista=q.list(); //Obtenemos los alumnos de la tabla Nuevosalumnos
		for(Nuevosalumnos a:lista) {
			if(!tx.isActive()) tx=session.beginTransaction(); //Volvemos a iniciar la transaccion, pues hacemos el commit dentro del bucle y se haran varios.
			boolean insertar=true;
			int codAlum=0,codRepresentante=0, codCurso=0;
			String hqlComprobarNombre="select count(*) from Alumnos a where a.nombre=:nombre"; //Consulta para comprobar si ya existe un alumno con ese nombre. Devuelve un contador
			Query<Long> qComprobarNombre=session.createQuery(hqlComprobarNombre,Long.class);
			qComprobarNombre.setParameter("nombre", a.getNombre());
			int contador=qComprobarNombre.uniqueResult().intValue();
			if(contador>0) { //Si el contador es mayor que 0, un alumno con ese nombre existe, asi que no se insertara de nuevo
				System.out.println("Alumno "+a.getNombre()+" no se inserta, el nombre ya existe");
				insertar=false;
			}
			if(insertar) { //Si se va a insertar, es decir, el alumno no esta repetido
				Alumnos alumnoComprobar=session.get(Alumnos.class, a.getCodalum()); ;//Comprobamos si existe un alumno con ese codigo
				if(alumnoComprobar!=null) { //Si el alumno ya existe en alumnos
					String hqlMax="select max(a.codalum)+1 from Alumnos a"; //Obtenemos el maximo codigo existente +1
					Query<BigInteger>qMaxCodigo=session.createQuery(hqlMax,BigInteger.class);
					codAlum=((BigInteger)qMaxCodigo.uniqueResult()).intValue(); //El codigo de alumno sera el obtenido
				}
				else { //Si no existe un alumno con ese codigo
					codAlum=a.getCodalum().intValue(); //El codigo de alumno sera el que tenia en la tabla Nuevosalumnos
				}
				if(a.getPoblacion().equalsIgnoreCase("Talavera")) { //Si es de Talavera
					codRepresentante=1; //Representante con codigo 1
					codCurso=1; //Curso con codigo 1
				}
				else if(a.getPoblacion().equalsIgnoreCase("Toledo")) { //Si no, si es de Toledo
					codRepresentante=8; //Representante con codigo 8
					codCurso=4; //Curso con codigo 4
				}
				else { //Si no
					codRepresentante=11; //Representante con codigo 11
					codCurso=5; //Curso con codigo 5
				}
				Alumnos alumno=new Alumnos(); //Alumno a insertar
				//Le damos al alumno a insertar los valores
				alumno.setCodalum(BigInteger.valueOf(codAlum));
				alumno.setNombre(a.getNombre());
				alumno.setDireccion(a.getDireccion());
				alumno.setPoblacion(a.getPoblacion());
				alumno.setTelef(a.getTelef());
				Alumnos representante=session.get(Alumnos.class, codRepresentante);
				representante.getAlumnoses().add(alumno); //Añadimos el alumno al set de su representante
				Cursos curso=session.get(Cursos.class, codCurso);
				curso.getAlumnoses().add(alumno); //Añadimos el alumno al set de su curso
				alumno.setAlumnos(representante); //Añadimos el representante al objeto alumno de nuestro alumno, lo que hace que salga el codigo de representante en la base de datos
				alumno.setCursos(curso); //Añadimos el curso al objeto curso de nuestro alumno, lo que hace que salga el codigo de curso en la base de datos
				session.persist(alumno);
				System.out.println("Alumno "+alumno.getNombre()+" con codigo "+alumno.getCodalum()+" insertado");
				tx.commit(); //Es necesario hacer el commit dentro del bucle para que los alumnos no repitan el codigo maximo de uno ya insertado. Si no, podrian tener varios el codigo 12
			}
		}
		session.close();
	}
}