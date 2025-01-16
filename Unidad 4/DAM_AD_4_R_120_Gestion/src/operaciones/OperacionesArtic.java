package operaciones;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.gui.server.Main;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import datos.Articulos;
import datos.Ventas;

public class OperacionesArtic {
	public static void actualizarImporteVentas() {
		ODB odb=ODBFactory.open("ARTICULOS.DAT");
		IQuery query = new CriteriaQuery(Ventas.class);
		Objects<Ventas> objects = odb.getObjects(query);

		if (objects.size() == 0) {
			System.out.println("No hay ventas");
		} else {
			for (int i = 0; i < objects.size(); i++) {
				
				Ventas venta = objects.next();
				float importe = (float) 0.0;
				if (venta.getCodarti() != null)
					importe = venta.getUniven() * venta.getCodarti().getPvp();
				float precioFinal = importe;
				if (venta.getNumcli().getDescuento() == 1) {
					precioFinal = precioFinal - (precioFinal * venta.getCodarti().getDescuento() / 100);
				}
				venta.setImporte(importe);
				venta.setPrecioFinal(precioFinal);
				odb.store(venta);
			}
		}
		odb.close(); 
	}
}