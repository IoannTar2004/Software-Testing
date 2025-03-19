package com.example.dbtest.services;

import com.example.dbtest.entities.Player;
import com.example.dbtest.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void login(String name, String password) {
        if (name.length() > 32 || name.length() < 4 || password.length() > 16 || password.length() < 4)
            throw new RuntimeException("Произошла ошибка валидации");

        List<Player> players = playerRepository.findAll();
        boolean find = players.stream().anyMatch(p -> p.getName().equals(name) && p.getPassword().equals(password));
        if (!find)
            throw new RuntimeException("Неверный логин или пароль");
    }

    public void registration(String name, String password, String phone) {
        if (name.length() > 32 || name.length() < 4 || password.length() > 16 || password.length() < 4 ||
            phone.length() < 4 || phone.length() > 16)
            throw new RuntimeException("Произошла ошибка валидации");

        List<Player> players = playerRepository.findAll();
        boolean find = players.stream().anyMatch(p -> p.getName().equals(name));
        if (find)
            throw new RuntimeException("Игрок с таким ником уже существует");

         playerRepository.save(new Player(name, password, phone));
    }
}
