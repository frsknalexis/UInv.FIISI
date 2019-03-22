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
				$('#alertDetalleInvestigacionInformesTrimestrales').html('Aun no hay Informes Trimestrales adjuntados al Proyecto de Investigacion: <strong>' + response.nombreInvestigacion + '</strong>');
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
						contenidoInvestigacionInformeInvestigadores.innerHTML += '<td>'+ response[i].investigador +'</td><td>'+ response[i].facultad.abreviaturaFacultad +'</td><td>'+ response[i].condicion.nombreCondicion +'</td>';
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
				
				console.log(response);
			}
		});
	});
});