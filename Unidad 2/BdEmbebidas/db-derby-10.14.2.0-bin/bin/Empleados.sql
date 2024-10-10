CREATE TABLE empleados (
 emp_no    INT  NOT NULL PRIMARY KEY,
 apellido  VARCHAR(10),
 oficio    VARCHAR(10),
 dir       INT,
 fecha_alt DATE      ,
 salario   FLOAT,
 comision  FLOAT,
 dept_no   INT NOT NULL REFERENCES departamentos(dept_no)
);
INSERT INTO empleados VALUES (7369,'SANCHEZ','EMPLEADO',7902,'1990-12-17', 1040,NULL,20);
INSERT INTO empleados VALUES (7499,'ARROYO','VENDEDOR',7698,'1990-02-20', 1500,390,30);
INSERT INTO empleados VALUES (7521,'SALA','VENDEDOR',7698,'1991-02-22', 1625,650,30);
commit;
