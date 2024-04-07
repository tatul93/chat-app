package org.service.chatapp.service.impl;

import org.service.chatapp.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Service implementation for handling user-related operations in the context of Spring Security.
 */
@Component
public class UserDetailsServiceImpl implements
		UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Loads user details by username.
	 * It retrieves the user from the UserService and constructs a UserDetails object for Spring Security.
	 *
	 * @param username The username of the user.
	 * @return The UserDetails object for the user.
	 * @throws UsernameNotFoundException If the user could not be found.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		org.service.chatapp.model.domain.User user = userService.loadUserByUsername(username);
		return new User(username,
				user.getPassword(), true, true, true,
				true, Collections.emptyList());
	}


}
