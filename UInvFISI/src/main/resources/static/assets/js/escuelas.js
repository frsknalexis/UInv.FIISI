/**
 * 
 */

$(document).ready(function() {
	
	$('#guardarEscuela').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombre-escuela').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.-\s]+$/) && 
			$('#directorEscuela').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var formData = {
					
					escuelaId: $('#escuela-id').val(),
					nombreEscuela: $('#nombre-escuela').val(),
					directorEscuela: $('#directorEscuela').val()
			};
						
			if(formData.escuelaId) {
				
				var escuelaId = formData.escuelaId;
				console.log("escuelaId: " + escuelaId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/escuela/update/' + escuelaId,
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
							title: "Escuela Actualizada con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/escuela/listar');
							}
						});
					}
				});
			}
			
			else {
			
				$.ajax({
					
					type: 'POST',
					url: '/api/escuela/save',
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
							title: "Escuela: " + response.data.nombreEscuela + " Registrada con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/escuela/listar');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Escuela');
					}
				});
			}
		}
		
		if($('#nombre-escuela').val() == "" && $('#directorEscuela').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
			
			$('#nombre-escuela').focus();
			return false;
		}
		
		else {
			
			if($('#nombre-escuela').val() == "" || $('#nombre-escuela').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Escuela no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombre-escuela').focus();
		    	return false;
			}
			
			if($('#directorEscuela').val() == "" || $('#directorEscuela').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Director Escuela no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#directorEscuela').focus();
		    	return false;
			}
			
			if(!($('#nombre-escuela').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Escuela no permite caracteres especiales ni numeros'
	            });
				
				$('#nombre-escuela').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#directorEscuela').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Director Escuela no permite caracteres especiales ni numeros'
		            });
					
					$('#directorEscuela').focus();
			    	return false;
				}
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#disabledEscuela', function() {
		
		var escuelaId = $(this).attr('idescuela');
		console.log("escuelaId: " + escuelaId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar esta Escuela ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Escuela !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/escuela/escuela/disabled/' + escuelaId,
	                 type: 'GET',
	                 dataType: 'json',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "La Escuela: " + response.data.nombreEscuela + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/escuela/listar');
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
	
	$('#dataTable tbody').on('click', 'button#enabledEscuela', function() {
		
		var escuelaId = $(this).attr('idescuela');
		console.log("escuelaId: " + escuelaId);
		
		swal({
	        title: '¿Esta Seguro de habilitar esta Escuela ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Escuela !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/escuela/escuela/enabled/' + escuelaId,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "La Escuela: " + response.data.nombreEscuela + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/escuela/listar');
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
	
	$('#dataTable tbody').on('click', 'button#detalleEscuela', function() {
		
		var escuelaId = $(this).attr('idescuela');
		console.log("escuelaId: " + escuelaId);
		
		$('#modalDetalleEscuela').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/escuela/escuela/' + escuelaId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				
				$('#titleEscuelaModal').html('E.AP: ' + response.nombreEscuela);
				$('#detalleNombreEscuela').val(response.nombreEscuela);
				$('#detalleDirectorEscuela').val(response.directorEscuela);
			}
		});
	});
	
	$('#directorEscuela').autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				
				url: '/api/docente/docentes/autocomplete/' + request.term,
				dataType: 'json',
				data : {
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
			$('#directorEscuela').val(ui.item.label);
			return false;
		}
	});
	
	$("#escuelasInvg").autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				url : "/escuelas/cargar-escuelas/" + request.term,
				dataType : 'json',
				data : {
					term : request.term
				},
				success : function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.escuelasId,
							label: item.nombre,
							
						};
					}));
				},
			});
		},
		
		select: function(event, ui) {
			$("#escuelasInvg").val(ui.item.label);
			$("#escuelasInvgId").val(ui.item.value);
			return false;
		}
	});
	
});