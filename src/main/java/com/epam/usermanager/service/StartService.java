package com.epam.usermanager.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartService {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private UserService userService = new UserService();
    private DeviceService deviceService = new DeviceService();

    public void startService() throws IOException {
        System.out.println("Выберите дальнейшие действия:");
        System.out.println("1. Работа с пользователями.");
        System.out.println("2. Работа с девайсами.");
        System.out.println("3. Завершение работы.");

        int actionNumber = Integer.parseInt(reader.readLine());
        switch (actionNumber) {
            case 1:
                userService.startUserService();
                break;
            case 2:
                deviceService.startDeviceService();
                break;
            case 3:
                System.out.println("До новых встреч.");
                System.exit(0);
            default:
                System.out.println("Все хуйня Миша, давай по новой!");
                System.out.println();
                startService();
                break;
        }
        startService();
    }
}
