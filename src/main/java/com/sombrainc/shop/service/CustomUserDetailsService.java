package org.chocolate.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.connectionmanager.DataBase;
import org.chocolate.shop.dao.impl.UserDAOImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private ConnectionManager connection = ConnectionManager.getInstance(DataBase.LOCALDB);
	private UserDAOImpl userDAO = UserDAOImpl.getInstance(connection);

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final org.chocolate.shop.entity.User user = userDAO.findByUsername(username);
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if (user.getRole().equals("ADMIN")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new User(user.getUsername(), user.getPassword(), true, true, true, user.isBan(), authorities);
	}

}
