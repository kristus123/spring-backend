package com.example.demo.services;

import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerModel save(PlayerModel player) {
        return playerRepository.save(player);
    }

    public void delete(PlayerModel player) {
        playerRepository.delete(player);
    }

    public Optional<PlayerModel> findById(Integer id) {
        return playerRepository.findById(id);
    }

    public List<PlayerModel> findAll() {
        return playerRepository.findAll();
    }

}
