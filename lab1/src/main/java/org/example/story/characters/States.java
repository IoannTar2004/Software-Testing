package org.example.story.characters;

import lombok.Getter;

public enum States {
    DISCOURAGED("обескураженный"), SHOCKED("потрясённый"), NORMAL("нормальный");

    @Getter
    private final String description;

    States(String description) {
        this.description = description;
    }
}
