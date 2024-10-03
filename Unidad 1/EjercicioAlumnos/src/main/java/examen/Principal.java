package examen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Principal {
	static String ficheroAlumnos="Alumnos.dat";
	static String ficheroNotas="Notas.dat";
	static int filesize_alumnos=92;
	static int filesize_notas=48;
	public static void main(String[] args) throws IOException {
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		do {
			menu();
			int opcion=s.nextInt();
			switch(opcion) {
				case 1:
					listarAlumnos();
					break;
				case 2:
					listarNotas();
					break;
				case 3:
					actualizarAlumnos();
					break;
				case 4:
					crearAlumnosXml();
					break;
				case 5:
					salir=true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		System.out.println("FIN");
	}
	
	public static void menu() {
		System.out.println("Elige una opcion: ");
		System.out.println("1. Listar alumnos");
		System.out.println("2. Listar notas");
		System.out.println("3. Actualizar el fichero alumnos");
		System.out.println("4. Generar Alumnos.xml");
		System.out.println("5. Salir");
	}
	
	private static void listarAlumnos() throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroAlumnos, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS
			System.out.printf("%-8s %-20s %-20s %-8s %-8s %n","NUM ALUM", "NOMBRE","LOCALIDAD","NUM ASIG","NOTA MEDIA");
			System.out.printf("%-8s %-20s %-20s %-8s %-8s %n","--------", "--------------------","--------------------","--------","--------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				for(;;){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						String nombre="";
						for(int i=0;i<20;i++) {
							nombre+=file.readChar();
						}
						String loc="";
						for(int i=0;i<20;i++) {
							loc+=file.readChar();
						}
						int numAsig=file.readInt();
						float notaMedia=file.readFloat();
						System.out.printf("%-8s %-20s %-20s %-8s %-8s %n",codigo, nombre,loc,numAsig,notaMedia);
						contador++;
					}
					posicion= posicion + filesize_alumnos; // me posiciono para el sig empleado	  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void listarNotas() throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroNotas, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS
			System.out.printf("%-8s %-8s %-20s %-8s %n","REGIS","NUMALUM", "ASIGNATURA","NOTA");
			System.out.printf("%-8s %-8s %-20s %-8s %n","--------","--------", "--------------------","--------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				int regis=1;
				for(;;){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						String asignatura="";
						for(int i=0;i<20;i++) {
							asignatura+=file.readChar();
						}
						float nota=file.readFloat();
						System.out.printf("%-8s %-8s %-20s %-8s %n",regis, codigo, asignatura,nota);
						contador++;
					}
					posicion= posicion + filesize_notas; // me posiciono
					regis++;
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void actualizarAlumnos() throws IOException {
		//Recorremos el fichero de empleados secuencialmente y actualizamos de manera directa en departamentos
		RandomAccessFile file = new RandomAccessFile(ficheroNotas, "r");
		int  codAlum=0, posicion=0;
		posicion=0;  //para situarnos al principio
		for(;;){  //recorro el fichero
			file.seek(posicion); //nos posicionamos en posicion
			codAlum=file.readInt();   // obtengo id de empleado
			file.seek(posicion+4+40);
			float nota=file.readFloat();
			if (codAlum!=0) { //Si el id es distinto de 0, seguimos leyendo
				if (consultarAlumno(codAlum)) {
					actualizarAlumno(codAlum,nota);
			}
			
			//Si he recorrido todos los bytes salgo del for	 	  
			}
			posicion= posicion + filesize_notas; // me posiciono para el sig empleado
			if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
		   }//fin bucle for
		actualizarMediaAlumnos();
		file.close();  //cerrar fichero 
	}

	private static void actualizarAlumno(int codAlum, float nota) throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroAlumnos, "rw");
			int posicion = (codAlum - 1 ) * filesize_alumnos; //calculo donde empieza el registro 
			file.seek(posicion+4+40+40);
			int numAsigs=file.readInt()+1;
			float notamedia=(file.readFloat()+nota);
			file.seek(posicion+4+40+40);
			file.writeInt(numAsigs);
			file.writeFloat(notamedia);
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void actualizarMediaAlumnos() throws IOException {
		try {
			float notaMediaMaxima=0, notaMediaTotal=0;
			String alumnoMediaMaxima="";
			RandomAccessFile file = new RandomAccessFile(ficheroAlumnos, "rw");
			int posicion=0;  //para situarnos al principio
			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				int contadorAlumnos=0;
				for(;;){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int cod=file.readInt();
					if (cod!=0) {
						String nombre="";
						for(int i=0;i<20;i++) {
							nombre+=file.readChar();
						}
						file.seek(posicion+4+40+40); //nos posicionamos en posicion
						int numAsigs=file.readInt();
						float notaTotal=file.readFloat();
						float media=notaTotal/numAsigs;
						if (media>notaMediaMaxima) {
							notaMediaMaxima=media;
							alumnoMediaMaxima=nombre;
						}
						file.seek(posicion+4+40+40+4);
						file.writeFloat(media);
						notaMediaTotal+=media;
						contadorAlumnos++;
					}
					posicion= posicion + filesize_alumnos; // me posiciono para el sig empleado
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
			       notaMediaTotal=notaMediaTotal/contadorAlumnos;
				   listarAlumnos();
				   System.out.println("Alumno con nota media maxima: "+alumnoMediaMaxima);
				   System.out.println("Nota media total: "+notaMediaTotal);
				   file.close();  //cerrar fichero 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean consultarAlumno(int cod) throws IOException {
		boolean existe=false;
		 try {
				RandomAccessFile file = new RandomAccessFile(ficheroAlumnos, "r");
				
				int posicion = (cod - 1 ) * filesize_alumnos; //calculo donde empieza el registro 
				if(posicion >= file.length()) existe=false; //Si esta fuera del fichero, no existe
				 else{ //Si no esta fuera del fichero
					file.seek(posicion);//nos posicionamos 
					int codigo=file.readInt(); // obtengo id del departamento
					if (codigo==cod) existe=true; //Si los id coinciden existe
					file.close();
				    }  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		 return existe;
	}
	
	private static void crearAlumnosXml() throws IOException {
		Alumnos a=new Alumnos();
		ArrayList<Alumno> alumnos=cargarAlumnos(); //La lista se iguala a lo que devuelve el metodo que lee el fichero
		a.setAlumnos(alumnos);
		try {
			// Creamos el contexto indicando la clase raíz
			JAXBContext context = JAXBContext.newInstance(Alumnos.class);
			//Creamos el Marshaller, convierte el java bean en una cadena XML
			Marshaller m = context.createMarshaller();
		       //Formateamos el xml para que quede bien
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        // Lo visualizamos con system out
		        m.marshal(a, System.out);
		         // Escribimos en el archivo
		        m.marshal(a, new File("alumnos.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	}
	private static ArrayList<Alumno> cargarAlumnos() throws IOException { //Lee el fichero y carga los departamentos en la lista
		ArrayList<Alumno>alumnos=new ArrayList<Alumno>();
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroAlumnos, "r");
			int posicion=0;  //para situarnos al principio

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				for(;;){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						String nombre="";
						for(int i=0;i<20;i++) {
							nombre+=file.readChar();
						}
						nombre=nombre.trim(); //Quitamos los espacios al final, pues en el fichero del que leemos es de tamaño fijo
						String loc="";
						for(int i=0;i<20;i++) {
							loc+=file.readChar();
						}
						loc=loc.trim(); //Quitamos los espacios al final, pues en el fichero del que leemos es de tamaño fijo
						int numAsigs=file.readInt();
						float mediaNota=file.readFloat();
						ArrayList<Nota>notas=cargarNotas(codigo);
						alumnos.add(new Alumno(codigo,nombre,loc,numAsigs,mediaNota,notas)); //Creamos un departamento con los datos leidos y lo guardamos en la lista
					}
					posicion= posicion + filesize_alumnos;  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return alumnos; //Devolvemos la lista
	}
	private static ArrayList<Nota> cargarNotas(int codigo) throws IOException { //Lee el fichero y carga los departamentos en la lista
		ArrayList<Nota>notas=new ArrayList<Nota>();
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroNotas, "r");
			int posicion=0;  //para situarnos al principio

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				for(;;){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int cod=file.readInt();
					if (cod!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						if(cod==codigo) {
							String asignatura="";
							for(int i=0;i<20;i++) {
								asignatura+=file.readChar();
							}
							asignatura=asignatura.trim();
							float nota=file.readFloat();
							notas.add(new Nota(asignatura,nota));
						}
					}
					posicion= posicion + filesize_notas;  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return notas; //Devolvemos la lista
	}
	
}
