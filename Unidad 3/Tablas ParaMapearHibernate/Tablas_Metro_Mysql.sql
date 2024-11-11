SET FOREIGN_KEY_CHECKS = 0; 

DROP TABLE IF EXISTS t_lineas;
create table  t_lineas(
 cod_linea   int primary key,
 nombre      varchar(50) not null
 ) ENGINE = InnoDB ;

Insert into  t_lineas  values(1,'PLAZA CASTILLA-CONGOSTO');
Insert into  t_lineas  values(2,'VENTAS-CUATRO CAMINOS');
Insert into  t_lineas  values(3,'LEGAZPI-MONCLOA');
Insert into  t_lineas  values(4,'ARGÜELLES-PARQUE DE SANTA MARÍA');
Insert into  t_lineas  values(5,'CANILLEJAS-CASA DE CAMPO');
Insert into  t_lineas  values(6,'CIRCULAR');
Insert into  t_lineas  values(7,'LAS MUSAS-PITIS');


DROP TABLE IF EXISTS t_estaciones;
create table  t_estaciones(
 cod_estacion    int primary key,
 nombre           varchar(50) not null,
 direccion        varchar(50) not null
 ) ENGINE = InnoDB ;

Insert into  t_estaciones  values(1,'PLAZA DE CASTILLA','PLAZA DE CASTILLA');
Insert into  t_estaciones  values(2,'VALDEACEDERAS','BARRIO VALDEACEDERAS');
Insert into  t_estaciones  values(3,'TETUÁN','BARUO DE TETUÁN');
Insert into  t_estaciones  values(4,'ALVARADO','CALLE ALVARADO');
Insert into  t_estaciones  values(5,'CUATRO CAMINOS','AVDA CUATRO CAMINOS');
Insert into  t_estaciones  values(6,'IGLESIA','RONDA DE GOYA' );
Insert into  t_estaciones  values(7,'GRAN VÍA','C/GRAN VÍA');
Insert into  t_estaciones  values(8,'SOL','PUERTA DEL SOL');
Insert into  t_estaciones  values(9,'ANTON MARTÍN','C/ ANTÓN MARTÍN');
Insert into  t_estaciones  values(10,'ATOCHA','CALLE ATOCHA');
Insert into  t_estaciones  values(11,'PUENTE DE VALLECAS','VALLECAS');
Insert into  t_estaciones  values(12,'PORTAZGO','C/LOS COMUNEROS');
Insert into  t_estaciones  values(13,'CONGOSTO','C/SIERRA DE BOBIA');

Insert into  t_estaciones  values(14,'VENTAS','PLAZA DE VENTAS');
Insert into  t_estaciones  values(15,'GOYA','CALLE GOYA');
Insert into  t_estaciones  values(16,'SEVILLA','PLAZA DE SEVILLA');
Insert into  t_estaciones  values(17,'ÓPERA','PLAZA DE ÓPERA');
Insert into  t_estaciones  values(18,'SAN BERNARDO','C/ SAN BERNARDO');
Insert into  t_estaciones  values(19,'QUEVEDO','GLORIETA DE QUEVEDO');

Insert into  t_estaciones  values(20,'LEGAZPI','PLAZA DE LEGAZPI');
Insert into  t_estaciones  values(21,'DELICIAS','CALLE DELICIAS');
Insert into  t_estaciones  values(22,'CALLAO','PLAZA DE CALLAO');
Insert into  t_estaciones  values(23,'VENTURA RODRIGUEZ','C/ VENTURA 20');
Insert into  t_estaciones  values(24,'ARGÜELLES','PLAZA DE ARGÜELLES');
Insert into  t_estaciones  values(25,'MONCLOA','PALACIO DE LA MONCLOA');

Insert into  t_estaciones  values(26,'BILBAO','C/ BILBAO');
Insert into  t_estaciones  values(27,'ALONSO MARTÍNEZ','C/ ALONSO MARTINEZ');
Insert into  t_estaciones  values(28,'COLÓN','PLAZA DE COLÓN');
Insert into  t_estaciones  values(29,'LISTA','C/ LISTA DE AMÉRICA');
Insert into  t_estaciones  values(30,'AVENIDA DE AMÉRICA','AVDA DE AMÉRICA 10');
Insert into  t_estaciones  values(31,'CANILLAS','PLAZA DE CANILLAS');
Insert into  t_estaciones  values(32,'PARQUE DE SANTA MARÍA','BARRIO DE SANTA MARÍA');

