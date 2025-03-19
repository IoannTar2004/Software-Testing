package com.example.dbtest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "players")
@ToString(exclude = {"cars"})
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String phone;

    public Player(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = null;
    }

    public Player() {}

    @ManyToMany
    @JoinTable(
            name = "players_cars",
            joinColumns = { @JoinColumn(name = "car_id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id")}
    )
    private List<Car> cars = new ArrayList<>();
}
