/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarCategoriaDocente').click(function(e) {
		e.preventDefault();
		
		if($('#categoriaNombre').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/)) {
			
			var formData = {
					
					categoriaDocenteId: $('#categoria-id').val(),
					nombreCategoria: $('#categoriaNombre').val()
			};
			
			if(formData.categoriaDocenteId) {
				
				var categoriaDocenteId = formData.categoriaDocenteId;
				console.log(categoriaDocenteId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/categoriadocente/update/' + categoriaDocenteId,
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
							title: "Categoria Docente Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/categoriadoc/listar');
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Categoria Docente');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/categoriadocente/save',
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
							title: "Categoria Docente Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/categoriadoc/listar');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Categoria Docente');
					}
				});
				
			}
		}
		
		if($('#categoriaNombre').val() == "" || $('#categoriaNombre').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Categoria Docente no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#categoriaNombre').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#categoriaNombre').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Categoria Docente no permite caracteres especiales'
	            });
		    	
		    	$('#categoriaNombre').focus();
		    	return false;
				
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#detalleCategoriaDocente', function() {
		
		var categoriaDocenteId = $(this).attr('idcategoriadocente');
		
		console.log("categoriaDocenteId: " + categoriaDocenteId);
		
		$('#modalDetalleCategoriaDocente').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/categoriadocente/categoria/' + categoriaDocenteId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				$('#titleCategoriaDocenteModal').html(response.nombreCategoria);
				$('#detalleCategoriaNombre').val(response.nombreCategoria);
			}		
		});
	});
	
	$('#dataTable tbody').on('click', 'button#disabledCategoriaDocente', function() {
		
		var categoriaDocenteId = $(this).attr('idcategoriadocente');
		console.log('categoriaDocenteId: ' + categoriaDocenteId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar esta Categoria ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Categoria !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/categoriadocente/categoria/disabled/' + categoriaDocenteId,
	                 type: 'GET',
	                 success: function(){
	                     swal({
	                         type: "success",
	                         title: "La Categoria ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/categoriadoc/listar");
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
	
	
	$('#dataTable tbody').on('click', 'button#enabledCategoriaDocente', function() {
		
		var categoriaDocenteId = $(this).attr('idcategoriadocente');
		console.log('categoriaDocenteId: ' + categoriaDocenteId);
		
		swal({
	        title: '¿Esta Seguro de habilitar esta Categoria ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Categoria !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/categoriadocente/categoria/enabled/'+ categoriaDocenteId,
	        		type: 'GET',
	        		success: function(){
	        			swal({
	        				type: "success",
	                        title: "La Categoria ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/categoriadoc/listar");
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