DROP TABLE IF EXISTS t_LINEA_ESTACION;
create table  t_LINEA_ESTACION(
 cod_linea     int not null,
 cod_estacion  int not null,
 orden         int not null,
 FOREIGN KEY (cod_linea)    REFERENCES   t_lineas(cod_linea),
 FOREIGN KEY (cod_estacion) REFERENCES   t_estaciones(cod_estacion),
 PRIMARY KEY (cod_linea, cod_estacion)
 ) ENGINE = InnoDB ;

Insert into  t_LINEA_ESTACION values(1,1,1);
Insert into  t_LINEA_ESTACION values(1,2,2);
Insert into  t_LINEA_ESTACION values(1,3,3);
Insert into  t_LINEA_ESTACION values(1,4,4);
Insert into  t_LINEA_ESTACION values(1,5,5);
Insert into  t_LINEA_ESTACION values(1,6,6);
Insert into  t_LINEA_ESTACION values(1,7,7);
Insert into  t_LINEA_ESTACION values(1,8,8);
Insert into  t_LINEA_ESTACION values(1,9,9);
Insert into  t_LINEA_ESTACION values(1,10,10);
Insert into  t_LINEA_ESTACION values(1,11,11);
Insert into  t_LINEA_ESTACION values(1,12,12);
Insert into  t_LINEA_ESTACION values(1,13,13);

Insert into  t_LINEA_ESTACION values(2,14,1);
Insert into  t_LINEA_ESTACION values(2,15,2);
Insert into  t_LINEA_ESTACION values(2,16,3);
Insert into  t_LINEA_ESTACION values(2,8,4);
Insert into  t_LINEA_ESTACION values(2,17,5);
Insert into  t_LINEA_ESTACION values(2,18,6);
Insert into  t_LINEA_ESTACION values(2,19,7);
Insert into  t_LINEA_ESTACION values(2,5,8);

Insert into  t_LINEA_ESTACION values(3,20,1);
Insert into  t_LINEA_ESTACION values(3,21,2);
Insert into  t_LINEA_ESTACION values(3,8,3);
Insert into  t_LINEA_ESTACION values(3,22,4);
Insert into  t_LINEA_ESTACION values(3,23,5);
Insert into  t_LINEA_ESTACION values(3,24,6);
Insert into  t_LINEA_ESTACION values(3,25,7);

Insert into  t_LINEA_ESTACION values(4,24,1);
Insert into  t_LINEA_ESTACION values(4,18,2);
Insert into  t_LINEA_ESTACION values(4,26,3);
Insert into  t_LINEA_ESTACION values(4,27,4);
Insert into  t_LINEA_ESTACION values(4,28,5);
Insert into  t_LINEA_ESTACION values(4,15,6);
Insert into  t_LINEA_ESTACION values(4,29,7);
Insert into  t_LINEA_ESTACION values(4,30,8);
Insert into  t_LINEA_ESTACION values(4,31,9);
Insert into  t_LINEA_ESTACION values(4,32,10);


DROP TABLE IF EXISTS t_accesos;
create table  t_accesos(
 cod_acceso     int primary key,
 descripcion    varchar(50) not null,
 cod_estacion   int not null,
FOREIGN KEY (cod_estacion) REFERENCES   t_estaciones(cod_estacion)
) ENGINE = InnoDB ;


Insert into  t_accesos values(1,'BULEVAR DE LA CASTELLANA',1);
Insert into  t_accesos values(2,'CASTELLANA 189',1);
Insert into  t_accesos values(3,'BRAVO MURILLO 377',1);

Insert into  t_accesos values(4,'CAPITÁN BLANCO 20',2);
Insert into  t_accesos values(5,'BRAVO MURILLO 324',2);
Insert into  t_accesos values(6,'ANIBAL 1',2);

