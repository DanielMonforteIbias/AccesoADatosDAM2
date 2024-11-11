
DROP TABLE detcompras cascade constraints;
DROP TABLE compras cascade constraints; 
DROP TABLE clientes cascade constraints;
DROP TABLE productos cascade constraints;


/* Create Tables */

CREATE TABLE clientes
(
	CODCLIENTE int NOT NULL,
	NOMBRE varchar2(25),
	POBLACION varchar2(35),
	TLF varchar2(10),
	PRIMARY KEY (CODCLIENTE)
);
/

INSERT INTO CLIENTES VALUES  (1,'JUAN GOMEZ','MADRID','91123456');
INSERT INTO CLIENTES VALUES  (2,'ALBERTO PEREZ','TALAVERA','92535996');
INSERT INTO CLIENTES VALUES  (3,'ANA GARCIA','TALAVERA','92523456');
INSERT INTO CLIENTES VALUES  (4,'ANGUS PEREZ','MADRID','9135996');
INSERT INTO CLIENTES VALUES  (5,'PETER CODD','OROPESA','91123456');
INSERT INTO CLIENTES VALUES  (6,'ALICIA GIL MAS','OROPESA','92223456');


CREATE TABLE productos
(
	CODPRODUCTO int NOT NULL,
	DENOMINACION varchar2(20),
	TIPO varchar2(1),  /* --A (21% IVA), B(10% IVA), C(4% iva) */
	PVP float,
	STOCK int,
	PRIMARY KEY (CODPRODUCTO)
);
/

insert into productos values (1,'Mesa Ordenador','A', 10, 100);
insert into productos values (2,'Catálogo','B', 15, 150);
insert into productos values (3,'Agua 5L','C', 20, 10);
insert into productos values (4,'Portátil','A', 25, 200);
insert into productos values (5,'Altavoces','A', 30, 70);
insert into productos values (6,'Silla ergonómica','B', 12, 10);
insert into productos values (7,'Manta térmica','B', 24, 100);
insert into productos values (8,'Envase 6CC','C', 15, 80);
insert into productos values (9,'Pastillero','C', 60, 90);
insert into productos values (10,'Tablet 100','A', 70, 140);
insert into productos values (11,'Grifo portátil','C', 60, 90);
insert into productos values (12,'Cámara K4','A', 70, 140);


CREATE TABLE compras
(
	NUMCOMPRA int NOT NULL,
	CODCLIENTE int NOT NULL,
	FECHA date,
	DESCUENTO int,
	PRIMARY KEY (NUMCOMPRA)
);
/

/*Cliente 1 */
insert into COMPRAS values (1,1,SYSDATE-40,0);
insert into COMPRAS values (3,1,SYSDATE-38,2);
insert into COMPRAS values (6,1,SYSDATE-20,5);
insert into COMPRAS values (11,1,SYSDATE-18,25);

/*Cliente 2 */
insert into COMPRAS values (2,2,SYSDATE-38,22);
insert into COMPRAS values (8,2,SYSDATE-19,8);

/*Cliente 3 */
insert into COMPRAS values (4,3,SYSDATE-30,10);
insert into COMPRAS values (7,3,SYSDATE-20,10);

/*Cliente 4 */
insert into COMPRAS values (9,3,SYSDATE-19,10);
insert into COMPRAS values (10,4,SYSDATE-18,30);
insert into COMPRAS values (5,4,SYSDATE-26,13);

/*Cliente 5 */

insert into COMPRAS values (12,5,SYSDATE-15,0);
insert into COMPRAS values (13,5,SYSDATE-13,2);
insert into COMPRAS values (14,5,SYSDATE-12,5);
insert into COMPRAS values (15,5,SYSDATE-10,25);


CREATE TABLE detcompras
(
	NUMCOMPRA int NOT NULL,
	CODPRODUCTO int NOT NULL,
	UNIDADES int,
	PRIMARY KEY (NUMCOMPRA, CODPRODUCTO)
);
/

