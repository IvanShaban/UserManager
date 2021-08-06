package com.epam.usermanager.repository;

import com.epam.usermanager.entity.Device;
import com.epam.usermanager.entity.Type;
import com.epam.usermanager.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeviceRepository {
    String USERNAME = "root";
    String PASSWORD = "root";
    String URL = "jdbc:mysql://localhost:3306/usermanager";

    private static Map<Integer, Device> deviceRepository = new HashMap<>();
    private static UserRepository userRepository = new UserRepository();

    public Map<Integer, Device> getDeviceRepository() {
        return deviceRepository;
    }

    public void createTables() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    "    name VARCHAR(30) NOT NULL,\n" +
                    "    sex VARCHAR(6) NOT NULL,\n" +
                    "    favoritePorn VARCHAR(30) NOT NULL\n" +
                    ")");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS devices (\n" +
                    "    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,\n" +
                    "    name VARCHAR(30) NOT NULL,\n" +
                    "    type VARCHAR(7) NOT NULL,\n" +
                    "    userId int,\n" +
                    "    FOREIGN KEY (userId)  REFERENCES users (id)\n" +
                    ")");
        }
    }

    public Device insert(String nameOfDevice, Type type, User ownerOfDevice) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        createTables();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            if (null == ownerOfDevice) {
                statement.executeUpdate("INSERT INTO devices (name, type) " +
                        "VALUES ('" + nameOfDevice + "', '" + type + "')");
            } else {
                statement.executeUpdate("INSERT INTO devices (name, type, userId) " +
                        "VALUES ('" + nameOfDevice + "', '" + type + "', '" + ownerOfDevice.getId() + "')");
            }
            ResultSet resultSet = statement.executeQuery("SELECT id from devices where id = LAST_INSERT_ID()");
            resultSet.next();
            int id = resultSet.getInt(1);
            return new Device(id, nameOfDevice, type, ownerOfDevice);
        }

//        Device device = new Device(nameOfDevice, type, owner);
//        deviceRepository.put(device.getId(), device);
//        return device;
    }

    public Device findByNameOfDevice(String nameOfDevice) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from devices where name = '" + nameOfDevice + "'");
            resultSet.next();
            int id = resultSet.getInt(1);
            Type type = Type.valueOf(resultSet.getString(3));
            User owner = userRepository.findById(resultSet.getInt(4));
            return new Device(id, nameOfDevice, type, owner);
        }

//        return deviceRepository.values().stream()
//                .filter(device -> nameOfDevice.equals(device.getNameOfDevice()))
//                .findFirst().get();

//        for (Map.Entry<Integer, Device> pair : deviceRepository.entrySet()) {
//            if (pair.getValue().getNameOfDevice().equals(nameOfDevice)) {
//                return deviceRepository.get(pair.getKey());
//            }
//        }
//        return null;
    }

    public Device findByIdOfDevice(int idOfDevice) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from devices where id = '" + idOfDevice + "'");
            resultSet.next();
            String nameOfDevice = resultSet.getString(2);
            Type type = Type.valueOf(resultSet.getString(3));
            User owner = userRepository.findById(resultSet.getInt(4));
            return new Device(idOfDevice, nameOfDevice, type, owner);
        }
    }

    public List<Device> findByTypeAndOwner(Type type, Boolean hasOwner) {
        return deviceRepository.values().stream()
                .filter(device -> type == device.getType())
                .filter(device -> hasOwner.equals(device.getOwner() != null))
                .collect(Collectors.toList());
    }

    public Device updateById(int id, String nameOfDevice, Type typeOfDevice, User ownerOfDevice) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            if (null == ownerOfDevice) {
                statement.executeUpdate("UPDATE devices SET name = '" + nameOfDevice + "', type = '" + typeOfDevice +
                        "' where id = " + id);
            } else {
                statement.executeUpdate("UPDATE devices SET name = '" + nameOfDevice + "', type = '" + typeOfDevice +
                        "', userId = '" + ownerOfDevice.getId() + "' where id = " + id);
            }
            return new Device(id, nameOfDevice, typeOfDevice, ownerOfDevice);
        }

//        Device device = deviceRepository.get(id);
//        device.setNameOfDevice(nameOfDevice);
//        device.setType(typeOfDevice);
//        device.setOwner(owner);
//        return device;
    }

    public void deleteById(int id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from devices where id = " + id);
        }

//        if (deviceRepository.containsKey(id)) {
//            deviceRepository.remove(id);
//        }
    }

    public List<Device> getUserDevices(User owner) throws ClassNotFoundException, SQLException {
        List<Device> listOfDevices = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from devices where userId = '" + owner.getId() + "'");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String nameOfDevice = resultSet.getString(2);
                Type type = Type.valueOf(resultSet.getString(3));
                Device device = new Device(id, nameOfDevice, type, owner);
                listOfDevices.add(device);
            }
            return listOfDevices;
        }
//            return deviceRepository.values().stream()
//                    .filter(device -> owner.equals(device.getOwner()))
//                    .collect(Collectors.toList());

//        List<Device> listOfDevices = new ArrayList<>();
//        for (Map.Entry<Integer, Device> pair : deviceRepository.entrySet()) {
//            if (pair.getValue().getOwner().equals(owner)) {
//                listOfDevices.add(pair.getValue());
//            }
//        }
//        return listOfDevices;
    }

    public List<Device> getUserDevices() throws ClassNotFoundException, SQLException {
        List<Device> listOfDevices = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from devices where userId IS NULL");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String nameOfDevice = resultSet.getString(2);
                Type type = Type.valueOf(resultSet.getString(3));
                Device device = new Device(id, nameOfDevice, type, null);
                listOfDevices.add(device);
            }
            return listOfDevices;
        }

//        return deviceRepository.values().stream()
//                .filter(device -> null == device.getOwner())
//                .collect(Collectors.toList());

//        List<Device> listOfDevices = new ArrayList<>();
//        for (Map.Entry<Integer, Device> pair : deviceRepository.entrySet()) {
//            if (pair.getValue().getOwner() == null) {
//                listOfDevices.add(pair.getValue());
//            }
//        }
//        return listOfDevices;
    }
}
