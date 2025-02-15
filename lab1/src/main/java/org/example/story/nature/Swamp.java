package org.example.story.nature;

import lombok.Data;
import org.example.story.interfaces.Biome;

import java.util.HashSet;
import java.util.Set;

@Data
public class Swamp implements Biome {
    private final String name;
    private Set<Flora> floras = new HashSet<>();
    private double dustInch;

    public Swamp(String name) {
        this.name = "Болото" + name;
    }

    public Swamp() {
        this.name = "Болото";
    }

    @Override
    public void describeFlora() {
        if (floras.isEmpty())
            System.out.println(name + " лишено растительности");
        else {
            System.out.print(name + "имеет следующую растительность: ");
            floras.forEach(e -> System.out.print(e.getFlora() + ", "));
        }
        System.out.println(name + " покрыто пылью в " + dustInch + " дюйм толщиной");
    }

    @Override
    public String toString() {
        return name;
    }
}
