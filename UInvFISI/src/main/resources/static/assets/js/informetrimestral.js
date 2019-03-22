/**
 * 
 */
$(document).on('ready', function() {
	
	$('#fileInformeTrimestral').on('change', function() {
		
		var fileInput = $('#fileInformeTrimestral');
		
		var file = $('#fileInformeTrimestral').val();
		
		var extensionPermitida = ".pdf";
		
		var extension = (file.substring(file.lastIndexOf("."))).toLowerCase();
		
		if(extension == extensionPermitida) {
			
			fileInformeTrimestralPreview(this);
		}
		else {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Asegurate de haber selecciondo un archivo PDF'
            });
			
			fileInput.val("");
			return false;
		}
	});
	
	function fileInformeTrimestralPreview(input) {
		
		if(input.files && input.files[0]) {
			
			var visor = new FileReader();
			visor.onload = function(e) {
				$('#visorInformeTrimestral').html('<embed src="' + e.target.result + '" width="100%" height="700">');
			};
			
			visor.readAsDataURL(input.files[0]);
		}
	}
	
	
	$('#guardarInformeTrimestral').on('click', function(e) {
		
		e.preventDefault();
		
		if($('#descripcionInformeTrimestral').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) 
			&& $('#trimestreInformeTrimestral').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var asignacionDetalleId = localStorage.getItem("asignacionDetalleId");
			console.log("asignacionDetalleId: " + asignacionDetalleId);
			
			var form = $('#formInformeTrimestral')[0];
			var data = new FormData(form);
			
			var formData = {
				
					informeTrimestralId: $('#informeTrimestralId').val(),
					descripcion: $('#descripcionInformeTrimestral').val(),
					trimestre: $('#trimestreInformeTrimestral').val()
			};
			
			data.append("informeTrimestralJson", JSON.stringify(formData));
			
			console.log(formData);
			
			if(formData.informeTrimestralId) {
				
				var informeTrimestralId = formData.informeTrimestralId;
				console.log("informeTrimestralId: " + informeTrimestralId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/informetrimestral/update/asignacionDocente/' + asignacionDetalleId + '/informeTrimestral/' + informeTrimestralId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe Trimestral Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/informeTrimestral/formInformeTrimestral/' + asignacionDetalleId);
							}
						});
					},
					error: function() {
						alert('Erro al Actualizar Informe Trimestral');
					}
					
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/informetrimestral/save/asignacionDocente/' + asignacionDetalleId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: response.data.descripcion +" Cargado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/informeTrimestral/formInformeTrimestral/' + asignacionDetalleId);
							}
						});
					},
					error: function() {
						alert('Error al Cargar Informe Trimestral');
					}
				});
			}
		}
		
		if($('#descripcionInformeTrimestral').val() == "" && $('#trimestreInformeTrimestral').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#descripcionInformeTrimestral').val() == "" || $('#descripcionInformeTrimestral').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El Campo Descripcion Informe Trimestral no puede estar vacio, ingrese un valor'
	            });
		    	
		    	$('#descripcionInformeTrimestral').focus();
		    	return false;
			}
			
			if($('#trimestreInformeTrimestral').val() == "" || $('#trimestreInformeTrimestral').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El Campo Trimestre Informe Trimestral no puede estar vacio, ingrese un valor'
	            });
		    	
		    	$('#trimestreInformeTrimestral').focus();
		    	return false;
			}
			
			if(!($('#descripcionInformeTrimestral').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Descripcion Informe Trimestral no permite caracteres especiales'
	            });
				
				$('#descripcionInformeTrimestral').val("");
				$('#descripcionInformeTrimestral').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#trimestreInformeTrimestral').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Trimestre Informe Trimestral no permite caracteres especiales'
		            });
					
					$('#trimestreInformeTrimestral').val("");
					$('#trimestreInformeTrimestral').focus();
			    	return false;
				}
			}
		}
	});
	
	$('#listarInformesTrimestralesPorAsignacionDocente').on('click', function() {
		
		var asignacionDetalleId = $(this).attr('idasignaciondetalle');
		console.log("asignacionDetalleId: " + asignacionDetalleId);
		
		setTimeout(
				"$('#modalListadoInformesTrimestralesPorAsignacionDocente').modal('show');", 
				1000);
		
		var contenidoInformesTrimestralesPorAsignacionDocente = document.querySelector('#contenidoInformesTrimestralesPorAsignacionDocente');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/asignaciondocente/asignaciondocente/' + asignacionDetalleId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				
				$('#titleListadoInformesTrimestralesPorAsignacionDocenteModal').html('Investigador encargado: ' + response.investigador);
				$('#alertListadoInformesTrimestralesPorAsignacionDocente').html('Aun no hay Informes Trimestrales adjuntados por el Investigador: <strong>' + response.investigador + '</strong>');
				
				$.ajax({
					
					type: 'GET',
					url: '/api/informetrimestral/informestrimestrales/asignacionDocente/' + asignacionDetalleId,
					dataType: 'json',
					success: function(response){
						
						if(response !=null) {
							
							$('#tablaInformesTrimestralesPorAsignacionDocente').show();
							$('#alertListadoInformesTrimestralesPorAsignacionDocente').hide();
							
							contenidoInformesTrimestralesPorAsignacionDocente.innerHTML = '';
							for(var i = 0; i < response.length; i++) {
								
								console.log(response[i]);
								contenidoInformesTrimestralesPorAsignacionDocente.innerHTML += '<tr>';
								contenidoInformesTrimestralesPorAsignacionDocente.innerHTML += '<td>' + (i+1) +'</td><td>' + response[i].descripcion +'</td><td>' + response[i].trimestre +'</td><td>' + response[i].nombreFichero + '</td><td><div class="btn-group" role="group"><a href="/api/informetrimestral/view/' + response[i].nombreFichero +  '" target="_blank" class="btn btn-outline-info btn-xs"><i class="fa fa-eye" title="Visualizar PDF"></i></a><button id="downloadFileInformeTrimestral" nombreFicheroInformeTrimestral="' + response[i].nombreFichero +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-download" title="Descargar"></i></button><a href="/informeTrimestral/formInformeTrimestral/' + response[i].asignacionDetalle.asignacionDetalleId + '/' + response[i].informeTrimestralId + '" class="btn btn-outline-primary btn-xs"><i class="fa fa-pencil" title="Editar"></i></a></div></td>';
								contenidoInformesTrimestralesPorAsignacionDocente.innerHTML += '</tr>';
							}
						}
						
						else {
							
							$('#tablaInformesTrimestralesPorAsignacionDocente').hide();
							$('#alertListadoInformesTrimestralesPorAsignacionDocente').show();
							console.log('No hay datos');
						}
					}
				});
			}
			
		});
	});
	
	$('#tablaInformesTrimestralesPorAsignacionDocente tbody').on('click', 'button#downloadFileInformeTrimestral', function() {
		
		var nombreFicheroInformeTrimestral = $(this).attr('nombreficheroinformetrimestral');
		console.log('nombreFicheroInformeTrimestral: ' + nombreFicheroInformeTrimestral);
		
		$(location).attr('href', '/api/informetrimestral/download/' + nombreFicheroInformeTrimestral);
	});
	
	$('#cancelarInformeTrimestral').on('click', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log('asignacionId: ' + asignacionId);
		
		localStorage.removeItem("asignacionDetalleId");
		
		$(location).attr('href', '/informeTrimestral/investigadores/' + asignacionId);
	});
});