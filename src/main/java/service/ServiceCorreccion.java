package service;

import dao.UserRepository;


public class ServiceCorreccion {
    public void ejecuta() {
        new UserRepository().save("Anto");
    }
}
// // Service → UI (OK), no Service → DAO