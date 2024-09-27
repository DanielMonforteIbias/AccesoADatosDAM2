package cursosAlumnosVer2;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Principal {
	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(CursosAlumnos.class);
			Unmarshaller unmars = context.createUnmarshaller();
	        CursosAlumnos cursosAlumnos=(CursosAlumnos) unmars.unmarshal(new File("cursosalumnosVer2.xml"));
	        ArrayList<Curso> cursos=cursosAlumnos.getCursos();
		    int totalCursos=0;
		    for(Curso c:cursos) {
		    	double mediaTotal=0;
		    	int numAlumnos=0;
		    	System.out.println("CURSO: "+c.getNombre());
		    	System.out.printf("%10s %-20s %10s %n"," ","NOMBRE", "NOTA MEDIA");
		    	System.out.printf("%10s %-20s %10s %n"," ","--------------------", "----------");
		    	for(Alumno a:c.getAlumnos()) {
		    		System.out.printf("%10s %20s %10s %n"," ",a.getNombre(), a.getNotaMedia());
		    		numAlumnos++;
		    		mediaTotal+=a.getNotaMedia();
		    	}
		    	if (numAlumnos>0) mediaTotal=(double)Math.round((mediaTotal/numAlumnos)*100)/100; //Calculamos la media solo si hay alumnos. Multiplicar y dividir por 100 en ese orden sirve para redondear con 2 decimales
		    	System.out.printf("%10s %-20s %10s %n"," ","--------------------", "----------");
		    	System.out.printf("%10s %-20s %10s %n"," ","MEDIA TOTAL", mediaTotal);
		    	System.out.println();
		    	totalCursos++;
		    }
		    System.out.println("TOTAL CURSOS: "+totalCursos);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
