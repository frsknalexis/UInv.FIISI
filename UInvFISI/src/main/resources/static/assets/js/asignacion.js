/**
 * 
 */

$(document).ready(function() {
	
	var fechaActual = new Date();
	/*
	 * Primera Forma de obtener fecha Actual
	var fechaActual = fecha.getDate() + "/" + (fecha.getMonth() +1) + "/" + fecha.getFullYear();
	console.log(fechaActual);
	*/
	var dd = fechaActual.getDate();
	var MM = fechaActual.getMonth() + 1;
	var yyyy = fechaActual.getFullYear();
	
	if(dd < 10) {
		dd = '0' + dd;
	}
	
	if(MM < 10) {
		MM = '0' + MM;
	}
	
	fechaActual = yyyy + '-' + MM + '-' + dd;
	console.log(fechaActual);
	$('#fechaInicio').val(fechaActual);
	
	$('#fechaFin').on('change', function() {
		
		var fechaInicio = $('#fechaInicio').val();
		var fechaFin = $('#fechaFin').val();
		console.log("fechaFin: " + fechaFin);
		
		if(fechaInicio == fechaFin) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'La Fecha Inicio no puede ser igual a la Fecha Fin !'
            });
			$('#fechaFin').val('');
		}
		
		if(fechaInicio > fechaFin) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'La Fecha Fin no puede ser menor a la Fecha Inicio !'
            });
			$('#fechaFin').val('');
		}
		
	});
	
	$('#fechaInicio').on('change', function() {
		
		var fechaInicio = $('#fechaInicio').val();
		var fechaFin = $('#fechaFin').val();
		console.log("fechaInicio: " + fechaInicio);
		console.log("fechaFin: " + fechaFin);
		
		if(fechaInicio == fechaFin) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'La Fecha Inicio no puede ser igual a la Fecha Fin !'
            });
			$('#fechaInicio').val('');
		}
		
		if(fechaInicio > fechaFin) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'La Fecha Inicio no puede ser mayor a la Fecha Fin !'
            });
			$('#fechaInicio').val('');
		}
	});
	
	$('#guardarInvestigacion').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombreInvestigacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) && 
			$('#investigacionLineaInvg').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) && $('#anioInvestigacion').val().match(/^[0-9\s]+$/) &&
			$('#fechaInicio').val() != "" && $('#fechaFin').val() != "") {
			
			var formData =  {
				
				asignacionId: $('#asignacion-id').val(),
				nombreInvestigacion: $('#nombreInvestigacion').val(),
				lineaInvestigacion: {
					lineaInvestigacionId: $('#investigacionLineaInvgId').val()
				},
				anio: $('#anioInvestigacion').val(),
				fechaInicio: $('#fechaInicio').val(),
				fechaFin: $('#fechaFin').val()
			};
			
			if(formData.asignacionId) {
				
				var asignacionId = formData.asignacionId;
				console.log("asignacionId: " + asignacionId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/asignacioninvestigacion/update/' + asignacionId,
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
							title: "Investigacion Actualizada con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/asignacion/list');
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Investigacion');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/asignacioninvestigacion/save',
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
							title: "Investigacion: " + response.data.nombreInvestigacion + " Registrada con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/asignacion/list');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Investigacion');
					}
				});
			}
		}
		
		if($('#nombreInvestigacion').val() == "" && $('#investigacionLineaInvg').val() == "" && $('#anioInvestigacion').val() == ""
			 && $('#fechaInicio').val() == "" && $('#fechaFin').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#nombreInvestigacion').val() == "" || $('#nombreInvestigacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Investigacion no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombreInvestigacion').focus();
		    	return false;
			}
			
			if($('#investigacionLineaInvg').val() == "" || $('#investigacionLineaInvg').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Linea Investigacion no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#investigacionLineaInvg').focus();
		    	return false;
			}
			
			if($('#anioInvestigacion').val() == "" || $('#anioInvestigacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Periodo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#anioInvestigacion').focus();
		    	return false;
			}
			
			if($('#fechaInicio').val() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Fecha Inicio no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#fechaInicio').focus();
		    	return false;
			}
			
			if($('#fechaFin').val() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Fecha Fin no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#fechaInicio').focus();
		    	return false;
			}
			
			if(!($('#nombreInvestigacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
			
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Investigacion no permite caracteres especiales'
	            });
				
				$('#nombreInvestigacion').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#investigacionLineaInvg').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Linea Investigacion no permite caracteres especiales ni numeros'
		            });
					
					$('#investigacionLineaInvg').focus();
			    	return false;
				}
				
				else {
					
					if(!($('#anioInvestigacion').val().match(/^[0-9\s]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'El campo Periodo no permite caracteres especiales ni letras'
			            });
						
						$('#anioInvestigacion').focus();
				    	return false;
					}
				}
			}
			
		}
	});
	
	$('#dataTable tbody').on('click', 'button#asignarInvestigador', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: " + asignacionId);
		localStorage.setItem("asignacionId", asignacionId);
		
		$(location).attr('href', '/asignacionDocente/formAsignacionDetalle/' + asignacionId);
	});
	
	$('#dataTable tbody').on('click', 'button#adjuntarInformeInvestigacion', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: " + asignacionId);
		localStorage.setItem("asignacionId", asignacionId);
		
		$(location).attr('href', '/informe/formInformeInvestigacion/' + asignacionId);
	});
	
	$('#dataTable tbody').on('click', 'button#disabledAsignacion', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: " + asignacionId);
		
		swal({
	        title: '¿Esta Seguro de finalizar el Proceso de este Proyecto Investigacion ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, finalizar Proceso !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/asignacioninvestigacion/asignacion/disabled/' + asignacionId,
	                 type: 'GET',
	                 dataType: 'json',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Proceso del Proyecto Investigacion: " + response.data.nombreInvestigacion + " ha sido finalizado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/asignacion/list');
	                         }
	                     })
	                 }
	             });
	        }
	        else {
	            swal({
	                type: "error",
	                title: "Cancelado", 
	                text: "Usted ha cancelado la acción de finalizar Proceso"
	            });
	        }
	    });
	});
	
	$('#dataTable tbody').on('click', 'button#enabledAsignacion', function() {
		
		var asignacionId = $(this).attr('idasignacion');
		console.log("asignacionId: "+ asignacionId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Proyecto Investigacion ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Proyecto !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/asignacioninvestigacion/asignacion/enabled/' + asignacionId,
	        		type: 'GET',
	        		dataType: 'json',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "El Proyecto Investigacion: " + response.data.nombreInvestigacion + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/asignacion/list');
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
});