package ejercicios;

import java.math.BigInteger;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ObjectValues;
import org.neodatis.odb.Values;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import datos.*;

public class ConsultasVentas {
	private static ODB odb;
	public static void main(String[] args) {
		odb= ODBFactory.open("ARTICULOS.DAT");
		countVentas();
		groupByArticulo(1);
		odb.close();
	}

	public static void countVentas() {
		Values val2 = odb.getValues(new ValuesCriteriaQuery(Ventas.class).count("*"));
		ObjectValues ov2 = val2.nextValues();
		BigInteger value2 = (BigInteger) ov2.getByAlias("*");
		System.out.println("Numero de ventas: " + value2.intValue());
	}

	public static void groupByArticulo(int codArticulo) {
		Values groupby = odb.getValues(new ValuesCriteriaQuery(Ventas.class,Where.equal("codarti.codarti", codArticulo)).field("codarti.codarti").count("*").groupBy(("codarti.codarti")));
		while (groupby.hasNext()) {
			ObjectValues objetos = (ObjectValues) groupby.next();
			System.out.println("Cod Arti: "+objetos.getByAlias("codarti.codarti") + "   Num. Ventas:" + objetos.getByIndex(1));
		}
	}
}