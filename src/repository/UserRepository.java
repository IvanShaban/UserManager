package repository;

import entity.Sex;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<Integer, User> userRepository = new HashMap<>();

    public User insertRepository(String name, Sex sex, String favoritePorn) {
        User user = new User(name, sex, favoritePorn);
        userRepository.put(user.getId(), user);
        return user;
    }

    public User findById(int id) throws UserNotFoundException {
        if (!userRepository.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден.");
        }
        return userRepository.get(id);
    }

    public User findByName(String name) {
        for (Map.Entry<Integer, User> pair : userRepository.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                return userRepository.get(pair.getKey());
            }
            else {
                throw new UserNotFoundException("Пользователь не найден.");
            }
        }
        return null;
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
}
