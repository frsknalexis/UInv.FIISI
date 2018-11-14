alter sequence tbl_documentos_documento_id_seq rename to documento_seq
create table tbl_documentos
(
documento_id serial not null,
abreviatura varchar(20) not null,
nombre_documento varchar(20) not null,

constraint pk_documento primary key (documento_id)
);

alter sequence tbl_categoria_docentes_categoria_docente_id_seq rename to categoria_seq
create table tbl_categoria_docentes
(
categoria_docente_id serial not null,
nombre_categoria varchar(50) not null,
fecha_registro timestamp not null,
fecha_modificacion timestamp not null,

constraint pk_categoria_docente primary key (categoria_docente_id)
);

alter sequence tbl_regimen_dedicacion_regimen_dedicacion_id_seq rename to regimend_seq
create table tbl_regimen_dedicacion
(
regimen_dedicacion_id serial not null,
nombre_regimen varchar(50) not null,
fecha_registro timestamp not null,
fecha_modificacion timestamp not null,

constraint pk_regimen_dedicacion primary key (regimen_dedicacion_id)

);

alter sequence tbl_docentes_docente_id_seq rename to docente_seq
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
documento_id int not null,
codigo_ORCID varchar(100) not null,
publicaciones_ORCID int,

Constraint pk_docente primary key (docente_id),
Constraint fk_categoriadoc foreign key (categoria_docente_id) references tbl_categoria_docentes (categoria_docente_id),
Constraint fk_regimendedicacion foreign key (regimen_dedicacion_id) references tbl_regimen_dedicacion (regimen_dedicacion_id),
Constraint fk_documento foreign key (documento_id) references tbl_documentos (documento_id)
);


