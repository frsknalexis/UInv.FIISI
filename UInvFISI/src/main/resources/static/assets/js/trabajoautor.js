/**
 * 
 */

$(document).ready(function() {
	
	$('#cancelarTrabajoAutor').on('click', function() {
		
		localStorage.removeItem("trabajoId");
		
		$(location).attr('href', '/trabajo/list');
	});
	
	$('#guardarTrabajoAutor').on('click', function(e){
		
		e.preventDefault();
		
		if($('#autorAsignacion').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/) && $('#emailAsignacion').val().match(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/)
				&& $('#celularAsignacion').val().match(/^[0-9]+$/) && $('#documentoAsignacion').val().trim() != "" 
				&& $('#nmrAsignacion').val().match(/^[0-9]+$/) && $('#condicionDocenteAsignacion').val().trim() != "") {
			
			var trabajoId = localStorage.getItem("trabajoId");
			console.log("trabajoId: " + trabajoId);
			
			var formData = {
				
					autorId: $('#autor-id').val(),
					nombre: $('#autorAsignacion').val(),
					email: $('#emailAsignacion').val(),
					celular: $('#celularAsignacion').val(),
					documento: {
						documentoId: $('#documentoAsignacion').val()
					},
					nroDocumento: $('#nmrAsignacion').val(),
					condicion: {
						condicionId: $('#condicionDocenteAsignacion').val()
					}
			};
			
			console.log(formData);
			
			if(formData.autorId) {
				
				var autorId = formData.autorId;
				console.log("autorId: " + autorId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/autor/update/trabajo/' + trabajoId + '/autor/' + autorId,
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
							title: "Asignacion Autor Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/autor/formAutorDetalle/' + trabajoId);
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Asignacion Autor');
					}
				});
			}
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/autor/save/trabajo/' + trabajoId,
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
							title: "Autor: " + response.data.nombre + " Asignado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/autor/formAutorDetalle/' + trabajoId);
							}
						});
					},
					error: function() {
						alert('Error al Asignar Autor')
					}
				});
			}
		}
		
		if($('#autorAsignacion').val() == "" && $('#emailAsignacion').val() == "" && $('#celularAsignacion').val() == ""
			 && $('#documentoAsignacion').val().trim() == "" && $('#nmrAsignacion').val() == "" && $('#condicionDocenteAsignacion').val().trim() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#autorAsignacion').val() == "" || $('#autorAsignacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Autor no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#autorAsignacion').focus();
		    	return false;
			}
			
			if($('#emailAsignacion').val() == "" || $('#emailAsignacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Email de Autor no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#emailAsignacion').focus();
		    	return false;
			}
			
			if($('#celularAsignacion').val() == "" || $('#celularAsignacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Numero Celular de Autor no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#celularAsignacion').focus();
		    	return false;
			}
			
			if($('#documentoAsignacion').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Documento'
	            });
				
		    	return false;
			}
			
			if($('#nmrAsignacion').val() == "" || $('#nmrAsignacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Numero Documento de Autor no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nmrAsignacion').focus();
		    	return false;
			}
			
			if($('#condicionDocenteAsignacion').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Condicion'
	            });
				
		    	return false;
			}
			
			if(!($('#autorAsignacion').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Autor no permite caracteres especiales ni numeros'
	            });
				
				$('#autorAsignacion').val('');
				$('#autorAsignacion').focus();
		    	return false;
			}
			else {
				
				if(!($('#emailAsignacion').val().match(/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Email de Autor no tiene un formato de Email valido'
		            });
					
					$('#emailAsignacion').val('');
					$('#emailAsignacion').focus();
			    	return false;
				}
				else {
					
					if(!($('#celularAsignacion').val().match(/^[0-9]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'El campo Numero Celular de Autor no permite caracteres especiales ni letras'
			            });
				    	
						$('#celularAsignacion').val('');
				    	$('#celularAsignacion').focus();
				    	return false;
					}
					else {
						
						if(!($('#nmrAsignacion').val().match(/^[0-9]+$/))) {
							
							swal({
				                type: 'error',
				                title: 'Ooops',
				                text: 'El campo Numero Documento de Autor no permite caracteres especiales ni letras'
				            });
					    	
							$('#nmrAsignacion').val('');
					    	$('#nmrAsignacion').focus();
					    	return false;
						}
					}
				}
			}
		}
	});
	
	$('#listarAutoresPorTrabajo').on('click', function() {
		
		var trabajoId = $(this).attr('idtrabajo');
		console.log("trabajoId: " + trabajoId);
		
		$('#modalListadoAutoresPorTrabajo').modal('show');
		
		var contenidoListadoAutoresPorTrabajo = document.querySelector('#contenidoListadoAutoresPorTrabajo');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/trabajo/trabajo/' + trabajoId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				$('#titleListadoAutoresPorTrabajoModal').html(response.nombre);
				$('#alertlistadoAutoresPorTrabajo').html('Aun no hay Autores Asignados al Trabajo: <strong>' + response.nombre + '</strong>')
			}
		});
		
		$.ajax({
			
			type: 'GET',
			url: '/api/autor/autores/trabajo/' + trabajoId,
			dataType: 'json',
			success: function(response) {
				
				if(response != null) {
					$('#tablaListadoAutoresPorTrabajo').show();
					$('#alertlistadoAutoresPorTrabajo').hide();
					
					contenidoListadoAutoresPorTrabajo.innerHTML = '';
					for(var i = 0; i < response.length; i++) {
						
						
						contenidoListadoAutoresPorTrabajo.innerHTML += '<tr>';
						if(response[i].habilitado == true) {
							contenidoListadoAutoresPorTrabajo.innerHTML += '<td>'+ (i+1) +'</td><td>'+ response[i].nombre +'</td><td>'+ response[i].email +'</td><td>'+ response[i].condicion.nombreCondicion+'</td><td><span class="status-p bg-success">Activo</span></td><td><div class="btn-group" role="group"><a href="/autor/formAutorDetalle/'+ response[i].trabajo.trabajoId +'/'+ response[i].autorId +'" class="btn btn-outline-primary btn-xs"><i class="fa fa-pencil" title="Editar"></i></a><button id="disabledAutor" idautor="' + response[i].autorId + '" class="btn btn-outline-danger btn-xs"><i class="fa fa-close" title="Deshabilitar"></i></button></div></td>';
							console.log(response[i]);
						}
						if(response[i].habilitado == false) {
							contenidoListadoAutoresPorTrabajo.innerHTML += '<td>'+ (i+1) +'</td><td>'+ response[i].nombre +'</td><td>'+ response[i].email +'</td><td>'+ response[i].condicion.nombreCondicion+'</td><td><span class="status-p bg-danger">Inactivo</span></td><td><div class="btn-group" role="group"><button id="enabledAutor" idautor="' + response[i].autorId + '" class="btn btn-outline-success btn-xs"><i class="fa fa-check" title="Habilitar"></i></button></div></td>';
							console.log(response[i]);
						}	
						contenidoListadoAutoresPorTrabajo.innerHTML += '</tr>';
					}
					
				}
				else {
					$('#tablaListadoAutoresPorTrabajo').hide();
					$('#alertlistadoAutoresPorTrabajo').show();
					console.log('No hay datos');
				}
			}
		});
	});
	
	$('#tablaListadoAutoresPorTrabajo tbody').on('click', 'button#disabledAutor', function() {
		
		var autorId = $(this).attr('idautor');
		console.log("autorId: " + autorId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar este Autor ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Autor !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/autor/autor/disabled/' + autorId,
	                 type: 'GET',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Autor: " + response.data.nombre + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/autor/formAutorDetalle/' + response.data.trabajo.trabajoId);
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
	
	$('#tablaListadoAutoresPorTrabajo tbody').on('click', 'button#enabledAutor', function() {
		
		var autorId = $(this).attr('idautor');
		console.log("autorId: " + autorId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Autor ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Autor !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/autor/autor/enabled/' + autorId,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "El Autor: " + response.data.nombre + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/autor/formAutorDetalle/' + response.data.trabajo.trabajoId);
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