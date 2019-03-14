/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarFacultad').click(function(e) {
		
		e.preventDefault();
		
		if($('#abreviatura-facultad').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,\s]+$/) && 
			$('#facultad').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,\s]+$/)) {
			
			var formData = {
					
					facultadId: $('#facultad-id').val(),
					nombreFacultad: $('#facultad').val(),
					abreviaturaFacultad: $('#abreviatura-facultad').val()
			};
			
			if(formData.facultadId) {
				
				var facultadId = formData.facultadId;
				console.log(facultadId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/facultad/update/' + facultadId,
					headers: {
						"Content-Type": "application/json",
						"Acept": "application/json"
					},
					data: JSON.stringify(formData),
					dataType: 'json',
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Facultad Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/facultad/listar');
							}
						});
					},
					error: function() {
						
						alert('Error al Actualizar Facultad');
					}
					
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/facultad/save',
					headers: {
						"Content-Type": "application/json",
						"Acept": "application/json"
					},
					data: JSON.stringify(formData),
					dataType: 'json',
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Facultad: " + response.data.abreviaturaFacultad + " Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/facultad/listar');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Facultad');
					}
				});
			}
			
		}
		
		if($('#abreviatura-facultad').val() == "" && $('#facultad').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
			
			$('#abreviatura-facultad').focus();
			return false;
		}
		
		else {
			
			if($('#abreviatura-facultad').val() == "" || $('#abreviatura-facultad').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Abreviatura Facultad no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#abreviatura-facultad').focus();
		    	return false;
			}
			
			if($('#facultad').val() == "" || $('#facultad').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Facultad no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#facultad').focus();
		    	return false;
			}
			
			if(!($('#abreviatura-facultad').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Abreviatura Facultad no permite caracteres especiales ni numeros'
	            });
				
				$('#abreviatura-facultad').focus();
		    	return false;
			}
			else {
				
				if(!($('#facultad').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Nombre Facultad no permite caracteres especiales ni numeros'
		            });
					
					$('#facultad').focus();
			    	return false;
					
				}
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#detalleFacultad', function() {
		
		var facultadId = $(this).attr('idfacultad');
		console.log("facultadId: " + facultadId);
		
		$('#modalDetalleFacultad').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/facultad/facultad/' + facultadId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				$('#titleFacultadModal').html(response.nombreFacultad);
				$('#detalleAbreviaturaFacultad').val(response.abreviaturaFacultad);
				$('#detalleNombreFacultad').val(response.nombreFacultad);
			}
		});
	});
	
	$('#dataTable tbody').on('click', 'button#disabledFacultad', function() {
		
		var facultadId = $(this).attr('idfacultad');
		console.log('facultadId: ' + facultadId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar esta Facultad ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Facultad !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/facultad/facultad/disabled/' + facultadId,
	                 type: 'GET',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "La Facultad: " + response.data.nombreFacultad + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/facultad/listar");
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
	
	$('#dataTable tbody').on('click', 'button#enabledFacultad', function() {
		
		var facultadId = $(this).attr('idFacultad');
		console.log("facultadId: " + facultadId);
		
		swal({
	        title: '¿Esta Seguro de habilitar esta Facultad ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Facultad !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/facultad/facultad/enabled/' + facultadId,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "La Facultad: " + response.data.nombreFacultad + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/facultad/listar");
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