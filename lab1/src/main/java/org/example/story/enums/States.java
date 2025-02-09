package org.example.story.enums;

import lombok.Getter;

public enum States {
    DISCOURAGED("обескураженный"), SHOCKED("потрясённый"), NORMAL("нормальный");

    @Getter
    private final String description;

    States(String description) {
        this.description = description;
    }
}
