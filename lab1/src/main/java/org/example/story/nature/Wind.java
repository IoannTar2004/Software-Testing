package org.example.story.nature;

import lombok.Data;
import org.example.story.characters.DamagesPart;
import org.example.story.characters.Person;
import org.example.story.interfaces.Influence;
import org.example.story.info.Damage;

@Data
public class Wind implements Influence {

    private String name;

    public Wind(String name) {
        this.name = "Ветер " + name;
    }

    public Wind() {
        this.name = "Ветер";
    }

    @Override
    public void negativeAction(Person person, DamagesPart damagePart, int damage) {
        System.out.printf("%s оглушает и ослепляет %s персонажа %s%n",
                getName(), damagePart.getDescription(), person.getName());
        person.getDamages().add(new Damage(damagePart, damage));
    }
}
