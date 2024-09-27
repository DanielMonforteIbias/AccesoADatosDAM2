package ejercicioDirectoProductos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Principal {
	private final int FILESIZE_PRODUCTOS=46;
	private final int FILESIZE_DATOSDEVENTAS=28;
	private String ficheroProductos="Productos.dat";
	private String ficheroVentas="DatosdeVentas.dat";
	
	public static void main(String[] args) throws IOException {
		Principal p=new Principal();
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		do {
			menu();
			int opcion=s.nextInt();
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
					salir=false;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		
	}
	
	private static void menu() {
		System.out.println("---------------------------------------------------");
		System.out.println("OPERACIONES CON PRODUCTOS");
		System.out.println("1. Ejercicio 1. Listar productos");
		System.out.println("2. Ejercicio 2. Listar datos ventas");
		System.out.println("3. Ejercicio 3. Actualizar existencias ");
		System.out.println("4. Salir");
		System.out.println("---------------------------------------------------");
	}
	
	private void listarProductos() throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroProductos, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS
			System.out.printf("%-10s %-20s %-10s %-10s %n","CODIGO", "NOMBRE","EXISTENCIAS","PVP");
			System.out.printf("%-10s %-20s %-10s %-10s %n","----------", "--------------------","----------","----------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				for(;;){  //recorro el fichero
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
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void listarDatosVentas() throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroVentas, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS
			System.out.printf("%-6s %-6s %-20s %n","CODIGO", "UNID. VEN","FECHA");
			System.out.printf("%-6s %-6s %-20s %n","------", "------","--------------------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				for(;;){  //recorro el fichero
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
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
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
		//Recorremos el fichero de empleados secuencialmente y actualizamos de manera directa en departamentos
		RandomAccessFile fileDatosVentas = new RandomAccessFile(ficheroVentas, "r");
		int codigo=0, uniVen=0;
		char fecha[]=new char[10];
		int posicion=0;  //para situarnos al principio

		for(;;){  //recorro el fichero
			fileDatosVentas.seek(posicion); //nos posicionamos en posicion
			codigo=fileDatosVentas.readInt();   // obtengo id de empleado
			if (codigo!=0) { //Si el id es distinto de 0, seguimos leyendo
				if(codigo>99) System.out.println("El codigo "+codigo+" es mayor de 99, no existe");
				else {
					uniVen=fileDatosVentas.readInt();
					for (int i = 0; i < fecha.length; i++) {
						char aux = fileDatosVentas.readChar();//recorro uno a uno los caracteres del apellido 
						fecha[i] = aux;    //los voy guardando en el array
					}     
					String fechaS= new String(fecha);//convierto a String el array
					if (consultarProducto(codigo)) { //Si el departamento existe en el fichero de departamentos
						actualizarProducto(codigo,uniVen);
					}
				}
			}
			else System.out.println("El producto con el codigo "+codigo+" no existe");
			posicion= posicion + FILESIZE_DATOSDEVENTAS; // me posiciono para el sig empleado
			if (fileDatosVentas.getFilePointer()==fileDatosVentas.length() || posicion>=fileDatosVentas.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
		   }//fin bucle for
		fileDatosVentas.close();  //cerrar fichero 
	}
	
	private void actualizarProducto(int codigo, int uniVen) throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroProductos, "rw");
			int posicion = (codigo - 1 ) * FILESIZE_PRODUCTOS; //calculo donde empieza el registro 
			file.seek(posicion+4+30); //Nos colocamos en la posicion, +4 del codigo +30 del nombre
			int numUnidades=file.readInt();
			int numUnidadesActualizado=numUnidades-uniVen;
			file.seek(posicion+4+30); //Nos volvemos a posicionar despues de leer los datos actuales
			file.writeInt(numUnidadesActualizado); //Grabamos el nuevo numero de empleados
			System.out.println("Producto con codigo "+codigo+" actualizado");
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}