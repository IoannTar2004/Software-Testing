package org.example.story.characters;

import org.example.story.enums.DamagesPart;
import org.example.story.interfaces.Influence;
import org.example.story.info.DamageInfo;

public class Wind extends Character implements Influence {

    public Wind(String name) {
        super("Ветер " + name);
    }

    public Wind() {
        super("Ветер");
    }

    @Override
    public void negativeAction(Person person, DamagesPart damagePart, int damage) {
        System.out.printf("%s оглушает и ослепляет %s персонажа %s%n",
                getName(), damagePart.getDescription(), person.getName());
        person.getDamages().add(new DamageInfo(damagePart, damage));
    }
}
