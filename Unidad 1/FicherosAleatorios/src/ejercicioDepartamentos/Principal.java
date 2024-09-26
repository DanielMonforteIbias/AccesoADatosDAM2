package ejercicioDepartamentos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Principal {
	
	private String ficheroDep=".\\AleatorioDepart.dat";
	private int fileSize=72;
	
	public static void main(String[] args) throws IOException {
		Principal p=new Principal();
		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		boolean salir=false;
		int dep=0, numEmpleados=0;
		float mediaSalario=0;
		String nombre="", loc="";
		do {
			p.mostrarMenu();
			opcion = sc.nextInt();
			switch(opcion) {
			case 1:
				p.crearFichero();
				break;
			case 2:
				System.out.println("Introduce el id del departamento a consultar: ");
				dep=sc.nextInt();
				if (p.consultarRegistro(dep)) System.out.println("DEPARTAMENTO EXISTE");
				else System.out.println("DEPARTAMENTO NO EXISTE");
				break;
			case 3:
				System.out.println("Introduce el id del departamento a insertar: ");
				dep=sc.nextInt();
				System.out.println("Introduce del nombre del departamento: ");
				nombre=sc.next();
				System.out.println("Introduce la localidad del departamento: ");
				loc=sc.next();
				System.out.println("Introduce el numero de empleados ");
				numEmpleados=sc.nextInt();
				System.out.println("Introduce la media del salario del departamento: ");
				mediaSalario=sc.nextFloat();
				System.out.println(p.insertarRegistro(dep,nombre,loc,numEmpleados,mediaSalario));
				break;
			case 4:
				System.out.println("Introduce el id del departamento a visualizar: ");
				dep=sc.nextInt();
				if (!p.visualizarRegistro(dep)) System.out.println("DEPARTAMENTO NO EXISTE. NO SE VISUALIZA");
				break;
			case 5:
				System.out.println("Introduce el id del departamento a modificar: ");
				dep=sc.nextInt();
				System.out.println("Introduce la nueva localidad");
				loc=sc.next();
				System.out.println("Introduce la nueva media del salario");
				mediaSalario=sc.nextFloat();
				System.out.println(p.modificarRegistro(dep,loc,mediaSalario));
				break;
			case 6:
				System.out.println("Introduce el id del departamento a borrar: ");
				dep=sc.nextInt();
				p.borrarRegistro(dep);
				break;
			case 7:
				p.listarRegistros();
				break;
			case 8:
				p.listarEmpleados();
				break;
			case 9:
				p.actualizarDepartamentos();
				break;
			case 0:
				salir=true;
				break;
			default:
				System.out.println("Seleccione una opción válida!");
				break;
			}
		}while(!salir);
		System.out.println("FIN");
		sc.close();
	}


	private void crearFichero() { 
		try {
			File fichero=new File(ficheroDep);
			if (fichero.exists()) System.out.println("EL FICHERO YA EXISTE, NO SE PUEDE CREAR");
			else {
				System.out.println("FICHERO CREADO");
				RandomAccessFile file = new RandomAccessFile(ficheroDep, "rw");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean consultarRegistro(int id) throws IOException {
		boolean existe=false;
		 try {
				RandomAccessFile file = new RandomAccessFile(ficheroDep, "r");
				
				int posicion = (id - 1 ) * fileSize; //calculo donde empieza el registro 
				if(posicion >= file.length()) existe=false; //Si esta fuera del fichero, no existe
				 else{ //Si no esta fuera del fichero
					file.seek(posicion);//nos posicionamos 
					int idd=file.readInt(); // obtengo id del departamento
					if (idd==id) existe=true; //Si los id coinciden existe
					file.close();
				    }  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		 return existe;
	}
	
	private String insertarRegistro(int codigoDep, String nombre, String loc, int numEmpleados, float mediaSalario) throws IOException {
		String mensaje="";
		if (codigoDep<1 || codigoDep>100) mensaje="ERROR. EL DEPARTAMENTO DEBE ESTAR ENTRE 1 Y 100";
		else if (consultarRegistro(codigoDep)) mensaje="ERROR. YA EXISTE UN DEPARTAMENTO CON ESE ID, NO SE PUEDE INSERTAR";
		else {
			try {
				RandomAccessFile file = new RandomAccessFile(ficheroDep, "rw");
				int posicion = (codigoDep - 1 ) * fileSize; //calculo donde empieza el registro 
				file.seek(posicion);
				file.writeInt(codigoDep);
				StringBuffer buffer=new StringBuffer(nombre);
				buffer.setLength(15);
				file.writeChars(buffer.toString());
				buffer=new StringBuffer(loc);
				buffer.setLength(15);
				file.writeChars(buffer.toString());
				file.writeInt(numEmpleados);
				file.writeFloat(mediaSalario);
				mensaje="REGISTRO INSERTADO: COD: "+codigoDep+" NOMBRE: "+nombre+" LOCALIDAD: "+loc+" NUMERO EMPLEADOS: "+numEmpleados+" MEDIA SALARIO: "+mediaSalario;
				file.close();
			} catch (FileNotFoundException e) {
				mensaje="ERROR REGISTRO NO INSERTADO";
				e.printStackTrace();
			}
		}
		return mensaje;	
	}

	private boolean visualizarRegistro(int dep) throws IOException {
		boolean existe=false;
		if (!consultarRegistro(dep)) {
			existe=false;
		}
		else {
			existe=true;
			try {
				RandomAccessFile file = new RandomAccessFile(ficheroDep, "r");
				int posicion = (dep - 1 ) * fileSize; //calculo donde empieza el registro 
				file.seek(posicion);
				int codigoDep=file.readInt();
				String nombre="";
				for(int i=0;i<15;i++) {
					nombre+=file.readChar();
				}
				String loc="";
				for(int i=0;i<15;i++) {
					loc+=file.readChar();
				}
				int numEmpleados=file.readInt();
				float mediaSalario=file.readFloat();
				System.out.println("CODIGO DEP: "+codigoDep+" NOMBRE: "+nombre+" LOCALIDAD: "+loc+" NUMERO EMPLEADOS: "+numEmpleados+" MEDIA SALARIO: "+mediaSalario);
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return existe;
	}

	private String modificarRegistro(int dep, String loc, float mediaSalario) throws IOException {
		String mensaje="";
		if (!consultarRegistro(dep)) mensaje="REGISTRO NO SE PUEDE MODIFICAR";
		else {
			try {
				RandomAccessFile file = new RandomAccessFile(ficheroDep, "rw");
				int posicion = (dep - 1 ) * fileSize; //calculo donde empieza el registro 
				file.seek(posicion+4+30); //Nos colocamos en la posicion, +4 del id, +30 del nombre para leer a continuacion la localidad
				StringBuffer buffer=new StringBuffer(loc); //El parametro loc recibido puede no medir 15, asi que lo almacenamos en un StringBuffer
				buffer.setLength(15); //Ajustamos la longitud del buffer a 15 para que ocupe los 30 bytes
				file.writeChars(buffer.toString()); //Grabamos la localidad recibida
				file.readInt(); //Leemos el numero de empleados, no hacemos nada con el, solo avanza el puntero
				file.writeFloat(mediaSalario); //Grabamos la media del salario recibida
				mensaje="REGISTRO MODIFICADO";
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return mensaje;
	}
	
	private void borrarRegistro(int dep) throws IOException {
		if (!consultarRegistro(dep)) System.out.println("El registro no se puede borrar"); //Esta linea comprueba si no existe o es hueco
		else {
			try {
				RandomAccessFile file = new RandomAccessFile(ficheroDep, "rw");
				int posicion = (dep - 1 ) * fileSize; //calculo donde empieza el registro 
				file.seek(posicion); //Nos colocamos en la posicion
				file.writeInt(0);
				StringBuffer buffer=new StringBuffer();
				buffer.setLength(15); //Ajustamos la longitud del buffer a 15 para que ocupe los 30 bytes
				file.writeChars(buffer.toString());//Ponemos el nombre a la cadena vacia de 15
				file.writeChars(buffer.toString()); //Ponemos la localidad a la cadena vacia de 15
				file.writeInt(0); //Leemos el numero de empleados, no hacemos nada con el, solo avanza el puntero
				file.writeFloat(0); //Grabamos la media del salario recibida
				System.out.println("REGISTRO BORRADO");
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	}
	private void listarRegistros() throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroDep, "r");
			int posicion=0, contador=0;  //para situarnos al principio
			//CABECERAS
			System.out.printf("%-10s %-15s %-15s %-13s %-9s %n","CODIGO DEP", "NOMBRE DEP","LOC DEP","NUMERO EMPLES","MEDIA SAL");
			System.out.printf("%-10s %-15s %-15s %-13s %-9s %n","----------", "----------","-------","-------------","---------");
			//El - sirve para alinearlo a la izquierda, la s para indicar que son cadenas, el numero es la longitud

			if (file.length()>0){ //Si hay contenido en el fichero, lo leemos, si no, no
				//BUCLE PARA LEER FICHERO
				for(;;){  //recorro el fichero
					file.seek(posicion); //nos posicionamos en posicion
					int codigoDep=file.readInt();
					if (codigoDep!=0) { //Si el id no es 0, es decir, no es un hueco, leemos e imprimimos los datos
						String nombre="";
						for(int i=0;i<15;i++) {
							nombre+=file.readChar();
						}
						String loc="";
						for(int i=0;i<15;i++) {
							loc+=file.readChar();
						}
						int numEmpleados=file.readInt();
						float mediaSalario=file.readFloat();
						System.out.printf("%-10s %-15s %-15s %-13s %-9s %n",codigoDep, nombre,loc,numEmpleados,mediaSalario);
						contador++;
					}
					posicion= posicion + fileSize; // me posiciono para el sig empleado	  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
			if (contador==0) System.out.println("El fichero esta vacio");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void listarEmpleados() throws IOException {
		   RandomAccessFile file = new RandomAccessFile(".\\AleatorioEmple.dat", "r");
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
			   	System.out.println("ID: " + id + ", Apellido: "+  apellidoS +", Departamento: "+dep + ", Salario: " + salario);        
			   	posicion= posicion + 36; // me posiciono para el sig empleado
			                         //Cada empleado ocupa 36 bytes (4+20+4+8) 
			//Si he recorrido todos los bytes salgo del for	 	  
		      if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
		   
		   }//fin bucle for 
		   file.close();  //cerrar fichero 
		   }

	public void actualizarDepartamentos() throws IOException {
		//Recorremos el fichero de empleados secuencialmente y actualizamos de manera directa en departamentos
		RandomAccessFile fileEmple = new RandomAccessFile(".\\AleatorioEmple.dat", "r");
		int  id, dep, posicion;    
		Double salario;
		char apellido[] = new char[10], aux; 
		posicion=0;  //para situarnos al principio

		for(;;){  //recorro el fichero
			fileEmple.seek(posicion); //nos posicionamos en posicion
			id=fileEmple.readInt();   // obtengo id de empleado
			if (id!=0) { //Si el id es distinto de 0, seguimos leyendo
				for (int i = 0; i < apellido.length; i++) {
					aux = fileEmple.readChar();//recorro uno a uno los caracteres del apellido 
					apellido[i] = aux;    //los voy guardando en el array
				}     
				String apellidoS= new String(apellido);//convierto a String el array
				dep=fileEmple.readInt();//obtengo dep
				salario=fileEmple.readDouble();  //obtengo salario
				
				//Ya hemos leido el empleado, ahora comprobamos si el departamento existe
				if (consultarRegistro(dep)) { //Si el departamento existe en el fichero de departamentos
					actualizarDepartamento(dep, salario.floatValue());
			}
			
			//Si he recorrido todos los bytes salgo del for	 	  
			}
			posicion= posicion + 36; // me posiciono para el sig empleado
			if (fileEmple.getFilePointer()==fileEmple.length() || posicion>=fileEmple.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
		   }//fin bucle for
		fileEmple.close();  //cerrar fichero 
	}

	private void actualizarDepartamento(int dep, float salario) throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroDep, "rw");
			int posicion = (dep - 1 ) * fileSize; //calculo donde empieza el registro 
			file.seek(posicion+4+30+30); //Nos colocamos en la posicion, +4 del id, +30 del nombre +30 de la localidad
			int numEmpleados=file.readInt()+1; //Leemos el numero de empleados y incrementamos su variable en 1
			float mediaSalario=(file.readFloat()+salario)/2; //Leemos la media actual, le sumamos el salario del nuevo empleado y hacemos la nueva media
			file.seek(posicion+4+30+30); //Nos volvemos a posicionar despues de leer los datos actuales
			file.writeInt(numEmpleados); //Grabamos el nuevo numero de empleados
			file.writeFloat(mediaSalario); //Grabamos la nueva media del salario
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void mostrarMenu() {
		System.out.println("------------------------------------------------------");
		System.out.println("OPERACIONES CON DEPARTAMENTOS");
		System.out.println("  1. Ejercicio 1. Crear fichero");
		System.out.println("  2. Ejercicio 2. Consultar registro");
		System.out.println("  3. Ejercicio 3. Insertar registro");
		System.out.println("  4. Ejercicio 4. Visualizar registro");
		System.out.println("  5. Ejercicio 5. Modificar registro");
		System.out.println("  6. Ejercicio 6. Borrar registro");
		System.out.println("  7. Ejercicio 7. Listar departamentos");
		System.out.println("  8. Ejercicio 8. Listar empleados");
		System.out.println("  9. Ejercicio 9. Actualizar departamentos");
		System.out.println("  0. Salir");
		System.out.println("------------------------------------------------------");
	}
}