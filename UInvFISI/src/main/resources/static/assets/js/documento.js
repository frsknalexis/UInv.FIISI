/**
 * 
 */
$(document).on('ready', function() {
	
	
	listarDocumentos();
	
	$('#guardarDocumento').click(function(e) {
		e.preventDefault();
		if($('#abreviatura-documento').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/) 
				&& $('#documento').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/)) {
			
			var formData = {
					documentoId: $('#documento-id').val(),
					abreviatura: $('#abreviatura-documento').val(),
					nombreDocumento: $('#documento').val()
			};
			
			if(formData.documentoId) {
				
				var documentoId = formData.documentoId;
				console.log(documentoId);
				
				$.ajax({
					type: 'PUT',
					url: '/api/documento/update/' + documentoId,
					headers: {
						"Content-Type": "application/json",
						"Acept": "application/json"
					},
					data: JSON.stringify(formData),
					dataType:'json',
					success: function(response) {
						console.log(response);
						swal({
							type: "success",
							title: "Documento Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/documento/listar');
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Documento');
					}
				});
			}
			
			else {
				
				$.ajax({
					type: 'POST',
					url: '/api/documento/save',
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
							title: "Documento Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/documento/listar');
							}
						});
						
					},
					error: function() {
						alert('Error al Registrar Documento');
					}
				});
			}
		}
				
		if($('#abreviatura-documento').val() == "" && $('#documento').val() == "") {

			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
			
			$('#abreviatura-documento').focus();
			return false;
		}
		
		else if(!($('#abreviatura-documento').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/))) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Ingrese un valor valido para la Abreviatura Documento'
            });
			
			$('#abreviatura-documento').focus();
			return false;
		}
		
	    else if($('#abreviatura-documento').val() == "" || $('#abreviatura-documento').val() == 0) {
	    	
	    	swal({
                type: 'error',
                title: 'Ooops',
                text: 'La Abreviatura Documento no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#abreviatura-documento').focus();
	    	return false;
		}
		
	    else if(!($('#documento').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\s]+$/))) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Ingrese un valor valido para el Nombre Documento'
            });	
			$('#documento').focus();
			return false;
		}
		
		else if($('#documento').val() == "" || $('#documento').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El Nombre Documento no puede estar vacio, ingrese un valor valido'
            });
			
			$('#documento').focus();
			return false;
		}
	});
	
	$('#dataTable tbody').on('click', "button#detalleDocumento", function() {
		
		var documentoId = $(this).attr("iddocumento");
		
		$('#modalDetalleDocumento').modal('show');
		
		$.ajax({
			type: 'GET',
			url: '/api/documento/documento/' + documentoId,
			dataType: 'json',
			success: function(response) {
				console.log(response);
				$('#titleDocumentoModal').html(response.nombreDocumento);
				$('#detalle-abreviatura-documento').val(response.abreviatura);
				$('#detalle-documento').val(response.nombreDocumento);
			}
		});
	});
	
	$('#dataTable tbody').on("click", "button#disabledDocumento", function() {
		
		var documentoId = $(this).attr("iddocumento");
		
		console.log("documentoId: " + documentoId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar este Documento?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Documento !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/documento/documento/disabled/' + documentoId,
	                 type: 'GET',
	                 success: function(){
	                     swal({
	                         type: "success",
	                         title: "El Documento ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/documento/listar");
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
	
	
	$('#dataTable tbody').on("click", "button#enabledDocumento", function() {
		
		var documentoId = $(this).attr("iddocumento");
		
		console.log("documentoId: " + documentoId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Documento?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Documento !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/documento/documento/enabled/'+ documentoId,
	        		type: 'GET',
	        		success: function(){
	        			swal({
	        				type: "success",
	                        title: "El Documento ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/documento/listar");
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

function listarDocumentos() {
	
	$.ajax({
		type: 'GET',
		url: '/api/documento/documentos',
		dataType: 'json',
		success: function(response) {
			console.log(response);
		}
	})
}