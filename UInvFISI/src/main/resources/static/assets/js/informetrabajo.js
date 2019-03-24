/**
 * 
 */
$(document).on('ready', function() {
	
	$('#cancelarInformeTrabajo').on('click', function() {
		
		localStorage.removeItem('trabajoId');
		$(location).attr('href', '/trabajo/list');
	});
	
	$('#fileInformeTrabajo').on('change', function() {
		
		var fileInput = $('#fileInformeTrabajo');
		var file = $('#fileInformeTrabajo').val();
		var extensionPermitida = ".pdf";
		var extension = (file.substring(file.lastIndexOf("."))).toLowerCase();
		
		if(extension == extensionPermitida) {
			
			fileInformeTrabajoPreview(this);
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
	
	function fileInformeTrabajoPreview(input) {
		
		if(input.files && input.files[0]) {
			
			var visor = new FileReader();
			visor.onload = function(e) {
				
				$('#visorInformeTrabajo').html('<embed src="' + e.target.result + '" width="100%" height="800">');
			};
			
			visor.readAsDataURL(input.files[0]);
		}
	}
	
	
	$('#guardarInformeTrabajo').on('click', function(e) {
		
		e.preventDefault();
		
		if($('#asuntoInformeTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var trabajoId = localStorage.getItem("trabajoId");
			console.log("trabajoId: " + trabajoId);
			
			var form = $('#formInformeTrabajo')[0];
			
			var data = new FormData(form);
			
			var formData = {
					informeTrabajoId: $('#informeTrabajoId').val(),
					asunto: $('#asuntoInformeTrabajo').val()
			}
			
			data.append("informeTrabajoJson", JSON.stringify(formData));
			
			console.log(formData);
			
			if(formData.informeTrabajoId) {
				
				var informeTrabajoId = formData.informeTrabajoId;
				console.log("informeTrabajoId: " + informeTrabajoId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/informetrabajo/update/trabajo/' + trabajoId + '/informeTrabajo/' + informeTrabajoId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe Academico Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/archivo/formInformeTrabajo/' + trabajoId);
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Informe Academico');
					}
				});
			}
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/informetrabajo/save/trabajo/' + trabajoId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe Academico: " + response.data.asunto +" Cargado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/archivo/formInformeTrabajo/' + trabajoId);
							}
						});
					},
					error: function() {
						alert('Error al Cargar Informe Academico');
					}
				});
			}
		}
		
		if($('#asuntoInformeTrabajo').val() == "" || $('#asuntoInformeTrabajo').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Descripcion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#asuntoInformeTrabajo').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#asuntoInformeTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Descripcion no permite caracteres especiales'
	            });
				
				$('#asuntoInformeTrabajo').val("");
				$('#asuntoInformeTrabajo').focus();
		    	return false;
			}
		}
	});
	
	$('#listarInformesTrabajosPorTrabajo').on('click', function() {
		
		var trabajoId = $(this).attr('idtrabajo');
		console.log("trabajoId: " + trabajoId);
		
		$('#modalListadoInformesTrabajosPorTrabajo').modal('show');
		
		var contenidoInformesTrabajosPorTrabajo = document.querySelector('#contenidoInformesTrabajosPorTrabajo');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/trabajo/trabajo/' + trabajoId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				$('#titleListadoInformesTrabajosPorTrabajoModal').html(response.nombre);
				$('#alertInformesTrabajosPorTrabajo').html('Aun no hay ficheros asociados al Trabajo: <strong>' + response.nombre + '</strong>')
			}
		});
		
		$.ajax({
			
			type: 'GET',
			url: '/api/informetrabajo/informestrabajos/trabajo/' + trabajoId,
			dataType: 'json',
			success: function(response) {
				
				if(response != null) {
					
					$('#tablaInformesTrabajosPorTrabajo').show();
					$('#alertInformesTrabajosPorTrabajo').hide();
					
					contenidoInformesTrabajosPorTrabajo.innerHTML = '';
					for(var i = 0; i < response.length; i++) {
						
						console.log(response[i]);
						contenidoInformesTrabajosPorTrabajo.innerHTML += '<tr>';
						contenidoInformesTrabajosPorTrabajo.innerHTML += '<td>'+ (i+1) +'</td><td>'+ response[i].asunto +'</td><td>'+ response[i].nombreFichero +'</td><td><div class="btn-group" role="group"><a href="/api/informetrabajo/view/'+ response[i].nombreFichero +'" target="_blank"  class="btn btn-outline-info btn-xs"><i class="fa fa-eye" title="Visualizar PDF"></i></a><button id="downloadFileInformeTrabajo" nombreFicheroInformeTrabajo="' + response[i].nombreFichero +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-download" title="Descargar"></i></button><a href="/archivo/formInformeTrabajo/'+ response[i].trabajo.trabajoId +'/'+ response[i].informeTrabajoId +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-pencil" title="Editar"></i></a></div></td>';
						contenidoInformesTrabajosPorTrabajo.innerHTML += '</tr>';
					}
				}
				else {
					
					$('#tablaInformesTrabajosPorTrabajo').hide();
					$('#alertInformesTrabajosPorTrabajo').show();
					console.log('No hay datos');
				}
			}
		});
	});
	
	$('#tablaInformesTrabajosPorTrabajo tbody').on('click', 'button#downloadFileInformeTrabajo', function() {
		
		var nombreFicheroInformeTrabajo = $(this).attr('nombreficheroinformetrabajo');
		console.log("nombreFicheroInformeTrabajo: " + nombreFicheroInformeTrabajo);
		
		$(location).attr('href', '/api/informetrabajo/download/' + nombreFicheroInformeTrabajo);
	});
});