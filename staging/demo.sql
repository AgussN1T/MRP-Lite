insert into taller (id, codigo, nombre) values (10,'DEMO', 'Taller de la demo');
insert into tipo_equipo (id,nombre) values (20,'moladora');
insert into tipo_equipo (id,nombre) values (30,'martillo');
insert into tipo_equipo (id,nombre) values (40,'taladro');


insert into equipo (id,capacidad, codigo, tipo_equipo_id) values (2,1,'moladora 1',20);
insert into equipo (id,capacidad, codigo, tipo_equipo_id) values (3,2,'martillo automatico 1',30);
insert into equipo (id,capacidad, codigo, tipo_equipo_id) values (4,1.3,'moladora 2',20);
insert into equipo (id,capacidad, codigo, tipo_equipo_id) values (5,0.9,'martillo prehistorico 1',30);
insert into equipo (id,capacidad, codigo, tipo_equipo_id) values (6,2.3,'taladro manual',40);

insert into taller_equipos (taller_id,equipos_id)values (10,2);
insert into taller_equipos (taller_id,equipos_id)values (10,3);
insert into taller_equipos (taller_id,equipos_id)values (10,4);
insert into taller_equipos (taller_id,equipos_id)values (10,5);
insert into taller_equipos (taller_id,equipos_id)values (10,6);

insert into cliente (id,cuit,razon_social) values (20, 90000000009, 'Bruce Luis');

insert into producto (id, nombre) values (10,'reja de madera 2x50');

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (2,'cortar madera',1,10,20);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (3,'aflojar madera',2,16,30);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (4,'clavar clavos',3,10,30);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (5,'recortar puntas',4,15,20);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (6,'agujerear centro',5,25,40);



insert into producto_tareas (producto_id, tareas_id) values (10,2);
insert into producto_tareas (producto_id, tareas_id) values (10,3);
insert into producto_tareas (producto_id, tareas_id) values (10,4);
insert into producto_tareas (producto_id, tareas_id) values (10,5);
insert into producto_tareas (producto_id, tareas_id) values (10,6);



insert into producto (id, nombre) values (20,'mesa de pool');

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (7,'cortar patas',1,22,20);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (8,'ensamblar patas',2,30,30);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (9,'ensamblar mesa',3,5,30);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (10,'recortar puntas',4,20,20);

insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (11,'realizar huecos',5,30,40);
insert into tarea (id,nombre, orden,tiempo, tipo_equipo_id) values (12,'realizar huecos otra vez',6,5,40);



insert into producto_tareas (producto_id, tareas_id) values (20,7);
insert into producto_tareas (producto_id, tareas_id) values (20,8);
insert into producto_tareas (producto_id, tareas_id) values (20,9);
insert into producto_tareas (producto_id, tareas_id) values (20,10);
insert into producto_tareas (producto_id, tareas_id) values (20,11);
insert into producto_tareas (producto_id, tareas_id) values (20,12);

