package com.hcc.services;

import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.utils.CustomPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserDetailServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    private CustomPasswordEncoder customPasswordEncoder;

    private UserDetailServiceImpl userDetailService;

    private final String validUsername = "validUser";
    private final String invalidUsername = "missingUser";
    private final String validPassword = "validPassword";

    @BeforeEach
    void setUp() {
        initMocks(this);

        customPasswordEncoder = new CustomPasswordEncoder();

        userDetailService = new UserDetailServiceImpl();
        userDetailService.userRepo = mockUserRepository;
        userDetailService.passwordEncoder = customPasswordEncoder;
    }

    @Test
    void loadUserByUsername_userExists_returnsUserDetails() {
        User mockUser = new User();
        mockUser.setUsername(validUsername);
        mockUser.setPassword(validPassword);

        when(mockUserRepository.findByUsername(validUsername))
                .thenReturn(Optional.of(mockUser));

        UserDetails result = userDetailService.loadUserByUsername(validUsername);

        verify(mockUserRepository).findByUsername(validUsername);

        assertNotNull(result);
        assertEquals(validUsername, result.getUsername());
        assertNotNull(result.getPassword());
        assertNotEquals(validPassword, result.getPassword());
    }

    @Test
    void loadUserByUsername_userNotFound_throwsUsernameNotFoundException() {
        when(mockUserRepository.findByUsername(invalidUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userDetailService.loadUserByUsername(invalidUsername));

        verify(mockUserRepository).findByUsername(invalidUsername);
    }
}
