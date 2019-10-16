package com.example.demo.services;

import com.example.demo.repositories.audit.IPlayerHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerHistoryService {

    @Autowired
    private IPlayerHistoryRepository playerHistoryRepository;

}
