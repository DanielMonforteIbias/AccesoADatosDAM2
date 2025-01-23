package clases;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

public class Principal {
	private static Connection conexion = Conexion.getOracle("PROYECTOS", "proyectos");

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		boolean salir = false;
		do {
			mostrarMenu();
			int opcion = s.nextInt();
			switch (opcion) {
			case 1:
				crearBD();
				break;
			case 2:
				listarProyecto(1); //Existe y tiene participantes
				System.out.println();
				listarProyecto(15); //Existe y no participantes
				System.out.println();
				listarProyecto(999); //No existe
				break;
			case 3:
				break;
			case 0:
				salir = true;
				break;
			default:
				System.out.println("Opcion incorrecta");
				break;
			}
		} while (!salir);
		System.out.println("FIN");
		s.close();
	}

	public static void mostrarMenu() {
		System.out.println();
		System.out.println("OPERACIONES PROYECTOS");
		System.out.println("1. Crear BD");
		System.out.println("2. Listar un proyecto");
		System.out.println("3. Insertar participacion");
		System.out.println("0. Salir");
		System.out.println();
	}

	public static void crearBD() {
		ODB odb = ODBFactory.open("proyectos.dat");// Abrir BD
		try {
			//VARIABLES PARA OBTENER LOS PROYECTOS DE LA BD DE ORACLE
			//Proyectos
			String sqlProyectos = "select * from proyectos";
			PreparedStatement sentencia = conexion.prepareStatement(sqlProyectos);
			ResultSet resulProyectos = sentencia.executeQuery();
			
			//Estudiantes
			String sqlEstudiantes = "select * from estudiantes";
			sentencia = conexion.prepareStatement(sqlEstudiantes);
			ResultSet resulEstudiantes = sentencia.executeQuery();
			
			//Participa
			String sqlParticipa = "select * from participa";
			sentencia = conexion.prepareStatement(sqlParticipa);
			ResultSet resulParticipa = sentencia.executeQuery();
			//Mientras haya registros en el resultado de proyectos
			while (resulProyectos.next()) { 
				int codigoProyecto = resulProyectos.getInt(1);
				Objects<Proyectos> proyectos = odb.getObjects(new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", codigoProyecto))); //Obtenemos los proyectos que tengan el codigo de proyecto de la participacion (habrá 1 como mucho)
				if (proyectos.size()==0) { //Si aun no existe un proyecto con ese codigo
					String nombre = resulProyectos.getString(2);
					Date fechaInicio = resulProyectos.getDate(3);
					Date fechaFin = resulProyectos.getDate(4);
					float presupuesto = resulProyectos.getFloat(5);
					float extraAportacion = resulProyectos.getFloat(6);
					ArrayList<Participa> participaciones=new ArrayList<Participa>();
					odb.store(new Proyectos(codigoProyecto, nombre, fechaInicio, fechaFin, presupuesto, extraAportacion,participaciones)); //Añadimos el proyecto a la base de datos
				}
			}
				
			
			//Mientras haya registros en el resultado de estudiantes
			while (resulEstudiantes.next()) {
				//Obtenemos los datos del estudiante
				int codigoEstudiante = resulEstudiantes.getInt(1);
				Objects<Estudiantes> estudiantes = odb.getObjects(new CriteriaQuery(Estudiantes.class, Where.equal("codestudiante", codigoEstudiante)));//Obtenemos los estudiantes que tengan el codigo de estudiante de la participacion (habrá 1 como mucho)
				if (estudiantes.size() == 0) {//Si aun no existe un estudiante con ese codigo, lo guardamos
					String nombre = resulEstudiantes.getString(2);
					String direccion = resulEstudiantes.getString(3);
					String tlfn = resulEstudiantes.getString(4);
					Date fechaAlta = resulEstudiantes.getDate(5);
					ArrayList<Participa>participantes=new ArrayList<Participa>(); //La dejamos vacia de momento
					odb.store(new Estudiantes(codigoEstudiante, nombre, direccion, tlfn, fechaAlta,participantes)); //Añadimos el estudiante a la base de datos
				}
			}
			
			
			//Mientras haya registros en el resultado de participa
			while (resulParticipa.next()) {
				//Obtenemos los datos del registro
				int codigoParticipacion = resulParticipa.getInt(1);
				Objects<Participa> participaciones = odb.getObjects(new CriteriaQuery(Participa.class, Where.equal("codparticipacion", codigoParticipacion)));//Obtenemos los estudiantes que tengan el codigo de estudiante de la participacion (habrá 1 como mucho)
				if (participaciones.size()==0) {//Si aun no existe un participante con ese codigo, la guardamos 
					int codigoEstudiante=resulParticipa.getInt(2);
					int codigoProyecto=resulParticipa.getInt(3);
					String tipoParticipacion = resulParticipa.getString(4);
					int numAportaciones = resulParticipa.getInt(5);
					Estudiantes estudiante=null; //Creamos un objeto estudiante, de momento a null
					Proyectos proyecto=null; //Creamos un objeto proyecto, de momento a null
					Objects<Proyectos> proyectos = odb.getObjects(new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", codigoProyecto))); //Obtenemos los proyectos que tengan el codigo de proyecto de la participacion (habrá 1 como mucho)
					if (proyectos.size() > 0) proyecto=proyectos.getFirst(); //Si existe, lo guardamos
					Objects<Estudiantes> estudiantes = odb.getObjects(new CriteriaQuery(Estudiantes.class, Where.equal("codestudiante", codigoEstudiante)));//Obtenemos los estudiantes que tengan el codigo de estudiante de la participacion (habrá 1 como mucho)
					if (estudiantes.size() > 0) estudiante=estudiantes.getFirst(); //Si existe, lo guardamos
					if(estudiante!=null && proyecto!=null) { //Si existen el estudiante y el proyecto, i
						Participa p=new Participa(codigoParticipacion, estudiante,proyecto, tipoParticipacion,numAportaciones); 
						odb.store(p); //Insertamos la participacion
						//Añadimos la participacion a la lista de participaciones del estudiante y el proyecto
						estudiante.getParticipaen().add(p);
						proyecto.getParticipantes().add(p);
						//Actualizamos el estudiante y el proyecto en la base de datos
						odb.store(estudiante);
						odb.store(proyecto);
					}
					else System.out.println("Error en el registro "+resulParticipa.getRow()+", no se inserta");
				}
			}
			
			odb.commit();
			resulParticipa.close();
			resulEstudiantes.close();
			resulProyectos.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Base de datos creada");
		odb.close();
	}

	public static void listarProyecto(int codigoProyecto) {
		ODB odb = ODBFactory.open("proyectos.dat");// Abrir BD
		Objects<Proyectos> objects = odb.getObjects(new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", codigoProyecto)));
		if (objects.size() > 0) {
			Proyectos proyecto = objects.getFirst();
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("Codigo de proyecto: " + proyecto.getCodigoproyecto() + "   Nombre: " + proyecto.getNombre());
			System.out.println("Fecha inicio: " + proyecto.getFechainicio() + "   Fecha fin: " + proyecto.getFechafin());
			System.out.println("Presupuesto: " + proyecto.getPresupuesto() + "   Extraaportacion: "+ proyecto.getExtraaportacion());
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("Participantes en el proyecto: ");
			System.out.println("---------------------------------------------");
			if (proyecto.getParticipantes().size() > 0) {
				System.out.printf("%16s %13s %30s %20s %20s %10s %n", "CODPARTICIPACION", "CODESTUDIANTE","NOMBREESTUDIANTE", "TIPOAPORTACION", "NUMAPORTACIONES", "IMPORTE");
				System.out.printf("%16s %13s %30s %20s %20s %10s%n", "----------------", "-------------","------------------------------", "--------------------", "--------------------", "----------");
				int totalNumAportaciones = 0;
				double totalImporte = 0;
				for (Participa p : proyecto.getParticipantes()) {
					Estudiantes e = p.getEstudiante();
					double importe = p.getNumaportaciones() * proyecto.getExtraaportacion();
					System.out.printf("%16s %13s %30s %20s %20s %10s %n", p.getCodparticipacion(), e.getCodestudiante(),
							e.getNombre(), p.getTipoparticipacion(), p.getNumaportaciones(), importe);
					totalNumAportaciones += p.getNumaportaciones();
					totalImporte += importe;
				}
				System.out.printf("%16s %13s %30s %20s %20s %10s%n", "----------------", "-------------","------------------------------", "--------------------", "--------------------", "----------");
				System.out.printf("%16s %13s %30s %20s %20s %10s %n", "TOTALES:", "", "", "", totalNumAportaciones,
						totalImporte);
			} else
				System.out.println("(No hay participantes)");
		} else
			System.out.println("No existe proyecto con codigo " + codigoProyecto);
		odb.close();
	}
	
	public static void insertarParticipacion(int codEstudiante, int codProyecto, String tipoParticipacion, int numParticipaciones) {
		
	}
}