// src/main/java/dao/DaoViolacionUI.java
package dao;

// import directo a UI → violación “DAO → UI”
import ui.UserView;

public class DaoViolacionUI {
    public void fallo() {
        new UserView().showUser("nico");
    }
}