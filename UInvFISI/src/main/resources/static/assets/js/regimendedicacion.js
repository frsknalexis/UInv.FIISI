/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarRegimenDedicacion').click(function(e) {
		
		e.preventDefault();
		
		if($('#regimenNombre').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/)) {
			
			var formData = {
				
					regimenDedicacionId: $('#regimen-id').val(),
					nombreRegimen: $('#regimenNombre').val()
			};
			
			if(formData.regimenDedicacionId) {
				
				var regimenDedicacionId = formData.regimenDedicacionId;
				console.log(regimenDedicacionId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/regimen/update/' + regimenDedicacionId,
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
							title: "Regimen Dedicacion Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/regimen/listar');
							}
						});
					},
					
					error: function() {
						
						alert('Error al Actualizar Regimen Dedicacion');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/regimen/save',
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
							title: "Regimen Dedicacion: " + response.data.nombreRegimen + " Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/regimen/listar');
							}
						});
					},
					error: function() {
						
						alert('Error al Registrar Regimen Dedicacion');
					}
				});
			}
		}
		
		if($('#regimenNombre').val() == "" || $('#regimenNombre').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Regimen Dedicacion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#regimenNombre').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#regimenNombre').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Regimen Dedicacion no permite caracteres especiales ni numeros'
	            });
		    	
		    	$('#condicionNombre').focus();
		    	return false;
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#detalleRegimenDedicacion', function() {
		
		var regimenDedicacionId = $(this).attr('idregimendedicacion');
		console.log('regimenDedicacionId: ' + regimenDedicacionId);
		
		$('#modalDetalleRegimenDedicacion').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/regimen/regimen/' + regimenDedicacionId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				
				$('#titleRegimenDedicacionModal').html(response.nombreRegimen);
				$('#detalleRegimenNombre').val(response.nombreRegimen);
			}
		});
	});
	
	$('#dataTable tbody').on('click', 'button#disabledRegimenDedicacion', function() {
		
		var regimenDedicacionId = $(this).attr('idregimendedicacion');
		console.log('regimenDedicacionId: ' + regimenDedicacionId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar este Regimen ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Regimen !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/regimen/regimen/disabled/' + regimenDedicacionId,
	                 type: 'GET',
	                 success: function(){
	                     swal({
	                         type: "success",
	                         title: "El Regimen ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/regimen/listar");
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
	
	$('#dataTable tbody').on('click', 'button#enabledRegimenDedicacion', function() {
		
		var regimenDedicacionId = $(this).attr('idregimendedicacion');
		console.log('regimenDedicacionId: ' + regimenDedicacionId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Regimen ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Regimen !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/regimen/regimen/enabled/' + regimenDedicacionId,
	        		type: 'GET',
	        		success: function(){
	        			swal({
	        				type: "success",
	                        title: "El Regimen ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/regimen/listar");
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