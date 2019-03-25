/**
 * 
 */

$(document).on('ready', function() {
	
	$.ajax({
		
		type: 'GET',
		url: '/api/docente/contadorDocentes',
		success: function(response){
			
			console.log(response);
			
			$('#totalDocentes').html(response);
		}
	});
	
	$.ajax({
		
		type: 'GET',
		url: '/api/asignacioninvestigacion/totalAsignacion',
		success: function(response) {
			
			console.log(response);
			$('#totalInvestigaciones').html(response);
		}
	});
	
	$.ajax({
		
		type: 'GET',
		url: '/api/trabajo/totalTrabajos',
		success: function(response) {
			
			console.log(response);
			$('#totalTrabajos').html(response);
		}
	});
	
	$.ajax({
		
		type: 'GET',
		url: '/api/asignatura/totalAsignatura',
		success: function(response) {
			
			console.log(response);
			$('#totalAsignaturas').html(response);
		}
	});
});