Insert into  t_accesos values(7,'ALGODONALES',3);
Insert into  t_accesos values(8,'GENERAL MARGALLO',3);

Insert into  t_accesos values(9,'BRAVO MURILLO 135',4);
Insert into  t_accesos values(10,'BRAVO MURILLO 136',4);

Insert into  t_accesos values(11,'BRAVO MURILLO 101',5);
Insert into  t_accesos values(12,'REINA VICTORIA 1',5);
Insert into  t_accesos values(13,'SANTA ENGRACIA 168',5);
Insert into  t_accesos values(14,'MAUDES 8',5);

Insert into  t_accesos values(15,'ELOY GONZALO 1',6);
Insert into  t_accesos values(16,'GENERAL MARTÍNEZ CAMPOS 38',6);
Insert into  t_accesos values(17,'SANTA ENGRACIA 58',6);

Insert into  t_accesos values(18,'MONTERA 44',7);
Insert into  t_accesos values(19,'GRAN VÍA 25',7);
Insert into  t_accesos values(20,'GRAN VÍA 26',7);

Insert into  t_accesos values(21,'CARRETAS',8);
Insert into  t_accesos values(22,'CARMEN',8);
Insert into  t_accesos values(23,'ALCALÁ 12',8);
Insert into  t_accesos values(24,'PRECIADOS 3',8);

Insert into  t_accesos values(25,'CARRETAS',9);
Insert into  t_accesos values(26,'CARMEN',9);
Insert into  t_accesos values(27,'ALCALÁ 39',9);

Insert into  t_accesos values(28,'AMOR DE DIOS 14',10);
Insert into  t_accesos values(29,'ATOCHA 83',10);
Insert into  t_accesos values(30,'ATOCHA 76',10);

Insert into  t_accesos values(31,'ALBUFERA 14',11);
Insert into  t_accesos values(32,'ALBUFERA 9',11);

Insert into  t_accesos values(33,'ALBUFERA 108',12); 
Insert into  t_accesos values(34,'ALBUFERA 117',12);
Insert into  t_accesos values(35,'TENIENTE MUÑOZ DÍAZ',12);

Insert into  t_accesos values(36,'CONGOSTO 35',13); 
Insert into  t_accesos values(37,'PLAZA DE CONGOSTO 33',13);

Insert into  t_accesos values(38,'ALCALÁ 233',14); 
Insert into  t_accesos values(39,'PLAZA DE TOROS 1',14);

Insert into  t_accesos values(40,'CONDE DE PEÑALVER 2',15); 
Insert into  t_accesos values(41,'FELIPE II 1',15);
Insert into  t_accesos values(42,'GENERAL DIEZ PORLIER',15);
Insert into  t_accesos values(43,'FELIPE II 12',15);

Insert into  t_accesos values(44,'SEVILLA',16); 
Insert into  t_accesos values(45,'VIRGEN DE LOS PELIGROS',16);
Insert into  t_accesos values(46,'IGLESIA DE CALATRAVA',16);

Insert into  t_accesos values(47,'ARENAL.Plaza de Isabel II, 1',17); 

Insert into  t_accesos values(48,'ALBERTO AGUILERA',18); 
Insert into  t_accesos values(49,'CARRANZA',18);


Insert into  t_accesos values(50,'ARAPILES. GLTA QUEVEDO 2',19); 
Insert into  t_accesos values(51,'FUENCARRAL. GLTA QUEVEDO 1',19);


DROP TABLE IF EXISTS  t_cocheras;
create table  t_cocheras(
 cod_cochera    int primary key,
 nombre         varchar(50) not null,
 direccion      varchar(50) not null

 ) ENGINE = InnoDB ;

