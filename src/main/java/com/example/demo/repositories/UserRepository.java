package com.example.demo.repositories;

import com.example.demo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel findByUsername(String username);

}
