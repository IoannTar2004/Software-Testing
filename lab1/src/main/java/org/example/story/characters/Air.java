package org.example.story.characters;

import org.example.story.enums.DamagesPart;
import org.example.story.interfaces.Influence;
import org.example.story.info.DamageInfo;

public class Air extends Character implements Influence {

    public Air(String name) {
        super("Воздух " + name);
    }

    public Air() {
        super("Воздух");
    }

    @Override
    public void negativeAction(Person person, DamagesPart damagePart, int damage) {
        System.out.printf("%s сдавливает %s персонажа %s%n", getName(), damagePart.getDescription(), person.getName());
        person.getDamagesSet().add(new DamageInfo(damagePart, damage));
    }
}
