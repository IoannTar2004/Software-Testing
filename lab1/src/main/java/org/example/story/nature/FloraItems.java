package org.example.story.nature;

import lombok.Getter;

public enum FloraItems {
    TREES("Деревья"), GRASS("Трава"), FLOWERS("Цветки");

    @Getter
    private final String floraItem;

    FloraItems(String floraItem) {
        this.floraItem = floraItem;
    }
}
