/**
 * 
 */
$(document).on('ready', function() {
	
	$('#cancelarInformeTrabajo').on('click', function() {
		
		localStorage.removeItem('trabajoId');
		$(location).attr('href', '/trabajo/list');
	});
	
	$('#fileInformeTrabajo').on('change', function() {
		
		var fileInput = $('#fileInformeTrabajo');
		var file = $('#fileInformeTrabajo').val();
		var extensionPermitida = ".pdf";
		var extension = (file.substring(file.lastIndexOf("."))).toLowerCase();
		
		if(extension == extensionPermitida) {
			
			fileInformeTrabajoPreview(this);
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
	
	function fileInformeTrabajoPreview(input) {
		
		if(input.files && input.files[0]) {
			
			var visor = new FileReader();
			visor.onload = function(e) {
				
				$('#visorInformeTrabajo').html('<embed src="' + e.target.result + '" width="100%" height="800">');
			};
			
			visor.readAsDataURL(input.files[0]);
		}
	}
	
	
	$('#guardarInformeTrabajo').on('click', function(e) {
		
		e.preventDefault();
		
		if($('#asuntoInformeTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var trabajoId = localStorage.getItem("trabajoId");
			console.log("trabajoId: " + trabajoId);
			
			var form = $('#formInformeTrabajo')[0];
			
			var data = new FormData(form);
			
			var formData = {
					informeTrabajoId: $('#informeTrabajoId').val(),
					asunto: $('#asuntoInformeTrabajo').val()
			}
			
			data.append("informeTrabajoJson", JSON.stringify(formData));
			
			console.log(formData);
			
			if(formData.informeTrabajoId) {
				
				var informeTrabajoId = formData.informeTrabajoId;
				console.log("informeTrabajoId: " + informeTrabajoId);
				
				$.ajax({
					
				});
			}
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/informetrabajo/save/trabajo/' + trabajoId,
					enctype: 'multipart/form-data',
					data: data,
					processData: false,
					contentType: false,
					success: function(response) {
						
						console.log(response);
						
						swal({
							type: "success",
							title: "Informe Academico: " + response.data.asunto +" Cargado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/archivo/formInformeTrabajo/' + trabajoId);
							}
						});
					},
					error: function() {
						alert('Error al Cargar Informe Academico');
					}
				});
			}
		}
		
		if($('#asuntoInformeTrabajo').val() == "" || $('#asuntoInformeTrabajo').val() == 0) {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'El campo Descripcion no puede estar vacio, ingrese un valor valido'
            });
	    	
	    	$('#asuntoInformeTrabajo').focus();
	    	return false;
		}
		
		else {
			
			if(!($('#asuntoInformeTrabajo').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Descripcion no permite caracteres especiales'
	            });
				
				$('#asuntoInformeTrabajo').val("");
				$('#asuntoInformeTrabajo').focus();
		    	return false;
			}
		}
	});
});