Insert into  t_cocheras values(1,'COCHERA ARGÜELLES','MADRID. CENTRO ARGÜELLES S/N'); 
Insert into  t_cocheras values(2,'COCHERA MONCLOA','MADRID. AVDA MONCLOA S/N'); 
Insert into  t_cocheras values(3,'COCHERA MIGUEL HERNÁNDEZ','MADRID. BARRIO DEL PILAR'); 
Insert into  t_cocheras values(4,'COCHERA CIUDAD UNIVERSITARIA','MADRID. CIUDAD UNIVERSITARIA 100'); 
Insert into  t_cocheras values(5,'COCHERA NUEVOS MINISTERIOS','MADRID. NUEVOS MINISTERIOS 200'); 
Insert into  t_cocheras values(6,'COCHERA UNIVERSIDAD REY JUAN CARLOS','LEGANES. CIUDAD INIVERSITARIA'); 
Insert into  t_cocheras values(7, 'COCHERAS PARA METRO LIGERO DE BOADILLA Y POZUELO','BOADILLA DEL MONTE'); 
Insert into  t_cocheras values(8, 'DEPÓSITO CUATRO CAMINOS','MADRID. CUATRO VIENTOS'); 
Insert into  t_cocheras values(9, 'DEPÓSITO LAS VENTAS','MADRID. LAS VENTAS 100'); 
Insert into  t_cocheras values(10, 'DEPÓSITO PLAZA DE CASTILLA','MADRID. PLAZA DE CASTILLA'); 
Insert into  t_cocheras values(11, 'DEPÓSITO FUENCARRAL','MADRID. BARRIO FUENCARRAL'); 
Insert into  t_cocheras values(12, 'DEPÓSITO CANILLEJAS','MADRID. BARRIO CANILLEJAS'); 
Insert into  t_cocheras values(13, 'DEPÓSITO VALDECARROS','MADRID. EL ENSANCHE DE VALLECAS'); 



DROP TABLE IF EXISTS t_trenes;
create table  t_trenes(
 cod_tren   int primary key,
 nombre     varchar(50) not null,
 tipo       varchar(20) not null,
 cod_linea     int not null,
 cod_cochera   int not null,
 FOREIGN KEY (cod_linea)    REFERENCES   t_lineas(cod_linea),
 FOREIGN KEY (cod_cochera) REFERENCES   t_cocheras(cod_cochera)
) ENGINE = InnoDB ;

Insert into  t_trenes values (1,'COCHE 3000 , 10','SERIE 3000',2,1);
Insert into  t_trenes values (2,'COCHE 3000 , 20','SERIE 3000',2,1);
Insert into  t_trenes values (3,'COCHE 3000 , 30','SERIE 3000',2,1);

Insert into  t_trenes values (4,'COCHE 5000 , 1','SERIE 5000',1,2);
Insert into  t_trenes values (5,'COCHE 5000 , 2','SERIE 5000',1,2);
Insert into  t_trenes values (6,'COCHE 5000 , 3','SERIE 6000',1,2);
Insert into  t_trenes values (7,'COCHE 5000 , 4','SERIE 5000',1,2);

Insert into  t_trenes values (8,'COCHE 6000 , 10','SERIE 3000',3,3);
Insert into  t_trenes values (9,'COCHE 6000 , 20','SERIE 3000',3,3);
Insert into  t_trenes values (10,'COCHE 6000 , 30','SERIE 6000',3,3);

Insert into  t_trenes values (11,'COCHE 9000 , 1','SERIE 9000',4,2);
Insert into  t_trenes values (12,'COCHE 9000 , 2','SERIE 9000',4,2);
Insert into  t_trenes values (13,'COCHE 9000 , 3','SERIE 9000',4,2);
Insert into  t_trenes values (14,'COCHE 9000 , 4','SERIE 9000',4,2);


Insert into  t_trenes values (15,'COCHE 8000 , 11','SERIE 8000',5,6);
Insert into  t_trenes values (16,'COCHE 8000 , 12','SERIE 8000',5,6);
Insert into  t_trenes values (17,'COCHE 8000 , 13','SERIE 3000',5,6);

Insert into  t_trenes values (18,'COCHE 8400 , 41','SERIE 8400',6,7);
Insert into  t_trenes values (19,'COCHE 8400 , 42','SERIE 8400',6,7);
Insert into  t_trenes values (20,'COCHE 8400 , 43','SERIE 8400',6,7);

Insert into  t_trenes values (21,'COCHE 8400 , 54','SERIE 3000',7,5);
Insert into  t_trenes values (22,'COCHE 8400 , 55','SERIE 8400',7,5);

COMMIT;
