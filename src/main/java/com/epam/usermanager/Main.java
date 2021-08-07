package com.epam.usermanager;

import com.epam.usermanager.repository.UserNotFoundException;
import com.epam.usermanager.service.StartService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws UserNotFoundException, IOException, SQLException, ClassNotFoundException {
        System.out.println("Hello there! It's UserManager.");
        StartService startService = new StartService();
        startService.startService();
    }
}
