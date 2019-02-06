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
alter sequence tbl_trabajo_trabajo_id_seq rename to trabajo_seq

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
alter sequence tbl_autor_autor_id_seq rename to autor_seq