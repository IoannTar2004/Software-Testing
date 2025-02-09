package org.example.story.characters;

import org.example.story.enums.WeakPart;
import org.example.story.interfaces.Influence;

import java.util.Arrays;

public class Air extends Character implements Influence {

    public Air(String name) {
        super("Воздух " + name);
    }

    public Air() {
        super("Воздух");
    }

    @Override
    public void negativeAction(Person person, WeakPart... weakParts) {
        System.out.print(getName() + " сдавливает ");
        Arrays.stream(weakParts).forEach(e -> System.out.print(e.getDescription() + ", "));
        System.out.println("персонажа " + person.getName());
    }
}
