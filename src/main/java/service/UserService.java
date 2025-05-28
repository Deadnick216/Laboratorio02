// src/main/java/service/UserService.java
package service;

import dao.UserRepository;
import ui.LoginController;

public class UserService {
    private final UserRepository repository = new UserRepository();
    // usamos el controlador de UI para violar la regla
    private final LoginController loginController = new LoginController();

    public void registerUser(String name) {
        repository.save(name);
    }

    public void getUserInfo(String name) {
        // Violation: Service → UI
        loginController.login(name, "pwd");
        System.out.println("User info for: " + name);
    }
}
