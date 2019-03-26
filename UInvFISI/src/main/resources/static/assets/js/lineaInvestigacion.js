/**
 * 
 */

$(document).ready(function() {
	
	$('#guardarLineaInvestigacion').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombreLineaInvestigacion').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\.,-\s]+$/)) {

			var programaId = localStorage.getItem("programaId");
		    console.log("programaId: " + programaId);
		    
		    var formData = {
		    	
		    		lineaInvestigacionId: $('#lineaInvestigacionId').val(),
		    		nombreLineaInvestigacion: $('#nombreLineaInvestigacion').val()
		    };
		    
		    console.log(formData);
		    
		    if(formData.lineaInvestigacionId) {
    	
		    	var lineaInvestigacionId = formData.lineaInvestigacionId;
		    	console.log("lineaInvestigacionId: " + lineaInvestigacionId);
		    	
		    	$.ajax({
		    		
		    		type: 'PUT',
		    		url: '/api/linea/update/programa/' + programaId + '/linea/' + lineaInvestigacionId,
		    		headers: {
		    			"Content-Type": "application/json",
		    			"Accept": "application/json"
		    		},
		    		data: JSON.stringify(formData),
		    		dataType: 'json',
		    		success: function(response) {
		    			
		    			console.log(response);
		    			
		    			swal({
							type: "success",
							title: "Linea Investigacion Actualizado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/linea/formLineaInvestigacion/' + programaId);
							}
						});
		    		},
		    		error: function() {
		    			alert('Error al  Actualizar Linea Investigacion');
		    		}
		    	});
		    }
		    
		    else {
		    	
		    	$.ajax({
		    		
		    		type:'POST',
		    		url: '/api/linea/save/programa/' + programaId,
		    		headers: {
		    			"Content-Type": "application/json",
		    			"Accept": "application/json"
		    		},
		    		data: JSON.stringify(formData),
		    		dataType: 'json',
		    		success:function(response) {
		    			
		    			console.log(response);
		    			
		    			swal({
							type: "success",
							title: "Linea Investigacion: " + response.data.nombreLineaInvestigacion + " Guardado con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/linea/formLineaInvestigacion/' + programaId);
							}
						});
		    			
		    		},
		    		error: function() {
		    			alert('Error al Registrar Linea Investigacion');
		    		}
		    	});
		    }
		}
		
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
	
	$('#tablaLineasInvestigacion tbody').on('click', 'button#disabledLineaInvestigacion', function() {
		
		var lineaInvestigacionId = $(this).attr('idlineainvestigacion');
		
		console.log('lineaInvestigacionId: ' + lineaInvestigacionId);
		
		swal({
	        title: '¿Esta Seguro de deshabilitar esta Linea Investigacion ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, deshabilitar Linea !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/linea/linea/disabled/' + lineaInvestigacionId,
	                 type: 'GET',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "La Linea Investigacion: " + response.data.nombreLineaInvestigacion + " ha sido deshabilitado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", "/linea/formLineaInvestigacion/" + response.data.programa.programaId);
	                         }
	                     })
	                 }
	             });
	        }
	        else {
	            swal({
	                type: "error",
	                title: "Cancelado", 
	                text: "Usted ha cancelado la acción de deshabilitar"
	            });
	        }
	    });
		
	});
	
	$('#tablaLineasInvestigacion tbody').on('click', 'button#enabledLineaInvestigacion', function() {
		
		var lineaInvestigacionId = $(this).attr('idlineainvestigacion');
		console.log("lineaInvestigacionId: " + lineaInvestigacionId);
		
		swal({
	        title: '¿Esta Seguro de habilitar esta Linea Investigacion ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Linea !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/linea/linea/enabled/' + lineaInvestigacionId,
	        		type: 'GET',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "La Linea Investigacion: " + response.data.nombreLineaInvestigacion + " ha sido habilitado correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", "/linea/formLineaInvestigacion/" + response.data.programa.programaId);
	                        }
	                     })
	                 }
	        	});
	        }
	        else {
	            swal({
	                type: "error",
	                title: "Cancelado", 
	                text: "Usted ha cancelado la acción de habilitar"
	            });
	        }
	    });
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
	
	$('#cancelarLinea').on('click', function() {
		
		localStorage.removeItem("programaId");
		$(location).attr('href', '/programa/listar');
	});
	
});