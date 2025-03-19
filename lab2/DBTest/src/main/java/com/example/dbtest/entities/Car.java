package com.example.dbtest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@ToString(exclude = {"players"})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String model;
    private int speed;
    private int control;

    public Car(String model, int speed, int control) {
        this.model = model;
        this.speed = speed > 0 ? speed : 100;
        this.control = control > 0 && control <= 100 ? control : 50;
    }

    public Car() {}

    @ManyToMany(mappedBy = "cars")
    private List<Player> players = new ArrayList<>();
}
