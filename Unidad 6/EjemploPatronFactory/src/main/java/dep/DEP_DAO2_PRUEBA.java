package dep;

import java.sql.Date;
import java.util.Scanner;

public class DEP_DAO2_PRUEBA {

    public static void main(String[] args) {
//        System.out.println("------------------------------");
//        System.out.println("PRUEBA MYSQL");
//
//        pruebamysql();
//        System.out.println("------------------------------");
//        System.out.println("PRUEBA NEODATIS");
//
//        pruebaneodatis();
//        
//        System.out.println("------------------------------");
//        System.out.println("PRUEBA ORACLE");
//
//        pruebaOracle();
//        System.out.println("------------------------------");
//        System.out.println("PRUEBA SQLITE");
//
//        pruebaSQLite();
    	System.out.println("------------------------------");
      System.out.println("PRUEBA ORACLE EMPLE");

      pruebaOracleEmpleado();

    }

    public static void pruebaneodatis() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();

        //crear departamento
        Departamento dep = new Departamento(17, "NÓMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Departamento a visualizar (0 sale): ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }

    public static void pruebamysql() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();

        //crear departamento
        Departamento dep = new Departamento(17, "NOMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Departamento a visualizar (0 sale): ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }
    
    public static void pruebaOracle() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();

        //crear departamento
        Departamento dep = new Departamento(17, "NOMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Departamento a visualizar (0 sale): ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }
    
    public static void pruebaSQLite() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
        DepartamentoDAO depDAO = bd.getDepartamentoDAO();

        //crear departamento
        Departamento dep = new Departamento(17, "NOMINAS", "SEVILLA");
        depDAO.InsertarDep(dep);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar departamentos leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Departamento a visualizar (0 sale): ");
            entero = sc.nextInt();
            dep = depDAO.ConsultarDep(entero);
            System.out.printf("Dep: %d, Nombre: %s, Loc: %s %n", dep.getDeptno(),
                    dep.getDnombre(), dep.getLoc());
        }
    }

    
    public static void pruebaneodatisEmpleado() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.NEODATIS);
        EmpleadoDAO empDAO = bd.getEmpleadoDAO();

        //crear empleado
        Empleado emp=new Empleado(97,"Lopez","Nuevo",7369,new Date(21,1,2005),1000,100,10);
        empDAO.InsertarEmp(emp);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar empleados leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Empleado a visualizar (0 sale): ");
            entero = sc.nextInt();
            emp = empDAO.ConsultarEmp(entero);
            System.out.printf("Emp: %d, Nombre: %s, Oficio: %s %n",emp.getEmp_no(),emp.getApellido(),emp.getOficio());
        }
    }
    
    public static void pruebamysqlEmpleado() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        EmpleadoDAO empDAO = bd.getEmpleadoDAO();

        //crear empleado
        Empleado emp=new Empleado(97,"Lopez","Nuevo",7369,new Date(21,1,2005),1000,100,10);
        empDAO.InsertarEmp(emp);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar empleados leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Empleado a visualizar (0 sale): ");
            entero = sc.nextInt();
            emp = empDAO.ConsultarEmp(entero);
            System.out.printf("Emp: %d, Nombre: %s, Oficio: %s %n",emp.getEmp_no(),emp.getApellido(),emp.getOficio());
        }
    }
    
    public static void pruebaOracleEmpleado() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
        EmpleadoDAO empDAO = bd.getEmpleadoDAO();

        //crear empleado
        Empleado emp=new Empleado(97,"Lopez","Nuevo",7369,new Date(21,1,2005),1000,100,10);
        empDAO.InsertarEmp(emp);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar empleados leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Empleado a visualizar (0 sale): ");
            entero = sc.nextInt();
            emp = empDAO.ConsultarEmp(entero);
            System.out.printf("Emp: %d, Nombre: %s, Oficio: %s %n",emp.getEmp_no(),emp.getApellido(),emp.getOficio());
        }
    }
    
    public static void pruebasqliteEmpleado() {
        DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.SQLITE);
        EmpleadoDAO empDAO = bd.getEmpleadoDAO();

        //crear empleado
        Empleado emp=new Empleado(97,"Lopez","Nuevo",7369,new Date(21,1,2005),1000,100,10);
        empDAO.InsertarEmp(emp);

        Scanner sc = new Scanner(System.in);
        int entero = 1;
        //Visualizar empleados leidos por teclado
        while (entero > 0) {
            System.out.println("Teclea Empleado a visualizar (0 sale): ");
            entero = sc.nextInt();
            emp = empDAO.ConsultarEmp(entero);
            System.out.printf("Emp: %d, Nombre: %s, Oficio: %s %n",emp.getEmp_no(),emp.getApellido(),emp.getOficio());
        }
    }
}