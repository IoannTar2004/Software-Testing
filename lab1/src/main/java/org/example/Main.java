package org.example;

import org.example.story.characters.Air;
import org.example.story.characters.Ground;
import org.example.story.characters.Person;
import org.example.story.characters.Wind;
import org.example.story.enums.States;
import org.example.story.enums.DamagesPart;
import org.example.story.info.GroupInfo;
import org.example.story.nature.Colors;
import org.example.story.nature.Spot;
import org.example.story.nature.Swamp;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person arthur = new Person("Артур", States.SHOCKED);
        Person zafod = new Person("Зафод", States.DISCOURAGED);
        List<Person> people = List.of(arthur, zafod, new Person("p1", States.NORMAL),
                new Person("p2", States.NORMAL), new Person("p3", States.NORMAL));
        GroupInfo group = new GroupInfo();
        people.forEach(e -> {
            e.setX(Math.random() * 5 + 2);
            e.setY(Math.random() * 5 + 2);
            group.getMembers().add(e);
            e.setSpeed(1.5);
            e.move();
        });

        Ground ground = new Ground();
        ground.getLandscapes().add(new Spot(Colors.DULLGRAY, 30));
        ground.getLandscapes().add(new Spot(Colors.DULLBROWN, 40));
        ground.getLandscapes().add(new Spot(Colors.DULLBROWN, 10));
        ground.attracts(30);
        Swamp swamp = new Swamp();
        swamp.setDustInch(1);
        ground.setSimilarBiome(swamp);
        ground.isSimilarTo(true);


        group.setLeader(people.get(4));
        zafod.setX(30);
        zafod.setY(30);
        group.getRemoteMember();

        Wind wind = new Wind();
        wind.negativeAction(arthur, DamagesPart.EYES, 30);
        wind.negativeAction(arthur, DamagesPart.EARS, 50);
        Air air = new Air();
        air.negativeAction(arthur, DamagesPart.THROAT, 40);
        air.negativeAction(arthur, DamagesPart.MIND, 80);

        arthur.findMostDamagedPart();
    }
}
