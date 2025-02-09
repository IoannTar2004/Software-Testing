package org.example.story.enums;

import lombok.Getter;

public enum WeakPart {
    EARS("уши"), EYES("глаза"), THROAT("горло");

    @Getter
    private final String description;

    WeakPart(String description) {
        this.description = description;
    }
}
