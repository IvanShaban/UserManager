package com.epam.usermanager.service;

import com.epam.usermanager.entity.Device;
import com.epam.usermanager.entity.Type;
import com.epam.usermanager.entity.User;
import com.epam.usermanager.repository.DeviceRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DeviceService {
    private DeviceRepository deviceRepository = new DeviceRepository();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private UserService userService = new UserService();

    public void startDeviceService() throws IOException {
        System.out.println("Работа с девайсами.");
        System.out.println("Выберите дальнейшие действия:");
        System.out.println("1. Сохранить новый девайс.");
        System.out.println("2. Обновить информацию о девайсе.");
        System.out.println("3. Удалить девайс.");
        System.out.println("4. Найти девайсы владельца.");
        System.out.println("5. Найти свободные девайсы и назначить владельца.");
        System.out.println("6. Вернуться назад.");

        int actionNumber = Integer.parseInt(reader.readLine());

        switch (actionNumber) {
            case 1:
                createDevice();
                break;
            case 2:
                updateDevice();
                break;
            case 3:
                deleteDevice();
                break;
            case 4:
                User owner = userService.findUser();
                findDevices(owner);
                break;
            case 5:
                findDevices(null);
                break;
            case 6:
                StartService startService = new StartService();
                startService.startService();
                break;
            default:
                System.out.println("Все хуйня Миша, давай по новой!");
                System.out.println();
                startDeviceService();
                break;
        }
        startDeviceService();
    }

    public void createDevice() throws IOException {
        System.out.println("Сохранение нового девайса.");
        System.out.println("Введите название девайса.");

        String nameOfDevice = reader.readLine();
        if (nameOfDevice.equals("")) {
            System.out.println("Все хуйня Миша, давай по новой!");
            createDevice();
        }

        System.out.println("Выберите тип девайса.");
        System.out.println("1. Телефон.");
        System.out.println("2. Ноутбук.");
        System.out.println("3. Монитор.");
        int actionNumber = Integer.parseInt(reader.readLine());
        String typeOfDevice = "PHONE";
        if (actionNumber == 1) {
            typeOfDevice = "PHONE";
        } else if (actionNumber == 2) {
            typeOfDevice = "LAPTOP";
        } else if (actionNumber == 3) {
            typeOfDevice = "MONITOR";
        } else {
            System.out.println("Все хуйня Миша, давай по новой!");
            createDevice();
        }

        System.out.println("У девайса есть владелец?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        actionNumber = Integer.parseInt(reader.readLine());
        User owner = null;
        if (actionNumber == 1) {
            owner = userService.findUser();
            System.out.println("Новый владелец девайса - " + owner.getName());
        } else if (actionNumber == 2) {
            System.out.println("Девайс сохранен");
        } else {
            System.out.println("Все хуйня Миша, давай по новой!");
            createDevice();
        }
        Device device = deviceRepository.insert(nameOfDevice, Type.valueOf(typeOfDevice), owner);
        System.out.println(device);
    }

    public void updateDevice() throws IOException {
        System.out.println("Обновление информации о девайсе.");
        System.out.println("Введите название девайса.");
        String nameOfDevice = reader.readLine();
        Device device = deviceRepository.findByNameOfDevice(nameOfDevice);
        System.out.println(device);

        if (nameOfDevice.equals("")) {
            System.out.println("Все хуйня Миша, давай по новой!");
            updateDevice();
        }

        System.out.println("Выберите тип девайса.");
        System.out.println("1. Телефон.");
        System.out.println("2. Ноутбук.");
        System.out.println("3. Монитор.");
        int actionNumber = Integer.parseInt(reader.readLine());
        String typeOfDevice = "PHONE";
        if (actionNumber == 1) {
            typeOfDevice = "PHONE";
        } else if (actionNumber == 2) {
            typeOfDevice = "LAPTOP";
        } else if (actionNumber == 3) {
            typeOfDevice = "MONITOR";
        } else {
            System.out.println("Все хуйня Миша, давай по новой!");
            updateDevice();
        }

        System.out.println("У девайса есть владелец?");
        System.out.println("1. Да.");
        System.out.println("2. Нет.");
        actionNumber = Integer.parseInt(reader.readLine());
        User owner = null;
        if (actionNumber == 1) {
            owner = userService.findUser();
            System.out.println("Новый владелец девайса - " + owner.getName());
        } else if (actionNumber == 2) {
            System.out.println("Девайс сохранен");
        } else {
            System.out.println("Все хуйня Миша, давай по новой!");
            updateDevice();
        }
        device = deviceRepository.updateById(device.getId(), nameOfDevice, Type.valueOf(typeOfDevice), owner);
        System.out.println("Девайс обновлен");
        System.out.println(device);
        System.out.println();
    }

    public void deleteDevice() throws IOException {
        System.out.println("Удаление девайса.");
        System.out.println("Введите id девайса.");
        int id = Integer.parseInt(reader.readLine());
        deviceRepository.deleteById(id);
        System.out.println("Девайс удален.");
        System.out.println();
    }

    public void findDevices(User owner) throws IOException {
        List<Device> listOfDevices;
        if (owner == null) {
            listOfDevices = deviceRepository.getUserDevices();
        } else {
            listOfDevices = deviceRepository.getUserDevices(owner);
        }
        if (!listOfDevices.isEmpty()) {
            for (Device device : listOfDevices) {
                System.out.println(device);
            }
            System.out.println("Хотите назначить девайсу нового владельца?");
            System.out.println("1. Да.");
            System.out.println("2. Нет.");
            int actionNumber = Integer.parseInt(reader.readLine());
            appointNewOwner(actionNumber);
        } else {
            System.out.println("У этого пользователя нет девайсов.");
        }
    }

    public Device appointNewOwner(int actionNumber) throws IOException {
        if (actionNumber == 2) {
            return null;
        }
        System.out.println("Выберите id девайса.");
        int idOfDevice = Integer.parseInt(reader.readLine());
        Device device = deviceRepository.getDeviceRepository().get(idOfDevice);
        User owner = userService.findUser();
        String nameOfDevice = device.getNameOfDevice();
        Type typeOfDevice = device.getType();
        device = deviceRepository.updateById(idOfDevice, nameOfDevice, typeOfDevice, owner);
        System.out.println("Девайс обновлен. Владелец девайса - " + owner.getName());
        System.out.println(device);
        System.out.println();
        return device;
    }
}
