/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarPrograma').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombrePrograma').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.,-\s]+$/)) {
			
			var formData = {
				
					programaId: $('#programa-id').val(),
					nombrePrograma: $('#nombrePrograma').val()
			};
			
			if(formData.programaId) {
				
				var programaId = formData.programaId;
				console.log("programaId: " + programaId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/programa/update/' + programaId,
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
							title: "Programa Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/programa/listar');
							}
						});
					},
					error: function() {
						
						alert('Error al Actualizar Programa');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/programa/save',
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
							title: "Programa: " + response.data.nombrePrograma + " Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/programa/listar');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Programa');
					}
				});
			}
		}
		
		if($('#nombrePrograma').val() == "" || $('#nombrePrograma').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Nombre Programa no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#nombrePrograma').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#nombrePrograma').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.,-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Programa no permite caracteres especiales'
	            });
		    	
		    	$('#nombrePrograma').focus();
		    	return false;
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#disabledPrograma', function() {
		
		var programaId = $(this).attr('idprograma');
		
		console.log('programaId: ' + programaId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar este Programa ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Programa !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/programa/programa/disabled/' + programaId,
	                 type: 'GET',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Programa: " + response.data.nombrePrograma + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/programa/listar");
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
	
	$('#dataTable tbody').on('click', 'button#enabledPrograma', function() {
		
		var programaId = $(this).attr('idprograma');
		console.log('programaId: ' + programaId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Programa ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Programa !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/programa/programa/enabled/' + programaId,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "El Programa: " + response.data.nombrePrograma + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/programa/listar");
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
	
	$('#dataTable tbody').on('click', 'button#detallePrograma', function() {
		
		var programaId = $(this).attr('idprograma');
		console.log('programaId: ' + programaId);
		
		var contenido = document.querySelector('#contenido');
		
		setTimeout(
				"$('#modalDetallePrograma').modal('show');", 1500);
		
		$.ajax({
			
			type: 'GET',
			url: '/api/programa/programa/' + programaId,
			dataType: 'json',
			success: function(programa) {
				
				console.log(programa);
				
				$('#titleProgramaModal').html('Programa: ' + programa.nombrePrograma);
				$('#detalleNombrePrograma').val(programa.nombrePrograma);
				$('#alertLineaInvestigacion').html('Aun no hay Lineas de Investigacion asociadas al Programa: ' + programa.nombrePrograma);
				
				$.ajax({
					
					type: 'GET',
					url: '/api/linea/lineas/programa/' + programaId,
					dataType: 'json',
					success: function(response){
						
						if(response != null) {
							
							$('#tablaProgramaLinea').show();
							$('#alertLineaInvestigacion').hide();
							
							contenido.innerHTML = '';
							for(var i = 0; i < response.length; i++) {
								console.log(response[i]);
								contenido.innerHTML += '<tr><td>' + (i+1) +'</td><td>'+ response[i].nombreLineaInvestigacion +'</td><td>' + response[i].programa.nombrePrograma + '</td></tr>';
							}
						}
						else {
							
							console.log("No hay datos");
							$('#tablaProgramaLinea').hide();
							$('#alertLineaInvestigacion').show();
						}
					} 
				});
			} 
		});
		
		
	
	});
	
	$('#dataTable tbody').on('click', 'button#programaLineaInvestigacion', function() {
		
		var programaId = $(this).attr('idprograma');
		localStorage.setItem("programaId", programaId);
		$(location).attr('href', '/linea/formLineaInvestigacion/' + programaId);
	});
});