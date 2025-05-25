package ui;

import service.UserService;

// UI → Service (OK), no UI → DAO
public class UiCorreccion {
    public void ejecuta() {
        new UserService().registerUser("Nico");
    }
}