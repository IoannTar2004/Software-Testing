package com.example.dbtest.controllers;

import com.example.dbtest.services.PlayerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    private final PlayerService playerService;

    @Autowired
    public AuthorizationController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "data") String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            playerService.login(jsonObject.getString("name"), jsonObject.getString("password"));
            return new JSONObject().put("status", "ok").toString();
        } catch (RuntimeException e) {
            return new JSONObject().put("status", e.getMessage()).toString();
        }
    }

    @PostMapping("/reg")
    public String registration(@RequestParam(name = "data") String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            playerService.registration(jsonObject.getString("name"), jsonObject.getString("password"));
            return new JSONObject().put("status", "ok").toString();
        } catch (RuntimeException e) {
            return new JSONObject().put("status", e.getMessage()).toString();
        }
    }
}
