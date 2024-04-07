package org.service.chatapp.service;

import org.service.chatapp.model.domain.User;

public interface UserService {

	User loadUserByUsername(String userName);


}
