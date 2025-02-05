package dep;

import java.util.ArrayList;

public interface EmpleadoDAO {    
    public boolean InsertarEmp(Empleado emp);
    public boolean EliminarEmp(int empno); 
    public boolean ModificarEmp(int empno,  Empleado emp);
    public Empleado ConsultarEmp(int empno);
    public ArrayList<Empleado> Obtenerempleados();
}
