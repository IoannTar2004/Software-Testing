package com.example.dbtest.controllers;

import com.example.dbtest.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    private final PlayerService playerService;

    @Autowired
    public AuthorizationController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/login")
    public String login(String name, String password) {
        try {
            playerService.login(name, password);
            return "ok";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @PostMapping("/reg")
    public String registration(String name, String password) {
        try {
            playerService.registration(name, password);
            return "ok";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
