package org.example.story.nature;

import lombok.Getter;

public enum Colors {
    DULLGRAY("Тускло-серый"), DULLBROWN("Тускло-коричневый");

    @Getter
    private final String color;

    Colors(String color) {
        this.color = color;
    }
}
