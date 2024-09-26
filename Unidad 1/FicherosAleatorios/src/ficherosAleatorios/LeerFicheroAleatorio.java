package ficherosAleatorios;

import java.io.*;
public class LeerFicheroAleatorio {
  public static void main(String[] args) {
	try {
		leerFichero();
		System.out.println("====================================");
		consultarRegistro(5); //Registro existe
		System.out.println("====================================");
		consultarRegistro(10); //Registro no existe
		System.out.println("====================================");
		insertarRegistro(6,"Nuevo6",10,1000); //Ya existe, no se deberia insertar
		System.out.println("====================================");
		insertarRegistro(20,"Nuevo20",10,1000); //Se inserta
		System.out.println("====================================");
		leerFichero();
		System.out.println("====================================");
		insertarRegistro(5,"Nuevo5",10,1000); //Se inserta
		insertarRegistro(10,"Nuevo10",10,1000); //Se inserta
		insertarRegistro(15,"Nuevo15",10,1000); //Se inserta
		System.out.println("====================================");
		System.out.println("MODIFICAR");
		//Sumar subida al salario de los empleados del dep 10
		modificarTodosEmpleadosDep(10,100d); //No hay error
		modificarTodosEmpleadosDep(100,100d); //No existe dep
		//Sumar 100 al salario de un empleado
		modificarEmple(5,100d); //Existe
		modificarEmple(50,100d); //No existe
		modificarEmple(16,100d); //No existe, es hueco
		System.out.println("====================================");
		System.out.println("BORRAR");
		borrarEmple(5); //Lo va a borrar
		borrarEmple(35); //Esta fuera del fichero, no lo borra
		borrarEmple(16); //Es hueco, no lo borra
		System.out.println("====================================");
		leerFichero();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  private static void borrarEmple(int id) throws IOException {
	  File fichero = new File(".\\AleatorioEmple.dat");
	   //declara el fichero de acceso aleatorio
	   try {
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		long posicion = (id -1 ) * 36; //calculamos la posicion
		if (posicion>=file.length()) { //ID no existe
			System.out.println("ID: "+id+" no existe, no se puede borrar");
		}
		else { //Existe o es un hueco
			//Comprobamos el id
			file.seek(posicion);
			int iden=file.readInt();
			if(iden==0) { //Es hueco
				System.out.println("EL ID "+id+" es un hueco, no se puede borrar");
			}
			else { //Existe, se borra
				System.out.println("EL ID "+id+" existe, se hara un borrado logico");
				file.seek(posicion);
				file.writeInt(0); //Como es borrado logico no desaparece, sino que se cambia a 0 el id
				StringBuffer buffer = new StringBuffer();      
				buffer.setLength(10); //10 caracteres para el apellido
				file.writeChars(buffer.toString());//insertar apellido
				file.writeInt(0);       //insertar departamento
				file.writeDouble(0);//insertar salario
			}
		}
		file.close();
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
}

private static void modificarEmple(int identificador, double subida) throws IOException {
	  File fichero = new File(".\\AleatorioEmple.dat");
	   //declara el fichero de acceso aleatorio
	   try {
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		long posicion = (identificador -1 ) * 36; //calculamos la posicion
		if (posicion>=file.length()) { //ID no existe
			System.out.println("ID: "+identificador+" no existe, no se modifica");
		}
		else { //Existe o es un hueco
			//Comprobamos el id
			file.seek(posicion);
			int id=file.readInt();
			if(id==0) { //Es hueco
				System.out.println("EL ID "+identificador+" es un hueco, no se modifica");
			}
			else { //Existe, se modifica
				System.out.println("EL ID "+id+" existe, se subira el sueldo en "+subida);
				file.seek(posicion+4+20+4); //Nos situamos en el salario, despues del id, apellido y departamento
				double salario=file.readDouble()+subida; //Obtenemos el salario y lo aumentamos
				file.seek(posicion+4+20+4); //Volvemos hacia atras despues de leer el salario para obtenerlo
				file.writeDouble(salario);
			}
		}
		file.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
}

private static void modificarTodosEmpleadosDep(int depart, double subida) throws IOException {
	//Leer secuencialmente todos los registros
	File fichero = new File(".\\AleatorioEmple.dat");
	int contadorModificados=0;
	   //declara el fichero de acceso aleatorio
	   try {
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
	   //
	   int  id, dep, posicion;    
	   Double salario;
	   char apellido[] = new char[10], aux; 
	   posicion=0;  //para situarnos al principio
	   
	   for(;;){  //recorro el fichero
			file.seek(posicion); //nos posicionamos en posicion
			id=file.readInt();   // obtengo id de empleado	  	  
		      for (int i = 0; i < apellido.length; i++) {
		         aux = file.readChar();//recorro uno a uno los caracteres del apellido 
		         apellido[i] = aux;    //los voy guardando en el array
		      }     
		      String apellidoS= new String(apellido);//convierto a String el array
			dep=file.readInt();//obtengo dep
			if (dep==depart) { //Si el departament coincide con el recibido, actualizamos
				contadorModificados++; //Aumentamos el contador de modificados
				salario=file.readDouble()+subida;  //obtengo salario y sumo la subida
				file.seek(posicion+4+20+4); //posicion +4 del id +20 del ape +4 del dep
				file.writeDouble(salario);
				System.out.println("Empleado actualizado. ID: " + id + ", Apellido: "+  apellidoS + 
	                     ", Departamento: "+dep + ", Salario: " + salario);        
			}
			posicion= posicion + 36; // me posiciono para el sig empleado
			//Si he recorrido todos los bytes salgo del for	 	  
		      if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
		   
		   }//fin bucle for 
	   	   System.out.println("Se han actualizado "+contadorModificados+ " empleados del dep "+depart);
		   file.close();  //cerrar fichero 
	   } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
}

private static void insertarRegistro(int id, String apellido, int dep, double salario) throws IOException {
	  File fichero = new File(".\\AleatorioEmple.dat");
	   //declara el fichero de acceso aleatorio
	   try {
		RandomAccessFile file = new RandomAccessFile(fichero, "rw");
		long posicion = (id -1 ) * 36; //calculamos la posicion
		if (posicion>=file.length()) { //ID no existe, se añade
			System.out.println("ID: "+id+" no existe, se añade");
			file.seek(posicion); //nos posicionamos 
			file.writeInt(id); //se escribe id 
			StringBuffer buffer = new StringBuffer( apellido);      
			buffer.setLength(10); //10 caracteres para el apellido
			file.writeChars(buffer.toString());//insertar apellido
			file.writeInt(dep);       //insertar departamento
			file.writeDouble(salario);//insertar salario
			        

		}
		else { //Existe o es un hueco
			//Comprobamos el id
			file.seek(posicion);
			int identificador=file.readInt();
			if(identificador==0) { //Es hueco
				System.out.println("EL ID "+id+" es un hueco, se inserta");
				file.seek(posicion); //nos posicionamos 
				file.writeInt(id); //se escribe id 
				StringBuffer buffer = new StringBuffer( apellido);      
				buffer.setLength(10); //10 caracteres para el apellido
				file.writeChars(buffer.toString());//insertar apellido
				file.writeInt(dep);       //insertar departamento
				file.writeDouble(salario);//insertar salario
			}
			else { //Ya existe
				System.out.println("EL ID "+id+" ya existe, no se inserta");
			}
		}
		file.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
}

private static void consultarRegistro(int identificador) throws IOException {
	   File fichero = new File(".\\AleatorioEmple.dat");
	   //declara el fichero de acceso aleatorio
	   try {
		RandomAccessFile file = new RandomAccessFile(fichero, "r");
		int posicion = (identificador - 1 ) * 36; //calculo donde empieza el registro 
		if(posicion >= file.length())     
		    System.out.println("ID: " + identificador + ", NO EXISTE EMPLEADO...");
		 else{ //Nos posicionamos y leemos, y mostramos
			 System.out.println("ID "+identificador+" LOCALIZADO");
			file.seek(posicion);//nos posicionamos 
			int id=file.readInt(); // obtengo id de empleado
			String ape="";
			for (int i = 0; i < 10; i++) {
		        ape=ape+file.readChar();
		      }     
			int dep=file.readInt();//obtengo dep
		  	Double salario=file.readDouble();  //obtengo salario
			System.out.println("ID: " + id + ", Apellido: "+  ape + 
			                     ", Departamento: "+dep + ", Salario: " + salario);        
			file.close();
		    }  

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	
  }

public static void leerFichero() throws IOException {     
   File fichero = new File(".\\AleatorioEmple.dat");
   //declara el fichero de acceso aleatorio
   RandomAccessFile file = new RandomAccessFile(fichero, "r");
   //
   int  id, dep, posicion;    
   Double salario;
   char apellido[] = new char[10], aux; 


   posicion=0;  //para situarnos al principio


   for(;;){  //recorro el fichero
	file.seek(posicion); //nos posicionamos en posicion
	id=file.readInt();   // obtengo id de empleado	  	  
      for (int i = 0; i < apellido.length; i++) {
         aux = file.readChar();//recorro uno a uno los caracteres del apellido 
         apellido[i] = aux;    //los voy guardando en el array
      }     
      String apellidoS= new String(apellido);//convierto a String el array
	dep=file.readInt();//obtengo dep
  	salario=file.readDouble();  //obtengo salario
	System.out.println("ID: " + id + ", Apellido: "+  apellidoS + 
	                     ", Departamento: "+dep + ", Salario: " + salario);        
	posicion= posicion + 36; // me posiciono para el sig empleado
	                         //Cada empleado ocupa 36 bytes (4+20+4+8) 
	//Si he recorrido todos los bytes salgo del for	 	  
      if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
   
   }//fin bucle for 
   file.close();  //cerrar fichero 
   }
}
