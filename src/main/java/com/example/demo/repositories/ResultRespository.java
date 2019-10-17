package com.example.demo.repositories;



import com.example.demo.models.ResultModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRespository extends JpaRepository<ResultModel, Integer> {
}
