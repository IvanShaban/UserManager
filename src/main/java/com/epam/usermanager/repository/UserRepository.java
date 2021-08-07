package com.epam.usermanager.repository;

import com.epam.usermanager.entity.Sex;
import com.epam.usermanager.entity.User;

import java.sql.*;

public class UserRepository {
//    private static Map<Integer, User> userRepository = new HashMap<>();
    String USERNAME = "root";
    String PASSWORD = "root";
    String URL = "jdbc:mysql://localhost:3306/usermanager";

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
                    "    FOREIGN KEY (userId)  REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ")");
        }
    }

    public User insertRepository(String name, Sex sex, String favoritePorn) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        createTables();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users (name, sex, favoritePorn) " +
                    "VALUES ('" + name + "', '" + sex + "', '" + favoritePorn + "')");
            ResultSet resultSet = statement.executeQuery("SELECT id from users where id = LAST_INSERT_ID()");
            resultSet.next();
            int id = resultSet.getInt(1);
            return new User(id, name, sex, favoritePorn);
        }

//        User user = new User(name, sex, favoritePorn);
//        userRepository.put(user.getId(), user);
//        return user;
    }

    public User findById(int id) throws UserNotFoundException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from users where id = " + id);
            resultSet.next();
            String name = resultSet.getString(2);
            Sex sex = Sex.valueOf(resultSet.getString(3));
            String favoritePorn = resultSet.getString(4);
            return new User(id, name, sex, favoritePorn);
        } catch (SQLException e) {
            return null;
        }

//        if (!userRepository.containsKey(id)) {
//            throw new UserNotFoundException("Пользователь не найден.");
//        } else {
//            return userRepository.get(id);
//        }
    }

    public User findByName(String name) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from users where name = '" + name + "'");
            resultSet.next();
            int id = resultSet.getInt(1);
            Sex sex = Sex.valueOf(resultSet.getString(3));
            String favoritePorn = resultSet.getString(4);
            return new User(id, name, sex, favoritePorn);
        }

//        return userRepository.values().stream()
//                .filter(user -> name.equals(user.getName()))
//                .findFirst().get();

//        userRepository.values().forEach(user -> {
//            if (name.equals(user.getName())) {
//                System.out.println(user.getName());
//            }
//        });
//        for (Map.Entry<Integer, User> pair : userRepository.entrySet()) {
//            if (pair.getValue().getName().equals(name)) {
//                return userRepository.get(pair.getKey());
//            }
//        }
//        return null;
    }

    public void deleteById(int id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("delete from users where id = " + id);
        }

//        if (userRepository.containsKey(id)) {
//            userRepository.remove(id);
//        }
    }

    public User updateById(int id, String name, Sex sex, String favoritePorn) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("update users set name = '" + name + "', sex = '" + sex +
                    "', favoritePorn = '" + favoritePorn + "' where id = " + id);
            return new User(id, name, sex, favoritePorn);
        }
//        User user = userRepository.get(id);
//        user.setName(name);
//        user.setSex(sex);
//        user.setFavoritePorn(favoritePorn);
//        return user;
    }
}
