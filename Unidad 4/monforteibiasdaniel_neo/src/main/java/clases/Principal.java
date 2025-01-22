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
				listarProyecto(1);
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
		System.out.println("OPERACIONES PROYECTOS");
		System.out.println("1. Crear BD");
		System.out.println("2. Listar un proyecto");
		System.out.println("3. Insertar participacion");
		System.out.println("0. Salir");
	}

	public static void crearBD() {
		ODB odb = ODBFactory.open("proyectos.dat");// Abrir BD
		try {
			String sql = "select * from proyectos";
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			ResultSet resul = sentencia.executeQuery();
			while (resul.next()) {
				int codigoProyecto = resul.getInt(1);
				String nombre = resul.getString(2);
				Date fechaInicio = resul.getDate(3);
				Date fechaFin = resul.getDate(4);
				float presupuesto = resul.getFloat(5);
				float extraAportacion = resul.getFloat(6);
				odb.store(new Proyectos(codigoProyecto, nombre, fechaInicio, fechaFin, presupuesto, extraAportacion,
						new ArrayList<Participa>()));
			}
			sql = "select * from estudiantes";
			sentencia = conexion.prepareStatement(sql);
			resul = sentencia.executeQuery();
			while (resul.next()) {
				int codigoEstudiante = resul.getInt(1);
				String nombre = resul.getString(2);
				String direccion = resul.getString(3);
				String tlfn = resul.getString(4);
				Date fechaAlta = resul.getDate(5);
				odb.store(new Estudiantes(codigoEstudiante, nombre, direccion, tlfn, fechaAlta,
						new ArrayList<Participa>()));
			}
			sql = "select * from participa";
			sentencia = conexion.prepareStatement(sql);
			resul = sentencia.executeQuery();
			while (resul.next()) {
				int codigoParticipacion = resul.getInt(1);
				String tipoParticipacion = resul.getString(4);
				int numAportaciones = resul.getInt(5);
				odb.store(new Participa(codigoParticipacion, new Estudiantes(), new Proyectos(), tipoParticipacion,
						numAportaciones));
			}
			odb.commit();
			resul.close();
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		odb.close();
	}

	public static void listarProyecto(int codigoProyecto) {
		ODB odb = ODBFactory.open("proyectos.dat");// Abrir BD
		Objects<Proyectos> objects = odb
				.getObjects(new CriteriaQuery(Proyectos.class, Where.equal("codigoproyecto", codigoProyecto)));
		if (objects.size() > 0) {
			Proyectos proyecto = objects.getFirst();
			System.out.println("--------------------------------------------------------------------------");
			System.out.println(
					"Codigo de proyecto: " + proyecto.getCodigoproyecto() + "   Nombre: " + proyecto.getNombre());
			System.out
					.println("Fecha inicio: " + proyecto.getFechainicio() + "   Fecha fin: " + proyecto.getFechafin());
			System.out.println("Presupuesto: " + proyecto.getPresupuesto() + "   Extraaportacion: "
					+ proyecto.getExtraaportacion());
			System.out.println("--------------------------------------------------------------------------");
			System.out.println("Participantes en el proyecto: ");
			System.out.println("---------------------------------------------");
			if (proyecto.getParticipantes().size() > 0) {
				System.out.printf("%16s %13s %30s %20s %20s %10s", "CODPARTICIPACION", "CODESTUDIANTE",
						"NOMBREESTUDIANTE", "TIPOAPORTACION", "NUMAPORTACIONES", "IMPORTE");
				System.out.printf("%16s %13s %30s %20s %20s %10s", "----------------", "-------------",
						"------------------------------", "--------------------", "--------------------", "----------");
				int totalNumAportaciones = 0;
				double totalImporte = 0;
				for (Participa p : proyecto.getParticipantes()) {
					Estudiantes e = p.getEstudiante();
					double importe = p.getNumaportaciones() * proyecto.getExtraaportacion();
					System.out.printf("%16s %13s %30s %20s %20s %10s", p.getCodparticipacion(), e.getCodestudiante(),
							e.getNombre(), p.getTipoparticipacion(), p.getNumaportaciones(), importe);
					totalNumAportaciones += p.getNumaportaciones();
					totalImporte += importe;
				}
				System.out.printf("%16s %13s %30s %20s %20s %10s", "TOTALES:", "", "", "", totalNumAportaciones,
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