package principal;

import java.util.ArrayList;
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

		factori = Conexion.getSession(); // Creo la sessionFactory una única vez.

//		System.out.println("----------------");
//		verlinea(1); // de 1 a 7
//		
//		System.out.println("----------------");
//		verlinea(5); // linea sin estaciones
//		
//		System.out.println("----------------");
//		verlinea(50); // linea no existe
//		
//		
//		actualizarOrdenLineaEstacion(1,1,14); //Existe
//		actualizarOrdenLineaEstacion(2,1,14); //No existe
		
//		verAccesosEstacion(1);
//		verAccesosEstacion(21);
//		verAccesosEstacion(210);
//		
//		List<String>tipos=new ArrayList<>();
//		tipos.add("SERIE 3000");
//		tipos.add("SERIE 8400");
//		verTrenesPorTipo(tipos);
//		
//		listarLineasEstacionesAccesos();
		
		verAccesosPorEstacion();
		
		verTiposTrenes();
		
		insertarYActualizarTrenesDeNuevos();
		factori.close();
	}

	private static void verlinea(int id) {
		Session session=factori.openSession();
		TLineas linea=session.get(TLineas.class, id);
		if(linea==null) {
			System.out.println("Linea no existe: "+id);
		}
		else {
			System.out.println("COD LINEA: "+linea.getCodLinea()+" NOMBRE: "+linea.getNombre());
			Set<TLineaEstacion>listaEstaciones=linea.getTLineaEstacions();
			if(listaEstaciones.size()>0) {
				System.out.println("Estaciones de la línea:");
				System.out.printf("    %-10s %-50s %-50s %n","CODIGO","NOMBRE","DIRECCION");
				System.out.printf("    %-10s %-50s %-50s %n","---------","--------------------------------------------------","--------------------------------------------------");
				for(TLineaEstacion t:listaEstaciones) {
					System.out.printf("    %10s %-50s %-50s %n",t.getTEstaciones().getCodEstacion() , t.getTEstaciones().getNombre() , t.getTEstaciones().getDireccion());
							 
				}
			}
			Set<TTrenes>listaTrenes=linea.getTTreneses();
			if(listaTrenes.size()>0) {
				System.out.println("Trenes de la línea:");
				System.out.printf("    %-10s %-50s %-20s %-10s %-50s%n","CODIGO","NOMBRE","TIPO","CODCOCHERA","NOMBRE_COCHERA");
				System.out.printf("    %-10s %-50s %-20s %-10s %-50s%n","----------","--------------------------------------------------","--------------------","----------","--------------------------------------------------");
				for(TTrenes t:listaTrenes) {
					System.out.printf("    %10s %-50s %-20s %-10s %-50s%n",t.getCodTren(), t.getNombre(), t.getTipo(),t.getTCocheras().getCodCochera(),t.getTCocheras().getNombre());	 
				}
			}
		}
		session.close();
	}
	//Metodo que reciba un codigo de tren, un codigo de linea y un orden, y actualice el orden para esa linea y esa estacion
	//Comprobar que existe esa linea estacion antes de actualizar (que existen juntos, no por separado)
	private static void actualizarOrdenLineaEstacion(int codLinea, int codEstacion, int orden) {
		Session session=factori.openSession();
		TLineaEstacion lineaEstacion=session.get(TLineaEstacion.class,new TLineaEstacionId(codLinea,codEstacion)); //Si el ID de la clase es un objeto hay que pasarle como clave ese objeto
		if(lineaEstacion!=null) {
			Transaction tx=session.beginTransaction();
			lineaEstacion.setOrden(orden);
			session.merge(lineaEstacion);
			tx.commit();
			
			System.out.println("Linea-Estacion ("+codLinea+","+codEstacion+") actualizado. Orden nuevo: "+orden);
		}
		else System.out.println("Linea-Estacion no existe: ("+codLinea+", "+codEstacion+")");
		session.close();
	}
	private static void verAccesosEstacion(int codEstacion) {
		Session session=factori.openSession();
		TEstaciones est=session.get(TEstaciones.class, codEstacion);
		if(est!=null) {
			String con="from TAccesos e where e.TEstaciones.codEstacion=:idestacion order by e.codAcceso";
			Query q=session.createQuery(con,TAccesos.class);
			q.setParameter("idestacion",codEstacion);
			List<TAccesos>lista=q.list();
			if(lista.size()>0) {
				System.out.println("Accesos a la estacion con codigo: "+codEstacion);
				System.out.printf("%15s %20s %n","COD ACCESO", "DESCRIPCION");
				System.out.printf("%15s %20s %n","---------------", "--------------------");
				for(TAccesos a:lista) {
					System.out.printf("%15s %20s %n",a.getCodAcceso(),a.getDescripcion());
				}
			}
			else System.out.println("La estacion no tiene accesos: "+codEstacion);
		}
		else System.out.println("La estacion no existe: "+codEstacion);
	}
	private static void verTrenesPorTipo(List<String>tipos) {
		Session session=factori.openSession();
		String con="from TTrenes e where e.tipo in (:lista)";
		Query q=session.createQuery(con,TTrenes.class);
		q.setParameterList("lista",tipos);
		List<TTrenes>lista=q.list();
		System.out.println("Trenes de los tipos: "+tipos.toString());
		for(TTrenes t:lista) {
			System.out.printf("%5s %20s %15s %n",t.getCodTren(),t.getNombre(),t.getTipo());
		}
		
		session.close();
	}
	private static void listarLineasEstacionesAccesos() {
		Session session=factori.openSession();
		String con="from TLineas T left join TLineaEstacions LT left join LT.TEstaciones.TAccesoses TA order by T.codLinea";
		Query q=session.createQuery(con,Object.class);
		List<Object[]>datos=q.list();
		System.out.printf("%6s %-30s %6s %-30s %6s %-30s %n","CODLIN", "NOMBRELIN","CODEST","NOMBREESTACION","CODACC","DESCR.ACCESO");
		System.out.printf("%6s %-30s %6s %-30s %6s %-30s %n","------", "------------------------------","------","------------------------------","------","------------------------------");
		for(Object linea:datos) {
			Object[] l=(Object[])linea;
			TLineas lin=(TLineas)l[0];
			TLineaEstacion liEst=(TLineaEstacion)l[1];
			TAccesos acc=(TAccesos)l[3];
			String est="SIN ESTACIONES";
			String acceso="SIN ACCESOS";
			String codEs="";
			String codAc="";
			if(liEst!=null) {
				est=liEst.getTEstaciones().getNombre();
				codEs=liEst.getTEstaciones().getCodEstacion()+"";
			}
			if(acc!=null) {
				acceso=acc.getDescripcion();
				codAc=acc.getCodAcceso()+"";
			}
			System.out.printf("%6s %-30s %6s %-30s %6s %-30s %n",lin.getCodLinea(),lin.getNombre(),codEs,est,codAc,acceso);
		}
		session.close();
	}
	
	private static void verAccesosPorEstacion() {
		Session session=factori.openSession();
		String con="select new clases.AccesosPorEstacion(e.codEstacion, e.nombre, e.direccion, count(a)) from TEstaciones e left join e.TAccesoses a group by e.codEstacion, e.nombre, e.direccion order by  e.codEstacion";
		Query q=session.createQuery(con,AccesosPorEstacion.class);
		List<AccesosPorEstacion>lista=q.list();
		System.out.printf("%6s %-30s %-30s %6s %n","CODEST", "NOMBRE","DIRECCION","NUMACC");
		System.out.printf("%6s %-30s %-30s %6s %n","------", "------------------------------", "------------------------------","------");
		int contador=0, sumaAccesos=0, maxAccesos=0;
		String estacionMaxAccesos="";
		for(AccesosPorEstacion a:lista) {
			System.out.printf("%6s %-30s %-30s %6s %n",a.getCodEstacion(),a.getNombre(),a.getDireccion(),a.getContador());
			contador++;
			sumaAccesos+=a.getContador();
			if(a.getContador()>maxAccesos) {
				maxAccesos=(int) a.getContador();
				estacionMaxAccesos=a.getNombre();
			}
			else if (a.getContador()==maxAccesos) estacionMaxAccesos+="      "+a.getNombre();
		}
		System.out.printf("%6s %-30s %-30s %6s %n","------", "------------------------------", "------------------------------","------");
		System.out.println("Nombre estacion/estaciones con mas accesos: "+estacionMaxAccesos);
		System.out.println("Media de accesos por estacion: "+((float)sumaAccesos/contador));
		session.close();
	}
	private static void verTiposTrenes() {
		Session session = factori.openSession();
		Query cons = session.createQuery("select  t.tipo, count(*) from TTrenes t group by t.tipo order by t.tipo",Object.class);
		System.out.printf("%-30s %-10s %n", "TIPO", "NUM TRENES");
		System.out.printf("%-30s %-10s %n", "------------------------------", "----------");
		List filas = cons.list();
		int contador=0, sumaTrenes=0;
		long maxTrenes=0;
		String tipoMaxTrenes="";
		for (int i = 0; i < filas.size(); i++) {
			Object[] filaActual = (Object[]) filas.get(i); // Acceso a una fila
			System.out.printf("%-30s %-10s %n",filaActual[0],filaActual[1]);
			contador++;
			sumaTrenes+=(long)filaActual[1];
			if((long)filaActual[1]>maxTrenes) {
				maxTrenes=(long)filaActual[1];
				tipoMaxTrenes=filaActual[0]+"";
			}
			else if((long)filaActual[1]==maxTrenes) {
				tipoMaxTrenes+="      "+filaActual[0];
			}
		}
		System.out.println("Media de trenes por tipo: "+((float)sumaTrenes/contador));
	}
	
	//SIN INSERT
	private static void insertarYActualizarTrenesDeNuevos() {
		Session session =factori.openSession();
		
		String hql="from TNuevosTrenes n order by n.codTren";
		Query q=session.createQuery(hql,TNuevosTrenes.class);
		List<TNuevosTrenes>lista=q.list();
		Transaction tx=session.beginTransaction();
		for(TNuevosTrenes t:lista) {
			if(!tx.isActive()) tx=session.beginTransaction();
			TTrenes tren=session.get(TTrenes.class, t.getCodTren());
			TCocheras cochera=session.get(TCocheras.class, t.getCodCochera());
			TLineas linea=session.get(TLineas.class, t.getCodLinea());
			String mensaje="";
			boolean error=false;
			if(cochera==null) {
				error=true;
				mensaje+="Error, la cochera no existe: "+t.getCodCochera()+"\n";
			}
			if(linea==null) {
				error=true;
				mensaje+="Error, la linea no existe: "+t.getCodLinea()+"\n";
			}
			if(tren==null) { //Si no existe, es nuevo, se inserta
				if(!error) {
					TTrenes nuevo=new TTrenes();
					nuevo.setCodTren(t.getCodTren());
					nuevo.setNombre(t.getNombre());
					nuevo.setTipo(t.getTipo());
					nuevo.setTCocheras(cochera);
					nuevo.setTLineas(linea);
					session.persist(nuevo);
					tx.commit();
					mensaje+="Se ha insertado un tren nuevo: "+t.getCodTren()+"\n";
				}
				else mensaje+="Error, no se puede insertar nuevo tren: "+t.getCodTren()+"\n";
			}
			else { //Si ya existe, se actualiza el tren
				if(!error) {
					tren.setCodTren(t.getCodTren());
					tren.setNombre(t.getNombre());
					tren.setTipo(t.getTipo());
					tren.setTCocheras(cochera);
					tren.setTLineas(linea);
					session.merge(tren);
					tx.commit();
					mensaje+="Se ha actualizado el tren: "+t.getCodTren()+"\n";
				}
				else mensaje+="Error, no se puede actualizar el tren: "+t.getCodTren()+"\n";
			}
			System.out.println(mensaje);
		}
		session.close();
	}
	
	private static void insertarconinsert() {
		Session session = factori.openSession();
		String con = " INSERT into TTrenes  (codTren, nombre, tipo, TCocheras.codCochera, TLineas.codLinea ) select codTren, nombre, tipo, codCochera, codLinea from TNuevosTrenes ";
		Transaction tx = session.beginTransaction();
		try {
			Query cons = (Query) session.createMutationQuery(con);
			int filascreadas = cons.executeUpdate();
			tx.commit(); // valida la transacción
			System.out.printf("FILAS INSERTADAS: %d%n", filascreadas);
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			System.out.println(e.getErrorMessage());
			tx.commit(); 
		}
	}
}