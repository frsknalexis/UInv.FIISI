/**
 * 
 */
$(document).on('ready', function() {
	
	$('#fileInformeInvestigacion').on('change', function() {
		
		var fileInput = $('#fileInformeInvestigacion');
		
		var file = $('#fileInformeInvestigacion').val();
		
		var extensionPermitida = ".pdf";
		
		var extension = (file.substring(file.lastIndexOf("."))).toLowerCase();
		
		if(extension == extensionPermitida) {
			
			fileInformeInvestigacionPreview(this);
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
	
	function fileInformeInvestigacionPreview(input) {
		
		if(input.files && input.files[0]) {
			
			var visor = new FileReader();
			visor.onload = function(e) {
				
				$('#visorInformeInvestigacion').html('<embed src="' + e.target.result + '" width="100%" height="800">');
			};
			
			visor.readAsDataURL(input.files[0]);
		}
	}
	
	$('#guardarInformeInvestigacion').click(function(e) {
		
		e.preventDefault();
		
		if($('#asuntoInformeInvestigacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var asignacionId = localStorage.getItem("asignacionId");
			console.log("asignacionId: " + asignacionId);
			
			var form = $('#formInformeInvestigacion')[0];
			
			var data = new FormData(form);
			
			var formData = {
				
					informeAsignacionId: $('#informeInvestigacionId').val(),
					asunto: $('#asuntoInformeInvestigacion').val()
			};
			
			data.append("informeJson", JSON.stringify(formData));
			
			console.log(formData);
			
			if(formData.informeAsignacionId) {
						
				var informeAsignacionId = formData.informeAsignacionId;
				console.log("informeAsignacionId: " + informeAsignacionId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/informeinvestigacion/update/asignacion/' + asignacionId + '/informeInvestigacion/' + informeAsignacionId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe Investigacion Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/informe/formInformeInvestigacion/' + asignacionId);
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Informe Investigacion');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/informeinvestigacion/save/asignacion/' + asignacionId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe: " + response.data.asunto +" Cargado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/informe/formInformeInvestigacion/' + asignacionId);
							}
						});
					},
					error: function() {
						alert('Error al Cargar Informe Investigacion');
					}
				});
			}
		}
		
		if($('#asuntoInformeInvestigacion').val() == "" || $('#asuntoInformeInvestigacion').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Descripcion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#asuntoInformeInvestigacion').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#asuntoInformeInvestigacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Descripcion no permite caracteres especiales'
	            });
				
				$('#asuntoInformeInvestigacion').val("");
				$('#asuntoInformeInvestigacion').focus();
		    	return false;
			} 
		}
	});
	
	$('#listarInformesInvestigacionPorAsignacionId').on('click', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: " + asignacionId);
		
		$('#modalListadoInformeInvestigacionPorAsignacion').modal('show');
		
		var contenidoInformeInvestigacionPorAsignacion = document.querySelector('#contenidoInformeInvestigacionPorAsignacion');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/informeinvestigacion/informes/asignacion/' + asignacionId,
			dataType: 'json',
			success: function(response) {
				
				if(response != null) {
					
					$('#tablaInformesInvestigacionPorAsignacion').show();
					$('#alertInformesInvestigacionPorAsignacion').hide();
					
					contenidoInformeInvestigacionPorAsignacion.innerHTML = '';
					for(var i = 0; i < response.length; i++) {
						console.log(response[i]);
						contenidoInformeInvestigacionPorAsignacion.innerHTML += '<tr>';
						contenidoInformeInvestigacionPorAsignacion.innerHTML += '<td>' + (i+1) +'</td><td>' + response[i].asunto + '</td><td>' + response[i].nombreFichero + '</td><td><div class="btn-group" role="group"><a href="/api/informeinvestigacion/view/' + response[i].nombreFichero + '" target="_blank" class="btn btn-outline-info btn-xs"><i class="fa fa-eye" title="Visualizar PDF"></i></a><button id="downloadFileInformeInvestigacion" nombreFicheroInformeInvestigacion="' + response[i].nombreFichero +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-download" title="Descargar"></i></button><a href="/informe/formInformeInvestigacion/' + response[i].asignacion.asignacionId + '/' + response[i].informeAsignacionId + '" class="btn btn-outline-primary btn-xs"><i class="fa fa-pencil" title="Editar"></i></a></div></td>'; 
						contenidoInformeInvestigacionPorAsignacion.innerHTML += '</tr>'
					}
				}
				
				else {
					console.log('No hay datos');
					$('#tablaInformesInvestigacionPorAsignacion').hide();
					$('#alertInformesInvestigacionPorAsignacion').show();
				}				
			}
		});
	});
	
	$('#cancelarInformeInvestigacion').on('click', function() {
		
		localStorage.removeItem("asignacionId");
		$(location).attr('href', '/asignacion/list');
	});
	
	$('#tablaInformesInvestigacionPorAsignacion tbody').on('click', 'button#downloadFileInformeInvestigacion', function() {
		
		var nombreFicheroInformeInvestigacion = $(this).attr('nombreficheroinformeinvestigacion');
		
		console.log('nombreFicheroInformeInvestigacion: ' + nombreFicheroInformeInvestigacion);
		
		$(location).attr('href', '/api/informeinvestigacion/download/' + nombreFicheroInformeInvestigacion);
	});
});