package org.example.story.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.story.enums.DamagesPart;

@Data
@AllArgsConstructor
public class DamageInfo {
    private DamagesPart damagesPart;
    private int damage;
}

