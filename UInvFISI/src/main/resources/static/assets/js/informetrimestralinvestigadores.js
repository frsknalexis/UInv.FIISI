/**
 * 
 */

$(document).on('ready', function() {
	
	$('#dataTable tbody').on('click', 'button#adjuntarInformeTrimestral', function() {
		
		var asignacionDetalleId = $(this).attr('idasignaciondetalle');
		console.log("asignacionDetalleId: " + asignacionDetalleId);
		
		localStorage.setItem("asignacionDetalleId", asignacionDetalleId);
		
		$(location).attr('href', '/informeTrimestral/formInformeTrimestral/' + asignacionDetalleId);
	});
	
	$('#dataTable tbody').on('click', 'button#detalleInvestigador', function() {
		
		var asignacionDetalleId = $(this).attr('idasignaciondetalle');
		
		console.log("asignacionDetalleId: " + asignacionDetalleId);
		
		$('#modalDetalleInvestigador').modal('show');
		
		var contenidoInvestigadores = document.querySelector('#contenidoInvestigadores');
		
		var contenidoInvestigadoresInformesTrimestrales = document.querySelector('#contenidoInvestigadoresInformesTrimestrales');
		
		contenidoInvestigadores.innerHTML = '';
		$.ajax({
			
			type: 'GET',
			url: '/api/asignaciondocente/asignaciondocente/' + asignacionDetalleId,
			dataType: 'json',
			success: function(response) {
				console.log(response);
				$('#titleDetalleInvestigadorModal').html('Investigador: ' + response.investigador);
				contenidoInvestigadores.innerHTML += '<tr><td>' + response.investigador + '</td><td>' + response.facultad.nombreFacultad + '</td><td>' + response.condicion.nombreCondicion + '</td></tr>';
				$('#alertDetalleInvestigadoresInformesTrimestrales').html('Aun no hay Informes Trimestrales adjuntados por el Investigador: <strong>' + response.investigador + '</strong>');
				
				$.ajax({
					
					type: 'GET',
					url: '/api/informetrimestral/informestrimestrales/asignacionDocente/' + asignacionDetalleId,
					dataType: 'json',
					success: function(response) {
						
						if(response != null) {
							
							$('#tablaInvestigadoresInformesTrimestrales').show();
							$('#alertDetalleInvestigadoresInformesTrimestrales').hide();
							
							contenidoInvestigadoresInformesTrimestrales.innerHTML = '';
							for(var i = 0; i < response.length; i++) {
								
								console.log(response[i]);
								contenidoInvestigadoresInformesTrimestrales.innerHTML += '<tr>';
								contenidoInvestigadoresInformesTrimestrales.innerHTML += '<td>' + (i+1) + '</td><td>' + response[i].descripcion + '</td><td>' + response[i].trimestre + '</td><td>' + response[i].nombreFichero + '</td><td><div class="btn-group" role="group"><a href="/api/informetrimestral/view/' + response[i].nombreFichero +  '" target="_blank" class="btn btn-outline-info btn-xs"><i class="fa fa-eye" title="Visualizar PDF"></i></a><button id="downloadFileInformeTrimestral" nombreFicheroInformeTrimestral="' + response[i].nombreFichero +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-download" title="Descargar"></i></button></div></td>';
								contenidoInvestigadoresInformesTrimestrales.innerHTML += '</tr>';
							}
						}
						else {
							
							console.log('No hay datos');
							$('#tablaInvestigadoresInformesTrimestrales').hide();
							$('#alertDetalleInvestigadoresInformesTrimestrales').show();
						}
					}
				});
			}
		});
	});
	
	$('#tablaInvestigadoresInformesTrimestrales tbody').on('click', 'button#downloadFileInformeTrimestral', function() {
		
		var nombreFicheroInformeTrimestral = $(this).attr('nombreficheroinformetrimestral');
		console.log("nombreFicheroInformeTrimestral: " + nombreFicheroInformeTrimestral);
		
		$(location).attr('href', '/api/informetrimestral/download/' + nombreFicheroInformeTrimestral);
	});
});