/**
 * 
 */
$(document).on('ready', function() {
	
	$('#fileInformeInvestigacion').on('change', function() {
		
		var fileInput = $('#fileInformeInvestigacion');
		
		var file = $('#fileInformeInvestigacion').val();
		
		var extensionPermitida = ".pdf";
		
		var extension = (file.substring(file.lastIndexOf("."))).toLowerCase();
		
		if(extension == extensionPermitida) {
			
			fileInformeInvestigacionPreview(this);
		}
		
		else {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Asegurate de haber selecciondo un archivo PDF'
            });
			
			fileInput.val("");
			return false;
		}
	});
	
	function fileInformeInvestigacionPreview(input) {
		
		if(input.files && input.files[0]) {
			
			var visor = new FileReader();
			visor.onload = function(e) {
				
				$('#visorInformeInvestigacion').html('<embed src="' + e.target.result + '" width="100%" height="800">');
			};
			
			visor.readAsDataURL(input.files[0]);
		}
	}
	
	$('#guardarInformeInvestigacion').click(function(e) {
		
		e.preventDefault();
		
		if($('#asuntoInformeInvestigacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var pathname = window.location.pathname;
			var asignacionId = pathname.substr(34);
			console.log("asignacionId: " + asignacionId);
			
			var form = $('#formInformeInvestigacion')[0];
			
			var data = new FormData(form);
			
			var formData = {
				
					informeAsignacionId: $('#informeInvestigacionId').val(),
					asunto: $('#asuntoInformeInvestigacion').val()
			};
			
			data.append("informeJson", JSON.stringify(formData));
			
			console.log(formData);
			
			if(formData.informeAsignacionId) {
				
				var informeAsignacionId = formData.informeAsignacionId;
				console.log("informeAsignacionId: " + informeAsignacionId);
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/informeinvestigacion/save/asignacion/' + asignacionId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe: " + response.data.asunto +" Cargado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/informe/formInformeInvestigacion/' + asignacionId);
							}
						});
					},
					error: function() {
						alert('Error al Cargar Informe Investigacion');
					}
				});
			}
		}
		
		if($('#asuntoInformeInvestigacion').val() == "" || $('#asuntoInformeInvestigacion').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Descripcion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#asuntoInformeInvestigacion').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#asuntoInformeInvestigacion').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Descripcion no permite caracteres especiales'
	            });
				
				$('#asuntoInformeInvestigacion').val("");
				$('#asuntoInformeInvestigacion').focus();
		    	return false;
			} 
		}
	});
});