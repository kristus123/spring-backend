package com.example.demo.services;

import com.example.demo.enums.UserRole;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    public UserModel signup(String username, String password) {

        System.out.println(password);
        final String PASSWORD = passwordEncoder.encode(password);

        UserModel user = new UserModel(username, PASSWORD, UserRole.STANDARD);

        UserModel dbUser = userRepository.save(user);


        System.out.println("AAAAAAAAAAAAAAAAAA");
        System.out.println(userRepository.findByUsername(username).get().getPassword());
        System.out.println("AAAAAAAAAAAAAAAAAA");

        return dbUser;
    }

    public UserModel save(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean login(String username, String password) {
        final String PASSWORD_HASH = userRepository.findByUsername(username).get().getPassword();

        return passwordEncoder.matches(PASSWORD_HASH, password);
    }
}
