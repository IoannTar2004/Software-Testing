package org.example.story.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.story.enums.DamagesPart;

@Data
@AllArgsConstructor
public class DamageInfo {
    private DamagesPart damagesPart;
    private int damage;

    public void setDamage(int damage) {
        if (damage <= 0)
            this.damage = 1;
        else
            this.damage = damage;
    }
}

