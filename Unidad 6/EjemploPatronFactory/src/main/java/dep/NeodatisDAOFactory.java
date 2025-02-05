package dep;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

public class NeodatisDAOFactory extends DAOFactory {
    static ODB odb = null;

    public NeodatisDAOFactory() { }

    public static ODB crearConexion() {      
        if (odb == null) {
            odb = ODBFactory.open("Departamento.BD");
        }          
        return odb;
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new NeodatisDepartamentoImpl();
    }

	@Override
	public EmpleadoDAO getEmpleadoDAO() {
		return new NeodatisEmpleadoImpl();
	}
}
