/**
 * 
 */

$(document).on('ready', function() {
	
	$('#fileReglamento').on('change', function() {
		
		var fileInput = $('#fileReglamento');
		
		var file = $('#fileReglamento').val();
		
		var extensionPermitida = ".pdf";
		
		var extension = (file.substring(file.lastIndexOf("."))).toLowerCase();
		
		if(extension == extensionPermitida) {
			
			fileReglamentoPreview(this);
		}
		else {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Asegurate de haber selecciondo un archivo PDF'
            });
			
			fileInput.val("");
			return false;
		}
		
	});
	
	function fileReglamentoPreview(input) {
		
		if(input.files && input.files[0]) {
			
			var visor = new FileReader();
			visor.onload = function(e) {
				
				$('#visorArchivoReglamento').html('<embed src="' + e.target.result + '" width="600" height="600">');
			};
			
			visor.readAsDataURL(input.files[0]);
		}
	}
	
	$('#guardarReglamento').click(function() {
		
		if($('#asuntoReglamento').val() == "" || $('#asuntoReglamento').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Descripcion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#asuntoReglamento').focus();
	    	return false;
			
		}
		
		else {
			
			if(!($('#asuntoReglamento').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Descripcion no permite caracteres especiales'
	            });
				
				$('#asuntoReglamento').focus();
		    	return false;
			}
		}
	});
});