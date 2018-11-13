create table dbo.DIM_CUMPLIMIENTO
(
	Cod_Cumplimiento tinyint,
	Desc_Cumplimiento varchar(50) 
);

create table dbo.DIM_LOCAL
(
	Cod_Local varchar(50),
	Nombre_Local varchar(50)
);

create table dbo.DIM_QUIEBRE
(
	Cod_Quiebre int,
	Desc_Quiebre varchar(50)
);

create table dbo.DIM_STOCK_MAYOR_CERO
(
	Dim_Stock_Mayor_Cero int,
	Desc_St_Cero varchar(50)
);

create table dbo.DIM_STOCK_MAYOR_STOCK_MIN
(
	Cod_St_Min int,
	Desc_St_Min varchar(50)
);

create table dbo.DIM_SURTIDO
(
	Cod_Surtido tinyint,
	Desc_Surtido varchar(50)
);