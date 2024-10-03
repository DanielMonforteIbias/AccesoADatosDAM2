package danielmonforteibias;

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
	// Variables con los nombres y tamaños de los ficheros
	static String ficheroViajes = "Viajes.dat";
	static String ficheroMovimientos = "Movimientos.dat";
	static int filesize_viajes = 74;
	static int filesize_movimientos = 74;

	public static void main(String[] args) throws IOException {
		Scanner s = new Scanner(System.in);
		boolean salir = false; // Variable para salir del bucle del programa
		do {
			menu();
			int opcion = s.nextInt();
			switch (opcion) {
			case 1:
				actualizarViajes();
				break;
			case 2:
				listarViajes();
				break;
			case 3:
				crearXmlViajes();
				break;
			case 0:
				salir = true; // Cambiamos la variable que controla la salida del bucle
				break;
			default:
				System.out.println("Opcion incorrecta");
				break;
			}
		} while (!salir);
		System.out.println("FIN");
		s.close();
	}

	public static void menu() {
		System.out.println("\nOPERACIONES CON VIAJES\n");
		System.out.println("1. Actualizar Viajes.dat");
		System.out.println("2. Listar el fichero Viajes.dat");
		System.out.println("3. Crear XML viajes.xml");
		System.out.println("0. Salir");
	}

	public static void actualizarViajes() throws IOException {
		System.out.println("--------------------------------------- PROCESO DE ACTUALIZACION. Fichero movimientos ---------------------------------------");
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroMovimientos, "r");
			int posicion = 0;
			System.out.printf("%-10s %-30s %8s %8s %9s %-55s %n", "CodViaje", "Nombre", "PVP", "PLAZAS", "OPERACION","RESULTADO");
			System.out.printf("%-10s %-30s %8s %8s %9s %-55s %n", "----------", "------------------------------","--------", "--------", "---------", "-------------------------------------------------------");

			if (file.length() > 0) {
				boolean seguir = true;
				while (seguir) {
					file.seek(posicion);
					int codigo = file.readInt();
					if (codigo != 0) {
						String nombre = "";
						for (int i = 0; i < 30; i++) {
							nombre += file.readChar();
						}
						int pvp = file.readInt();
						int plazas = file.readInt();
						String operacion = "";
						for (int i = 0; i < 1; i++) {
							operacion += file.readChar();
						}
						String resultado = "";
						switch (operacion) {
						case "A":
							if (consultarViaje(codigo)) {
								resultado = "ERROR. VIAJE A DAR DE ALTA YA EXISTE";
							} else {
								resultado = insertarViaje(codigo, nombre, pvp, plazas, operacion);
							}
							break;
						case "B":
							if (consultarViaje(codigo)) {
								resultado = borrarViaje(codigo, operacion);
							} else {
								resultado = "ERROR. VIAJE NO EXISTE, NO SE PUEDE BORRAR";
							}
							break;
						case "M":
							if (consultarViaje(codigo)) {
								resultado = modificarViaje(codigo, pvp, plazas, operacion);
							} else {
								resultado = "ERROR. VIAJE NO EXISTE, NO SE PUEDE MODIFICAR";
							}
							break;
						default: // Por si en un fichero hubiese una operacion que no sea A, M ni B
							resultado = "Error. Operacion no valida";
							break;
						}
						System.out.printf("%10s %-30s %8s %8s %9s %-55s %n", codigo, nombre, pvp, plazas, operacion,
								resultado);
					}
					posicion = posicion + filesize_movimientos;
					if (file.getFilePointer() == file.length() || posicion >= file.length()) seguir = false;
				}
				file.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean consultarViaje(int cod) throws IOException {
		boolean existe = false;
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroViajes, "r");
			int posicion = (cod - 1) * filesize_viajes;
			if (posicion >= file.length()) existe = false;
			else {
				file.seek(posicion);
				int codigo = file.readInt();
				if (codigo == cod) existe = true;
				file.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return existe;
	}

	private static String insertarViaje(int codigo, String nombre, int pvp, int plazas, String situacion)
			throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroViajes, "rw");
			int posicion = (codigo - 1) * filesize_viajes;
			file.seek(posicion);
			file.writeInt(codigo);
			StringBuffer buffer = new StringBuffer(nombre);
			buffer.setLength(30);
			file.writeChars(buffer.toString());
			file.writeInt(pvp);
			file.writeInt(plazas);
			buffer = new StringBuffer(situacion);
			buffer.setLength(1);
			file.writeChars(buffer.toString());
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "VIAJE AÑADIDO A VIAJES";
	}

	private static String modificarViaje(int cod, int pvpNuevo, int plazasNuevas, String situacion) throws IOException {
		String mensaje = "";
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroViajes, "rw");
			int posicion = (cod - 1) * filesize_viajes;
			file.seek(posicion + 4 + 60 + 4 + 4); // Nos desplazamos hasta la operacion
			String operacion = file.readChar() + ""; // Leemos la operacion
			if (!operacion.equals("M")) { // Si la operacion no es M (es decir, aun no lo hemos modificado), lo modificamos
				file.seek(posicion + 4 + 60); //Ponemos el puntero justo antes del precio, despues del codigo y el nombre
				int pvpActual = file.readInt();
				int plazasActuales = file.readInt();
				int pvp = pvpActual + pvpNuevo;
				int plazas = plazasActuales + plazasNuevas;
				file.seek(posicion + 4 + 60); //Despues de leer los datos necesarios regresamos hacia atras con el puntero para grabar
				file.writeInt(pvp);
				file.writeInt(plazas);
				StringBuffer buffer = new StringBuffer(situacion);
				buffer.setLength(1);
				file.writeChars(buffer.toString());
				mensaje = "VIAJE MODIFICADO";
			} else mensaje = "ERROR. VIAJE YA MODIFICADO, NO SE VUELVE A MODIFICAR"; //Para que el resultado no sea "VIAJE MODIFICADO" si no se está modificando nada porque ya se hizo
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return mensaje;
	}

	private static String borrarViaje(int dep, String operacion) throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroViajes, "rw");
			int posicion = (dep - 1) * filesize_viajes;
			file.seek(posicion);
			//Borrado logico, ponemos todo a 0
			file.writeInt(0);
			StringBuffer buffer = new StringBuffer();
			buffer.setLength(30);
			file.writeChars(buffer.toString());
			file.writeInt(0);
			file.writeInt(0);
			//La situacion no quedará vacía, sino que será la operación (B)
			buffer = new StringBuffer(operacion);
			buffer.setLength(1);
			file.writeChars(buffer.toString());
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "VIAJE BORRADO";
	}

	public static void listarViajes() throws IOException {
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroViajes, "r");
			int posicion = 0, contador = 0;
			float mediaPvp = 0, mediaPlazas = 0;
			System.out.printf("%-10s %-30s %8s %8s %-9s %n", "CodViaje", "Nombre", "PVP", "PLAZAS", "SITUACION");
			System.out.printf("%-10s %-30s %8s %8s %-9s %n", "----------", "------------------------------","--------", "--------", "---------");

			if (file.length() > 0) { // Si la longitud del fichero es mayor que 0, es decir, no está vacío, lo leemos
				/**
				 * En lugar de usar un for infinito con un break para recorrer el fichero, se
				 * usa un while con la variable booleana "seguir", que cambia su valor donde
				 * habría un break al usar el for. Esto se hace para quitar los warnings de
				 * Eclipse, pues al parecer si se hace un bucle infinito no detecta que se
				 * cierra el fichero después de este, y sale un Warning como si no se hubiese
				 * cerrado
				 */
				boolean seguir = true;
				while (seguir) {
					file.seek(posicion);
					int codigo = file.readInt();
					if (codigo != 0) {
						String nombre = "";
						for (int i = 0; i < 30; i++) {
							nombre += file.readChar();
						}
						int pvp = file.readInt();
						int plazas = file.readInt();
						String situacion = "";
						for (int i = 0; i < 1; i++) {
							situacion += file.readChar();
						}
						System.out.printf("%10s %-30s %8s %8s %9s %n", codigo, nombre, pvp, plazas, situacion);
						contador++; // Incrementamos el contador en 1
						mediaPvp += pvp; // Acumulamos la suma de los precios en la media
						mediaPlazas += plazas; // Acumulamos la suma de las plazas en la media
					}
					posicion = posicion + filesize_viajes;
					if (file.getFilePointer() == file.length() || posicion >= file.length()) seguir = false;
				}
				// Dividimos los totales acumulados entre el numero de vuelos para obtener las medias
				mediaPvp = (float)Math.round((mediaPvp / contador)*100)/100; //Se redondea la media a dos decimales multiplicandola por 100, quitando los decimales y dividiendola de nuevo por 100
				mediaPlazas = (float)Math.round(mediaPlazas / contador*100)/100;
				System.out.printf("%-10s %-30s %-8s %-8s %-9s %n", "----------", "------------------------------","--------", "--------", "---------");
				System.out.printf("%10s %-30s %8s %8s %-9s %n", "MEDIAS", "", mediaPvp, mediaPlazas, "");
				// La media de precios sale distinta al enunciado, se ha comprobado con calculadora que el resultado con decimales es el correcto
				file.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void crearXmlViajes() throws IOException {
		Viajes v = new Viajes();
		ArrayList<Viaje> viajes = cargarViajes(); //Cargamos la lista de viajes
		v.setListaViajes(viajes); //Asignamos la lista de viajes al objeto de la clase Viajes
		try {
			JAXBContext context = JAXBContext.newInstance(Viajes.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(v, System.out); //Imprimimos por pantalla el resultado
			m.marshal(v, new File("viajes.xml")); //Lo añadimos al fichero
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<Viaje> cargarViajes() throws IOException {
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		try {
			RandomAccessFile file = new RandomAccessFile(ficheroViajes, "r");
			int posicion = 0;
			if (file.length() > 0) { //Si el fichero no está vacío, lo leemos
				boolean seguir = true;
				while (seguir) {
					file.seek(posicion);
					int codigo = file.readInt();
					if (codigo != 0) { //Si el viaje no es un hueco, seguimos
						String nombre = "";
						for (int i = 0; i < 30; i++) {
							nombre += file.readChar();
						}
						nombre = nombre.trim(); //Quitamos los espacios en blanco
						int pvp = file.readInt();
						int plazas = file.readInt();
						String situacion = "";
						for (int i = 0; i < 1; i++) {
							situacion += file.readChar();
						}
						viajes.add(new Viaje(codigo, nombre, pvp, plazas, situacion)); //Creamos un objeto viaje con los datos leidos y lo añadimos a la lista
					}
					posicion = posicion + filesize_viajes;
					if (file.getFilePointer() == file.length() || posicion >= file.length()) seguir = false;
				}
				file.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return viajes; // Devolvemos la lista
	}
}