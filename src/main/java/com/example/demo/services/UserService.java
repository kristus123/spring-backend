package com.example.demo.services;

import com.example.demo.enums.UserRole;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel signup(String username, String password) {
        Optional<UserModel> u = userRepository.findByUsername(username);
        if (u.isPresent()) { //finn bedre løsning her
            return u.get();
        }

        final String PASSWORD = passwordEncoder.encode(password);
        UserModel user = new UserModel(username, PASSWORD, UserRole.STANDARD);
        UserModel dbUser = userRepository.save(user);
        System.out.println(userRepository.findByUsername(username).get().getPassword());
        return dbUser;
    }

    public boolean elevateUserToAdmin(int id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isPresent()) {
            userModel.get().changeRole(UserRole.ADMINISTRATOR);
            System.out.println(userModel.get().getRoles()[0]);
            userRepository.save(userModel.get());
            return true;
        }
        return false;
    }

    public boolean elevateUserToStandard(int id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isPresent()) {
            userModel.get().changeRole(UserRole.STANDARD);
            System.out.println(userModel.get().getRoles()[0]);
            userRepository.save(userModel.get());
            return true;
        }
        return false;
    }



    public boolean elevateUserToAdmin(UserModel user) {
        return elevateUserToAdmin(user.getId());
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

    public UserModel getUser(Principal principal) {
        System.out.println("++++++++");
        findAll().forEach(u -> System.out.println(u.getUsername()));
        System.out.println("TEST: " + principal.getName());
        Optional<UserModel> user = findByUsername(principal.getName());
        if (user.isPresent()) {
            System.out.println("TEST: han finnes");
            return user.get();
        }
        return null;
    }

    public UserModel getMe(Principal principal) {
        return findByUsername(principal.getName()).get();
    }

}
