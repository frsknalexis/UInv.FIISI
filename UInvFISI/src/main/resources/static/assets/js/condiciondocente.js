/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarCondicionDocente').click(function(e) {
		
		e.preventDefault();
		
		if($('#condicionNombre').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/)) {
			
			var formData = {
				
					condicionId: $('#condicion-id').val(),
					nombreCondicion: $('#condicionNombre').val()		
			};
			
			if(formData.condicionId) {
				
				var condicionId = formData.condicionId;
				console.log(condicionId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/condicion/update/' + condicionId,
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
							title: "Condicion Docente Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/condicion/listar');
							}
						});
					},
					
					error: function() {
						
						alert('Error al Actualizar Condicion Docente');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/condicion/save',
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
							title: "Condicion Docente Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/condicion/listar');
							}
						});
					},
					
					error: function() {
						alert('Error al Registrar Condicion Docente');
					}
				});
			}
		}
		
		if($('#condicionNombre').val() == "" || $('#condicionNombre').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Condicion Docente no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#condicionNombre').focus();
	    	return false;
		}
		else {
			
			if(!($('#condicionNombre').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Condicion Docente no permite caracteres especiales'
	            });
		    	
		    	$('#condicionNombre').focus();
		    	return false;
			}
		}
	});
	
	
	$('#dataTable tbody').on('click', 'button#detalleCondicionDocente', function() {
		
		var condicionId = $(this).attr('idcondiciondocente');
		console.log("condicionId: " + condicionId);
		
		$('#modalDetalleCondicionDocente').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/condicion/condicion/' + condicionId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				$('#titleCondicionDocenteModal').html(response.nombreCondicion);
				$('#detalleCondicionNombre').val(response.nombreCondicion);
			}
		});
	});
	
	$('#dataTable tbody').on('click', 'button#disabledCondicionDocente', function() {
		
		var condicionId = $(this).attr('idcondiciondocente');
		console.log("condicionId: " + condicionId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar esta Condicion ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Condicion !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/condicion/condicion/disabled/' + condicionId,
	                 type: 'GET',
	                 success: function(){
	                     swal({
	                         type: "success",
	                         title: "La Condicion ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/condicion/listar");
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
	
	
	$('#dataTable tbody').on('click', 'button#enabledCondicionDocente', function() {
		
		var condicionId = $(this).attr('idcondiciondocente');
		
		console.log("condicionId: " + condicionId);
		
		swal({
	        title: '¿Esta Seguro de habilitar esta Condicion ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Condicion !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/condicion/condicion/enabled/'+ condicionId,
	        		type: 'GET',
	        		success: function(){
	        			swal({
	        				type: "success",
	                        title: "La Condicion ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/condicion/listar");
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
