package com.epam.usermanager.entity;

public enum Type {
    PHONE("Телефон"),
    LAPTOP("Ноутбук"),
    MONITOR("Монитор");

    private String translation;

    Type(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
