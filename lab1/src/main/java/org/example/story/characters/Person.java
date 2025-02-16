package org.example.story.characters;

import lombok.Getter;
import lombok.Setter;
import org.example.story.enums.DamagesPart;
import org.example.story.enums.States;
import org.example.story.info.DamageInfo;

import java.util.*;

@Getter
@Setter
public class Person extends Character {
    private States state;
    private double speed;
    private List<DamageInfo> damages;
    private double x, y;

    public Person(String name, States state) {
        super(name);
        this.state = state;
        this.damages = new ArrayList<>();
    }

    public void move() {
        if (speed == 0)
            System.out.println(getName() + " стоит на месте!");
        else if (speed > 0 && speed < 2)
            System.out.println(getName() + " медленно бредёт");
        else
            System.out.println(getName() + " идёт");
    }

    public DamagesPart findMostDamagedPart() {
        if (damages.isEmpty()) {
            System.out.println("У персонажа " + getName() + " нет повреждений.");
            return null;
        }

        DamageInfo maxDamage = damages.get(0);
        boolean allMatch = true;
        for (int i = 1; i < damages.size(); i++) {
            if (damages.get(i).getDamage() != maxDamage.getDamage())
                allMatch = false;
            maxDamage = damages.get(i).getDamage() > maxDamage.getDamage() ? damages.get(i) : maxDamage;
        }
        if (allMatch)
            System.out.println("У персонажа " + getName() + " всё одинаково повреждено.");
        else
            System.out.printf("Сильнее всего повреждённым является %s персонажа %s%n",
                    maxDamage.getDamagesPart().getDescription(), getName());
        return maxDamage.getDamagesPart();
    }

}
