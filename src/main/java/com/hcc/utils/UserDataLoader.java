package com.hcc.utils;

import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Application startup data loader for initializing user data.
 *
 * this component runs automatically when the application starts and
 * seeds the database with a default user if no users currently exist.
 * It is intended for development and testing environments to ensure
 * the application has initial data available.
 *
 * The loader is idempotent and will not create duplicate users on
 * subsequent application restarts.
 */
@Component
public class UserDataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepo;
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
        //loadAuthorityData();
    }

    private void loadUserData() {
        if (userRepo.count() == 0) {
            PasswordEncoder pwenc = new BCryptPasswordEncoder();
            String pw = pwenc.encode("asdfasdf");
            User tom2 = new User(LocalDate.now(), "codereviewer2", pw);
            userRepo.save(tom2);
        }
    }

//    private void loadAuthorityData() {
//        if (authRepo.count() == 0) {
//            Authority reviewer =
//        }
//    }
}
