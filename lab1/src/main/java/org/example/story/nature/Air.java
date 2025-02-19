package org.example.story.nature;

import lombok.Data;
import org.example.story.characters.DamagesPart;
import org.example.story.characters.Person;
import org.example.story.interfaces.Influence;
import org.example.story.info.Damage;

@Data
public class Air implements Influence {

    private String name;

    public Air(String name) {
        this.name = "Воздух " + name;
    }

    public Air() {
        this.name = "Воздух";
    }

    @Override
    public void negativeAction(Person person, DamagesPart damagePart, int damage) {
        System.out.printf("%s сдавливает %s персонажа %s%n", getName(), damagePart.getDescription(), person.getName());
        person.getDamages().add(new Damage(damagePart, damage));
    }
}
