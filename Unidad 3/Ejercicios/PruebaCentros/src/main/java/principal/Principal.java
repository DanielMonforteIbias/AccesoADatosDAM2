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
		
		factori=Conexion.getSession();
		
		verDatosCentro(1000);
		verDatosCentro(1111);
		verDatosCentro(1050);
	}
	
	public static void verDatosCentro(int cod) {
		Session session=factori.openSession();
		C1Centros cen=session.get(C1Centros.class, cod);
		if(cen==null) {
			System.out.println("Cod Centro no existe: "+cod);
		}
		else {
			System.out.println("Cod Centro: "+cen.getCodCentro()+" Nombre: "+cen.getNomCentro());
			Set<C1Profesores>lista=cen.getC1Profesoreses();
			if(lista.size()>0) {
				mostrarDatosProfesoresCentro(lista);
			}
			else System.out.println("Centro sin profesores");
		}
		session.close();
	}
	
	public static void mostrarDatosProfesoresCentro(Set<C1Profesores>lista) {
		System.out.printf("%5s %-30s %-30s %-30s %-20s %n","Cod","NombreProfesor","NombreEspecialidad","Nombre Jefe","NumAsig que imparte");
		System.out.printf("%5s %-30s %-30s %-30s %-20s %n","-----","------------------------------","------------------------------","------------------------------","--------------------");
		for(C1Profesores p:lista) {
			String jefe="NO TIENE";
			String espe="NO TIENE";
			if(p.getC1Especialidad()!=null) espe=p.getC1Especialidad().getNombreEspe();
			if(p.getC1Profesores()!=null) jefe=p.getC1Profesores().getNombreApe();
			System.out.printf("%5s %-30s %-30s %-30s %-20s %n",p.getCodProf(),p.getNombreApe(),espe,jefe,p.getC1Asignaturases().size());
		}
	}
}
