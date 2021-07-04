package com.epam.usermanager.service;

import com.epam.usermanager.entity.Sex;
import com.epam.usermanager.entity.User;
import com.epam.usermanager.repository.UserNotFoundException;
import com.epam.usermanager.repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void startUserService() throws IOException, UserNotFoundException {
        System.out.println("Работа с пользователями.");
        System.out.println("Выберите дальнейшие действия:");
        System.out.println("1. Сохранить нового пользователя.");
        System.out.println("2. Найти пользователя по id или имени.");
        System.out.println("3. Удалить пользователя по id.");
        System.out.println("4. Обновить пользователя по id.");
        System.out.println("5. Вернуться назад.");

        int actionNumber = Integer.parseInt(reader.readLine());

        switch (actionNumber) {
            case 1:
                createUser();
                break;
            case 2:
                findUser();
                break;
            case 3:
                deleteUser();
                break;
            case 4:
                updateUser();
                break;
            case 5:
                StartService startService = new StartService();
                startService.startService();
                break;
            default:
                System.out.println("Все хуйня Миша, давай по новой!");
                System.out.println();
                startUserService();
                break;
        }
        startUserService();
    }

    public void createUser() throws IOException {
        System.out.println("Сохранение нового пользователя.");
        System.out.println("Введите имя.");
        String name = reader.readLine();
        if (name.equals("")) {
            System.out.println("Все хуйня Миша, давай по новой!");
            createUser();
        }
        System.out.println("Выберите пол :");
        System.out.println("1 - мужской, 2 - женский.");
        String sex = "MALE";
        int value = Integer.parseInt(reader.readLine());
        if (value == 1) {
            sex = "MALE";
        }
        else if (value == 2) {
            sex = "FEMALE";
        }
        else {
            System.out.println("Все хуйня Миша, давай по новой!");
            createUser();
        }

        System.out.println("Введите любимое порно.");
        String favoritePorn = reader.readLine();
        if (favoritePorn.equals("")) {
            System.out.println("Все хуйня Миша, давай по новой!");
            createUser();
        }
        User user = userRepository.insertRepository(name, Sex.valueOf(sex), favoritePorn);
        System.out.println(user);
        System.out.println();
    }

    public User findUser() throws IOException, UserNotFoundException {
        System.out.println("Поиск пользователя по id или по имени.");
        System.out.println("Выберите вид поиска:");
        System.out.println("1 - поиск по id, 2 - поиск по имени.");
        User user = null;

        int value = Integer.parseInt(reader.readLine());
        if (value == 1){
            System.out.println("Поиск по id.");
            System.out.println("Введите id.");
            int id = Integer.parseInt(reader.readLine());
            user = userRepository.findById(id);
            System.out.println(user);
            System.out.println();
        }
        else if (value == 2){
            System.out.println("Поиск по имени.");
            System.out.println("Введите имя.");
            String name = reader.readLine();
            user = userRepository.findByName(name);
            System.out.println(user);
            System.out.println();
        }
        else {
            System.out.println("Введены неправильные данные. Попробуйте снова.");
            findUser();
        }
        return user;
    }

    public void deleteUser() throws IOException {
        System.out.println("Удаление пользователя.");
        System.out.println("Введите id.");
        int id = Integer.parseInt(reader.readLine());
        userRepository.deleteById(id);
        System.out.println("Пользователь удален.");
        System.out.println();
    }

    public void updateUser() throws IOException {
        System.out.println("Обновление пользователя.");
        System.out.println("Введите id.");
        int id = Integer.parseInt(reader.readLine());

        System.out.println("Введите имя.");
        String name = reader.readLine();

        System.out.println("Выберите пол :");
        System.out.println("1 - мужской, 2 - женский.");
        String sex = "";
        int value = Integer.parseInt(reader.readLine());
        if (value == 1) {
            sex = "MALE";
        }
        else if (value == 2) {
            sex = "FEMALE";
        }
        else {
            System.out.println("Все хуйня Миша, давай по новой!");
            updateUser();
        }
        System.out.println("Введите любимое порно.");
        String favoritePorn = reader.readLine();

        User user = userRepository.updateById(id, name, Sex.valueOf(sex), favoritePorn);
        System.out.println("Пользователь обновлен.");
        System.out.println(user);
        System.out.println();
    }
}
