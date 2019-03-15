select * from tbl_programa;
select count(*) from tbl_programa;

select extract(day from fecha_registro), COUNT (*) as programa from tbl_programa group by extract(day from fecha_registro);

select * from tbl_linea_investigacion;

select p.nombre_programa, COUNT(*) as LINEAS_INVESTIGACION from tbl_programa p 
inner join tbl_linea_investigacion li on p.programa_id = li.programa_id group by p.nombre_programa;

select li.nombre_linea_investigacion, COUNT(*) as LINEAS_INVESTIGACION from tbl_programa p 
inner join tbl_linea_investigacion li on p.programa_id = li.programa_id group by li.nombre_linea_investigacion;