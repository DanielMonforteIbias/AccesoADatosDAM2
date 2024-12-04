package principal;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		}
		System.out.printf("%12s %30s %10s %10s %10s %n","------------","------------------------------","----------","----------","----------");
		System.out.printf("%12s %30s %10s %10s %10s %n","TOTALES","","",sumaUnidadesTotal,sumaImportes);
		System.out.println("Producto mas vendido: "+productoMaximo);
		session.close();
	}
}