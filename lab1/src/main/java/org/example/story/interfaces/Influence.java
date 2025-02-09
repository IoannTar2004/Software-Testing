package org.example.story.interfaces;

import org.example.story.characters.Person;
import org.example.story.enums.WeakPart;

import java.util.List;

public interface Influence {
    void negativeAction(Person person, WeakPart... weakParts);
}
