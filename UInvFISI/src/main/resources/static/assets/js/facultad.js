/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarFacultad').click(function() {
		
		if($('#abreviatura-facultad').val() == "" && $('#facultad').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
			
			$('#abreviatura-facultad').focus();
			return false;
		}
		
		else {
			
			if($('#abreviatura-facultad').val() == "" || $('#abreviatura-facultad').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Abreviatura Facultad no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#abreviatura-facultad').focus();
		    	return false;
			}
			
			if($('#facultad').val() == "" || $('#facultad').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Facultad no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#facultad').focus();
		    	return false;
			}
			
			if(!($('#abreviatura-facultad').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Abreviatura Facultad no permite caracteres especiales ni numeros'
	            });
				
				$('#abreviatura-facultad').focus();
		    	return false;
			}
			else {
				
				if(!($('#facultad').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Nombre Facultad no permite caracteres especiales ni numeros'
		            });
					
					$('#facultad').focus();
			    	return false;
					
				}
			}
		}
	});
});