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
		
		var contenidoListadoInvestigadoresPorAsignacion = document.querySelector('#contenidoListadoInvestigadoresPorAsignacion');
		
		$('#modalListadoInvestigadoresPorAsignacion').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/asignacioninvestigacion/investigacion/' + asignacionId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				
				$('#titleListadoInvestigadoresPorAsignacionModal').html(response.nombreInvestigacion);
				$('#listadoInvestigadoresPorAsignacion').html('Aun no hay Investigadores Asignados al Proyecto de Investigacion: <strong>' + response.nombreInvestigacion +'</strong>');
				
				$.ajax({
					
					type: 'GET',
					url: '/api/asignaciondocente/asignaciondocentes/asignacion/' + asignacionId,
					dataType: 'json',
					success: function(response) {
						
						if(response != null) {
							$('#tablaListadoInvestigadoresPorAsignacion').show();
							$('#listadoInvestigadoresPorAsignacion').hide();
							
							contenidoListadoInvestigadoresPorAsignacion.innerHTML = '';
							for(var i = 0; i < response.length; i++) {
								
								contenidoListadoInvestigadoresPorAsignacion.innerHTML += '<tr>';
								if(response[i].habilitado == true) {
									
									contenidoListadoInvestigadoresPorAsignacion.innerHTML += '<td>' + (i+1) +'</td><td>' + response[i].investigador + '</td><td>' + response[i].facultad.abreviaturaFacultad +'</td><td>' + response[i].condicion.nombreCondicion + '</td><td><span id="investigadorestado" class="status-p bg-success">Activo</span></td><td><div class="btn-group" role="group"><a href="/asignacionDocente/formAsignacionDetalle/' + response[i].asignacion.asignacionId + '/' + response[i].asignacionDetalleId + '" class="btn btn-outline-primary btn-xs"><i class="fa fa-pencil" title="Editar"></i></a><button id="disabledInvestigador" idasignaciondetalle="' + response[i].asignacionDetalleId + '" class="btn btn-outline-danger btn-xs"><i class="fa fa-close" title="Deshabilitar"></i></button></div></td>';
									console.log(response[i]);
								}
								if(response[i].habilitado == false) {
									console.log(response[i]);
									contenidoListadoInvestigadoresPorAsignacion.innerHTML += '<td>' + (i+1) +'</td><td>' + response[i].investigador + '</td><td>' + response[i].facultad.abreviaturaFacultad +'</td><td>' + response[i].condicion.nombreCondicion + '</td><td><span id="investigadorestado" class="status-p bg-danger">Inactivo</span></td><td><div class="btn-group" role="group"><button id="enabledInvestigador" idasignaciondetalle="' + response[i].asignacionDetalleId + '" class="btn btn-outline-success btn-xs"><i class="fa fa-check" title="Habilitar"></i></button></div></td>';
									
								}
								contenidoListadoInvestigadoresPorAsignacion.innerHTML += '</tr>';
							}
							
							
						}
						else {
							
							console.log('No hay datos');
							$('#tablaListadoInvestigadoresPorAsignacion').hide();
							$('#listadoInvestigadoresPorAsignacion').show();
						}					
					}
				});
			}
		});
	});
	
	$('#tablaListadoInvestigadoresPorAsignacion tbody').on('click', 'button#disabledInvestigador', function() {
		
		var asignacionDetalleId = $(this).attr('idasignaciondetalle');
		console.log('asignacionDetalleId: ' + asignacionDetalleId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar este Investigador ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Investigador !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/asignaciondocente/investigador/disabled/' + asignacionDetalleId,
	                 type: 'GET',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Investigador: " + response.data.investigador + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/asignacionDocente/formAsignacionDetalle/' + response.data.asignacion.asignacionId);
	                         }
	                     })
	                 }
	             });
	        }
	        else {
	            swal({
	                type: "error",
	                title: "Cancelado", 
	                text: "Usted ha cancelado la acción de deshabilitar"
	            });
	        }
	    });
	});
	
	$('#tablaListadoInvestigadoresPorAsignacion tbody').on('click', 'button#enabledInvestigador', function() {
		
		var asignacionDetalleId = $(this).attr('idasignaciondetalle');
		console.log("asignacionDetalleId: " + asignacionDetalleId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Investigador ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Investigador !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/asignaciondocente/investigador/enabled/' + asignacionDetalleId ,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "El Investigador: " + response.data.investigador + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/asignacionDocente/formAsignacionDetalle/' + response.data.asignacion.asignacionId);
	                        }
	                     })
	                 }
	        	});
	        }
	        else {
	            swal({
	                type: "error",
	                title: "Cancelado", 
	                text: "Usted ha cancelado la acción de habilitar"
	            });
	        }
	    });
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