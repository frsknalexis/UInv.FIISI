/**
 * 
 */

$(document).on('ready', function() {
	
	$('#dataTable tbody').on('click', 'button#adjuntarInformeTrimestral', function() {
		
		var asignacionDetalleId = $(this).attr('idasignaciondetalle');
		console.log("asignacionDetalleId: " + asignacionDetalleId);
		
		localStorage.setItem("asignacionDetalleId", asignacionDetalleId);
		
		$(location).attr('href', '/informeTrimestral/formInformeTrimestral/' + asignacionDetalleId);
	});
});