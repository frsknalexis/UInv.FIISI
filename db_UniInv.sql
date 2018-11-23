create table tbl_documentos
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
facultad_id int,
condicion_id int,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_docente primary key (docente_id),
constraint fk_docente_categoria foreign key (categoria_docente_id) references tbl_categoria_docentes(categoria_docente_id),
constraint fk_docente_regimen foreign key (regimen_dedicacion_id) references tbl_regimen_dedicacion(regimen_dedicacion_id),
constraint fk_docente_documento foreign key (documento_id) references tbl_documentos(documento_id),
constraint fk_docente_facultad foreign key (facultad_id) references tbl_facultad(facultad_id),
constraint fk_docente_condicion foreign key (condicion_id) references tbl_condicion(condicion_id)
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


create table tbl_investigacion
(
investigacion_id serial not null,
linea_investigacion_id int,
nombre_investigacion varchar(200) not null,
fecha_publicacion date,
resumen_investigacion varchar(300) not null,
nombre_archivo varchar(150) not null,
tamanio_archivo varchar(10) not null,
formato_archivo varchar(10) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_investigacion primary key(investigacion_id),
constraint fk_investigacion_linea foreign key(linea_investigacion_id) references tbl_linea_investigacion(linea_investigacion_id)
);
alter sequence tbl_investigacion_investigacion_id_seq rename to investigacion_seq;

create table tbl_detalle_investigacion
(
detalle_investigacion_id serial not null,
docente_id int,
investigacion_id int,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_detalle_investigacion primary key(detalle_investigacion_id),
constraint fk_detalle_docente foreign key(docente_id) references tbl_docentes(docente_id),
constraint fk_detalle_investigacion_investigacion foreign key(investigacion_id) references tbl_investigacion(investigacion_id)
);
alter sequence tbl_detalle_investigacion_detalle_investigacion_id_seq rename to detalle_investigacion_seq;

