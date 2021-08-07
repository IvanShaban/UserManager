package com.epam.usermanager.entity;

public class Device {
    private static int count;
    private int id;
    private String nameOfDevice;
    private Type type;
    private User owner;

    public Device(int id, String nameOfDevice, Type type, User user) {
        this.id = id;
        this.nameOfDevice = nameOfDevice;
        this.type = type;
        if (user != null) {
            this.owner = user;
        }
        count++;
    }

    public int getId() {
        return id;
    }

    public String getNameOfDevice() {
        return nameOfDevice;
    }

    public void setNameOfDevice(String nameOfDevice) {
        this.nameOfDevice = nameOfDevice;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Девайс: " +
                "id девайса = '" + id + '\'' +
                ";название = '" + nameOfDevice + '\'' +
                "; тип = '" + type + '\'';
    }
}
