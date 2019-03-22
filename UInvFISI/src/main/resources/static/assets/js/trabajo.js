/**
 * 
 */

$(document).on('ready', function(){
	
	$('#guardarTrabajo').click(function(e){
		
		e.preventDefault();
		
		if($('#nombreTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) && $('#escuela').val().trim() != ""
			&& $('#anioInvestigacion').val().match(/^[0-9\s]+$/) && $('#tipoTrabajo').val().trim() != "" 
				&& $('#denominacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/) && $('#fechaSustentacion').val() != ""
				&& $('#citacionAcademicos').val().trim() != "" && $('#gradoAcademicoTrabajo').val().trim() != "" 
				&& $('#canhojas').val().match(/^[0-9\s]+$/) && $('#areaConocimiento').val().trim() != "") {
			
			var formData = {
				
					trabajoId: $('#trabajo-id').val(),
					
			};
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
});