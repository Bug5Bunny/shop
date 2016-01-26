package org.chocolate.shop.controller;

import java.util.List;

import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.connectionmanager.DataBase;
import org.chocolate.shop.dao.impl.UserDAOImpl;
import org.chocolate.shop.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

	private ConnectionManager connection = ConnectionManager.getInstance(DataBase.LOCALDB);
	private UserDAOImpl userDAO = UserDAOImpl.getInstance(connection);

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String users(final Model model) {
		final List<User> list = userDAO.getAllNotLocked();
		model.addAttribute("list", list);
		final List<User> lockedList = userDAO.getAllLocked();
		model.addAttribute("lockedlist", lockedList);
		return "users";
	}

	@RequestMapping(value = "/lock", method = RequestMethod.GET)
	public String lockUser(@RequestParam(value = "username", required = true) final String username,
			@RequestParam(value = "notlocked", required = true) final String notlocked) {
		userDAO.lock(username, notlocked);
		return "redirect:/users";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam(value = "username", required = true) final String username) {
		userDAO.delete(userDAO.findByUsername(username));
		return "redirect:/users";
	}
}
