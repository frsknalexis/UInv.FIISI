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
});