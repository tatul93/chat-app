package org.service.chatapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.service.chatapp.model.domain.User;
import org.service.chatapp.service.impl.UserDetailsServiceImpl;
import org.service.chatapp.service.impl.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("userName");
        user.setPassword("password");
        user.setFullName("fullName");
        when(userService.loadUserByUsername("userName")).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("userName");

        assertNotNull(userDetails);
        assertEquals("userName", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

}
