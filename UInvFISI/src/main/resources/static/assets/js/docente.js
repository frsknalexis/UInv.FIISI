/**
 * 
 */

$(document).on('ready', function() {
	
	$('#guardarDocente').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombreDocente').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/) && $('#apellidoDocente').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/)
				&& $('#categoriaDocente').val().trim() != "" && $('#documentoDocente').val().trim() != ""
				&& $('#nroDocumentoDocente').val().match(/^[0-9]+$/) && $('#regimenDedicacionDocente').val().trim() != ""
				&& $('#dinaDatosAcademicos').val().trim() != "" && $('#dinaProyectosInvestigacion').val().trim() != ""
				&& $('#codigoOrcid').val().match(/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/)
				&& $('#publicacionesOrcid').val() >= 0) {
			
			var formData = {
				
					docenteId: $('#docente-id').val(),
					nombresDocente: $('#nombreDocente').val(),
					apellidosDocente: $('#apellidoDocente').val(),
					categoriaDocente: {
						categoriaDocenteId: $('#categoriaDocente').val()
					},
					documento: {
						documentoId: $('#documentoDocente').val()
					},
					nroDocumento: $('#nroDocumentoDocente').val(),
					regimenDedicacion: {
						regimenDedicacionId: $('#regimenDedicacionDocente').val()
					},
					dinaDatosAcademicos: $('#dinaDatosAcademicos').val(),
					dinaProyectosInvestigacion: $('#dinaProyectosInvestigacion').val(),
					codigoOrcid: $('#codigoOrcid').val(),
					publicacionesOrcid: $('#publicacionesOrcid').val()
			};
					
			if(formData.docenteId) {
				
				var docenteId = formData.docenteId;
				console.log("docenteId: " + docenteId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/docente/update/' + docenteId,
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
							title: "Docente Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/docente/listar');
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Docente');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/docente/save',
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
							title: "Docente: " + response.data.nombresDocente + " " + response.data.apellidosDocente + " Registrado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/docente/listar');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Docente al DINA');
					}
				});
			}
		}
		
		if($('#nombreDocente').val() == "" && $('#apellidoDocente').val() == "" && $('#categoriaDocente').val().trim() == ""
			 && $('#documentoDocente').val().trim() == "" && $('#nroDocumentoDocente').val() == "" && 
			 $('#regimenDedicacionDocente').val().trim() == "" && $('#dinaDatosAcademicos').val().trim() == "" && 
			 $('#dinaProyectosInvestigacion').val().trim() == "" && $('#codigoOrcid').val() == "" && $('#publicacionesOrcid').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#nombreDocente').val() == "" || $('#nombreDocente').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Docente no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombreDocente').focus();
		    	return false;
			}
			
			if($('#apellidoDocente').val() == "" || $('#apellidoDocente').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Apellido Docente no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#apellidoDocente').focus();
		    	return false;
			}
			
			if($('#categoriaDocente').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Categoria'
	            });
				
		    	return false;
			}
			
			if($('#documentoDocente').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Documento'
	            });
				
		    	return false;
			}
			
			if($('#nroDocumentoDocente').val() == "" || $('#nroDocumentoDocente').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Numero Documento no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nroDocumentoDocente').focus();
		    	return false;
			}
			
			if($('#regimenDedicacionDocente').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Regimen Dedicacion'
	            });
				
		    	return false;
			}
			
			if($('#dinaDatosAcademicos').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Opcion DINA (Datos Academicos)'
	            });
				
		    	return false;
			}
			
			if($('#dinaProyectosInvestigacion').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Opcion DINA (Proyectos Investigacion)'
	            });
				
		    	return false;
			}
			
			if($('#codigoOrcid').val() == "" || $('#codigoOrcid').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Codigo ORCID no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#codigoOrcid').focus();
		    	return false;
			}
			
			if($('#publicacionesOrcid').val() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Codigo ORCID (Cantidad publicaciones) no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#publicacionesOrcid').focus();
		    	return false;
			}
			
			if(!($('#nombreDocente').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Docente no permite caracteres especiales ni numeros'
	            });
				
				$('#nombreDocente').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#apellidoDocente').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Apellido Docente no permite caracteres especiales ni numeros'
		            });
					
					$('#apellidoDocente').focus();
			    	return false;
				}
				
				else {
					
					if(!($('#nroDocumentoDocente').val().match(/^[0-9]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'El campo Numero Documento no permite caracteres especiales ni letras'
			            });
						
						$('#nroDocumentoDocente').focus();
				    	return false;
					}
					
					else {
						
						if(!($('#codigoOrcid').val().match(/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/))) {
							
							swal({
				                type: 'error',
				                title: 'Ooops',
				                text: 'Ingrese un valor valido para el Codigo ORCID'
				            });
							
							$('#codigoOrcid').focus();
					    	return false;
						}
						
						else {
							
							if($('#publicacionesOrcid').val() <= -1) {
								
								swal({
					                type: 'error',
					                title: 'Ooops',
					                text: 'El campo Codigo ORCID (Cantidad publicaciones) no permite valores negativos, ingrese un valor valido'
					            });
								
								$('#publicacionesOrcid').focus();
						    	return false;
							}
						}
					}
				}
			}
		}
	});
	
	$('#publicacionesOrcid').on('keyup', function() {
		
		var valor = $(this).val();
		console.log(valor);
		
		if(parseInt(valor) <= -1) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Codigo ORCID (Cantidad publicaciones) no permite valores negativos, ingrese un valor valido'
            });
			
			$(this).val('');
		}
		
		/*else if(parseInt(valor) >= 0) {
			
			console.log('Valor aceptado');
		}
		*/
	});
	
	$('#dataTable tbody').on('click', 'button#detalleDocente', function() {
		
		var docenteId = $(this).attr('iddocente');
		console.log("docenteId: " + docenteId);
		
		$('#modalDetalleDocente').modal('show');
		
		$.ajax({
			
			type: 'GET',
			url: '/api/docente/docente/' + docenteId,
			dataType: 'json',
			success: function(response) {
				
				console.log(response);
				
				$('#titleDocenteModal').html('Docente: ' + response.nombresDocente + " " + response.apellidosDocente);
				$('#detalleNombreDocente').val(response.nombresDocente);
				$('#detalleApellidoDocente').val(response.apellidosDocente);
				$('#detallecategoriaDocente').val(response.categoriaDocente.nombreCategoria);
				$('#detalleDocumentoDocente').val(response.documento.nombreDocumento);
				$('#detalleNroDocumentoDocente').val(response.nroDocumento);
				$('#detalleRegimenDedicacionDocente').val(response.regimenDedicacion.nombreRegimen);
				$('#detalleDinaDatosAcademicos').val(response.dinaDatosAcademicos);
				$('#detalleDinaProyectosInvestigacion').val(response.dinaProyectosInvestigacion);
				$('#detalleCodigoOrcid').attr('disabled', true);
				$('#detalleCodigoOrcid').val(response.codigoOrcid);
				$('#detallePublicacionesOrcid').attr('disabled', true);
				$('#detallePublicacionesOrcid').val(response.publicacionesOrcid);
			}
		});
	});
	
	$('#dataTable tbody').on('click', 'button#disabledDocente', function() {
		
		var docenteId = $(this).attr('iddocente');
		console.log("docenteId: " + docenteId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar este Docente ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Docente !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/docente/docente/disabled/' + docenteId,
	                 type: 'GET',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Docente: " + response.data.nombresDocente + " " + response.data.apellidosDocente + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/docente/listar');
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
	
	$('#dataTable tbody').on('click', 'button#enabledDocente', function() {
		
		var docenteId = $(this).attr('iddocente');
		console.log("docenteId: " + docenteId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Docente ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Docente !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/docente/docente/enabled/' + docenteId,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "El Docente: " + response.data.nombresDocente + " " + response.data.apellidosDocente + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/docente/listar');
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