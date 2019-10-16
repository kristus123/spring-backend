package com.example.demo.repositories.audit;

import com.example.demo.models.PlayerHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPlayerHistoryRepository {
    List<PlayerHistoryModel> listPlayerHistoryRevisions(Integer id);
}
