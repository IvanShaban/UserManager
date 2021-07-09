package com.epam.usermanager.repository;

import com.epam.usermanager.entity.Device;
import com.epam.usermanager.entity.Type;
import com.epam.usermanager.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeviceRepository {
    private static Map<Integer, Device> deviceRepository = new HashMap<>();

    public Map<Integer, Device> getDeviceRepository() {
        return deviceRepository;
    }

    public Device insert(String nameOfDevice, Type type, User owner) {
        Device device = new Device(nameOfDevice, type, owner);
        deviceRepository.put(device.getId(), device);
        return device;
    }

    public Device findByNameOfDevice(String nameOfDevice) {
        for (Map.Entry<Integer, Device> pair : deviceRepository.entrySet()) {
            if (pair.getValue().getNameOfDevice().equals(nameOfDevice)) {
                return deviceRepository.get(pair.getKey());
            }
        }
        return null;
    }

    public List<Device> findByTypeAndOwner(Type type, Boolean hasOwner) {
        return deviceRepository.values().stream()
                .filter(device -> type == device.getType())
                .filter(device -> hasOwner.equals(device.getOwner() != null))
                .collect(Collectors.toList());
    }

    public Device updateById(int id, String nameOfDevice, Type typeOfDevice, User owner) {
        Device device = deviceRepository.get(id);
        device.setNameOfDevice(nameOfDevice);
        device.setType(typeOfDevice);
        device.setOwner(owner);
        return device;
    }

    public void deleteById(int id) {
        if (deviceRepository.containsKey(id)) {
            deviceRepository.remove(id);
        }
    }

    public List<Device> getUserDevices(User owner) {
        return deviceRepository.values().stream()
                .filter(device -> owner.equals(device.getOwner()))
                .collect(Collectors.toList());
//        List<Device> listOfDevices = new ArrayList<>();
//        for (Map.Entry<Integer, Device> pair : deviceRepository.entrySet()) {
//            if (pair.getValue().getOwner().equals(owner)) {
//                listOfDevices.add(pair.getValue());
//            }
//        }
//        return listOfDevices;
    }

    public List<Device> getUserDevices() {
        return deviceRepository.values().stream()
                .filter(device -> null == device.getOwner())
                .collect(Collectors.toList());
//        List<Device> listOfDevices = new ArrayList<>();
//        for (Map.Entry<Integer, Device> pair : deviceRepository.entrySet()) {
//            if (pair.getValue().getOwner() == null) {
//                listOfDevices.add(pair.getValue());
//            }
//        }
//        return listOfDevices;
    }
}
