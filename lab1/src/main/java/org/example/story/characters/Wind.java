package org.example.story.characters;

import org.example.story.enums.WeakPart;
import org.example.story.interfaces.Influence;

import java.util.Arrays;

public class Wind extends Character implements Influence {

    public Wind(String name) {
        super("Ветер " + name);
    }

    public Wind() {
        super("Ветер");
    }

    @Override
    public void negativeAction(Person person, WeakPart... weakParts) {
        System.out.print(getName() + " оглушает и ослепляет ");
        Arrays.stream(weakParts).forEach(e -> System.out.print(e.getDescription() + ", "));
        System.out.println("персонажа " + person.getName());
    }
}
