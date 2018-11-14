﻿SELECT 
  tbl_docentes.apellidos_docente,
  tbl_docentes.nombres_docente,
  tbl_categoria_docentes.nombre_categoria,
  tbl_documentos.abreviatura,
  tbl_docentes.nro_documento, 
  tbl_regimen_dedicacion.nombre_regimen, 
  tbl_docentes.dina_datos_academicos,
  tbl_docentes.dina_proyectos_investigacion, 
  tbl_docentes.codigo_orcid, 
  tbl_docentes.publicaciones_orcid
FROM 
  tbl_categoria_docentes, 
  public.tbl_docentes, 
  public.tbl_documentos, 
  public.tbl_regimen_dedicacion
WHERE 
  tbl_categoria_docentes.categoria_docente_id = tbl_docentes.categoria_docente_id AND
  tbl_documentos.documento_id = tbl_docentes.documento_id AND
  tbl_regimen_dedicacion.regimen_dedicacion_id = tbl_docentes.regimen_dedicacion_id;

////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////
  
SELECT 
  tbl_docentes.apellidos_docente, 
  tbl_docentes.nombres_docente,  
  tbl_categoria_docentes.nombre_categoria, 
  tbl_documentos.abreviatura,
  tbl_docentes.nro_documento, 
  tbl_regimen_dedicacion.nombre_regimen,
  tbl_docentes.dina_datos_academicos,
  tbl_docentes.dina_proyectos_investigacion, 
  tbl_docentes.codigo_orcid, 
  tbl_docentes.publicaciones_orcid
  
FROM 
  tbl_docentes inner join tbl_categoria_docentes ON tbl_docentes.categoria_docente_id = tbl_categoria_docentes.categoria_docente_id inner join
  tbl_regimen_dedicacion on tbl_docentes.regimen_dedicacion_id = tbl_regimen_dedicacion.regimen_dedicacion_id inner join
  tbl_documentos on tbl_docentes.documento_id = tbl_documentos.documento_id;
  
   
