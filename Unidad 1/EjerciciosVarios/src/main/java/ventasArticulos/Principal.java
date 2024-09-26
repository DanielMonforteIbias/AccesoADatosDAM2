package ventasArticulos;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Principal {
	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(VentasArticulos.class);
			Unmarshaller unmars = context.createUnmarshaller();
	        VentasArticulos ventasArticulos=(VentasArticulos) unmars.unmarshal(new File("ventasarticulos.xml"));
	        Articulo articulo=ventasArticulos.getArticulo();
	        System.out.println("Código: "+articulo.getCodigo()+"   Nombre: "+articulo.getDenominacion()+"   PVP: "+articulo.getPrecio());
	        System.out.printf("%9s %11s %-20s %8s %10s %n","NUM VENTA", "FECHA VENTA","NOM-CLIENTE","UNIDADES","IMPORTE");
	        System.out.printf("%9s %11s %-20s %8s %10s %n","---------", "-----------","--------------------","--------","-------");
		    ArrayList<Venta>ventas=ventasArticulos.getVentas();
		    int totalUnidades=0, totalImporte=0;
		    for(Venta v:ventas) {
		    	System.out.printf("%9s %11s %-20s %8s %10s %n",v.getNumventa(),v.getFecha(),v.getNombreCliente(),v.getUnidades(),v.getUnidades()*articulo.getPrecio());
		    	totalUnidades+=v.getUnidades();
		    	totalImporte+=v.getUnidades()*articulo.getPrecio();
		    }
		    System.out.printf("%9s %11s %-20s %8s %10s %n","---------", "-----------","--------------------","--------","-------");
		    System.out.printf("%9s %11s %-20s %8s %10s %n","TOTALES","","",totalUnidades,totalImporte);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
