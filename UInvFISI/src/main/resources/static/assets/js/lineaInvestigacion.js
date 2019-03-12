/**
 * 
 */

$(document).ready(function() {
	
	$('#guardarLineaInvestigacion').click(function() {
		
		if($('#nombreLineaInvestigacion').val() == "" || $('#nombreLineaInvestigacion').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Nombre Linea Investigacion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#nombreLineaInvestigacion').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#nombreLineaInvestigacion').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.,-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Linea Investigacion no permite caracteres especiales'
	            });
		    	
		    	$('#nombreLineaInvestigacion').focus();
		    	return false;
			}
		}
	});
	
	$("#investigacionLineaInvg").autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				url : "/linea/cargar-lineas/" + request.term,
				dataType : 'json',
				data : {
					term : request.term
				},
				success : function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.lineaInvestigacionId,
							label: item.nombreLineaInvestigacion,
							
						};
					}));
				},
			});
		},
		
		select: function(event, ui) {
			$("#investigacionLineaInvg").val(ui.item.label);
			$("#investigacionLineaInvgId").val(ui.item.value);
			return false;
		}
	});
	
});