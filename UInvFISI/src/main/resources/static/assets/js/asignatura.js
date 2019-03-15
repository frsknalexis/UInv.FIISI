/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarAsignatura').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombreAsignatura').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,-\s]+$/) 
			 && $('#asignaturaEscuela').val().trim() != "" && $('#periodo').val().match(/^[a-zA-Z0-9\-\s]+$/)
			 && $('#ciclo').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/)
			 && $('#docenteAsignatura').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var formData = {
				
					asignaturaId: $('#asigantura-id').val(),
					nombreAsignatura: $('#nombreAsignatura').val(),
					escuela: {
						escuelaId: $('#asignaturaEscuela').val()
					},
					periodo: $('#periodo').val(),
					ciclo: $('#ciclo').val(),
					nombreDocente: $('#docenteAsignatura').val()
			};
			
			console.log(formData);
			
			if(formData.asignaturaId) {
				
				var asignaturaId = formData.asignaturaId;
				console.log("asignaturaId: " + asignaturaId);
			}
			
			else {
				
				
			}
		}
		
		if($('#nombreAsignatura').val() == "" && $('#asignaturaEscuela').val().trim() == "" 
			&& $('#periodo').val() == "" && $('#ciclo').val() == "" && $('#docenteAsignatura').val() == "") {
			
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
			
			if($('#asignaturaEscuela').val().trim() == "") {
				
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
			
			if($('#docenteAsignatura').val() == "" || $('#docenteAsignatura').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Docente no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#docenteAsignatura').focus();
		    	return false;
			}
			
			if(!($('#nombreAsignatura').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,-\s]+$/))) {
				
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
					
					else {
						
						if(!($('#docenteAsignatura').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
							
							swal({
				                type: 'error',
				                title: 'Ooops',
				                text: 'El campo Docente no permite caracteres especiales ni numeros'
				            });
							
							$('#docenteAsignatura').focus();
					    	return false;
						} 
					}
				}
			}
		}
	});
	
	$('#docenteAsignatura').autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				
				type: 'GET',
				url: '/api/docente/docentes/autocomplete/' + request.term,
				dataType: 'json',
				data: {
					term: request.term
				},
				success: function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.docenteId,
							label: item.nombresDocente + ', ' + item.apellidosDocente
						};
					}));
				}
			});
		},
		
		select: function(event, ui) {
			$('#docenteAsignatura').val(ui.item.label);
			return false;
		}
	});
});