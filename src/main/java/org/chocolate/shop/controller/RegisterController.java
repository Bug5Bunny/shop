package org.chocolate.shop.controller;

import java.util.ArrayList;
import java.util.List;

import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.connectionmanager.DataBase;
import org.chocolate.shop.dao.impl.UserDAOImpl;
import org.chocolate.shop.entity.RegisterForm;
import org.chocolate.shop.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

	private ConnectionManager connection = ConnectionManager.getInstance(DataBase.LOCALDB);
	private UserDAOImpl userDAO = UserDAOImpl.getInstance(connection);

	@RequestMapping(method = RequestMethod.GET)
	public String register(final ModelMap model) {
		final RegisterForm registerForm = new RegisterForm();
		model.put("registerForm", registerForm);
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processRegister(final RegisterForm registerForm, final BindingResult result) {
		final ModelAndView model = new ModelAndView();
		if (result.hasErrors()) {
			model.setViewName("register");
			return model;
		}
		final User user = new User();
		user.setUsername(registerForm.getUsername());
		user.setPassword(registerForm.getPassword());
		user.setEmail(registerForm.getEmail());
		user.setRole("USER");
		userDAO.create(user);
		authenticateUser(user);
		model.addObject("title", "Chocolate shop");
		model.setViewName("main");
		return model;
	}

	private static void authenticateUser(final User user) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities));
	}
}
