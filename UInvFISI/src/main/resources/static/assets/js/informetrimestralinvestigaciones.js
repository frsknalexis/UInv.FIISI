/**
 * 
 */
$(document).on('ready', function() {
	
	$('#dataTable tbody').on('click', 'button#detalleInvestigacion', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: " + asignacionId);
		
		$('#modalDetalleInvestigacionInforme').modal('show');
		
		var contenidoInvestigacion = document.querySelector('#contenidoInvestigacion');
		
		var contenidoInvestigacionInformeInvestigadores = document.querySelector('#contenidoInvestigacionInformeInvestigadores');
		
		var contenidoInvestigacionInformeInformesTrimestrales = document.querySelector('#contenidoInvestigacionInformeInformesTrimestrales');
		
		contenidoInvestigacion.innerHTML = '';
		
		$.ajax({
			
			type: 'GET',
			url: '/api/asignacioninvestigacion/investigacion/' + asignacionId,
			dataType: 'json',
			success: function(response) {
				console.log(response);
				
				$('#titleDetalleInvestigacionInformeModal').html('PROYECTO DE INVESTIGACION ' + response.anio);
				$('#detalleNombreInvestigacionInforme').html(response.nombreInvestigacion);
				$('#alertDetalleInvestigacionInformeInvestigadores').html('Aun no hay Investigadores asignados al Proyecto de Investigacion: <strong>' + response.nombreInvestigacion + '</strong>');
				$('#alertDetalleInvestigacionInformeInformesTrimestrales').html('Aun no hay Informes Trimestrales adjuntados al Proyecto de Investigacion: <strong>' + response.nombreInvestigacion + '</strong>');
				contenidoInvestigacion.innerHTML += '<tr><td>' + response.lineaInvestigacion.programa.nombrePrograma + '</td><td>' + response.lineaInvestigacion.nombreLineaInvestigacion + '</td></tr>';
			}
		});
		
		$.ajax({
			
			type: 'GET',
			url: '/api/asignaciondocente/asignaciondocenteshabilitados/asignacion/' + asignacionId,
			dataType: 'json',
			success: function(response) {
				
				if(response != null) {
					
					$('#tablaInvestigacionInformeInvestigadores').show();
					$('#alertDetalleInvestigacionInformeInvestigadores').hide();
					
					contenidoInvestigacionInformeInvestigadores.innerHTML = '';
					for(var i = 0; i < response.length; i++) {
						console.log(response[i]);
						contenidoInvestigacionInformeInvestigadores.innerHTML += '<tr>';
						contenidoInvestigacionInformeInvestigadores.innerHTML += '<td>'+ (i+1) +'</td><td>'+ response[i].investigador +'</td><td>'+ response[i].facultad.abreviaturaFacultad +'</td><td>'+ response[i].condicion.nombreCondicion +'</td>';
						contenidoInvestigacionInformeInvestigadores.innerHTML += '</tr>';
					}
				}
				else {
					
					$('#tablaInvestigacionInformeInvestigadores').hide();
					$('#alertDetalleInvestigacionInformeInvestigadores').show();
					console.log('No hay datos');
				}
			}
		});
		
		$.ajax({
			
			type: 'GET',
			url: '/api/informetrimestral/informestrimestrales/asignacion/' + asignacionId,
			dataType: 'json',
			success: function(response) {
				
				if(response != null) {
					
					$('#tablaInvestigacionInformeInformesTrimestrales').show();
					$('#alertDetalleInvestigacionInformeInformesTrimestrales').hide();
					
					contenidoInvestigacionInformeInformesTrimestrales.innerHTML = '';
					for(var i = 0; i < response.length; i++) {
						console.log(response[i]);
						contenidoInvestigacionInformeInformesTrimestrales.innerHTML += '<tr>';
						contenidoInvestigacionInformeInformesTrimestrales.innerHTML += '<td>'+ (i+1) +'</td><td>'+ response[i].descripcion +'</td><td>'+ response[i].trimestre +'</td><td>'+ response[i].nombreFichero +'</td><td><div class="btn-group" role="group"><a href="/api/informetrimestral/view/' + response[i].nombreFichero +  '" target="_blank" class="btn btn-outline-info btn-xs"><i class="fa fa-eye" title="Visualizar PDF"></i></a><button id="downloadFileInformeTrimestral" nombreFicheroInformeTrimestral="' + response[i].nombreFichero +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-download" title="Descargar"></i></button></div></td>';
						contenidoInvestigacionInformeInformesTrimestrales.innerHTML += '</tr>';
					}
				}
				else {
					
					$('#tablaInvestigacionInformeInformesTrimestrales').hide();
					$('#alertDetalleInvestigacionInformeInformesTrimestrales').show();
					console.log('No hay datos');
				}
				
			}
		});
	});
	
	$('#tablaInvestigacionInformeInformesTrimestrales tbody').on('click', 'button#downloadFileInformeTrimestral', function() {
		
		var nombreFicheroInformeTrimestral = $(this).attr('nombreficheroinformetrimestral');
		console.log('nombreFicheroInformeTrimestral: ' + nombreFicheroInformeTrimestral);
		
		$(location).attr('href', '/api/informetrimestral/download/' + nombreFicheroInformeTrimestral);
	});
});