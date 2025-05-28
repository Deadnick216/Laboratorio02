package ui;

import dao.ConnectionManager;
import service.UserService;

public class UserView {
    private final UserService service = new UserService();

    public void showUser(String name) {
        
        service.getUserInfo(name);

        // Violation: UI → DAO
        new ConnectionManager().connect();
    }
}
