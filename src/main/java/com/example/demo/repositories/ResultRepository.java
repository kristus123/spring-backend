package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.ResultModel;

public interface ResultRepository extends JpaRepository<ResultModel, Integer> {
}


