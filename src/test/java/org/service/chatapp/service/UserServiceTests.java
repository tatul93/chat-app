package org.service.chatapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.service.chatapp.model.domain.User;
import org.service.chatapp.repository.UserRepository;
import org.service.chatapp.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testLoadUserByUsername() {

        User user = new User();
        user.setId(1L);
        user.setUsername("userName");
        user.setPassword("password");
        user.setFullName("fullName");
        when(userRepository.findByUsername("userName")).thenReturn(Optional.of(user));

        User result = userService.loadUserByUsername("userName");

        assertNotNull(user);
        assertEquals(1L, result.getId());
        assertEquals("userName", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals("fullName", result.getFullName());
    }

}
