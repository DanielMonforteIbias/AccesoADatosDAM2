package principal;

import java.util.List;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class Principal {
	private static SessionFactory factory;
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
					
					break;
				case 2:
					break;
				case 3:
					consulta1();
					System.out.println();
					break;
				case 0:
					salir=true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
	}
	
	private static void menu() {
		System.out.println("Elige una opcion");
		System.out.println("1 - Llenar RESUMEN_CAMISETAS");
		System.out.println("2 - Resumen ciclistas de un equipo");
		System.out.println("3 - Consultas");
		System.out.println("0 - Salir");
	}
	
	private static void consulta1() {
		Session session=factory.openSession();
		String hql="select distinct e.codigoetapa, e.km, e.pobsalida, e.pobllegada, e.ciclistas.nombreciclista\r\n"
				+ "from Etapas e join e.tramospuertoses\r\n"
				+ "where e.pobsalida=e.pobllegada \r\n"
				+ "order by e.codigoetapa";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.println("CONSULTA 1");
		System.out.printf("%13s %10s %30s %30s %30s %n","CODIGOETAPA","KM","POBSALIDA","POBLLEGADA","NOMBRECICLISTA");
		System.out.printf("%13s %10s %30s %30s %30s %n","-------------","----------","------------------------------","------------------------------","------------------------------");
		for (int i = 0; i < datos.size(); i++) {
			Object[] filaActual = (Object[]) datos.get(i); // Acceso a una fila
			System.out.printf("%13s %10s %30s %30s %30s %n",filaActual[0],filaActual[1],filaActual[2],filaActual[3],filaActual[4]);
		}
		session.close();
	}
}
