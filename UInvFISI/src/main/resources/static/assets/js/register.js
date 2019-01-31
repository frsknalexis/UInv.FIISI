/**
 * 
 */

$(document).ready(function() {
	
	var allowSubmit = false;
	
	$('#password2').keyup(function(e) {
		var password = $('#password').val();
		var confirmpassword = $('#password2').val();
		
		if(password == confirmpassword) {
			allowSubmit = true;
			console.log("Correcto");
			$('#error').html('');
		}
		else {
			allowSubmit = false;
			console.log("False");
			$('#error').html('<div class="alert alert-danger" role="alert">Password not matching</div>')

		}
	})
	
	$('#registerForm').submit(function() {
		var password = $('#password').val();
		var confirmpassword = $('#password2').val();
		
		if(password == confirmpassword) {
			allowSubmit = true;
		}
		
		if(allowSubmit) {
			
			return true;
		}
		else {
			return false;
		}
	})
	
	$('#form_submit').submit(function() {
		
		$('#nombre').val();
	})
});