/* NUMCOMPRA , CODPRODUCTO, UNIDADES */
/* compra 1, cliente 1 */
insert into detcompras values( 1,1,10);
insert into detcompras values( 1,2,8);
insert into detcompras values( 1,3,20);
insert into detcompras values( 1,4,5);
insert into detcompras values( 1,5,15);

/* -- compra 11 cliente 1*/
insert into detcompras values( 11,1,15);
insert into detcompras values( 11,3,10);

/* compra 3, cliente 1*/
insert into detcompras values( 3,5,4);
insert into detcompras values( 3,6,10);
insert into detcompras values( 3,7,5);

/* -- compra 6 cliente 1*/
insert into detcompras values( 6,3,5);
insert into detcompras values( 6,4,10);
insert into detcompras values( 6,5,10);
insert into detcompras values( 6,6,10);
insert into detcompras values( 6,7,10);
insert into detcompras values( 6,8,10);

/* compra 2, cliente 2 */
insert into detcompras values( 2,2,5);
insert into detcompras values( 2,3,4);
insert into detcompras values( 2,4,10);
insert into detcompras values( 2,5,5);
insert into detcompras values( 2,6,5);
insert into detcompras values( 2,7,8);
insert into detcompras values( 2,8,5);

/* -- compra 8 cliente 2*/
insert into detcompras values( 8,2,5);
insert into detcompras values( 8,4,10);
insert into detcompras values( 8,5,10);
insert into detcompras values( 8,6,10);
insert into detcompras values( 8,7,10);
insert into detcompras values( 8,8,10);

/* compra 4,cliente 3*/
insert into detcompras values( 4,1,5);
insert into detcompras values( 4,5,4);
insert into detcompras values( 4,6,10);
insert into detcompras values( 4,7,5);

/* -- compra 7 cliente 3*/
insert into detcompras values( 7,1,5);
insert into detcompras values( 7,2,10);
insert into detcompras values( 7,3,10);
insert into detcompras values( 7,10,15);

/* -- compra 9 cliente 3*/ 
insert into detcompras values( 9,1,15);
insert into detcompras values( 9,3,10);
insert into detcompras values( 9,5,20);
insert into detcompras values( 9,7,15);
insert into detcompras values( 9,11,15);

/* -- compra 5 cliente 4*/
insert into detcompras values( 5,7,5);
insert into detcompras values( 5,8,10);
insert into detcompras values( 5,9,10);
insert into detcompras values( 5,10,15);

/* -- compra 10 cliente 4*/
insert into detcompras values( 10,2,5);
insert into detcompras values( 10,4,10);
insert into detcompras values( 10,5,10);
insert into detcompras values( 10,6,10);
insert into detcompras values( 10,7,10);
insert into detcompras values( 10,8,15);
insert into detcompras values( 10,9,5);

/* -- compra 12 cliente 5*/
insert into detcompras values( 12,1,5);
insert into detcompras values( 12,3,8);
insert into detcompras values( 12,5,8);
insert into detcompras values( 12,6,10);
insert into detcompras values( 12,8,5);
insert into detcompras values( 12,9,5);

/* -- compra 13 cliente 5*/
insert into detcompras values( 13,1,5);
insert into detcompras values( 13,4,4);
insert into detcompras values( 13,5,2);
insert into detcompras values( 13,8,3);

/* -- compra 14 cliente 5*/

insert into detcompras values( 14,9,5);
insert into detcompras values( 14,10,10);



/* Create Foreign Keys */

ALTER TABLE compras
	ADD CONSTRAINT comprasfkclien FOREIGN KEY (CODCLIENTE)
	REFERENCES clientes (CODCLIENTE);


ALTER TABLE detcompras
	ADD CONSTRAINT detcompraskcompras FOREIGN KEY (NUMCOMPRA)
	REFERENCES compras (NUMCOMPRA) ;


ALTER TABLE detcompras
	ADD CONSTRAINT detcomprasfkproduc FOREIGN KEY (CODPRODUCTO)
	REFERENCES productos (CODPRODUCTO);
	

