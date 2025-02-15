package org.example.story.nature;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Spot extends Landscape {
    private Colors color;

    public Spot(Colors color, int attraction) {
        super(attraction);
        this.color = color;
    }

    @Override
    public String toString() {
        return color.getColor() + " пятна";
    }
}
