package principal;

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
}
