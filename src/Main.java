import repository.UserNotFoundException;
import service.UserService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws UserNotFoundException, IOException {
        System.out.println("Hello there! It's UserManager");
        UserService.getMethod();
    }
}
