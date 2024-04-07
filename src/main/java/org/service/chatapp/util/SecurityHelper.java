package org.service.chatapp.util;

import org.service.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * Helper class for security-related operations.
 */
@Component
public class SecurityHelper {

	/**
	 * Service for managing users.
	 */
	private final UserService userService;

	/**
	 * Constructor for SecurityHelper, initializes userService.
	 *
	 * @param userService Service for managing users.
	 */
	@Autowired
	public SecurityHelper(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Retrieves the user ID from the Principal object.
	 * It retrieves the user from the UserService using the username from the Principal object and returns the user's ID.
	 *
	 * @param principal The Principal object containing the username of the user.
	 * @return The ID of the user.
	 */
	public Long getUserIdFromPrincipal(Principal principal) {
		return userService.loadUserByUsername(principal.getName()).getId();
	}
}
