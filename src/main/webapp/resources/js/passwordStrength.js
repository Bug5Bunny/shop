$(document).ready(
		function() {

			$('#password, #confirmPassword')
					.on(
							'keyup',
							function(e) {

								if ($('#password').val() == '' && $('#confirmPassword').val() == '') {
									$('#passwordStrength').removeClass().html('');

									return false;
								}

								if ($('#password').val() != '' && $('#confirmPassword').val() != ''
										&& $('#password').val() != $('#confirmPassword').val()) {
									$('#passwordStrength').removeClass().addClass('error').html('Please enter the same password as above');

									return false;
								}

								var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");

								var mediumRegex = new RegExp(
										"^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");

								if (strongRegex.test($(this).val())) {
									$('#passwordStrength').removeClass().addClass('success').html('Good Password!');
								} else if (mediumRegex.test($(this).val())) {
									$('#passwordStrength').removeClass().addClass('msg').html(
											'Make your password stronger with more capital letters, more numbers and special characters!');
								} else {
									$('#passwordStrength').removeClass().addClass('error').html(
											'Weak Password, try using numbers and capital letters.');
								}

								return true;
							});

		});