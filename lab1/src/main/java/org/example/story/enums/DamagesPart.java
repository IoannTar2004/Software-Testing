package org.example.story.enums;

import lombok.Getter;

public enum DamagesPart {
    EARS("уши"), EYES("глаза"), THROAT("горло"), MIND("разум");

    @Getter
    private final String description;

    DamagesPart(String description) {
        this.description = description;
    }
}
