/**
 * 
 */
$(document).on('ready', function() {
	
	$('#guardarAsignatura').click(function(e) {
		
		e.preventDefault();
		
		if($('#nombreAsignatura').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,-\s]+$/) 
			 && $('#asignaturaEscuela').val().trim() != "" && $('#periodo').val().match(/^[a-zA-Z0-9\-\s]+$/)
			 && $('#ciclo').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/)
			 && $('#docenteAsignatura').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/)) {
			
			var formData = {
				
					asignaturaId: $('#asigantura-id').val(),
					nombreAsignatura: $('#nombreAsignatura').val(),
					escuela: {
						escuelaId: $('#asignaturaEscuela').val()
					},
					periodo: $('#periodo').val(),
					ciclo: $('#ciclo').val(),
					nombreDocente: $('#docenteAsignatura').val()
			};
			
			if(formData.asignaturaId) {
				
				var asignaturaId = formData.asignaturaId;
				console.log("asignaturaId: " + asignaturaId);
				
				$.ajax({
					
					type: 'PUT',
					url: '/api/asignatura/update/' + asignaturaId,
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
							title: "Asignatura Actualizada con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/asignatura/list');
							}
						});
					},
					error: function() {
						alert('Error al Actualizar Asignatura');
					}
				});
			}
			
			else {
				
				$.ajax({
					
					type: 'POST',
					url: '/api/asignatura/save',
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
							title: "Asignatura: " + response.data.nombreAsignatura + " Registrada con exito",
							showConfirmButton: true,
							confirmButtonText: "Cerrar",
							closeOnConfirm: false
						}).then((result) => {

							if(result.value) {
								$(location).attr('href', '/asignatura/list');
							}
						});
						
					},
					error: function() {
						alert('Error al Registrar Asignatura');
					}
				});
			}
		}
		
		if($('#nombreAsignatura').val() == "" && $('#asignaturaEscuela').val().trim() == "" 
			&& $('#periodo').val() == "" && $('#ciclo').val() == "" && $('#docenteAsignatura').val() == "") {
			
			swal({
                type: 'error',
                title: 'Ooops',
                text: 'Debe llenar todos los Campos !'
            });
		
			return false;
		}
		
		else {
			
			if($('#nombreAsignatura').val() == "" || $('#nombreAsignatura').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Asignatura no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#nombreAsignatura').focus();
		    	return false;
			}
			
			if($('#asignaturaEscuela').val().trim() == "") {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'Debe seleccionar una Escuela'
	            });
				
		    	return false;
			}
			
			if($('#periodo').val() == "" || $('#periodo').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Periodo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#periodo').focus();
		    	return false;
			}
			
			if($('#ciclo').val() == "" || $('#ciclo').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Ciclo no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#ciclo').focus();
		    	return false;
			}
			
			if($('#docenteAsignatura').val() == "" || $('#docenteAsignatura').val() == 0) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Docente no puede estar vacio, ingrese un valor valido'
	            });
		    	
		    	$('#docenteAsignatura').focus();
		    	return false;
			}
			
			if(!($('#nombreAsignatura').val().match(/^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ\,-\s]+$/))) {
				
				swal({
	                type: 'error',
	                title: 'Ooops',
	                text: 'El campo Nombre Asignatura no permite caracteres especiales ni numeros'
	            });
				
				$('#nombreAsignatura').focus();
		    	return false;
			}
			
			else {
				
				if(!($('#periodo').val().match(/^[a-zA-Z0-9\-\s]+$/))) {
					
					swal({
		                type: 'error',
		                title: 'Ooops',
		                text: 'El campo Periodo no permite caracteres especiales ni numeros'
		            });
					
					$('#periodo').focus();
			    	return false;
				}
				
				else {
					
					if(!($('#ciclo').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$/))) {
						
						swal({
			                type: 'error',
			                title: 'Ooops',
			                text: 'El campo Ciclo no permite caracteres especiales ni numeros'
			            });
						
						$('#ciclo').focus();
				    	return false;
						
					}
					
					else {
						
						if(!($('#docenteAsignatura').val().match(/^[a-zA-ZñÑáéíóúÁÉÍÓÚ\,.-\s]+$/))) {
							
							swal({
				                type: 'error',
				                title: 'Ooops',
				                text: 'El campo Docente no permite caracteres especiales ni numeros'
				            });
							
							$('#docenteAsignatura').focus();
					    	return false;
						} 
					}
				}
			}
		}
	});
	
	$('#dataTable tbody').on('click', 'button#detalleAsignatura', function() {
		
		var asignaturaId = $(this).attr('idasignatura');
		console.log("asignaturaId: " + asignaturaId);
		
		var contenidoAsignatura = document.querySelector('#contenidoAsignatura');
		
		var contenidoAsignaturaAlumnos = document.querySelector('#contenidoAsignaturaAlumnos');
		
		setTimeout("$('#modalDetalleAsignatura').modal('show');", 1000);
		
		contenidoAsignatura.innerHTML = '';
		
		$.ajax({
			
			type: 'GET',
			url: '/api/asignatura/asignatura/' + asignaturaId,
			dataType: 'json',
			success: function(asignatura) {
				console.log(asignatura);
				$('#titleAsignaturaModal').html('Asignatura: ' + asignatura.nombreAsignatura);
				contenidoAsignatura.innerHTML += '<tr><td>' + asignatura.escuela.nombreEscuela +'</td><td>' + asignatura.periodo +'</td><td>' + asignatura.ciclo + '</td><td>' + asignatura.nombreDocente +'</td></tr>';
				$('#alertAsignaturaAlumnos').html('Aun no hay Alumnos Asignados a la Asignatura : ' + '<strong>' + asignatura.nombreAsignatura + '</strong>');
				
				$.ajax({
					
					type: 'GET',
					url: '/api/asignaturaalumno/asignaturaalumnos/asignatura/' + asignaturaId,
					dataType: 'json',
					success: function(response) {
						
						if(response != null) {
							
							$('.tablaAsignaturaAlumnos').show();
							$('#alertAsignaturaAlumnos').hide();
							
							contenidoAsignaturaAlumnos.innerHTML = '';
							for(var i = 0; i < response.length; i++) {
								console.log(response[i]);
								contenidoAsignaturaAlumnos.innerHTML += '<tr>';
								contenidoAsignaturaAlumnos.innerHTML += '<td>' + (i+1) + '</td><td>' + response[i].alumno +'</td><td>' + response[i].asunto +'</td><td><div class="btn-group" role="group" aria-label="Basic example"><a href="/api/asignaturaalumno/view/' + response[i].nombreFichero + '" target="_blank"  class="btn btn-outline-info btn-xs"><i class="fa fa-eye" title="Visualizar PDF"></i></a><a href="/api/asignaturaalumno/download/' + response[i].nombreFichero + '" class="btn btn-outline-primary btn-xs"><i class="fa fa-download" title="Descargar"></i></a></div></td></td>';
								contenidoAsignaturaAlumnos.innerHTML += '</tr>';
							}
						}
						else {
							console.log("No hay datos");
							$('.tablaAsignaturaAlumnos').hide();
							$('#alertAsignaturaAlumnos').show();
						}
					}
				});
				
			}
		});
	});
	
	$('#dataTable tbody').on('click', 'button#asignaturaAsignaturaAlumno', function() {
		
		var asignaturaId = $(this).attr('idasignatura');
		console.log("asignaturaId: " + asignaturaId);
		localStorage.setItem("asignaturaId", asignaturaId);
		$(location).attr('href', '/asignaturaAlumno/formAsignaturaDetalle/' + asignaturaId);
	});
	
	$('#dataTable tbody').on('click', 'button#disabledAsignatura', function() {
		
		var asignaturaId = $(this).attr('idasignatura');
		console.log("asignaturaId: " + asignaturaId);
		
		swal({
	        title: '¿Esta Seguro de finalizar el Proceso de esta Asignatura ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, finalizar Proceso !'
	    }).then((result) => {
	        if(result.value){
	           
	        	 $.ajax({
	                 url: '/api/asignatura/asignatura/disabled/' + asignaturaId,
	                 type: 'GET',
	                 dataType: 'json',
	                 success: function(response){
	                	 
	                	 console.log(response);
	                     swal({
	                         type: "success",
	                         title: "El Proceso de la Asignatura: " + response.data.nombreAsignatura + " ha sido finalizado correctamente",
	                         showConfirmButton: true,
	                         confirmButtonText: "Cerrar",
	                         closeOnConfirm: false
	                     }).then((result) => {
	                         if(result.value) {
	                             $(location).attr("href", '/asignatura/list');
	                         }
	                     })
	                 }
	             });
	        }
	        else {
	            swal({
	                type: "error",
	                title: "Cancelado", 
	                text: "Usted ha cancelado la acción de finalizar Proceso"
	            });
	        }
	    });
	});
	
	$('#dataTable tbody').on('click', 'button#enabledAsignatura', function() {
		
		var asignaturaId = $(this).attr('idasignatura');
		console.log("asignaturaId: " + asignaturaId);
		
		swal({
	        title: '¿Esta Seguro de habilitar esta Asignatura ?',
	        text: '¡Si no lo esta puede Cancelar la accion!',
	        type: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        cancelButtonText: 'Cancelar',
	        confirmButtonText: '¡Si, habilitar Asignatura !'
	    }).then((result) => {
	        if(result.value){
	           
	        	$.ajax({
	        		
	        		url: '/api/asignatura/asignatura/enabled/' + asignaturaId,
	        		type: 'GET',
	        		dataType: 'json',
	        		success: function(response){
	        			
	        			console.log(response);
	        			
	        			swal({
	        				type: "success",
	                        title: "La Asignatura: " + response.data.nombreAsignatura + " ha sido habilitada correctamente",
	                        showConfirmButton: true,
	                        confirmButtonText: "Cerrar",
	                        closeOnConfirm: false
	                       }).then((result) => {
	                         if(result.value) {
	                            $(location).attr("href", '/asignatura/list');
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
	
	$('#docenteAsignatura').autocomplete({
		
		source: function(request, response) {
			
			$.ajax({
				
				type: 'GET',
				url: '/api/docente/docentes/autocomplete/' + request.term,
				dataType: 'json',
				data: {
					term: request.term
				},
				success: function(data) {
					console.log(data);
					response($.map(data, function(item) {
						return {
							value: item.docenteId,
							label: item.nombresDocente + ', ' + item.apellidosDocente
						};
					}));
				}
			});
		},
		
		select: function(event, ui) {
			$('#docenteAsignatura').val(ui.item.label);
			return false;
		}
	});
});