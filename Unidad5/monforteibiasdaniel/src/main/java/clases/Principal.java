package clases;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class Principal {
	static MongoClient cliente = new MongoClient("localhost", 27017);
	static MongoDatabase db = cliente.getDatabase("MonforteIbiasDaniel2324");
	static MongoCollection<Document> clientes = db.getCollection("clientes");
	static MongoCollection<Document> viajes = db.getCollection("viajes");
	static MongoCollection<Document> reservas = db.getCollection("reservas");
	
	static Scanner s=new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Tarea MongoDB. Realizada por: Daniel Monforte Ibias");
		Scanner s=new Scanner(System.in);
		boolean salir=false;
		do {
			menu();
			int opcion=s.nextInt();
			switch(opcion) {
				case 1:
					actualizarViajes();
					break;
				case 2:
					actualizarClientes();
					break;
				case 3:
					consultarClientes();
					break;
				case 0:
					salir=true;
					break;
				default:
					System.out.println("Opcion incorrecta");
					break;
			}
		}while(!salir);
		s.close();
	}
	private static void menu() {
		System.out.println("............................................................");
		System.out.println("1 - Actualizar en la coleccion de viajes.");
		System.out.println("2 - Actualizar en la coleccion de clientes.");
		System.out.println("3 - Consulta de clientes");
		System.out.println("0 - Salir");
		System.out.println("............................................................");
	}
	
	
	private static void actualizarViajes() {
		for (Document viaje : viajes.find().sort(Sorts.ascending("id"))) {
	        int idViaje = viaje.getInteger("id");
	        String descripcion = viaje.getString("descripcion");
	        int plazasTotales = 0;
	        for (Document reserva : reservas.find(Filters.eq("idviaje", idViaje))) {
	            plazasTotales += reserva.getInteger("plazas");
	        }
	        viajes.updateOne(Filters.eq("id", idViaje), Updates.set("viajeros", plazasTotales));
	        if (plazasTotales > 0) {
	            System.out.println("idViaje: " + idViaje + ", descripción: " + descripcion + ", actualizado con " + plazasTotales + " viajeros.");
	        } else {
	            System.out.println("idViaje: " + idViaje + ", descripción: " + descripcion + ", sin viajeros.");
	        }
	    }
	    System.out.println("Fin actualización viajes.");
	}
	
	private static void actualizarClientes() {
		for (Document cliente : clientes.find().sort(Sorts.ascending("id"))) {
	        int idCliente = cliente.getInteger("id");
	        String nombre = cliente.getString("nombre");
	        int viajesContratados = 0;
	        double importeTotal=0;
	        for (Document reserva : reservas.find(Filters.eq("idcliente", idCliente))) {
	            viajesContratados ++;
	            Document viaje=viajes.find(Filters.eq("id",reserva.getInteger("idviaje"))).first();
	            importeTotal+=viaje.getDouble("pvp")*reserva.getInteger("plazas");
	        }
	        clientes.updateOne(Filters.eq("id", idCliente), Updates.set("viajescontratados", viajesContratados));
	        clientes.updateOne(Filters.eq("id", idCliente), Updates.set("importetotal", importeTotal));
	        if (viajesContratados > 0) {
	            System.out.println("idCliente: " + idCliente + ", nombre: " + nombre + ", actualizado con " + viajesContratados + " viajes contratados, y con total importe: "+importeTotal);
	        } else {
	            System.out.println("idCliente: " + idCliente + ", nombre: " + nombre + ", no ha contratado viajes");
	        }
	    }
	    System.out.println("Fin proceso de actualizacion...");
	}
	
	private static void consultarClientes() {
		int numeroCliente=0;
		do {
			System.out.println("====================================");
			System.out.println("Introduce el numero de cliente: ");
			numeroCliente=s.nextInt();
			if(numeroCliente!=0) {
				consultaCliente(numeroCliente);
			}
			
		}while(numeroCliente!=0);
		System.out.println("Fin proceso de consulta...");
	}
	
	private static void consultaCliente(int numeroCliente) {
		Document cliente = clientes.find(Filters.eq("id", numeroCliente)).first();
		if (cliente != null) {
			if(cliente.getInteger("viajescontratados")>0) {
				System.out.println(cliente.getString("nombre")+", Viajes contratados: "+cliente.getInteger("viajescontratados"));
			    System.out.println("====================================");
			    System.out.printf("%3s %-40s %-10s %8s %6s %n","ID","DESCRIPCION","FEC SALIDA","PVP","PLAZAS");
			    System.out.printf("%3s %-40s %10s %8s %6s %n","===","========================================","==========","========","======");
			    double importeTotal=0;
			    for (Document reserva : reservas.find(Filters.eq("idcliente", cliente.getInteger("id")))) {
		            Document viaje=viajes.find(Filters.eq("id",reserva.getInteger("idviaje"))).first();
		            System.out.printf("%3s %-40s %10s %,8.2f %6s %n",viaje.getInteger("id"),viaje.getString("descripcion"),viaje.getString("fechasalida"),viaje.getDouble("pvp"),reserva.getInteger("plazas"));
		        }
			    System.out.printf("%3s %40s %10s %8s %6s %n","   ","                                        ","==========","========","======");
			    System.out.printf("%3s %40s %14s %,11.2f %n","   ","                                        ","Importe total:",cliente.getDouble("importetotal"));
			}
			else System.out.println("   NO HA CONTRATADO NINGUN VIAJE");
		} else {
		    System.out.println("   NO EXISTE EL ID DEL CLIENTE: "+numeroCliente);
		}
	}
}
