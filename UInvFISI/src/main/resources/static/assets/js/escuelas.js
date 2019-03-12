/**
 * 
 */

$(document).ready(function() {
	
	$('#guardarEscuela').click(function() {
		
		if($('#nombre-escuela').val() == "" && $('#directorEscuela').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
			
			$('#nombre-escuela').focus();
			return false;
		}
		
		else {
			
			if($('#nombre-escuela').val() == "" || $('#nombre-escuela').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Escuela no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombre-escuela').focus();
		    	return false;
			}
			
			if($('#directorEscuela').val() == "" || $('#directorEscuela').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Director Escuela no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#directorEscuela').focus();
		    	return false;
			}
			
			if(!($('#nombre-escuela').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Escuela no permite caracteres especiales ni numeros'
	            });
				
				$('#nombre-escuela').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#directorEscuela').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Director Escuela no permite caracteres especiales ni numeros'
		            });
					
					$('#directorEscuela').focus();
			    	return false;
				}
			}
		}
	});
	
	
	$("#escuelasInvg").autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				url : "/escuelas/cargar-escuelas/" + request.term,
				dataType : 'json',
				data : {
					term : request.term
				},
				success : function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.escuelasId,
							label: item.nombre,
							
						};
					}));
				},
			});
		},
		
		select: function(event, ui) {
			$("#escuelasInvg").val(ui.item.label);
			$("#escuelasInvgId").val(ui.item.value);
			return false;
		}
	});
	
});