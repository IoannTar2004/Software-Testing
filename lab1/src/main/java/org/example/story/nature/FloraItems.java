package org.example.story.nature;

import lombok.Getter;

public enum FloraItems {
    TREES("деревья"), GRASS("трава"), FLOWERS("цветки");

    @Getter
    private final String floraItem;

    FloraItems(String floraItem) {
        this.floraItem = floraItem;
    }
}
