package com.hcc.services;

 import com.hcc.entities.User;
 import com.hcc.repositories.UserRepository;
 import com.hcc.utils.CustomPasswordEncoder;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Implements UserDetailService to load User by username from API endpoint.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    CustomPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        user.setUsername(username);
        user.setPassword(passwordEncoder.getPasswordEncoder().encode("asdfasdf"));
        return user;
    }
}
