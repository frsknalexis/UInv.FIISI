﻿create table tbl_documentos
(
documento_id serial not null,
abreviatura varchar(20) not null,
nombre_documento varchar(45) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_documento primary key (documento_id)
);
alter sequence tbl_documentos_documento_id_seq rename to documento_seq


create table tbl_categoria_docentes
(
categoria_docente_id serial not null,
nombre_categoria varchar(50) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_categoria_docente primary key (categoria_docente_id)
);
alter sequence tbl_categoria_docentes_categoria_docente_id_seq rename to categoria_seq

create table tbl_regimen_dedicacion
(
regimen_dedicacion_id serial not null,
nombre_regimen varchar(50) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_regimen_dedicacion primary key (regimen_dedicacion_id)
);
alter sequence tbl_regimen_dedicacion_regimen_dedicacion_id_seq rename to regimen_seq

create table tbl_facultad
(
facultad_id serial not null,
nombre_facultad varchar(150) not null,
abreviatura_facultad varchar(10) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_facultad primary key (facultad_id)
);
alter sequence tbl_facultad_facultad_id_seq rename to facultad_seq

create table tbl_condicion
(
condicion_id serial not null,
nombre_condicion varchar(45) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_condicion primary key (condicion_id)
);
alter sequence tbl_condicion_condicion_id_seq rename to condicion_seq

create table tbl_docentes
(
docente_id serial not null,
apellidos_docente varchar(100) not null,
nombres_docente varchar(50)not null,
nro_documento varchar(8) not null,
categoria_docente_id int,
regimen_dedicacion_id int,
DINA_datos_academicos varchar(5) not null,
DINA_proyectos_investigacion varchar(5) not null,
documento_id int,
codigo_ORCID varchar(100) not null,
publicaciones_ORCID int not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_docente primary key (docente_id),
constraint fk_docente_categoria foreign key (categoria_docente_id) references tbl_categoria_docentes(categoria_docente_id),
constraint fk_docente_regimen foreign key (regimen_dedicacion_id) references tbl_regimen_dedicacion(regimen_dedicacion_id),
constraint fk_docente_documento foreign key (documento_id) references tbl_documentos(documento_id)
);
alter sequence tbl_docentes_docente_id_seq rename to docente_seq


create table tbl_programa
(
programa_id serial not null,
nombre_programa varchar(200) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_programa primary key(programa_id)
);
alter sequence tbl_programa_programa_id_seq rename to programa_seq;

create table tbl_linea_investigacion
(
linea_investigacion_id serial not null,
nombre_linea_investigacion varchar(200) not null,
programa_id int,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_linea_investigacion primary key(linea_investigacion_id),
constraint fk_linea_programa foreign key(programa_id) references tbl_programa(programa_id)
);
alter sequence tbl_linea_investigacion_linea_investigacion_id_seq rename to linea_investigacion_seq;

create  table tbl_asignacion
(
asignacion_id serial not null,
nombre_investigacion varchar(500) not null,
linea_investigacion_id int,
anio integer,
fecha_inicio date,
fecha_fin date,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_asignacion primary key(asignacion_id),
constraint fk_asignacion_linea foreign key(linea_investigacion_id) references tbl_linea_investigacion(linea_investigacion_id)
);
alter sequence tbl_asignacion_asignacion_id_seq rename to asignacion_seq;

create table tbl_asignacion_detalle
(
asignacion_detalle_id serial not null,
asignacion_id int,
investigador varchar(150),
condicion_id int,
facultad_id int,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_asignacion_detalle primary key(asignacion_detalle_id),
constraint fk_asignacion_detalle_asignacion foreign key(asignacion_id) references tbl_asignacion(asignacion_id),
constraint fk_asignacion_detalle_condicion foreign key(condicion_id) references tbl_condicion(condicion_id),
constraint fk_asignacion_detalle_facultad foreign key(facultad_id) references tbl_facultad(facultad_id)
); 

alter sequence tbl_asignacion_detalle_asignacion_detalle_id_seq rename to asignacion_detalle_seq;

select * from tbl_asignacion_detalle;
