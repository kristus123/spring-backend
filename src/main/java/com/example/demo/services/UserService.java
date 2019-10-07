package com.example.demo.services;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public UserModel signup(String username, String password) {
        return userRepository.save(new UserModel(username, passwordEncoder.encode(password)));
    }

    public boolean login(String username, String password) {
        final String PASSWORD_HASH = userRepository.findByUsername(username).getPassword();



        return passwordEncoder.matches(PASSWORD_HASH, password);
    }
}
