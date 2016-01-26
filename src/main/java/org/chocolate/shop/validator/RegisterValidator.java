package org.chocolate.shop.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.connectionmanager.DataBase;
import org.chocolate.shop.dao.impl.UserDAOImpl;
import org.chocolate.shop.entity.RegisterForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

	private ConnectionManager connection = ConnectionManager.getInstance(DataBase.LOCALDB);
	private UserDAOImpl userDAO = UserDAOImpl.getInstance(connection);

	@Override
	public boolean supports(final Class<?> clazz) {
		return RegisterForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final RegisterForm registerForm = (RegisterForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty", "Please enter a username");
		final String username = registerForm.getUsername();
		if ((username.length()) > 20) {
			errors.rejectValue("username", "username.tooLong", "Please enter value less than or equal 20 characters");
		}

		if (userDAO.findByUsername(username) != null) {
			errors.rejectValue("username", "username.incorrect", "Username already exist");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Please provide a password");
		if (!(registerForm.getPassword()).equals(registerForm.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "confirmPassword.passwordDontMatch", "Please enter the same password as above");
		}

		if (!EmailValidator.getInstance().isValid(registerForm.getEmail())) {
			errors.rejectValue("email", "email.notValid", "Your email address must be in the format of name@domain.com");
		}

		if (userDAO.findByUsername(registerForm.getEmail()) != null) {
			errors.rejectValue("email", "email.incorrect", "Email already exist");
		}
	}
}
