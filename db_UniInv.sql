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

create table tbl_informes_asignacion(
informe_asignacion_id serial not null,
asignacion_id int,
asunto varchar(100),
nombre_fichero varchar(75),
tamanio_fichero varchar(10),
formato_fichero varchar(20),
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_informes_asignacion primary key(informe_asignacion_id),
constraint fk_informes_asignacion_asignacion foreign key(asignacion_id) references tbl_asignacion(asignacion_id)
);

alter sequence tbl_informes_asignacion_informe_asignacion_id_seq rename to informe_asignacion_seq;

create table tbl_informes_trimestrales(
informe_trimestral_id serial not null,
asignacion_detalle_id int,
descripcion varchar(100),
trimestre varchar(50),
nombre_fichero varchar(150),
tamanio_fichero int,
formato_fichero varchar(150),
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_informe_trimestral primary key(informe_trimestral_id),
constraint fk_informe_trimestral_asignacion_detalle foreign key(asignacion_detalle_id) references tbl_asignacion_detalle(asignacion_detalle_id)
);

alter sequence tbl_informes_trimestrales_informe_trimestral_id_seq rename to informe_trimestral_seq;


create table tbl_escuela
(
escuela_id serial not null,
nombre_escuela varchar(150) not null,
director_escuela varchar(150) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_escuela primary key (escuela_id)
);

alter sequence tbl_escuela_escuela_id_seq rename to escuela_seq;

create  table tbl_asignatura
(
asignatura_id serial not null,
nombre_asignatura varchar(200) not null,
escuela_id int,
nombre_docente varchar(100) not null,
periodo varchar(25) not null,
ciclo varchar(25) not null,
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_asignatura primary key(asignatura_id),
constraint fk_asignatura_escuela foreign key(escuela_id) references tbl_escuela(escuela_id)
);

alter sequence tbl_asignatura_asignatura_id_seq rename to asignatura_seq;

create  table tbl_asignatura_detalle
(
asignatura_detalle_id serial not null,
alumno varchar(200) not null,
asignatura_id int,
documento_id int,
nro_documento varchar(10) not null,
asunto varchar(100),
nombre_fichero varchar(100),
tamanio_fichero varchar(10),
formato_fichero varchar(40),
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_asignatura_detalle primary key(asignatura_detalle_id),
constraint fk_asignatura_detalle_documento foreign key(documento_id) references tbl_documentos(documento_id),
constraint fk_asignatura_detalle_asignatura foreign key(asignatura_id) references tbl_asignatura(asignatura_id)
);

alter sequence tbl_asignatura_detalle_asignatura_detalle_id_seq rename to asignatura_detalle_seq;

create table tbl_reglamento(
reglamento_id serial not null,
asunto varchar(100),
nombre_fichero varchar(100),
tamanio_fichero varchar(10),
formato_fichero varchar(40),
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_reglamento primary key(reglamento_id)
);

alter sequence tbl_reglamento_reglamento_id_seq rename to reglamento_seq;

CREATE TABLE tbl_trabajo
(
  trabajo_id serial not null,
  nombre character varying(150) NOT NULL,
  tipo_trabajo character varying(50) NOT NULL,
  cita_trabajo character varying(20) NOT NULL,
  grado_academico character varying(50) NOT NULL,
  denominacion character varying(100) NOT NULL,
  escuela_id integer,
  fecha_sustentacion date,
  anio integer,
  area_conocimiento character varying(100) NOT NULL,
  cantidad_hojas integer NOT NULL,
  fecha_registro timestamp without time zone,
  fecha_modificacion timestamp without time zone,
  habilitado boolean DEFAULT true,
  CONSTRAINT pk_trabajos PRIMARY KEY (trabajo_id),
  CONSTRAINT fk_trabajoesc FOREIGN KEY (escuela_id)
  REFERENCES tbl_escuela (escuela_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter sequence tbl_trabajo_trabajo_id_seq rename to trabajo_seq;

CREATE TABLE tbl_autor
(
  autor_id serial not null,
  trabajo_id integer,
  nombre character varying(150) NOT NULL,
  nro_documento character varying(10),
  celular character varying(12),
  email character varying(50),
  documento_id integer,
  condicion_id integer,
  fecha_registro timestamp without time zone,
  fecha_modificacion timestamp without time zone,
  habilitado boolean DEFAULT true,
  CONSTRAINT pk_autor PRIMARY KEY (autor_id),
  CONSTRAINT fk_autorcon FOREIGN KEY (condicion_id)
      REFERENCES tbl_condicion (condicion_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_autordoc FOREIGN KEY (documento_id)
      REFERENCES tbl_documentos (documento_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter sequence tbl_autor_autor_id_seq rename to autor_seq;


CREATE TABLE tbl_informes_trabajo
(
  informe_trabajo_id serial not null,
  trabajo_id integer,
  asunto character varying(100),
  nombre_fichero character varying(75),
  tamanio_fichero character varying(10),
  formato_fichero character varying(75),
  fecha_registro timestamp without time zone,
  fecha_modificacion timestamp without time zone,
  habilitado boolean DEFAULT true,
  CONSTRAINT pk_informes_trabajo PRIMARY KEY (informe_trabajo_id),
  CONSTRAINT fk_trabajoinf FOREIGN KEY (trabajo_id) references tbl_trabajo (trabajo_id)
);

alter sequence tbl_informes_trabajo_informe_trabajo_id_seq rename to informe_trabajo_seq;

create table tbl_usuarios(
usuario_id serial not null,
nombre varchar(50),
apellidos varchar(75),
email varchar(45),
password varchar(100),
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_usuario primary key(usuario_id)
);

alter sequence tbl_usuarios_usuario_id_seq rename to usuario_seq;

create table tbl_roles(
rol_id serial not null,
nombre varchar(45),
descripcion varchar(100),
fecha_registro timestamp,
fecha_modificacion timestamp,
habilitado boolean default true,
constraint pk_rol primary key(rol_id)
);

alter sequence tbl_roles_rol_id_seq rename to rol_seq;
insert into tbl_roles(nombre, descripcion) values('ADMIN_USER', 'Este usuario, tiene acceso a todo el sistema');

create table tbl_usuario_roles(
usuario_id int,
rol_id int,
constraint pk_usuario_rol primary key(usuario_id, rol_id),
constraint fk_usuario foreign key(usuario_id) references tbl_usuarios(usuario_id),
constraint fk_rol foreign key(rol_id) references tbl_roles(rol_id)
);