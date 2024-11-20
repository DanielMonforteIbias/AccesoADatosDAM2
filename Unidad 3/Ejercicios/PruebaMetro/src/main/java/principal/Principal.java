package principal;

import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import clases.*;

public class Principal {
	private static SessionFactory factori;

	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);

		factori = Conexion.getSession(); // Creo la sessionFactory una única vez.

		System.out.println("----------------");
		verlinea(1); // de 1 a 7
		
		System.out.println("----------------");
		verlinea(5); // linea sin estaciones
		
		System.out.println("----------------");
		verlinea(50); // linea no existe
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

}
