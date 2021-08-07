package com.epam.usermanager.entity;

public enum Sex {
    MALE("мужской"),
    FEMALE("женский");

    private String translation;

    Sex(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
