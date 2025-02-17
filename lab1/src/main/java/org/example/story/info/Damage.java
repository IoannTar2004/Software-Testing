package org.example.story.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.story.enums.DamagesPart;

@Data
@AllArgsConstructor
public class Damage {
    private DamagesPart damagesPart;
    private int damage;

    public void setDamage(int damage) {
        if (damage <= 0)
            throw new IllegalArgumentException("damage должен быть больше 0");
        this.damage = damage;
    }
}

