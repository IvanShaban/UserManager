package com.epam.usermanager.repository;

import com.epam.usermanager.entity.Sex;
import com.epam.usermanager.entity.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static Map<Integer, User> userRepository = new HashMap<>();
    //statement.executeUpdate("CREATE DATABASE IF NOT EXISTS UserManager");
    //statement.executeUpdate("CREATE TABLE users (
    //  id int NOT NULL AUTO_INCREMENT,
    //  name VARCHAR(30) NOT NULL,
    //  sex VARCHAR(6) NOT NULL,
    //  favoritePorn VARCHAR(30) NOT NULL,
    //  PRIMARY KEY (id))");

    public User insertRepository(String name, Sex sex, String favoritePorn) {
        User user = new User(name, sex, favoritePorn);
        //statement.executeUpdate("INSERT INTO users (name, sex, favoritePorn)
        // VALUES (" + name + ", " + sex + ", " + favoritePorn + ")");
        userRepository.put(user.getId(), user);
        return user;
    }

    public User findById(int id) throws UserNotFoundException {
        if (!userRepository.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        //ResultSet resultSet = statement.executeQuery("SELECT * from users");
        //while (resultSet.next()) {
        //  if (resultSet.getInt(1) == id) {
        //      String name = resultSet.getString();
        //      User user = new User();
        //  }
        //}
        return userRepository.get(id);
    }

    public User findByName(String name) {
//        userRepository.values().forEach(user -> {
//            if (name.equals(user.getName())) {
//                System.out.println(user.getName());
//            }
//        });
          return userRepository.values().stream()
                  .filter(user -> name.equals(user.getName()))
                  .findFirst().get();
//        for (Map.Entry<Integer, User> pair : userRepository.entrySet()) {
//            if (pair.getValue().getName().equals(name)) {
//                return userRepository.get(pair.getKey());
//            }
//        }
//        return null;
    }

    public void deleteById(int id) {
        if (userRepository.containsKey(id)) {
            userRepository.remove(id);
        }
    }

    public User updateById(int id, String name, Sex sex, String favoritePorn) {
        User user = userRepository.get(id);
        user.setName(name);
        user.setSex(sex);
        user.setFavoritePorn(favoritePorn);
        return user;
    }

    public void connectionToDataBase() throws SQLException, ClassNotFoundException {
        String USERNAME = "root";
        String PASSWORD = "root";
        String URL = "jdbc:mysql://localhost:3306/mydbtest?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); //TODO return connection

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
            Statement statement = connection.createStatement();
            //statement.executeUpdate("insert into users (name, age, email) values('Ivan', 26, 'ivanshaban@yandex.ru');");
            //statement.executeUpdate("create TABLE IF NOT EXISTS Books(id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
            //        "name CHAR(30) NOT NULL, PRIMARY KEY(id))");
            ResultSet resultSet = statement.executeQuery("select * from users");
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
                    "    FOREIGN KEY (userId)  REFERENCES users (id)\n" +
                    ")");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getInt(3));
//                System.out.println(resultSet.getString(4));
//                System.out.println("________________________");
//            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
