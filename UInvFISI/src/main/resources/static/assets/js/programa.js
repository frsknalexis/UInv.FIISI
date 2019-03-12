/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarPrograma').click(function() {
		
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
});