package com.example.demo.repositories;

import com.example.demo.models.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactModel, Integer> {
}
