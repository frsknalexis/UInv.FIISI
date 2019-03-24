/**
 * 
 */

$(document).on('ready', function(){
	
	var fechaActual = new Date();
	
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
	
	$('#fechaSustentacion').val(fechaActual);
	
	$('#guardarTrabajo').click(function(e){
		
		e.preventDefault();
		
		if($('#nombreTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) && $('#escuela').val().trim() != ""
			&& $('#anioInvestigacion').val().match(/^[0-9\s]+$/) && $('#tipoTrabajo').val().trim() != "" 
				&& $('#denominacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) && $('#fechaSustentacion').val() != ""
				&& $('#citacionAcademicos').val().trim() != "" && $('#gradoAcademicoTrabajo').val().trim() != "" 
				&& $('#canhojas').val().match(/^[0-9\s]+$/) && $('#areaConocimiento').val().trim() != "") {
			
			var formData = {
				
					trabajoId: $('#trabajo-id').val(),
					nombre: $('#nombreTrabajo').val(),
					escuela: {
						escuelaId: $('#escuela').val()
					},
					anio: $('#anioInvestigacion').val(),
					tipoTrabajo: $('#tipoTrabajo').val(),
					denominacion: $('#denominacion').val(),
					fechaSustentacion: $('#fechaSustentacion').val(),
					citaTrabajo: $('#citacionAcademicos').val(),
					gradoAcademico: $('#gradoAcademicoTrabajo').val(),
					cantidadHojas: $('#canhojas').val(),
					areaConocimiento: $('#areaConocimiento').val()
			};
			
			console.log(formData);
			
			if(formData.trabajoId) {
				
				var trabajoId = formData.trabajoId;
				console.log("trabajoId: " + trabajoId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/trabajo/update/' + trabajoId,
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
							title: "Trabajo Academico Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/trabajo/list');
							}
						});
						
					},
					error: function() {
						alert('Error al Actualizar Trabajo Academico');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/trabajo/save',
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
							title: "Trabajo Academico: " + response.data.nombre + " Registrado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/trabajo/list');
							}
						});
					},
					error: function() {
						alert('Error al Registrar Trabajo Academico');
					}
				});
			}
		}
		
		if($('#nombreTrabajo').val() == "" && $('#escuela').val().trim() == ""
			 && $('#anioInvestigacion').val() == "" && $('#tipoTrabajo').val().trim() == "" && $('#denominacion').val() == ""
				 && $('#fechaSustentacion').val() == "" && $('#citacionAcademicos').val().trim() == "" 
					 && $('#gradoAcademicoTrabajo').val().trim() == "" && $('#canhojas').val() == "" && $('#areaConocimiento').val().trim() == "" ) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#nombreTrabajo').val() == "" || $('#nombreTrabajo').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Trabajo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombreTrabajo').focus();
		    	return false;
			}
			
			if($('#escuela').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Escuela'
	            });
				
		    	return false;
			}
			
			if($('#anioInvestigacion').val() == "" || $('#anioInvestigacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Publicacion Trabajo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#anioInvestigacion').focus();
		    	return false;
			}
			
			if($('#tipoTrabajo').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Tipo Trabajo'
	            });
				
		    	return false;
			}
			
			if($('#denominacion').val() == "" || $('#denominacion').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Denominacion no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#denominacion').focus();
		    	return false;
			}
			
			if($('#fechaSustentacion').val() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Fecha Sustentacion no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#fechaSustentacion').focus();
		    	return false;
			}
			
			if($('#citacionAcademicos').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Citacion'
	            });
				
		    	return false;
			}
			
			if($('#gradoAcademicoTrabajo').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Grado Academico'
	            });
				
		    	return false;
			}
			
			if($('#canhojas').val().trim() == "" || $('#canhojas').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Cantidad Hojas no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#canhojas').focus();
		    	return false;
			}
			
			if($('#areaConocimiento').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Area de Conocimiento'
	            });
				
		    	return false;
			}
			
			if(!($('#nombreTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Trabajo no permite caracteres especiales'
	            });
				
				$('#nombreTrabajo').val('');
				$('#nombreTrabajo').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#anioInvestigacion').val().match(/^[0-9\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Publicacion Trabajo no permite caracteres especiales ni letras'
		            });
					
					$('#anioInvestigacion').val('');
					$('#anioInvestigacion').focus();
			    	return false;
				}
				
				else {
					
					if(!($('#denominacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'El campo Denominacion no permite caracteres especiales'
			            });
						
						$('#denominacion').val('');
						$('#denominacion').focus();
				    	return false;
					}
					
					else {
						
						if(!($('#canhojas').val().match(/^[0-9\s]+$/))) {
							
							swal({
				                type: 'error',
				                title: 'Ooops',
				                text: 'El campo Cantidad Hojas no permite caracteres especiales ni letras'
				            });
							
							$('#canhojas').val('');
							$('#canhojas').focus();
					    	return false;
						}
					}
				}
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#disabledTrabajo',function() {
		
		var trabajoId = $(this).attr('idtrabajo');
		console.log('trabajoId: ' + trabajoId);
		
		swal({
	        title: '¿Esta Seguro de finalizar el Proceso de este Trabajo Academico ?',
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
	                 url: '/api/trabajo/trabajo/disabled/' + trabajoId,
	                 type: 'GET',
	                 dataType: 'json',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Proceso del Trabajo Academico: " + response.data.nombre + " ha sido finalizado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/trabajo/list');
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
	
	$('#dataTable tbody').on('click', 'button#enabledTrabajo', function() {
		
		var trabajoId = $(this).attr('idtrabajo');
		console.log("trabajoId: " + trabajoId);
		
		swal({
	        title: '¿Esta Seguro de habilitar este Trabajo Academico ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Trabajo !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/trabajo/trabajo/enabled/' + trabajoId,
	        		type: 'GET',
	        		dataType: 'json',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "El Trabajo Academico: " + response.data.nombre + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/trabajo/list');
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
	
	$('#dataTable tbody').on('click', 'button#adjuntarArchivos', function() {
		
		var trabajoId = $(this).attr('idtrabajo');
		console.log("trabajoId: " + trabajoId);
		localStorage.setItem("trabajoId", trabajoId);
		
		$(location).attr('href', '/archivo/formInformeTrabajo/' + trabajoId);
	});
	
	$('#dataTable tbody').on('click', 'button#adjuntarAutores', function() {
		
		var trabajoId = $(this).attr('idtrabajo');
		console.log("trabajoId: " + trabajoId);
		localStorage.setItem("trabajoId", trabajoId);
		
		$(location).attr('href', '/autor/formAutorDetalle/' + trabajoId);
	});
});