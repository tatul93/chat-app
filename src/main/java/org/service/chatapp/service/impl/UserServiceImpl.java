package org.service.chatapp.service.impl;

import org.service.chatapp.exception.ResourceNotFoundException;
import org.service.chatapp.model.domain.User;
import org.service.chatapp.repository.UserRepository;
import org.service.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing users.
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * Repository for managing users in the database.
	 */
	private final UserRepository userRepository;

	/**
	 * Constructor for UserServiceImpl, initializes userRepository.
	 *
	 * @param userRepository Repository for managing users in the database.
	 */
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Loads user details by username.
	 * It retrieves the user from the UserRepository.
	 *
	 * @param userName The username of the user.
	 * @return The User object for the user.
	 * @throws ResourceNotFoundException If the user could not be found.
	 */
	@Override
	public User loadUserByUsername(String userName) {
		return userRepository.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
}
