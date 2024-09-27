package crearDepartamentosXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class Principal {
	private static int fileSize=72; //Tamaño del archivo que vamos a leer
	private static final String FICHERODEP="AleatorioDepart.dat"; //Nombre del archivo que vamos a leer
	private static final String MIARCHIVO_XML = "departamentos.xml"; //Nombre del archivo que vamos a crear
	
	public static void main(String[] args) throws IOException {
		//insertarRegistros();
		
		crearDepartamentosXml(); //Creamos el fichero xml de departamentos
	}
	
	private static void crearDepartamentosXml() throws IOException {
		Departamentos d=new Departamentos();
		ArrayList<Departamento> departamentos=cargarDepartamentos(); //La lista se iguala a lo que devuelve el metodo que lee el fichero
		d.setDepartamentos(departamentos);
		try {
			// Creamos el contexto indicando la clase raíz
			JAXBContext context = JAXBContext.newInstance(Departamentos.class);
			//Creamos el Marshaller, convierte el java bean en una cadena XML
			Marshaller m = context.createMarshaller();
		       //Formateamos el xml para que quede bien
		        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		        // Lo visualizamos con system out
		        m.marshal(d, System.out);
		         // Escribimos en el archivo
		        m.marshal(d, new File(MIARCHIVO_XML));
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	}
	
	private static ArrayList<Departamento> cargarDepartamentos() throws IOException { //Lee el fichero y carga los departamentos en la lista
		ArrayList<Departamento>departamentos=new ArrayList<Departamento>();
		try {
			RandomAccessFile file = new RandomAccessFile(FICHERODEP, "r");
			int posicion=0;  //para situarnos al principio

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
						nombre=nombre.trim(); //Quitamos los espacios al final, pues en el fichero del que leemos es de tamaño fijo
						String loc="";
						for(int i=0;i<15;i++) {
							loc+=file.readChar();
						}
						loc=loc.trim(); //Quitamos los espacios al final, pues en el fichero del que leemos es de tamaño fijo
						int numEmpleados=file.readInt();
						float mediaSalario=file.readFloat();
						departamentos.add(new Departamento(codigoDep,nombre,loc,numEmpleados,mediaSalario)); //Creamos un departamento con los datos leidos y lo guardamos en la lista
					}
					posicion= posicion + fileSize;  
				    if (file.getFilePointer()==file.length() || posicion>=file.length())break; //posicion >=file.length() se hace por si no se lee un dato para que no de EOFException, pues el puntero no leeria ese dato y no llegaria al final
				   
				   }//fin bucle for 
				   file.close();  //cerrar fichero 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return departamentos; //Devolvemos la lista
	}

	private static boolean consultarRegistro(int id) throws IOException {
		boolean existe=false;
		 try {
				RandomAccessFile file = new RandomAccessFile(FICHERODEP, "r");
				
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
	
	private static void insertarRegistros() throws IOException { //INSERCIONES MANUALES PARA HACER PRUEBAS
		System.out.println(insertarRegistro(10, "VENTAS", "TOLEDO", 6, 1500));
		System.out.println(insertarRegistro(20, "INFORMATICA", "MADRID", 3, 3000));
		System.out.println(insertarRegistro(30, "CONTABILIDAD", "MALAGA", 5, 1000));
		System.out.println(insertarRegistro(40, "COMPRAS", "BARCELONA", 4, 1100));
		System.out.println(insertarRegistro(50, "FORMACION", "TALAVERA", 2, 1100));
		
	}
	private static String insertarRegistro(int codigoDep, String nombre, String loc, int numEmpleados, float mediaSalario) throws IOException {
		String mensaje="";
		if (codigoDep<1 || codigoDep>100) mensaje="ERROR. EL DEPARTAMENTO DEBE ESTAR ENTRE 1 Y 100";
		else if (consultarRegistro(codigoDep)) mensaje="ERROR. YA EXISTE UN DEPARTAMENTO CON ESE ID, NO SE PUEDE INSERTAR";
		else {
			try {
				RandomAccessFile file = new RandomAccessFile(FICHERODEP, "rw");
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
}