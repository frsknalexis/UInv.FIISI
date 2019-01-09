/**
 * 
 */

$(document).ready(function() {
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