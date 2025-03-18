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

    public Car(String model) {
        this.model = model;
    }

    public Car() {}

    @ManyToMany(mappedBy = "cars")
    private List<Player> players = new ArrayList<>();
}
