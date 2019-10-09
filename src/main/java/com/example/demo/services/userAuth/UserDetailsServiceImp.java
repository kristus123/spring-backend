package com.example.demo.services.userAuth;



import com.example.demo.enums.UserRole;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserModel user = findUserbyUername(username);

        UserBuilder builder = null;
        if (user != null) {

            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());

            builder.roles(user.getRoles());
            System.out.println(builder);

        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private UserModel findUserbyUername(String username) {
        Optional<UserModel> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            return user.get();
        }

        System.out.println("user not found ");
        return null;
    }
}