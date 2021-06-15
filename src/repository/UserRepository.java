package repository;


import entity.Sex;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static Map<Integer, User> userRepository = new HashMap<>();

    public static void addOnRepository(String name, Sex sex, String favoritePorn) {
        User user = new User(name, sex, favoritePorn);
        userRepository.put(user.getId(), user);
        System.out.println(user);
        System.out.println();
    }

    public static User findById(int id) throws UserNotFoundException {
        User user = userRepository.get(id);
        if (!userRepository.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        System.out.println(user);
        System.out.println();
        return user;
    }

    public static void findByName(String name) {
        for (Map.Entry<Integer, User> pair : userRepository.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                User user = userRepository.get(pair.getKey());
                System.out.println(user);
                System.out.println();
            }
        }
    }

    public static void deleteById(int id) {
        if (!userRepository.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        userRepository.remove(id);

        System.out.println("Пользователь удален.");
        System.out.println();
    }

    public static void updateById(int id, String name, Sex sex, String favoritePorn) {
        userRepository.get(id).setName(name);
        userRepository.get(id).setSex(sex);
        userRepository.get(id).setFavoritePorn(favoritePorn);
        System.out.println("Пользователь обновлен.");
        System.out.println(userRepository.get(id));
    }
}
