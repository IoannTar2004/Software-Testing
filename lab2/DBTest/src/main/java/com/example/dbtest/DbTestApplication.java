package com.example.dbtest;

import com.example.dbtest.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbTestApplication {

    @Autowired
    private static PlayerRepository playerRepository;

    public static void main(String[] args) {
        SpringApplication.run(DbTestApplication.class, args);
    }

}
