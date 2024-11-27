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
		
		verAccesosEstacion(1);
		verAccesosEstacion(21);
		verAccesosEstacion(210);
		
		List<String>tipos=new ArrayList<>();
		tipos.add("SERIE 3000");
		tipos.add("SERIE 8400");
		verTrenesPorTipo(tipos);
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
}
