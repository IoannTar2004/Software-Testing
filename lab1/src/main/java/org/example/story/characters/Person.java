package org.example.story.characters;

import lombok.Getter;
import lombok.Setter;
import org.example.story.enums.DamagesPart;
import org.example.story.enums.States;
import org.example.story.info.DamageInfo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Person extends Character{
    private States state;
    private double speed;
    private Set<DamageInfo> damagesSet;
    private double x, y;

    public Person(String name, States state) {
        super(name);
        this.state = state;
        this.damagesSet = new HashSet<>();
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
        DamageInfo damageInfo = damagesSet.stream().max(Comparator.comparing(DamageInfo::getDamage)).orElse(null);
        if (damageInfo == null) return null;
        System.out.printf("Сильнее всего повреждённым является %s персонажа %s%n",
                damageInfo.getDamagesPart().getDescription(), getName());
        return damageInfo.getDamagesPart();
    }

}
