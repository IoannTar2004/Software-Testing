package org.example;

import org.example.story.characters.Air;
import org.example.story.characters.Person;
import org.example.story.characters.Wind;
import org.example.story.enums.States;
import org.example.story.enums.WeakPart;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person arthur = new Person("Артур", States.SHOCKED);
        Person zafod = new Person("Зафод", States.DISCOURAGED);
        List<Person> people = List.of(arthur, zafod, new Person("p1", States.NORMAL),
                new Person("p2", States.NORMAL), new Person("p3", States.NORMAL));
        people.forEach(e -> {
            e.setSpeed(1.5);
            e.move();
        });

        Wind wind = new Wind();
        wind.negativeAction(arthur, WeakPart.EYES, WeakPart.EARS);
        Air air = new Air();
        air.negativeAction(arthur, WeakPart.THROAT);
    }
}
