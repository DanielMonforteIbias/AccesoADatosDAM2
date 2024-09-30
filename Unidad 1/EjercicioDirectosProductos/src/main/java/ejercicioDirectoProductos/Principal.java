package ejercicioDirectoProductos;

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
	private final int FILESIZE_PRODUCTOS=46; //Constante con la longitud de un registro de Productos.dat
	private final int FILESIZE_DATOSDEVENTAS=28; //Constante con la longitud de un registro de DatosdeVentas.dat
	private String ficheroProductos="Productos.dat"; //Nombre del fichero de productos
	private String ficheroVentas="DatosdeVentas.dat"; //Nombre del fichero de ventas
	
	
	/* ***************************************IMPORTANTE***************************************
	 * Hasta ahora se habia usado un bucle for(;;) para leer los ficheros, con un break para salir.
	 * Esto hace que, al ser un bcle infinito, Eclipse no detecte que el fichero se esta cerrando al salir de él
	 * 
	 * Para arreglar esto y quitar los warnings se ha reemplaado el for por un while, del que solo se sale cuando se cambia
	 * el valor de una variable boolean. En lugar de hacer break, se cambia el valor de esta variable
	 */
	
	public static void main(String[] args) throws IOException {
		Principal p=new Principal();
		Scanner s=new Scanner(System.in);
		boolean salir=false; //Variable que controlara la salida del bucle
		do {
			menu(); //Imprimimos el menu
			int opcion=s.nextInt(); //Leemos la opcion del usuario
			switch(opcion) {
				case 1:
					p.listarProductos();
					break;
				case 2:
					p.listarDatosVentas();
					break;
				case 3:
					p.actualizarExistencias();
					break;
				case 4:
					p.crearXmlProductos();
					break;
				case 5:
					p.crearXmlProductos2();
					break;
				case 6:
					salir=true; //La variable que controla la salida del do while pasa a ser true, por lo que saldremos
					break;
				default: //Si la opcion no coincide con ninguna otra
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		s.close();
		System.out.println("FIN");
	}
	
	

	private static void menu() {
		System.out.println("---------------------------------------------------");
		System.out.println("OPERACIONES CON PRODUCTOS");
		System.out.println("1. Ejercicio 1. Listar productos");
		System.out.println("2. Ejercicio 2. Listar datos ventas");
		System.out.println("3. Ejercicio 3. Actualizar existencias ");
		System.out.println("4. Ejercicio 4. Crear Productos.xml");
		System.out.println("5. Ejercicio 5. Crear Productos2.xml");
		System.out.println("6. Salir");
		System.out.println("---------------------------------------------------");
	}
	
	private void listarProductos() throws IOException { //Muestra los productos en consola
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroProductos, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS
			System.out.printf("%-10s %-20s %-10s %-10s %n","CODIGO", "NOMBRE","EXISTENCIAS","PVP");
			System.out.printf("%-10s %-20s %-10s %-10s %n","----------", "--------------------","----------","----------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
				while(seguirLeyendo){  //recorro el fichero mientras podamos seguir leyendo
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						String nombre="";
						for(int i=0;i<15;i++) {
							nombre+=file.readChar();
						}
						int existencias=file.readInt();
						double pvp=file.readDouble();
						System.out.printf("%10s %-20s %10s %10s %n",codigo, nombre,existencias,pvp);
						contador++;
					}
					posicion= posicion + FILESIZE_PRODUCTOS; // me posiciono para el sig empleado	  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())seguirLeyendo=false;//Cambiamos la variable para dejar de seguir leyendo
				   }//fin bucle for 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
			file.close();  //cerrar fichero 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void listarDatosVentas() throws IOException { //Muestra los datos de venta en consola
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroVentas, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS 
			System.out.printf("%-6s %-6s %-20s %n","CODIGO", "UNID. VEN","FECHA");
			System.out.printf("%-6s %-6s %-20s %n","------", "------","--------------------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
				while(seguirLeyendo){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						int uniVen=file.readInt();
						String fecha="";
						for(int i=0;i<10;i++) {
							fecha+=file.readChar();
						}
						System.out.printf("%-6s %-6s %-20s %n",codigo, uniVen,fecha);
						contador++;
					}
					posicion= posicion + FILESIZE_DATOSDEVENTAS; // me posiciono para el sig empleado	  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())seguirLeyendo=false; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private boolean consultarProducto(int codigo) throws IOException {
		boolean existe=false;
		 try {
				RandomAccessFile file = new RandomAccessFile(ficheroProductos, "r");
				int posicion = (codigo - 1 ) * FILESIZE_PRODUCTOS; //calculo donde empieza el registro 
				if(posicion >= file.length()) existe=false; //Si esta fuera del fichero, no existe
				 else{ //Si no esta fuera del fichero
					file.seek(posicion);//nos posicionamos 
					int cod=file.readInt(); // obtengo codigo del producto
					if (cod==codigo) existe=true; //Si los codigos coinciden existe
					file.close();
				    }  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		 return existe;
	}
	
	public void actualizarExistencias() throws IOException {
		//Recorremos el fichero de empleados secuencialmente y actualizamos de manera directa en productos
		RandomAccessFile fileDatosVentas = new RandomAccessFile(ficheroVentas, "r");
		int codigo=0, uniVen=0;
		int posicion=0;  //para situarnos al principio
		boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
		while(seguirLeyendo){  //recorro el fichero
			fileDatosVentas.seek(posicion); //nos posicionamos en posicion
			codigo=fileDatosVentas.readInt();   // obtengo codigo de la venta
			if (codigo!=0) { //Si el codigo es distinto de 0, seguimos leyendo
				if(codigo>99 || codigo<1) System.out.println("El codigo "+codigo+" no existe porque no esta entre 1 y 99"); //El enunciado dice que los codigos deben estar entre 1 y 99
				else { //Si cumple con las condiciones, leemos el resto
					uniVen=fileDatosVentas.readInt();
					//No hace falta leer la fecha porque no la usamos
					if (consultarProducto(codigo)) { //Si el producto existe en el fichero de productos
						actualizarProducto(codigo,uniVen); //Lo actualizamos
					}
					else System.out.println("El producto con el codigo "+codigo+" no existe, no se puede actualizar"); //Si no, imprimimos un mensaje
				}
			}
			posicion= posicion + FILESIZE_DATOSDEVENTAS; // me posiciono para el siguiente registro
			if (fileDatosVentas.getFilePointer()==fileDatosVentas.length() || posicion>=fileDatosVentas.length())seguirLeyendo=false; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
		   }//fin bucle for
		fileDatosVentas.close();  //cerrar fichero 
	}
	
	private void actualizarProducto(int codigo, int uniVen) throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroProductos, "rw");
			int posicion = (codigo - 1 ) * FILESIZE_PRODUCTOS; //calculo donde empieza el registro 
			file.seek(posicion+4+30); //Nos colocamos en la posicion, +4 del codigo +30 del nombre
			int numUnidades=file.readInt(); //Leemos las unidades actuales
			int numUnidadesActualizado=numUnidades-uniVen; //Calculamos las nuevas unidades restando las recibidas
			file.seek(posicion+4+30); //Nos volvemos a posicionar despues de leer los datos actuales
			file.writeInt(numUnidadesActualizado); //Grabamos el nuevo numero de unidades
			System.out.println("Producto con codigo "+codigo+" actualizado"); //Imprimimos el resultado
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//********************METODOS CORRESPONDIENTES AL EJERCICIO 4********************
	private void crearXmlProductos() throws IOException {
		Productos p=new Productos();
		ArrayList<Producto>productos=cargarProductos(); //La lista se iguala a lo que devuelve el metodo que lee el fichero
		p.setProductos(productos);;
		try {
			// Creamos el contexto indicando la clase raíz
			JAXBContext context = JAXBContext.newInstance(Productos.class);
			//Creamos el Marshaller, convierte el java bean en una cadena XML
			Marshaller m = context.createMarshaller();
		       //Formateamos el xml para que quede bien
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        // Lo visualizamos con system out
		        m.marshal(p, System.out);
		         // Escribimos en el archivo
		        m.marshal(p, new File("Productos.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	}
	
	private ArrayList<Producto> cargarProductos() throws IOException { //Lee el fichero y carga los productos en la lista que luego devuelve
		ArrayList<Producto>productos=new ArrayList<Producto>(); //Creamos la lista de productos
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroProductos, "r");
			int posicion=0;  //para situarnos al principio
			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
				while(seguirLeyendo){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el codigo no es 0, es decir, no es un hueco, leemos los datos
						String nombre="";
						for(int i=0;i<15;i++) {
							nombre+=file.readChar();
						}
						nombre=nombre.trim(); //Quitamos los espacios al final, pues en el fichero del que leemos es de tamaño fijo
						int existencias=file.readInt();
						double precio=file.readDouble();
						int unidadesVendidas=obtenerUnidadesVendidasProducto(codigo); //Las unidades vendidas vienen del fichero DatosVentas.dat
						double importe=unidadesVendidas*precio; //Calculamos el importe
						String estado=""; //El estado estara vacio en un principio
						int stock=existencias-unidadesVendidas; //Calculamos el stock para comprobar el estado
						if (stock<2) estado="A reponer"; //Si lo cumple, el estado sera "A reponer", si no, se queda vacio
						productos.add(new Producto(codigo,nombre,existencias,precio,unidadesVendidas,importe,estado)); //Creamos un producto y lo añadimos a la lista
					}
					posicion= posicion + FILESIZE_PRODUCTOS;  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())seguirLeyendo=false; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   }//fin bucle for 
			}
			file.close();  //cerrar fichero 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return productos; //Devolvemos la lista
	}

	private int obtenerUnidadesVendidasProducto(int codigo) throws IOException {
		int uniVenTotal=0; //Variable que devolveremos, acumulara las unidades vendidas de cada venta con el codigo del producto recibido
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroVentas, "r");
			int posicion=0;
			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
				while(seguirLeyendo){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int cod=file.readInt();
					if (cod==codigo) { //Si el codigo leido coincide con el que nos han pasado
						int uniVen=file.readInt();
						uniVenTotal+=uniVen; //Sumamos las unidades vendidas de esta venta al total
					}
					posicion= posicion + FILESIZE_DATOSDEVENTAS; // me posiciono para la siguiente venta
				    if (file.getFilePointer()==file.length() || posicion>=file.length())seguirLeyendo=false; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
			}
		   file.close();  //cerrar fichero 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return uniVenTotal; //Devolvemos la variable con el total de ventas
	}
	
	//********************METODOS CORRESPONDIENTES AL EJERCICIO 5********************
	private void crearXmlProductos2() throws IOException {
		Productos2 p=new Productos2();
		ArrayList<Producto2>productos=cargarProductos2(); //La lista se iguala a lo que devuelve el metodo que lee el fichero
		p.setProductos(productos);;
		try {
			// Creamos el contexto indicando la clase raíz
			JAXBContext context = JAXBContext.newInstance(Productos2.class);
			//Creamos el Marshaller, convierte el java bean en una cadena XML
			Marshaller m = context.createMarshaller();
		       //Formateamos el xml para que quede bien
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        // Lo visualizamos con system out
		        m.marshal(p, System.out);
		         // Escribimos en el archivo
		        m.marshal(p, new File("Productos2.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	}
	private ArrayList<Producto2> cargarProductos2() throws IOException { //Lee el fichero y carga los departamentos en la lista
		ArrayList<Producto2>productos=new ArrayList<Producto2>();
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroProductos, "r");
			int posicion=0;  //para situarnos al principio

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
				while(seguirLeyendo){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigo=file.readInt();
					if (codigo!=0) { //Si el codigo no es 0, es decir, no es un hueco, leemos los datos
						String nombre="";
						for(int i=0;i<15;i++) {
							nombre+=file.readChar();
						}
						nombre=nombre.trim(); //Quitamos los espacios al final, pues en el fichero del que leemos es de tamaño fijo
						int existencias=file.readInt();
						double precio=file.readDouble();
						ArrayList<Venta>ventas=obtenerVentasProducto(codigo, precio); //Obtenemos el ArrayList de ventas, que lo devuelve este metodo
						
						productos.add(new Producto2(codigo,nombre,existencias,precio,ventas)); //Creamos un departamento con los datos leidos y lo guardamos en la lista
					}
					posicion= posicion + FILESIZE_PRODUCTOS; //Actualizamos la posicion
				    if (file.getFilePointer()==file.length() || posicion>=file.length())seguirLeyendo=false; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
			}
			file.close();  //cerrar fichero 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return productos; //Devolvemos la lista
	}
	private ArrayList<Venta> obtenerVentasProducto(int codigo, double precio) throws IOException {
		ArrayList<Venta>ventas=new ArrayList<Venta>(); //Lista que contendra todas las ventas que tengan el codigo igual que el recibido
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroVentas, "r");
			int posicion=0;
			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				boolean seguirLeyendo=true; //Variable que controla cuando salimos del bucle
				while(seguirLeyendo){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int cod=file.readInt();
					if (cod==codigo) { //Si el codigo leido coincide con el que nos han pasado, leemos el resto de la venta
						int uniVen=file.readInt();
						String fecha="";
						for(int i=0;i<10;i++) {
							fecha+=file.readChar();
						}
						double importe=uniVen*precio; //Calculamos el importe con las unidades leidas y el precio recibido
						ventas.add(new Venta(uniVen,fecha,importe)); //Creamos la venta y la añadimos a la lista
					}
					posicion= posicion + FILESIZE_DATOSDEVENTAS; //Actualizamos la posicion del puntero	  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())seguirLeyendo=false; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
			}
			file.close();  //cerrar fichero 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ventas; //Devolvemos la lista
	}
}