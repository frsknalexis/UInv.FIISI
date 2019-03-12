/**
 * 
 */

$(document).on('ready', function() {
		
	$('#fileAlumno').on('change', function() {
		
		var archivoInput = $('#fileAlumno');
		
		var archivo = $('#fileAlumno').val();
		
		var extensionPermitida = ".pdf";
		
		var extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();
		
		if(extension  == extensionPermitida) {
			
			filePreview(this);
		}
		else {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Asegurate de haber selecciondo un archivo PDF'
            });
			
			archivoInput.val("");
			return false;
		}
	});
	
	
	function filePreview(input) {
		
		if (input.files && input.files[0]) {
	        var reader = new FileReader();
	        reader.onload = function (e) {
	            
	        	$('#visorArchivo').html('<embed src="' + e.target.result + '" width="600" height="600">');
	        }
	        reader.readAsDataURL(input.files[0]);
	    }
	}
	
	$('#guardarAsignaturaAlumno').click(function() {
		
		if($('#alumnoAsignatura').val() == "" && $('#documentoAlumno').val().trim() == ""
			 && $('#nroDocumentoAlumno').val() == "" && $('#asuntoAsignaturaAlumno').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#alumnoAsignatura').val() == "" || $('#alumnoAsignatura').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El Nombre Alumno no puede estar vacio, asigne un valor'
	            });
		    	
		    	$('#alumnoAsignatura').focus();
		    	return false;
			}
			
			if($('#documentoAlumno').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar un Tipo Documento'
	            });
				
		    	return false;
			}
			
			if($('#nroDocumentoAlumno').val() == "" || $('#nroDocumentoAlumno').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El Numero Documento no puede estar vacio, ingrese un valor'
	            });
		    	
		    	$('#nroDocumentoAlumno').focus();
		    	return false;
			}
			
			if($('#asuntoAsignaturaAlumno').val() == "" || $('#asuntoAsignaturaAlumno').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'La Descripcion no puede estar vacio, ingrese un valor'
	            });
		    	
		    	$('#asuntoAsignaturaAlumno').focus();
		    	return false;
			}
			
			if(!($('#alumnoAsignatura').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Alumno no permite caracteres especiales ni numeros'
	            });
				
				$('#alumnoAsignatura').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#nroDocumentoAlumno').val().match(/^[0-9]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El Numero Documento solo acepta numeros'
		            });
					
					$('#nroDocumentoAlumno').focus();
			    	return false;
				}
				
				else {
					
					if(!($('#asuntoAsignaturaAlumno').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\-\s]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'La Descripcion no permite caracteres especiales'
			            });
						
						$('#asuntoAsignaturaAlumno').focus();
				    	return false;
					}
				}
			}
		}
	});
	
});