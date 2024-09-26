package actividad1_7;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Principal {
	private static final String MIARCHIVO_XML = "./Librerias.xml";

	public static void main(String[] args) {
		crearXML();
		
		leerXML();
	}
	
	public static void crearXML() {
		ArrayList<Libro> libroLista = new ArrayList<Libro>();
        // Creamos dos libros y los añadimos
        Libro libro1 = new Libro("Entornos de Desarrollo", 
                 "Alicia Ramos","Garceta","978-84-1545-297-3" );
        libroLista.add(libro1);
       
        Libro libro2 = new Libro("Acceso a Datos","Maria Jesús Ramos",
                 "Garceta","978-84-1545-228-7" );
        libroLista.add(libro2);
 
        // Se crea La libreria 1 y se le asigna la lista de libros
        Libreria milibreria1 = new Libreria();
        milibreria1.setNombre("Prueba de libreria 1 JAXB");
        milibreria1.setLugar("Talavera, como no");
        milibreria1.setListaLibro(libroLista);
        
        libroLista = new ArrayList<Libro>();
        // Creamos dos libros y los añadimos
        Libro libro3 = new Libro("Programacion", 
                 "Aaaa","Garceta","978-84-1545-221-1" );
        libroLista.add(libro3);
       
        Libro libro4 = new Libro("Sistemas Informaticos","Maria José",
                 "Editoriaaal","978-84-1545-228-0" );
        libroLista.add(libro4);
        Libro libro5 = new Libro("Bases de Datos","Pepe",
                "Garceta","978-84-1545-222-2" );
       libroLista.add(libro5);
 
       
        // Se crea La libreria 2 y se le asigna la lista de libros
        Libreria milibreria2 = new Libreria();
        milibreria2.setNombre("Prueba de libreria 2 JAXB");
        milibreria2.setLugar("Madrid");
        milibreria2.setListaLibro(libroLista);
        
        ArrayList<Libreria>listaLibrerias=new ArrayList<Libreria>();
        listaLibrerias.add(milibreria1);
        listaLibrerias.add(milibreria2);
        
        //Creamos la clase que tiene la lista de librerias
        Librerias l=new Librerias();
        l.setLibrerias(listaLibrerias);
		try {
			// Creamos el contexto indicando la clase raíz
			JAXBContext context = JAXBContext.newInstance(Librerias.class);
			//Creamos el Marshaller, convierte el java bean en una cadena XML
			Marshaller m = context.createMarshaller();
		       //Formateamos el xml para que quede bien
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        // Lo visualizamos con system out
		        m.marshal(l, System.out);
		         // Escribimos en el archivo
		        m.marshal(l, new File(MIARCHIVO_XML));
		} catch (JAXBException e) {
			e.printStackTrace();
		}    

	}
	public static void leerXML() {
		// Visualizamos ahora los datos del documento XML creado
	     System.out.println("------------ Leo el XML ---------");
	     //Se crea Unmarshaller en el cotexto de la clase Libreria
	     
		try {
			JAXBContext context = JAXBContext.newInstance(Librerias.class);
			Unmarshaller unmars = context.createUnmarshaller();
	        Librerias l=(Librerias) unmars.unmarshal(new File(MIARCHIVO_XML));

		    ArrayList<Libreria>librerias=l.getLibrerias();
		    //Recorrer las librerias
		    for(Libreria lib: librerias) {
		    	ArrayList<Libro>libros=lib.getListaLibro();
		    	String lugar=lib.getLugar();
		    	String nombre=lib.getNombre();
		    	System.out.println("Nombre libreria: "+nombre+", Lugar: "+lugar);
		    	System.out.println("Numero de libros: "+libros.size());
		    	//Mostrar libros
		    	for(Libro libro:libros) {
		    		System.out.println("\tNombre: "+libro.getNombre()+", Autor: "+libro.getAutor());
		    	}
		    }
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
