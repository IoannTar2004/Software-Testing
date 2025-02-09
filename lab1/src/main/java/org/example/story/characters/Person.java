package org.example.story.characters;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.story.enums.DamagesPart;
import org.example.story.enums.States;
import org.example.story.info.DamageInfo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class Person {
    private String name;
    private States state;
    private double speed;
    private Set<DamageInfo> damagesSet;
    private double x, y;

    public Person(String name, States state) {
        this.name = name;
        this.state = state;
        this.damagesSet = new HashSet<>();
    }

    public void move() {
        if (speed == 0)
            System.out.println(name + " стоит на месте!");
        else if (speed > 0 && speed < 2)
            System.out.println(name + " медленно бредёт");
        else
            System.out.println(name + " идёт");
    }

    public DamagesPart findMostDamagedPart() {
        DamageInfo damageInfo = damagesSet.stream().max(Comparator.comparing(DamageInfo::getDamage)).orElse(null);
        if (damageInfo == null) return null;
        System.out.printf("Сильнее всего повреждённым является %s персонажа %s%n",
                damageInfo.getDamagesPart().getDescription(), name);
        return damageInfo.getDamagesPart();
    }

}
