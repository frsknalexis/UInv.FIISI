/**
 * 
 */

$(document).ready(function() {
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