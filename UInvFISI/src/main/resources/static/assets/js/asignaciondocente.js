/**
 * 
 */

$(document).on('ready', function() {
	
	$('#guardarAsignacionDocente').click(function(e) {
		
		e.preventDefault();
		
		if($('#docenteAsignacion').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)
				&& $('#facultadAsignacion').val().trim() != "" && $('#condicionDocenteAsignacion').val().trim() != "") {
			
			var asignacionId = localStorage.getItem("asignacionId");
			console.log("asignacionId: " + asignacionId);
			
			var formData = {
					asignacionDetalleId: $('#asignacionDocente-id').val(),
					investigador: $('#docenteAsignacion').val(),
					facultad: {
						facultadId: $('#facultadAsignacion').val()
					},
					condicion: {
						condicionId: $('#condicionDocenteAsignacion').val()
					}
			};
			
			console.log(formData);
			if(formData.asignacionDetalleId) {
				var asignacionDetalleId = formData.asignacionDetalleId;
				console.log("asignacionDetalleId: " + asignacionDetalleId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/asignaciondocente/update/asignacion/' + asignacionId + '/asignaciondocente/' + asignacionDetalleId,
					headers: {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					data: JSON.stringify(formData),
					dataType: 'json',
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Asignacion Investigador Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/asignacionDocente/formAsignacionDetalle/' + asignacionId);
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Asignacion Investigador');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/asignaciondocente/save/asignacion/' + asignacionId,
					headers: {
						"Content-Type": "application/json",
						"Accept": "application/json"
					},
					data: JSON.stringify(formData),
					dataType: 'json',
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Investigador: " + response.data.investigador + " Asignado a Investigacion: " + response.data.asignacion.nombreInvestigacion + " con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/asignacionDocente/formAsignacionDetalle/' + asignacionId);
							}
						});
					},
					error: function() {
						alert('Error al Asignar Investigador a Investigacion');
					}
				});
			}
		}
		
		if($('#docenteAsignacion').val() == "" && $('#facultadAsignacion').val().trim() == ""
			 && $('#condicionDocenteAsignacion').val().trim() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#docenteAsignacion').val() == "" || $('#docenteAsignacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Investigador no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#docenteAsignacion').focus();
		    	return false;
			}
			
			if($('#facultadAsignacion').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Facultad'
	            });
				
		    	return false;
			}
			
			if($('#condicionDocenteAsignacion').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Condicion Docente'
	            });
				
		    	return false;
			}
			
			if(!($('#docenteAsignacion').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Investigador no permite caracteres especiales ni numeros'
	            });
				
				$('#docenteAsignacion').val('');
				$('#docenteAsignacion').focus();
		    	return false;
			}
		}
	});
	
	$('#listarInvestigadoresPorAsignacion').on('click', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: " + asignacionId);
		
		$('#modalListadoInvestigadoresPorAsignacion').modal('show');
	});
	
	$('#cancelarAsignacionDocente').on('click', function() {
		
		localStorage.removeItem("asignacionId");
		$(location).attr('href', '/asignacion/list');
	});
	
	$('#docenteAsignacion').autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				
				type: 'GET',
				url: '/api/docente/docentes/autocomplete/' + request.term,
				dataType: 'json',
				data: {
					term: request.term
				},
				success: function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.docenteId,
							label: item.nombresDocente + ', ' + item.apellidosDocente
						};
					}));
					
				}
			});
		},
		
		select: function(event, ui) {
			$('#docenteAsignacion').val(ui.item.label);
			return false;
		}
	});
});