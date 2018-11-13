Select [Local],[Fecha],[Producto],
SUM(CAST(v.[Venta_Monto] as decimal(18,2))) as Venta_Monto,
SUM(CAST(v.[Venta_Unidades] as int)) as Venta_Unidades,
AVG(CAST(v.[Venta_Monto] as decimal(18,2))) as Prom_Monto_Dia,
AVG(CAST(v.[Venta_Unidades] as int)) as Prom_Unidades_Dia
from [DB_FAPS].[dbo].[FAPS_Venta_Producto] v
group by [Local],[Fecha],[Producto]
order by [Local],[Fecha],[Producto]

select s.[Local],s.[Fecha],s.[Producto],s.[Stock_Minimo_Unid],s.[Stock_Valorizado],s.[Stock_Unidades],
case when s.[Stock_Unidades]>0 then 1 else 0 end Stock_Mayor_Cero,
ISNULL((select SUM(CAST(v.[Venta_Monto] as decimal(18,2)))from [dbo].[FAPS_Venta_Producto] v where s.[Local]=v.[Local] and s.Fecha=v.Fecha and s.Producto=v.Producto),0) as Venta_Monto, 
ISNULL((select SUM(CAST(v.[Venta_Unidades] as int))from [dbo].[FAPS_Venta_Producto] v where s.[Local]=v.[Local] and s.Fecha=v.Fecha and s.Producto=v.Producto),0) as Venta_Unidades,
ISNULL((select AVG(CAST(v.[Venta_Monto] as decimal(18,2)))from [dbo].[FAPS_Venta_Producto] v where s.[Local]=v.[Local] and s.Fecha=v.Fecha and s.Producto=v.Producto),0) as Prom_Monto_Dia,
ISNULL((select AVG(CAST(v.[Venta_Unidades] as int))from [dbo].[FAPS_Venta_Producto] v where s.[Local]=v.[Local] and s.Fecha=v.Fecha and s.Producto=v.Producto),0) as Prom_Unidades_Dia,
IND_Stock_Unidades_Menor_Stock_Minimo = Case when ISNULL(s.Stock_Unidades, 0)<s.Stock_Minimo_Unidad then 1 else 0 end,
case when Catalogado = 0 and ISNULL((select AVG(CAST(v.[Venta_Monto] as decimal(18,2)))from [dbo].[FAPS_Venta_Producto] v where s.[Local]=v.[Local] and s.Fecha=v.Fecha and s.Producto=v.Producto),0) > 0
AND Stock_Unidades = 0 then 1 else 0 end Quiebre

From [dbo].[FAPS_Stock_Producto] s inner join [dbo].[FAPS_Surtido] v
on s.[Local]=v.[Local] and s.Fecha=v.Fecha and s.Producto=v.Producto
order by 1,2,3

