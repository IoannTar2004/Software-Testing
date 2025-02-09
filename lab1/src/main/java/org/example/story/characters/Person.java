package org.example.story.characters;

import lombok.Data;
import org.example.story.enums.States;

@Data
public class Person {
    private String name;
    private States state;
    private double speed;

    public Person(String name, States state) {
        this.name = name;
        this.state = state;
    }

    public void move() {
        if (speed == 0)
            System.out.println(name + " стоит на месте!");
        else if (speed > 0 && speed < 2)
            System.out.println(name + " медленно бредёт");
        else
            System.out.println(name + " идёт");
    }
}
