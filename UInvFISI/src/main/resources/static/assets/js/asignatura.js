/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarAsignatura').click(function() {
		
		if($('#nombreAsignatura').val() == "" && $('#escuela').val().trim() == "" 
			&& $('#periodo').val() == "" && $('#ciclo').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#nombreAsignatura').val() == "" || $('#nombreAsignatura').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Asignatura no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombreAsignatura').focus();
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
			
			if($('#periodo').val() == "" || $('#periodo').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Periodo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#periodo').focus();
		    	return false;
			}
			
			if($('#ciclo').val() == "" || $('#ciclo').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Ciclo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#ciclo').focus();
		    	return false;
			}
			
			if(!($('#nombreAsignatura').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Asignatura no permite caracteres especiales ni numeros'
	            });
				
				$('#nombreAsignatura').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#periodo').val().match(/^[a-zA-Z0-9\-\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Periodo no permite caracteres especiales ni numeros'
		            });
					
					$('#periodo').focus();
			    	return false;
				}
				
				else {
					
					if(!($('#ciclo').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'El campo Ciclo no permite caracteres especiales ni numeros'
			            });
						
						$('#ciclo').focus();
				    	return false;
						
					}
				}
			}
		}
	});
});