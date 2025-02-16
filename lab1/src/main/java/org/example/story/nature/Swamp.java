package org.example.story.nature;

import lombok.Data;
import org.example.story.interfaces.Biome;

import java.util.ArrayList;
import java.util.List;

@Data
public class Swamp implements Biome {
    private final String name;
    private List<FloraItems> floras = new ArrayList<>();
    private double dustInch;

    public Swamp(String name) {
        this.name = "Болото " + name;
    }

    public Swamp() {
        this.name = "Болото";
    }

    @Override
    public void describeFlora() {
        if (floras.isEmpty())
            System.out.println(name + " лишено растительности.");
        else {
            System.out.print(name + " имеет следующую растительность: ");
            floras.forEach(e -> System.out.print(e.getFloraItem() + ", "));
        }
        System.out.println(name + " покрыто пылью в " + dustInch + " дюйм толщиной.");
    }

    public void setDustInch(double dustInch) {
        if (dustInch < 0)
            throw new IllegalArgumentException("Слой пыли должен быть больше или равен 0");
        this.dustInch = dustInch;
    }

    @Override
    public String toString() {
        return name;
    }
}
