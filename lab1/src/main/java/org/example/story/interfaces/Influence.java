package org.example.story.interfaces;

import org.example.story.characters.Person;
import org.example.story.enums.DamagesPart;

public interface Influence {
    void negativeAction(Person person, DamagesPart damagePart, int damage);
}
