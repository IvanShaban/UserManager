package service;

import entity.Sex;
import entity.User;
import repository.UserNotFoundException;
import repository.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserService {
    public static void getMethod() throws IOException, UserNotFoundException {
        System.out.println("Выберите дальнейшие действия:");
        System.out.println("1. Сохранить нового пользователя.");
        System.out.println("2. Найти пользователя по id или о имени и вывести на экран.");
        System.out.println("3. Удалить пользователя по id.");
        System.out.println("4. Обновить пользователя по id.");
        System.out.println("5. Завершить программу.");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
                System.out.println("До новых встреч.");
        }

        if (actionNumber != 5) {
            getMethod();
        }
    }

    public static void createUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Сохранение нового пользователя.");
        System.out.println("Введите имя.");
        String name = reader.readLine();

        System.out.println("Выберите пол :");
        System.out.println("1 - мужской, 2 - женский.");
        String sex;
        int value = Integer.parseInt(reader.readLine());
        if (value == 1)
            sex = "MALE";
        else
            sex = "FEMALE";

        System.out.println("Введите любимое порно.");
        String favoritePorn = reader.readLine();

        UserRepository.addOnRepository(name, Sex.valueOf(sex), favoritePorn);
    }

    public static void findUser() throws IOException, UserNotFoundException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Поиск пользователя по id или по имени и вывод на экран.");
        System.out.println("Выберите вид поиска:");
        System.out.println("1 - поиск по id, 2 - поиск по имени.");
        int value = Integer.parseInt(reader.readLine());
        if (value == 1){
            System.out.println("Поиск по id.");
            System.out.println("Введите id.");
            int id = Integer.parseInt(reader.readLine());
            UserRepository.findById(id);
        }
        else if (value == 2){
            System.out.println("Поиск по имени.");
            System.out.println("Введите имя.");
            String name = reader.readLine();
            UserRepository.findByName(name);
        }
        else {
            System.out.println("Введены неправильные данные. Попробуйте снова.");
            findUser();
        }
    }

    public static void deleteUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Удаление пользователя.");
        System.out.println("Введите id.");
        int id = Integer.parseInt(reader.readLine());
        UserRepository.deleteById(id);
    }

    public static void updateUser() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Обновление пользователя.");
        System.out.println("Введите id.");
        int id = Integer.parseInt(reader.readLine());

        System.out.println("Введите имя.");
        String name = reader.readLine();

        System.out.println("Выберите пол :");
        System.out.println("1 - мужской, 2 - женский.");
        String sex;
        int value = Integer.parseInt(reader.readLine());
        if (value == 1)
            sex = "MALE";
        else
            sex = "FEMALE";

        System.out.println("Введите любимое порно.");
        String favoritePorn = reader.readLine();
        UserRepository.updateById(id, name, Sex.valueOf(sex), favoritePorn);
    }

}
