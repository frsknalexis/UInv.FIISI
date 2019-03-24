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
});