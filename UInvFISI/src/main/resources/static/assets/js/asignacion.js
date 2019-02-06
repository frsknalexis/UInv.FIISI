/**
 * 
 */

$(document).ready(function() {
	$("#docenteAsignacion").autocomplete({
		
		source: function(request, response) {
			$.ajax({
				url : "/docente/cargar-docentes/" + request.term,
				dataType : 'json',
				data : {
					term : request.term
				},
				success : function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.docenteId,
							label: item.nombresDocente + ', ' + item.apellidosDocente,
						};
					}));
				},
			});
		},
		select: function(event, ui) {
			$("#docenteAsignacion").val(ui.item.label);
			return false;
		}
		
	});
});