package principal;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import clases.*;

public class Principal {
	private static SessionFactory factory;
	public static void main(String[] args) {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);
		factory=Conexion.getSession();
		
		conUniqueResultMaxProductoPorCompras();
		
		listarComprasClientes();
		
		listarComprasClientes2();
		
		listarTotalProductos();
		listarTotalProductos2();
		
		actualizarStock();
		
		listarDetallesProductos();
		
		borrarCliente(1); //No lo borra porque tiene registros
		borrarCliente(6); //Lo borra
		borrarCliente(100); //No existe
		factory.close();

	}
	private static void conUniqueResultMaxProductoPorCompras() {
		Session session=factory.openSession();
		Query<Compras>q=session.createQuery("from Compras c order by c.numcompra",Compras.class);
		List<Compras>lista=q.list();
		if(lista.size()>0) {
			System.out.printf("%9s %10s %30s %30s %n","NUMCOMPRA","CODCLIENTE","NOMBRE CLIENTE","PRODUCTO MAS CARO");
			System.out.printf("%9s %10s %30s %30s %n","---------","----------","------------------------------","------------------------------");
			for(Compras c:lista) {
				String con="select d.productos.denominacion from Detcompras d where d.compras.numcompra=:numcompra and d.productos.pvp=(select max(d2.productos.pvp) from Detcompras d2 where d2.compras.numcompra=:numcompra )";
				Query q2=session.createQuery(con,String.class);
				q2.setParameter("numcompra", c.getNumcompra());
				String producto=(String)q2.uniqueResult();
				System.out.printf("%9s %10s %30s %30s %n",c.getNumcompra(),c.getClientes().getCodcliente(),c.getClientes().getNombre(),producto);
			}
		}
		session.close();
	}
	//CON CLASE AUXILIAR
	private static void listarComprasClientes() {
		Session session=factory.openSession();
		String con="select new clases.TotalCliente(c.codcliente, c.nombre, count(distinct con), sum(det.unidades * det.productos.pvp)) from Clientes c left join c.comprases con left join con.detcomprases det group by c.codcliente, c.nombre order by c.codcliente";
		Query q=session.createQuery(con,TotalCliente.class);
		List<TotalCliente>lista=q.list();
		System.out.printf("%11s %-30s %-30s %-30s %n","Cod Cliente","Nombre", "Numero de compras","Total compras");
		System.out.printf("%11s %-30s %-30s %-30s %n","-----------", "------------------------------", "------------------------------","------------------------------");
		int totalNumCompras=0;
		double totalCompras=0;
		for(TotalCliente t:lista) {
			System.out.printf("%11s %-30s %-30s %-30s %n",t.getCodCliente(),t.getNombre(),t.getContador(),t.getSuma());
			totalNumCompras+=t.getContador();
			totalCompras+=t.getSuma();
		}
		System.out.printf("%11s %-30s %-30s %-30s %n","-----------", "------------------------------", "------------------------------","------------------------------");
		System.out.printf("%11s %-30s %-30s %-30s %n","TOTALES", "",totalNumCompras,totalCompras);
		session.close();
	}
	
	//SIN CLASE AUXILIAR
	private static void listarComprasClientes2() {
		Session session=factory.openSession();
		String hql="from Clientes c order by c.codcliente";
		Query q=session.createQuery(hql,Clientes.class);
		List<Clientes>lista=q.list();
		System.out.printf("%11s %-30s %-30s %-30s %n","Cod Cliente","Nombre", "Numero de compras","Total compras");
		System.out.printf("%11s %-30s %-30s %-30s %n","-----------", "------------------------------", "------------------------------","------------------------------");
		int totalNumCompras=0;
		double totalCompras=0;
		for(Clientes c:lista) {
			hql="select sum(det.unidades * det.productos.pvp) from Compras c join c.detcomprases det where c.clientes.codcliente= :codcli";
			Query<Double>q2 = session.createQuery(hql,Double.class);
			q2.setParameter("codcli",c.getCodcliente());
			Double totalCompra=(Double) q2.uniqueResult();
			if(totalCompra==null)totalCompra=0d; 
			System.out.printf("%11s %-30s %-30s %-30s %n",c.getCodcliente(),c.getNombre(),c.getComprases().size(),totalCompra);
			totalNumCompras+=c.getComprases().size();
			totalCompras+=totalCompra;
		}
		System.out.printf("%11s %-30s %-30s %-30s %n","-----------", "------------------------------", "------------------------------","------------------------------");
		System.out.printf("%11s %-30s %-30s %-30s %n","TOTALES", "",totalNumCompras,totalCompras);
		session.close();
	}
	//CON ARRAY DE OBJETOS
	private static void listarTotalProductos() {
		Session session=factory.openSession();
		String hql="select p.codproducto, p.denominacion, p.pvp, coalesce(sum(det.unidades),0), coalesce(sum(det.unidades*p.pvp),0) from Productos p left join p.detcomprases det group by p.codproducto, p.denominacion, p.pvp order by p.codproducto";
		Query<Object> cons = session.createQuery(hql,Object.class);
		List datos = cons.list();
		System.out.printf("%12s %30s %10s %10s %10s %n","COD PRODUCTO","DENOMINACION","PRECIO","SUMA UNI","SUMA IMP");
		System.out.printf("%12s %30s %10s %10s %10s %n","------------","------------------------------","----------","----------","----------");
		int sumaUnidades=0, maximo=0;
		float sumaImportes=0;
		String productoMaximo="";
		for (int i = 0; i < datos.size(); i++) {
			Object[] fila = (Object[]) datos.get(i);
			System.out.printf("%12s %30s %10s %10s %10s %n",fila[0],fila[1],fila[2],fila[3],fila[4]);
			sumaUnidades+=((BigInteger)fila[3]).intValue();
			sumaImportes+=(double)fila[4];
			if(((BigInteger)fila[3]).intValue()>maximo){
				productoMaximo=(String)fila[1];
				maximo=((BigInteger)fila[3]).intValue();
			}
			else if(((BigInteger)fila[3]).intValue()==maximo){
				productoMaximo+="     "+(String)fila[1];
			}
		}
		System.out.printf("%12s %30s %10s %10s %10s %n","------------","------------------------------","----------","----------","----------");
		System.out.printf("%12s %30s %10s %10s %10s %n","TOTALES","","",sumaUnidades,sumaImportes);
		System.out.println("Producto mas vendido: "+productoMaximo);
		session.close();
	}
	
	//CON CONSULTA FROM
	private static void listarTotalProductos2() {
		Session session=factory.openSession();
		String hql="from Productos p order by p.codproducto";
		Query<Productos> cons = session.createQuery(hql,Productos.class);
		List<Productos> datos = cons.list();
		System.out.printf("%12s %30s %10s %10s %10s %n","COD PRODUCTO","DENOMINACION","PRECIO","SUMA UNI","SUMA IMP");
		System.out.printf("%12s %30s %10s %10s %10s %n","------------","------------------------------","----------","----------","----------");
		int sumaUnidadesTotal=0, maximo=0;
		float sumaImportes=0;
		String productoMaximo="";
		for(Productos p:datos) {
			hql="select coalesce(sum(det.unidades),0) from Detcompras det where det.productos.codproducto= :codpro";
			Query<BigInteger>q2 = session.createQuery(hql,BigInteger.class);
			q2.setParameter("codpro",p.getCodproducto());
			int sumaUnidades= ((BigInteger)q2.uniqueResult()).intValue();
			System.out.printf("%12s %30s %10s %10s %10s %n",p.getCodproducto(),p.getDenominacion(),p.getPvp(),sumaUnidades,sumaUnidades*p.getPvp());
			sumaUnidadesTotal+=sumaUnidades;
			sumaImportes+=sumaUnidades*p.getPvp();
			if(sumaUnidades>maximo) {
				maximo=sumaUnidades;
				productoMaximo=p.getDenominacion();
			}
			else if(sumaUnidades==maximo) productoMaximo+="     "+p.getDenominacion();
			
			//OTRA FORMA EN VEZ DE UNA CONSULTA ES RECORRER EL SET
			//Set<Detcompras>detalle=p.getDetcomprases();
			//for(Detcompras d:detalle){
			//	sumaUnidades+=de.getUnidades().intValue();
			//}
			//Si se quiere sacar la cantidad de compras en las que esta un producto se puede utilizar el size del set
			
		}
		System.out.printf("%12s %30s %10s %10s %10s %n","------------","------------------------------","----------","----------","----------");
		System.out.printf("%12s %30s %10s %10s %10s %n","TOTALES","","",sumaUnidadesTotal,sumaImportes);
		System.out.println("Producto mas vendido: "+productoMaximo);
		session.close();
	}
	
	private static void listarDetallesProductos() {
		Session session=factory.openSession();
		String hql="from Productos p order by p.codproducto";
		Query<Productos> cons = session.createQuery(hql,Productos.class);
		List<Productos> datos = cons.list();
		for(Productos p:datos) {
			System.out.println();
			System.out.println("Cod producto: "+p.getCodproducto());
			System.out.println("Denominacion: "+p.getDenominacion()+"     Precio: "+p.getPvp());
			System.out.println("Stock actual: "+p.getStock().intValue());
			System.out.println("--------------------------------------------------------------");
			if(p.getDetcomprases().size()>0) {
				System.out.printf("%10s %10s %10s %-30s %10s %10s %n","NUM COMPRA","FECHA","COD CLI","NOMBRE CLI","UNIDADES","IMPORTE");
				System.out.printf("%10s %10s %10s %-30s %10s %10s %n","----------","----------","----------","------------------------------","----------","----------");
				Set<Detcompras>detalle=p.getDetcomprases();
				//Si queremos tener la salida ordenada por numero de compra habria que hacerlo por consulta en lugar de usar el set
				//String hql3="from Detcompras d where d.productos.codproducto=:codproducto order by id.numcompra";
				int sumaUnidades=0;
				float totalImporte=0;
				for(Detcompras d:detalle){
					float importe=d.getUnidades().intValue()*p.getPvp().floatValue();
					totalImporte+=importe;
					sumaUnidades+=d.getUnidades().intValue();
					System.out.printf("%10s %10s %10s %-30s %10s %10s %n",d.getCompras().getNumcompra(),d.getCompras().getFecha(),d.getCompras().getClientes().getCodcliente(),d.getCompras().getClientes().getNombre(),d.getUnidades(),importe);
				}
				System.out.printf("%10s %10s %10s %-30s %10s %10s %n","----------","----------","----------","------------------------------","----------","----------");
				System.out.printf("%10s %10s %10s %-30s %10s %10s %n","TOTALES","","","",sumaUnidades,totalImporte);
			}
			else System.out.println("   **SIN COMPRAS**   ");
		}
		session.close();
	}
	
	private static void actualizarStock() {
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();
		String hqlModif="update Productos p set p.stock=p.stock+:subida where p.stock<=70";
		int filasModif=session.createMutationQuery(hqlModif).setParameter("subida", 50).executeUpdate();
		tx.commit();
		System.out.println("FILAS MODIFICADAS: "+filasModif);
		session.close();
	}
	
	private static void borrarCliente(int cli) {
		Session session=factory.openSession();
		Transaction tx=session.beginTransaction();
		String hqlDel="delete Clientes c where c.codcliente=:codcli";
		try {
			int filas=session.createMutationQuery(hqlDel).setParameter("codcli",cli).executeUpdate();
			if(filas!=0) {
				System.out.println("CLIENTE BORRADO: "+cli);
				tx.commit();
			}
			else System.out.println("CLIENTE "+cli+" NO EXISTE, NO SE BORRA");
		}catch(org.hibernate.exception.ConstraintViolationException e) {
			System.out.println("CLIENTE "+cli+" TIENE REGISTROS RELACIONADOS, NO SE BORRA");
		}
		session.close();
	}
	
	
}