package principal;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import clases.*;

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
					consulta1();
					System.out.println();
					consulta2();
					System.out.println();
					consulta3();
					System.out.println();
					consulta4();
					System.out.println();
					consulta5();
					break;
				case 2:
					break;
				case 3:
					visualizarReservasPorCliente();
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
		System.out.println("1 - Consultas");
		System.out.println("2 - Insertar datos en reservas");
		System.out.println("3 - Visualizar reservas por cliente");
		System.out.println("0 - Salir");
	}
	
	private static void consulta1() {
		Session session=factory.openSession();
		String hql="select c.codigocliente, c.nombrecliente, r.codreserva, h.numhabitacion \r\n"
				+ "from T3clientes c join c.t3reservases r join r.t3habitaciones h\r\n"
				+ "where c.pais='ESPAÑA' and h.t3tiposhabitaciones.tipo='Twin'";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.println("CONSULTA 1");
		System.out.printf("%13s %30s %12s %13s %n","CODIGOCLIENTE","NOMBRE CLIENTE","CODRESERVA","NUMHABITACION");
		System.out.printf("%13s %30s %12s %13s %n","-------------","------------------------------","------------","-------------");
		for (int i = 0; i < datos.size(); i++) {
			Object[] filaActual = (Object[]) datos.get(i); // Acceso a una fila
			System.out.printf("%13s %30s %12s %13s %n",filaActual[0],filaActual[1],filaActual[2],filaActual[3]);
		}
		
		session.close();
	}
	private static void consulta2() {
		Session session=factory.openSession();
		String hql="select th.tipo, th.descripciontexto, count(r.codreserva) \r\n"
				+ "from T3tiposhabitaciones th join th.t3habitacioneses h\r\n"
				+ "join h.t3reservases r\r\n"
				+ "group by th.tipo, th.descripciontexto";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.println("CONSULTA 2");
		System.out.printf("%13s %30s %20s %n","TIPO","DESCRIPCION","NUMERO HABITACIONES");
		System.out.printf("%13s %30s %20s %n","-------------","------------------------------","--------------------");
		for (int i = 0; i < datos.size(); i++) {
			Object[] filaActual = (Object[]) datos.get(i); // Acceso a una fila
			System.out.printf("%13s %30s %20s %n",filaActual[0],filaActual[1],filaActual[2]);
		}
		session.close();
	}
	private static void consulta3() {
		Session session=factory.openSession();
		String hql="select distinct h.numhabitacion, h.nombrehabitacion, th.tipo, count(r.codreserva)\r\n"
				+ "from T3habitaciones h join h.t3tiposhabitaciones th\r\n"
				+ "join h.t3reservases r join r.t3clientes c where c.pais='ESPAÑA'\r\n"
				+ "group by h.numhabitacion, h.nombrehabitacion, th.tipo\r\n"
				+ "order by h.numhabitacion";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.println("CONSULTA 3");
		System.out.printf("%13s %30s %20s %13s %n","NUM HABIT","NOMBRE","TIPO","NUM RESERVAS");
		System.out.printf("%13s %30s %20s %13s %n","-------------","------------------------------","--------------------","-------------");
		for (int i = 0; i < datos.size(); i++) {
			Object[] filaActual = (Object[]) datos.get(i); // Acceso a una fila
			System.out.printf("%13s %30s %20s %13s %n",filaActual[0],filaActual[1],filaActual[2],filaActual[3]);
		}
		session.close();
	}
	
	private static void consulta4() {
		Session session=factory.openSession();
		String hql="select substring(h.numhabitacion,1,2),count(numhabitacion),count(distinct h.t3tiposhabitaciones)\r\n"
				+ "from T3habitaciones h\r\n"
				+ "group by substring(h.numhabitacion,1,2)";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.println("CONSULTA 4");
		System.out.printf("%13s %20s %20s %n","PISO","Num habitaciones","Num Tipos");
		System.out.printf("%13s %30s %20s %n","-------------","------------------------------","--------------------");
		for (int i = 0; i < datos.size(); i++) {
			Object[] filaActual = (Object[]) datos.get(i); // Acceso a una fila
			System.out.printf("%13s %30s %20s %n",filaActual[0],filaActual[1],filaActual[2]);
		}
		session.close();
	}
	
	private static void consulta5() {
		Session session=factory.openSession();
		String hql="select r.codreserva, r.t3habitaciones.numhabitacion,\r\n"
				+ "r.t3clientes.codigocliente,r.t3clientes.nombrecliente\r\n"
				+ "from T3reservas r\r\n"
				+ "where r.camassupletorias=(select max(camassupletorias) from T3reservas)";
		Query cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.println("CONSULTA 5");
		System.out.printf("%13s %13s %13s %30s %n","Cod Reserva","Num Habit","Cod Cliente","Nombre cliente");
		System.out.printf("%13s %13s %13s %30s %n","-------------","-------------","-------------","------------------------------");
		for (int i = 0; i < datos.size(); i++) {
			Object[] filaActual = (Object[]) datos.get(i); // Acceso a una fila
			System.out.printf("%13s %13s %13s %30s %n",filaActual[0],filaActual[1],filaActual[2],filaActual[3]);
		}
		session.close();
	}
	
	private static void visualizarReservasPorCliente() {
		Session session=factory.openSession();
		String hql="from T3clientes c order by c.codigocliente";
		Query<T3clientes> cons = session.createQuery(hql,T3clientes.class);
		List<T3clientes> datos = cons.list();
		for(T3clientes c:datos) {
			System.out.println("Cod cliente: "+c.getCodigocliente()+"   Nombre: "+c.getNombrecliente()+"   Apellidos: "+c.getApellido());
			if(c.getT3reservases().size()>0) {
				double totalTotalSuple=0;
				double totalDias=0;
				double totalPrecio=0;
				double totalImporte=0;
				double totalImporDesc=0;
				double totalImporteTotal=0;
				double maximoImporteTotal=0;
				String reservaMaximoImporte="";
				System.out.printf("%7s %8s %8s %12s %12s %12s %4s %7s %7s %12s %12s %n","CODRES","NUMHABIT","CAMSUPL","FECHAENTRADA","FECHASALIDA","TOTALSUPLE","DIAS","PVPDIA","IMPORTE","IMPORDESC","IMPORTOTAL");
				System.out.printf("%7s %8s %8s %12s %12s %12s %4s %7s %7s %12s %12s %n","-------","--------","--------","------------","------------","------------","----","-------","-------","------------","------------");
				Set <T3reservas>reservas= c.getT3reservases();
				for(T3reservas r:reservas) {
					String hqlFecha="select fechasalida-fechaentrada from T3reservas where codreserva=:codreserva";
					Query<Duration>consultaDias = session.createQuery(hqlFecha,Duration.class);
					consultaDias.setParameter("codreserva",r.getCodreserva());
					int dias= (int) consultaDias.uniqueResult().toDays();
					double totalSuple=r.getCamassupletorias().intValue()*10;
					double precio=r.getT3habitaciones().getT3tiposhabitaciones().getPrecio();
					double importe=(totalSuple+precio)*dias;
					double imporDesc=(importe*r.getDescuento().intValue()/100);
					double imporTotal=importe-imporDesc;
					System.out.printf("%7s %8s %8s %12s %12s %12s %4s %7s %7s %12s %12s %n",r.getCodreserva(),r.getT3habitaciones().getNumhabitacion(),r.getCamassupletorias(),r.getFechaentrada(),r.getFechasalida(),
							totalSuple,dias,precio,importe,imporDesc,imporTotal);
					totalTotalSuple+=totalSuple;
					totalDias+=dias;
					totalPrecio+=precio;
					totalImporte+=importe;
					totalImporDesc+=imporDesc;
					totalImporteTotal+=imporTotal;
					if (imporTotal>maximoImporteTotal){
						maximoImporteTotal=imporTotal;
						reservaMaximoImporte=r.getCodreserva()+"";
					}
					else if (imporTotal==maximoImporteTotal){
						reservaMaximoImporte+="     "+r.getCodreserva();
					}
				}
				
				System.out.printf("%7s %8s %8s %12s %12s %12s %4s %7s %7s %12s %12s %n","-------","--------","--------","------------","------------","------------","----","-------","-------","------------","------------");
				System.out.printf("%7s %8s %8s %12s %12s %12s %4s %7s %7s %12s %12s %n","TOTALES","","","","",totalTotalSuple,totalDias,totalPrecio,totalImporte,totalImporDesc,totalImporteTotal);
				System.out.println("Numero de reserva con mas importe total: "+reservaMaximoImporte);
			}
			else System.out.println("CLIENTE SIN RESERVAS");
			System.out.println();
		}
		session.close();
	}
}